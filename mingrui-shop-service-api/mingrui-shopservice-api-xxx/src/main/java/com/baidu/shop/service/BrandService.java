package com.baidu.shop.service;

import com.baidu.shop.base.Result;
import com.baidu.shop.dto.BrandDto;
import com.baidu.shop.entity.BrandEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Api(tags = "品牌接口")
public interface BrandService {

    @GetMapping(value = "brand/getBrandInfo")
    @ApiOperation(value = "查询品牌列表")
    Result<List<BrandEntity>>getBrandInfo(BrandDto brandDto);
}

