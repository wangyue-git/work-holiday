package com.baidu.shop.service.impl;

import com.baidu.shop.base.BaseApiService;
import com.baidu.shop.base.Result;
import com.baidu.shop.entity.CategoryEntity;
import com.baidu.shop.mapper.CategoryBrandMapper;
import com.baidu.shop.mapper.CategoryMapper;
import com.baidu.shop.service.CategoryService;
import com.baidu.shop.utils.ObjectUtil;
import com.google.gson.JsonObject;
import org.omg.CORBA.Object;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @ClassName CategoryServiceImpl
 * @Description: TODO
 * @Author wangyue
 * @Date 2021/1/19
 * @Version V1.0
 **/
@RestController
public class CategoryServiceImpl extends BaseApiService implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private CategoryBrandMapper categoryBrandMapper;

    @Override
    public Result<List<CategoryEntity>> getCategoryByPid(Integer pid) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setParentId(pid);
        List<CategoryEntity> list = categoryMapper.select(categoryEntity);
        return this.setResultSuccess(list);
    }

    @Transactional
    @Override
    public Result<JsonObject> delCategory(Integer id) {

        if(null != id && id > 0){//判断页面传递过来的id是否合法

            //通过id查询当前节点信息
            CategoryEntity categoryEntity = categoryMapper.selectByPrimaryKey(id);

            //判断当前节点是否为父节点 0不是1是
            if (categoryEntity.getIsParent()==1)return this.setResultError("当前节点是父错节点");//return之后的代码不会执行

            Example example = new Example(CategoryEntity.class);
            example.createCriteria().andEqualTo("parentId",categoryEntity.getParentId());

            //通过当前节点的父节点id查询当前将要被删除的父节点下是否还有其它子节点
            List<CategoryEntity>categoryList=categoryMapper.selectByExample(example);

            //如果size<=1 -->如果当前节点被删除的话,当前节点的父节点写就没有节点了,将当前节点父节点状态改为叶子节点
            if(categoryList.size()<=1){

                CategoryEntity updateCategoryEntity = new CategoryEntity();
                updateCategoryEntity.setIsParent(0);
                updateCategoryEntity.setId(categoryEntity.getParentId());

                categoryMapper.updateByPrimaryKeySelective(updateCategoryEntity);
            }
            categoryMapper.deleteByPrimaryKey(id);
            return this.setResultSuccess();
        }

        return this.setResultError("id不合法");
    }

    @Transactional
    @Override
    public Result<JsonObject> editCategory(CategoryEntity categoryEntity) {
        categoryMapper.updateByPrimaryKeySelective(categoryEntity);
        return this.setResultSuccess();
    }

    @Transactional
    @Override
    public Result<JsonObject> addCategory(CategoryEntity categoryEntity) {
        CategoryEntity parentCategoryEntity = new CategoryEntity();
        parentCategoryEntity.setId(categoryEntity.getParentId());
        parentCategoryEntity.setIsParent(1);
        categoryMapper.updateByPrimaryKeySelective(parentCategoryEntity);

        categoryMapper.insertSelective(categoryEntity);

        return this.setResultSuccess();
    }

    @Override
    public Result<List<CategoryEntity>> getCategoryByBrandId(Integer brandId) {
        List<CategoryEntity>list=categoryMapper.getCategoryByBrandId(brandId);
        return this.setResultSuccess(list);
    }
}
