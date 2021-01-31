package com.baidu.shop.service;

import com.alibaba.fastjson.JSONObject;
import com.baidu.shop.base.Result;
import com.baidu.shop.dto.BrandDto;
import com.baidu.shop.entity.BrandEntity;
import com.baidu.shop.validata.group.MingruiOperation;
import com.google.gson.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "品牌接口")
public interface BrandService {

    @GetMapping(value = "brand/getBrandInfo")
    @ApiOperation(value = "查询品牌列表")
    Result<List<BrandEntity>>getBrandInfo(BrandDto brandDto);

    @PostMapping (value = "brand/save")
    @ApiOperation(value = "新增品牌")
    Result<JsonObject>saveBrandInfo(@Validated({MingruiOperation.Add.class}) @RequestBody BrandDto brandDto);

    @PutMapping(value = "brand/save")
    @ApiOperation(value = "修改品牌")
    Result<JsonObject>editBrandInfo(@Validated({MingruiOperation.Update.class}) @RequestBody BrandDto brandDto);

    @DeleteMapping(value = "brand/delete")
    @ApiOperation(value = "删除品牌")
    Result<JSONObject> deleteBrandInfo(Integer id);

    @GetMapping(value = "brand/getBrandInfoByCategoryId")
    @ApiOperation(value = "通过分类id查询品牌")
    Result<List<BrandEntity>> getBrandInfoByCategoryId(Integer cid);

}

