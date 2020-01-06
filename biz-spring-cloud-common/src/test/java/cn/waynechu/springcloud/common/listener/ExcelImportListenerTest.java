package cn.waynechu.springcloud.common.listener;


import cn.waynechu.springcloud.common.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;

/**
 * @author zhuwei
 * @date 2020/1/6 19:17
 */
@Slf4j
public class ExcelImportListenerTest {

    /**
     * 示例1: 解析数据量较小的excel，一次性解析所有数据（推荐数据量小于3000时使用该方式）
     */
    @Test
    public void simpleRead1() {
        // 写法1:
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        ExcelImportListener<DemoData> listener1 = new ExcelImportListener<>();
        // 指定用哪个class去读，然后读取第一个sheet，文件流会自动关闭
        EasyExcel.read(fileName, DemoData.class, listener1).sheet().doRead();
        // 打印解析的数据
        log.info(JSONObject.toJSONString(listener1.getData()));

        // 写法2
        ExcelImportListener<DemoData> listener2 = new ExcelImportListener<>();
        ExcelReader excelReader = EasyExcel.read(fileName, DemoData.class, listener2).build();
        ReadSheet readSheet = EasyExcel.readSheet(0).build();
        excelReader.read(readSheet);
        // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
        excelReader.finish();
        // 打印解析的数据
        log.info(JSONObject.toJSONString(listener2.getData()));
    }

    /**
     * 示例1: 解析数据量比较大的excel，分批解析处理（推荐数据量大于3000时使用该方式）
     */
    @Test
    public void simpleRead2() {
        // 写法1:
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        // 创建解析监听器，指定解析数量上限和回调处理器
        ExcelImportListener<DemoData> listener = new ExcelImportListener<>(3, data -> {
            // 打印解析的数据
            log.info(JSONObject.toJSONString(data));
        });
        // 指定用哪个class去读，然后读取第一个sheet，文件流会自动关闭
        EasyExcel.read(fileName, DemoData.class, listener).sheet().doRead();

        // 写法2
        ExcelReader excelReader = EasyExcel.read(fileName, DemoData.class, listener).build();
        ReadSheet readSheet = EasyExcel.readSheet(0).build();
        excelReader.read(readSheet);
        // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
        excelReader.finish();
    }
}