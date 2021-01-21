package com.baidu.shop.service;

import com.baidu.shop.base.Result;
import com.baidu.shop.entity.CategoryEntity;
import com.baidu.shop.validata.group.MingruiOperation;
import com.google.gson.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @ClassName CategoryService
 * @Description: TODO
 * @Author wangyue
 * @Date 2021/1/19
 * @Version V1.0
 **/
@Api(tags="商品分类接口")
@Validated
public interface CategoryService {

    @ApiOperation(value = "通过查询商品分类")
    @GetMapping(value = "category/list")
    Result<List<CategoryEntity>> getCategoryByPid(Integer pid);

    @ApiOperation(value = "删除商品分类")
    @DeleteMapping(value = "category/delete")
    Result<JsonObject> delCategory(Integer id);

    @ApiOperation(value = "修改商品分类")
    @PutMapping(value = "category/edit")
    Result<JsonObject> editCategory(@Validated({MingruiOperation.Update.class})@RequestBody CategoryEntity categoryEntity);

    @ApiOperation(value = "新增商品分类")
    @PostMapping(value = "category/add")
    Result<JsonObject>addCategory(@Validated({MingruiOperation.Add.class})@RequestBody CategoryEntity categoryEntity);
}
