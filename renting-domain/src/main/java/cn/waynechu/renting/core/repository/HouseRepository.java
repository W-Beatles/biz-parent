package cn.waynechu.renting.core.repository;

import cn.waynechu.renting.dal.renting.entity.House;
import cn.waynechu.renting.dal.renting.entity.HouseExample;
import cn.waynechu.renting.dal.renting.mapper.HouseMapper;
import cn.waynechu.webcommon.page.PageInfo;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author zhuwei
 * @date 2018/12/28 16:57
 */
@Repository("houseRepository")
public class HouseRepository {

    @Autowired
    private HouseMapper houseMapper;

    public House getById(Long id) {
        return houseMapper.selectByPrimaryKey(id);
    }

    public boolean create(House house) {
        return houseMapper.insertSelective(house) > 0;
    }

    public boolean updateSelective(House house) {
        return houseMapper.updateByPrimaryKeySelective(house) > 0;
    }

    public boolean removeById(Long id) {
        House house = new House();
        house.setId(id);
        house.setIsDeleted(true);
        return houseMapper.updateByPrimaryKeySelective(house) > 0;
    }

    public PageInfo<House> query(House house, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        HouseExample example = new HouseExample();
        HouseExample.Criteria criteria = example.createCriteria();
        if (StringUtils.hasText(house.getTitle())) {
            criteria.andTitleEqualTo(house.getTitle());
        }
        if (house.getStatus() != null) {
            criteria.andStatusEqualTo(house.getStatus());
        }
        List<House> houses = houseMapper.selectByExample(example);
        return PageInfo.of(houses);
    }
}
