package com.baidu.shop.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baidu.shop.utils.BaiDuBeanUtil;
import com.baidu.shop.base.BaseApiService;
import com.baidu.shop.base.Result;
import com.baidu.shop.dto.BrandDto;
import com.baidu.shop.entity.BrandEntity;
import com.baidu.shop.entity.CategoryBrandEntity;
import com.baidu.shop.mapper.BrandMapper;
import com.baidu.shop.mapper.CategoryBrandMapper;
import com.baidu.shop.service.BrandService;
import com.baidu.shop.utils.PinyinUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.JsonObject;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName BrandServiceImpl
 * @Description: TODO
 * @Author wangyue
 * @Date 2021/1/22
 * @Version V1.0
 **/
@RestController
public class BrandServiceImpl extends BaseApiService implements BrandService {
    @Resource
    private BrandMapper brandMapper;

    @Resource
    private CategoryBrandMapper categoryBrandMapper;


    //品牌查询
    @Override
    public Result<List<BrandEntity>> getBrandInfo(BrandDto brandDto) {
        //调用分页工具类
        PageHelper.startPage(brandDto.getPage(),brandDto.getRows());
        //判断
        if (!StringUtils.isEmpty(brandDto.getSort()))PageHelper.orderBy(brandDto.getOrder());

        BrandEntity brandEntity = BaiDuBeanUtil.copyProperties(brandDto,BrandEntity.class);

        Example example = new Example(BrandEntity.class);
        example.createCriteria().andLike("name","%"+brandEntity.getName()+"%");

        List <BrandEntity> brandEntities = brandMapper.selectByExample(example);
        PageInfo <BrandEntity> pageInfo = new PageInfo<>(brandEntities);

        return this.setResultSuccess(pageInfo);
    }

    //品牌新增
    @Transactional
    @Override
    public Result<JsonObject> saveBrandInfo(BrandDto brandDto) {
        BrandEntity brandEntity = BaiDuBeanUtil.copyProperties(brandDto,BrandEntity.class);
        //处理品牌首字母
        brandEntity.setLetter(PinyinUtil.getUpperCase(String.valueOf(brandEntity.getName().toCharArray()[0]),false).toCharArray()[0]);

        brandMapper.insertSelective(brandEntity);

        //维护中间表数据
        this.insertCategoryBrandList(brandDto.getCategories(),brandEntity.getId());
        return this.setResultSuccess();
    }

    //修改
    @Transactional
    @Override
    public Result<JsonObject> editBrandInfo(BrandDto brandDto) {
        BrandEntity brandEntity = BaiDuBeanUtil.copyProperties(brandDto,BrandEntity.class);
        brandEntity.setLetter(PinyinUtil.getUpperCase(String.valueOf(brandEntity.getName().toCharArray()[0]),false).toCharArray()[0]);
        brandMapper.updateByPrimaryKeySelective(brandEntity);

        //通过brandId删除中间表的数据
        this.deleteCategoryBrandByBrandId(brandEntity.getId());

        this.insertCategoryBrandList(brandDto.getCategories(),brandEntity.getId());

        return this.setResultSuccess();
    }

    //删除
    @Transactional
    @Override
    public Result<JSONObject> deleteBrandInfo(Integer id) {
        //删除品牌
        brandMapper.deleteByPrimaryKey(id);
        //删除品牌关联的分类
        this.deleteCategoryBrandByBrandId(id);

        return this.setResultSuccess();
    }



    //封装批量新增
    private void insertCategoryBrandList(String categories,Integer brandId){
        // 自定义异常
        if(StringUtils.isEmpty(categories)) throw new RuntimeException("分类信息不能为空");

        //判断分类集合字符串中是否包含,
        if(categories.contains(",")){//多个分类 --> 批量新增

            categoryBrandMapper.insertList(
                    Arrays.asList(categories.split(","))
                            .stream()
                            .map(categoryIdStr -> new CategoryBrandEntity(Integer.valueOf(categoryIdStr)
                                    ,brandId))
                            .collect(Collectors.toList())
            );

        }else{//普通单个新增

            CategoryBrandEntity categoryBrandEntity = new CategoryBrandEntity();
            categoryBrandEntity.setBrandId(brandId);
            categoryBrandEntity.setCategoryId(Integer.valueOf(categories));

            categoryBrandMapper.insertSelective(categoryBrandEntity);
        }
    }

    //封装删除品牌关联分类
    private void deleteCategoryBrandByBrandId(Integer brandId){
        Example example = new Example(CategoryBrandEntity.class);
        example.createCriteria().andEqualTo("brandId",brandId);
        categoryBrandMapper.deleteByExample(example);
    }
}
