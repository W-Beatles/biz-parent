package cn.waynechu.renting.core.repository;

import cn.waynechu.renting.dal.common.entity.SysDictionary;
import cn.waynechu.renting.dal.common.entity.SysDictionaryExample;
import cn.waynechu.renting.dal.common.mapper.SysDictionaryMapper;
import cn.waynechu.webcommon.page.PageInfo;
import com.github.pagehelper.PageHelper;
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
        return sysDictionaryMapper.selectByPrimaryKey(id);
    }

    public boolean create(SysDictionary sysDictionary) {
        return sysDictionaryMapper.insertSelective(sysDictionary) > 0;
    }

    public boolean updateSelective(SysDictionary sysDictionary) {
        return sysDictionaryMapper.updateByPrimaryKeySelective(sysDictionary) > 0;
    }

    public boolean removeById(Long id) {
        SysDictionary sysDictionary = new SysDictionary();
        sysDictionary.setId(id);
        sysDictionary.setIsDeleted(true);
        return sysDictionaryMapper.updateByPrimaryKeySelective(sysDictionary) > 0;
    }

    public PageInfo<SysDictionary> query(SysDictionary sysDictionary, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        SysDictionaryExample example = new SysDictionaryExample();
        SysDictionaryExample.Criteria criteria = example.createCriteria();
        if (sysDictionary.getTypeCode() != null) {
            criteria.andTypeCodeEqualTo(sysDictionary.getTypeCode());
        }
        List<SysDictionary> houses = sysDictionaryMapper.selectByExample(example);
        return PageInfo.of(houses);
    }
}
