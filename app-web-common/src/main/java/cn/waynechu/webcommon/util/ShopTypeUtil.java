package cn.waynechu.webcommon.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 门店类型解析工具类
 *
 * @author zhuwei
 * @date 2018/11/7 12:33
 */
public class ShopTypeUtil {

    private static final Logger logger = LoggerFactory.getLogger(ShopTypeUtil.class);

    /**
     * 门店类型含义,按位取值一共11位,低位到高位分别代表：
     * {合作门店,供应商,仓库,途虎配送,虎式服务,上门服务,大客户门店,星级门店,雪地胎门店,途虎工场,店中店}
     * 若该位置为1则代表是此类型,反之则没有,.net使用这种逻辑是为了适应方便多种类型组合(猜测)
     * .net 原有逻辑逻辑For example:
     * if ((shopType & 1) == 1)
     * {
     * shopTypeName.Append("/合作门店");
     * }
     * <p>
     * if ((shopType & 2) == 2)
     * {
     * shopTypeName.Append("/供应商");
     * }
     * etc....
     *
     * @param shopType
     * @return
     */
    public static String getShopTypeName(Integer shopType) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] shopTypes = new String[]{"/合作门店", "/供应商", "/仓库", "/途虎配送", "/虎式服务", "/上门服务", "/大客户门店", "/星级门店",
                "/雪地胎门店", "/途虎工场", "/店中店"};
        int length = Integer.toBinaryString(shopType).length();

        logger.info("解析shop type字段为二进制字符串:" + Integer.toBinaryString(shopType));
        //针对超过11位的处理情况,直接忽略
        char[] chars = length > 11 ? Integer.toBinaryString(shopType).substring(1, length).toCharArray()
                : Integer.toBinaryString(shopType).toCharArray();
        for (int i = chars.length - 1, j = 0; i >= 0 && j < shopTypes.length; i--, j++) {
            if (chars[i] == '1') {
                stringBuilder.append(shopTypes[j]);
            }
        }

        return stringBuilder.toString();
    }

//    /**
//     * Gungnir.ShopConfig表,依赖.net逻辑：
//     * <p>
//     * if (workShopType == 1)//自营
//     * {
//     * sql += @"AND WorkShopType != N'工场店'
//     * AND WorkShopType != N''
//     * AND WorkShopType IS NOT NULL ";
//     * }
//     * else if (workShopType == 2)//加盟
//     * {
//     * sql += @"AND ( WorkShopType = N'工场店'
//     * OR WorkShopType = N''
//     * OR WorkShopType IS NULL ) ";
//     * }
//     *
//     * @param workShopType
//     * @return
//     */
//    public static String getWorkShopTypeName(String workShopType) {
//        String name = "";
//        if (StringUtils.isNotEmpty(workShopType) && !WorkShopTypeEnum.WORKSHOP.getDesc().equals(workShopType)) {
//            name = WorkShopTypeEnum.SELF_OPERATED.getDisplayDesc();
//        }
//        if (StringUtils.isEmpty(workShopType) || WorkShopTypeEnum.WORKSHOP.getDesc().equals(workShopType)) {
//            name = WorkShopTypeEnum.WORKSHOP.getDisplayDesc();
//        }
//
//        return name;
//    }
}
