package cn.waynechu.renting.web.service;

import cn.waynechu.renting.facade.dto.ShopConfigLogDTO;
import cn.waynechu.renting.facade.service.ShopConfigService;
import cn.waynechu.webcommon.util.DateUtil;
import cn.waynechu.webcommon.web.excel.ModelExcel;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author zhuwei
 * @date 2019/2/22 14:20
 */
@Service
public class ShopConfigWebService {

    @Reference(version = "1.0.0")
    private ShopConfigService shopConfigService;

    public ModelExcel<ShopConfigLogDTO> exportLog(Date startDate, Date endDate) {
        ModelExcel<ShopConfigLogDTO> modelExcel = new ModelExcel<>();
        modelExcel.setFileName(DateUtil.dateToString(startDate) + ModelExcel.SUFFIX_XLSX);
        modelExcel.setSheetName("sheet1");
        modelExcel.setTitles(new ArrayList<String>() {{
            add("shopId");
            add("changeItem");
            add("beforeValue");
            add("afterValue");
            add("createUser");
            add("createTime");
            add("updateUser");
            add("updateTime");
        }});
        modelExcel.setZhTitles(new ArrayList<String>() {{
            add("门店ID");
            add("变更项目");
            add("变更前");
            add("变更后");
            add("创建人");
            add("创建时间");
            add("更新人");
            add("更新时间");
        }});
        modelExcel.setExcelData(shopConfigService.exportLog(startDate, endDate));
        return modelExcel;
    }
}
