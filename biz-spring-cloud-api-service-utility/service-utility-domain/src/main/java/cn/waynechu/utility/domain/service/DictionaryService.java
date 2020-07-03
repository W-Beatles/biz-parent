package cn.waynechu.utility.domain.service;

import cn.waynechu.facade.common.page.BizPageInfo;
import cn.waynechu.springcloud.common.util.UserUtil;
import cn.waynechu.utility.dal.condition.DictionaryCondition;
import cn.waynechu.utility.dal.dataobject.DictionaryDO;
import cn.waynechu.utility.domain.convert.DictionaryConvert;
import cn.waynechu.utility.domain.repository.DictionaryRepository;
import cn.waynechu.utility.facade.request.CreateDictionaryRequest;
import cn.waynechu.utility.facade.request.SearchDictionaryRequest;
import cn.waynechu.utility.facade.response.DictionaryResponse;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zhuwei
 * @date 2020-06-29 00:07
 */
@Service
public class DictionaryService {

    @Autowired
    private DictionaryRepository dictionaryRepository;

    /**
     * 搜索字典列表
     *
     * @param request req
     * @return 分页信息
     */
    public BizPageInfo<DictionaryResponse> search(SearchDictionaryRequest request) {
        DictionaryCondition condition = new DictionaryCondition();

        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<DictionaryDO> dictionaryDOList = dictionaryRepository.selectByCondition(condition);
        List<DictionaryResponse> dictionaryResponseList = DictionaryConvert.toDictionaryResponseList(dictionaryDOList);
        return BizPageInfo.of(dictionaryDOList).replace(dictionaryResponseList);
    }

    /**
     * 添加字典
     *
     * @param request req
     * @return 字典id
     */
    public Long create(CreateDictionaryRequest request) {
        // TODO 2020/7/3 18:56 查找最大序号
        Integer sortNum = null;

        DictionaryDO dictionaryDO = new DictionaryDO();
        dictionaryDO.setPid(request.getPid());
        dictionaryDO.setDicCode(request.getDicCode());
        dictionaryDO.setDicValue(request.getDicValue());
        dictionaryDO.setDicDesc(request.getDicDesc());
        dictionaryDO.setSortNum(sortNum);
        dictionaryDO.setFixedStatus(request.getFixedStatus());
        dictionaryDO.setCreatedUser(UserUtil.getEmail());
        dictionaryDO.setCreatedTime(LocalDateTime.now());
        return dictionaryRepository.create(dictionaryDO);
    }

    /**
     * 删除字典
     *
     * @param id 字典id
     */
    public void remove(Long id) {
        dictionaryRepository.remove(id);
    }
}
