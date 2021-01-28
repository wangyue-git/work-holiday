package com.baidu.shop.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baidu.shop.base.BaiDuBeanUtil;
import com.baidu.shop.base.BaseApiService;
import com.baidu.shop.base.Result;
import com.baidu.shop.dto.SpecGroupDto;
import com.baidu.shop.dto.SpecParamDto;
import com.baidu.shop.entity.SpecGroupEntity;
import com.baidu.shop.entity.SpecParamEntity;
import com.baidu.shop.mapper.SpecGroupMapper;
import com.baidu.shop.mapper.SpecParamMapper;
import com.baidu.shop.service.SpecGroupService;
import com.baidu.shop.utils.ObjectUtil;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @ClassName SpecGroupServiceImpl
 * @Description: TODO
 * @Author wangyue
 * @Date 2021/1/25
 * @Version V1.0
 **/
@RestController
public class SpecGroupServiceImpl extends BaseApiService implements SpecGroupService {

    @Resource
    private SpecGroupMapper specGroupMapper;

    @Resource
    private SpecParamMapper specParamMapper;

    //规格组查询
    @Override
    public Result<List<SpecGroupEntity>> getSpecGroupInfo(SpecGroupDto specGroupDto) {
        Example example = new Example(SpecGroupEntity.class);
        example.createCriteria().andEqualTo("cid", BaiDuBeanUtil.copyProperties(specGroupDto,SpecGroupEntity.class).getCid());

        List<SpecGroupEntity> specGroupEntities = specGroupMapper.selectByExample(example);
        return this.setResultSuccess(specGroupEntities);
    }

    //规格组新增
    @Transactional
    @Override
    public Result<JSONObject> saveSpecGroup(SpecGroupDto specGroupDto) {
        //第一个参数是要转换的类，第二个参数是转换后的类。
        specGroupMapper.insertSelective(BaiDuBeanUtil.copyProperties(specGroupDto,SpecGroupEntity.class));
        return this.setResultSuccess();
    }

    //规格组修改
    @Transactional
    @Override
    public Result<JSONObject> editSpecGroup(SpecGroupDto specGroupDto) {
        specGroupMapper.updateByPrimaryKeySelective(BaiDuBeanUtil.copyProperties(specGroupDto,SpecGroupEntity.class));
        return this.setResultSuccess();
    }

    //规格组删除
    @Transactional
    @Override
    public Result<JSONObject> deleteSpecGroupById(Integer id) {
        //删除规格组之前需要先判断一下当前规格组下是否有规格参数 true不能被删除
        Example example = new Example(SpecParamEntity.class);
        //根据groupId查询,如果返回集合大于0 就不能删除
        example.createCriteria().andEqualTo("groupId",id);
        List<SpecParamEntity>specParamEntities=specParamMapper.selectByExample(example);
        if(specParamEntities.size()>0){
            return this.setResultError("该规格组有数据无法删除");
        }
        specGroupMapper.deleteByPrimaryKey(id);
        return this.setResultSuccess();
    }

    //规格参数查询
    @Override
    public Result<List<SpecParamEntity>> getSpecParamInfo(SpecParamDto specParamDTO) {
        SpecParamEntity specParamEntity = BaiDuBeanUtil.copyProperties(specParamDTO, SpecParamEntity.class);
        Example example = new Example(SpecParamEntity.class);
        Example.Criteria criteria = example.createCriteria();
        //example.createCriteria().andEqualTo("groupId",specParamEntity.getGroupId());
        //通过groupId查找
        if (ObjectUtil.isNotNull(specParamEntity.getGroupId()))
            criteria.andEqualTo("groupId",specParamEntity.getGroupId());
        //通过cid查找
        if (ObjectUtil.isNotNull(specParamEntity.getCid()))
            criteria.andEqualTo("cid",specParamEntity.getCid());

        List<SpecParamEntity> specParamEntities = specParamMapper.selectByExample(example);

        return this.setResultSuccess(specParamEntities);
    }

    //规格参数新增
    @Transactional
    @Override
    public Result<JSONObject> saveSpecParam(SpecParamDto specParamDTO) {
        specParamMapper.insertSelective(BaiDuBeanUtil.copyProperties(specParamDTO,SpecParamEntity.class));

        return this.setResultSuccess();
    }

    //规格参数修改
    @Transactional
    @Override
    public Result<JSONObject> editSpecParam(SpecParamDto specParamDTO) {
        specParamMapper.updateByPrimaryKeySelective(BaiDuBeanUtil.copyProperties(specParamDTO,SpecParamEntity.class));

        return this.setResultSuccess();
    }

    //规格参数删除
    @Transactional
    @Override
    public Result<JSONObject> deleteSpecParam(@NotNull Integer id) {
        specParamMapper.deleteByPrimaryKey(id);
        return this.setResultSuccess();
    }
}
