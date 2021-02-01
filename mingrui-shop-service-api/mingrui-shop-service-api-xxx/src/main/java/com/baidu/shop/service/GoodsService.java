package com.baidu.shop.service;

import com.alibaba.fastjson.JSONObject;
import com.baidu.shop.base.Result;
import com.baidu.shop.dto.SkuDto;
import com.baidu.shop.dto.SpuDto;
import com.baidu.shop.entity.SpuDetailEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "商品接口")
public interface GoodsService {

    @ApiOperation(value = "查询spu信息")
    @GetMapping(value = "/goods/getSpuInfo")
    Result<List<SpuDto>> getSpuInfo(SpuDto spuDTO);

    @ApiOperation(value = "新增商品")
    @PostMapping(value = "/goods/save")
    Result<JSONObject> saveGoods(@RequestBody SpuDto spuDTO);

    @ApiOperation(value = "通过spuId查询spudetail信息")
    @GetMapping(value = "/goods/getSpuDetailBySpuId")
    Result<SpuDetailEntity> getSpuDetailBySpuId(Integer spuId);

    @ApiOperation(value = "通过spuId查询sku信息")
    @GetMapping(value = "/goods/getSkusBySpuId")
    Result<List<SkuDto>> getSkusBySpuId(Integer spuId);

    @ApiOperation(value = "商品修改")
    @PutMapping(value = "/goods/save")
    Result<JSONObject> editGoods(@RequestBody SpuDto spuDTO);

    @ApiOperation(value = "商品删除")
    @DeleteMapping(value = "/goods/delete")
    Result<JSONObject> deleteGoods(Integer spuId);

    @ApiOperation(value = "商品状态")
    @PutMapping(value = "/goods/updateStauts")
    Result<JSONObject> updateStauts(@RequestBody SpuDto spuDTO);
}
