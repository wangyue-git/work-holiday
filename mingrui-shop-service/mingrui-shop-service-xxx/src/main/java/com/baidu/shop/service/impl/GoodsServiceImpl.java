package com.baidu.shop.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baidu.shop.dto.SkuDto;
import com.baidu.shop.dto.SpuDetailDto;
import com.baidu.shop.entity.*;
import com.baidu.shop.mapper.*;
import com.baidu.shop.utils.BaiDuBeanUtil;
import com.baidu.shop.base.BaseApiService;
import com.baidu.shop.base.Result;
import com.baidu.shop.dto.SpuDto;
import com.baidu.shop.service.GoodsService;
import com.baidu.shop.status.HTTPStatus;
import com.baidu.shop.utils.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName GoodsServiceImpl
 * @Description: TODO
 * @Author wangyue
 * @Date 2021/1/29
 * @Version V1.0
 **/

@RestController
public class GoodsServiceImpl extends BaseApiService implements GoodsService {
    @Resource
    private SpuMapper spuMapper;

    @Resource
    private BrandMapper brandMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private SpuDetailMapper spuDetailMapper;

    @Resource
    private SkuMapper skuMapper;

    @Resource
    private StockMapper stockMapper;

    //商品列表查询
    @Override
    public Result<List<SpuDto>> getSpuInfo(SpuDto spuDTO) {
        //分页 SpuDto继承了BaseDto
        if(ObjectUtil.isNotNull(spuDTO.getPage()) && ObjectUtil.isNotNull(spuDTO.getRows()))
            PageHelper.startPage(spuDTO.getPage(),spuDTO.getRows());
        //排序
        if(!StringUtils.isEmpty(spuDTO.getOrder()) && !StringUtils.isEmpty(spuDTO.getSort()))
            PageHelper.orderBy(spuDTO.getOrder());

        Example example = new Example(SpuEntity.class);
        Example.Criteria criteria = example.createCriteria();

        //判断一下是否上架 spuDTO数据传输
        if(ObjectUtil.isNotNull(spuDTO.getSaleable()) && spuDTO.getSaleable() < 2)
            criteria.andEqualTo("saleable",spuDTO.getSaleable());
        //搜索查询 模糊匹配
        if(!StringUtils.isEmpty(spuDTO.getTitle()))
            criteria.andLike("title","%" + spuDTO.getTitle() + "%");
        //查询sql
        List<SpuEntity> spuEntities = spuMapper.selectByExample(example);

        //商品分类和品牌传到前台是数字 需要转换一下
        List<SpuDto> spuDTOList = spuEntities.stream().map(spuEntity -> {
            SpuDto spuDTO1 = BaiDuBeanUtil.copyProperties(spuEntity, SpuDto.class);
            //通过分类id集合查询数据
            List<CategoryEntity>categoryEntities = categoryMapper.selectByIdList(Arrays.asList(spuEntity.getCid1(),spuEntity.getCid2(),spuEntity.getCid3()));

            String categoryName = categoryEntities.stream().map(categoryEntity -> categoryEntity.getName()).collect(Collectors.joining("/"));
            spuDTO1.setCategoryName(categoryName);

            BrandEntity brandEntity = brandMapper.selectByPrimaryKey(spuEntity.getBrandId());
            spuDTO1.setBrandName(brandEntity.getName());
            return spuDTO1;

        }).collect(Collectors.toList());

        //分页
        PageInfo<SpuEntity> spuEntityPageInfo = new PageInfo<>(spuEntities);

        return this.setResult(HTTPStatus.OK,spuEntityPageInfo.getTotal()+"",spuDTOList);
    }

    //新增
    @Transactional
    @Override
    public Result<JSONObject> saveGoods(SpuDto spuDTO) {
        final Date date = new Date();
        //新增spu,新增返回主键,给必要字段赋默认值
        SpuEntity spuEntity = BaiDuBeanUtil.copyProperties(spuDTO, SpuEntity.class);
        spuEntity.setSaleable(1);
        spuEntity.setValid(1);
        spuEntity.setCreateTime(date);
        spuEntity.setLastUpdateTime(date);
        spuMapper.insertSelective(spuEntity);

        //新增spuDetail
        SpuDetailDto spuDetail = spuDTO.getSpuDetail();
        SpuDetailEntity spuDetailEntity = BaiDuBeanUtil.copyProperties(spuDetail, SpuDetailEntity.class);
        spuDetailEntity.setSpuId(spuEntity.getId());
        spuDetailMapper.insertSelective(spuDetailEntity);

        //调用封装,新增sku list插入顺序有序 b,a set a,b treeSet b,a
        this.saveSkusAndStockInfo(spuDTO,spuEntity.getId(),date);
        return this.setResultSuccess();
    }

    @Override
    public Result<SpuDetailEntity> getSpuDetailBySpuId(Integer spuId) {
        SpuDetailEntity spuDetailEntity = spuDetailMapper.selectByPrimaryKey(spuId);
        return this.setResultSuccess(spuDetailEntity);
    }

    @Override
    public Result<List<SkuDto>> getSkusBySpuId(Integer spuId) {
        List<SkuDto> list = skuMapper.getSkusAndStockBySpuId(spuId);
        return this.setResultSuccess(list);
    }

    //修改
    @Transactional
    @Override
    public Result<JSONObject> editGoods(SpuDto spuDTO) {
        final Date date = new Date();
        //修改spu
        SpuEntity spuEntity = BaiDuBeanUtil.copyProperties(spuDTO,SpuEntity.class);
        spuEntity.setLastUpdateTime(date);
        spuMapper.updateByPrimaryKeySelective(spuEntity);

        //修改spuDetail
        spuDetailMapper.updateByPrimaryKeySelective(BaiDuBeanUtil.copyProperties(spuDTO.getSpuDetail(),SpuDetailEntity.class));

        //通过spuId查询sku信息,先删除后新增
        this.deleteSkusAndStock(spuEntity.getId());

        this.saveSkusAndStockInfo(spuDTO,spuEntity.getId(),date);

        return this.setResultSuccess();
    }

    //封装新增
    private void saveSkusAndStockInfo(SpuDto spuDTO,Integer spuId,Date date){
        List<SkuDto> skus = spuDTO.getSkus();
        skus.stream().forEach(skuDTO -> {
            SkuEntity skuEntity = BaiDuBeanUtil.copyProperties(skuDTO, SkuEntity.class);
            skuEntity.setSpuId(spuId);
            skuEntity.setCreateTime(date);
            skuEntity.setLastUpdateTime(date);
            skuMapper.insertSelective(skuEntity);

            //新增stock
            StockEntity stockEntity = new StockEntity();
            stockEntity.setSkuId(skuEntity.getId());
            stockEntity.setStock(skuDTO.getStock());
            stockMapper.insertSelective(stockEntity);
        });
    }

    //封装删除
    private void deleteSkusAndStock(Integer spuId){
        Example example = new Example(SkuEntity.class);
        example.createCriteria().andEqualTo("spuId",spuId);
        List<SkuEntity> skuEntities =skuMapper.selectByExample(example);
        //遍历得到sku集合
        List<Long> skuIdList =skuEntities.stream().map(skuEntity -> skuEntity.getId()).collect(Collectors.toList());
        //通过skuId集合删除信息
        skuMapper.deleteByIdList(skuIdList);
        //通过skuId集合删除stock信息
        stockMapper.deleteByIdList(skuIdList);

    }

}
