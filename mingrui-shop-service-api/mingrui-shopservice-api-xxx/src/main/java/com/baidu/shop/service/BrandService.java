package com.baidu.shop.service;

import com.baidu.shop.base.Result;
import com.baidu.shop.dto.BrandDto;
import com.baidu.shop.entity.BrandEntity;
import com.baidu.shop.validata.group.MingruiOperation;
import com.google.gson.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Api(tags = "品牌接口")
public interface BrandService {

    @GetMapping(value = "brand/getBrandInfo")
    @ApiOperation(value = "查询品牌列表")
    Result<List<BrandEntity>>getBrandInfo(BrandDto brandDto);

    @PostMapping (value = "brand/save")
    @ApiOperation(value = "新增品牌列表")
    Result<JsonObject>saveBrandInfo(@Validated({MingruiOperation.Add.class}) @RequestBody BrandDto brandDto);
}

