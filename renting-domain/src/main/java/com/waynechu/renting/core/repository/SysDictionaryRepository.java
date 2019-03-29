package com.waynechu.renting.core.repository;

import cn.waynechu.webcommon.page.PageInfo;
import com.github.pagehelper.PageHelper;
import com.waynechu.renting.dal.common.entity.SysDictionary;
import com.waynechu.renting.dal.common.entity.SysDictionaryExample;
import com.waynechu.renting.dal.common.mapper.SysDictionaryMapper;
import com.waynechu.renting.facade.dto.condition.SysDictionarySearchCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhuwei
 * @date 2019/1/18 14:12
 */
@Repository
public class SysDictionaryRepository {

    @Autowired
    private SysDictionaryMapper sysDictionaryMapper;

    public SysDictionary getById(Long id) {
        SysDictionaryExample example = new SysDictionaryExample();
        SysDictionaryExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        criteria.andIsDeletedEqualTo(false);
        List<SysDictionary> sysDictionaries = sysDictionaryMapper.selectByExample(example);
        return sysDictionaries.isEmpty() ? null : sysDictionaries.get(0);
    }

    public boolean create(SysDictionary sysDictionary) {
        return sysDictionaryMapper.insertSelective(sysDictionary) > 0;
    }

    public boolean updateSelective(SysDictionary sysDictionary) {
        SysDictionaryExample example = new SysDictionaryExample();
        SysDictionaryExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(sysDictionary.getId());
        criteria.andIsDeletedEqualTo(false);
        return sysDictionaryMapper.updateByExampleSelective(sysDictionary, example) > 0;
    }

    public boolean removeById(Long id) {
        SysDictionaryExample example = new SysDictionaryExample();
        SysDictionaryExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        criteria.andIsDeletedEqualTo(false);

        SysDictionary sysDictionary = new SysDictionary();
        sysDictionary.setId(id);
        sysDictionary.setIsDeleted(true);
        return sysDictionaryMapper.updateByExampleSelective(sysDictionary, example) > 0;
    }

    public PageInfo<SysDictionary> query(SysDictionarySearchCondition condition) {
        PageHelper.startPage(condition.getPageNum(), condition.getPageSize());

        SysDictionaryExample example = new SysDictionaryExample();
        SysDictionaryExample.Criteria criteria = example.createCriteria();
        if (condition.getId() != null) {
            criteria.andIdEqualTo(condition.getId());
        }
        if (condition.getParentId() != null) {
            criteria.andParentIdEqualTo(condition.getParentId());
        }
        criteria.andIsDeletedEqualTo(false);
        List<SysDictionary> sysDictionaryList = sysDictionaryMapper.selectByExample(example);
        return PageInfo.of(sysDictionaryList);
    }
}
