package com.baidu.shop.service.impl;

import com.baidu.shop.base.BaiDuBeanUtil;
import com.baidu.shop.base.BaseApiService;
import com.baidu.shop.base.Result;
import com.baidu.shop.dto.BrandDto;
import com.baidu.shop.entity.BrandEntity;
import com.baidu.shop.mapper.BrandMapper;
import com.baidu.shop.service.BrandService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

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
}
