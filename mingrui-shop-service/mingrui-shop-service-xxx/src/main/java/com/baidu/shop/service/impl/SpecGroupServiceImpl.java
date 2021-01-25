package com.baidu.shop.service.impl;

import com.baidu.shop.base.BaiDuBeanUtil;
import com.baidu.shop.base.BaseApiService;
import com.baidu.shop.base.Result;
import com.baidu.shop.dto.SpecGroupDto;
import com.baidu.shop.entity.SpecGroupEntity;
import com.baidu.shop.mapper.SpecGroupMapper;
import com.baidu.shop.service.SpecGroupService;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
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

    @Override
    public Result<List<SpecGroupEntity>> getSpecGroupInfo(SpecGroupDto specGroupDto) {
        Example example = new Example(SpecGroupEntity.class);
        example.createCriteria().andEqualTo("cid", BaiDuBeanUtil.copyProperties(specGroupDto,SpecGroupEntity.class).getCid());

        List<SpecGroupEntity> specGroupEntities = specGroupMapper.selectByExample(example);
        return this.setResultSuccess(specGroupEntities);
    }
}
