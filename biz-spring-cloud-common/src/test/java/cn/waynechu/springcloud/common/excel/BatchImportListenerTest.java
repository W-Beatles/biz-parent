package cn.waynechu.springcloud.common.excel;


import cn.waynechu.springcloud.common.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.SyncReadListener;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.util.List;

/**
 * https://github.com/alibaba/easyexcel
 *
 * @author zhuwei
 * @date 2020/1/6 19:17
 */
@Slf4j
public class BatchImportListenerTest {

    /**
     * 示例1: 解析数据量较小的excel，会把解析的所有数据放到内存里面（推荐数据量小于3000时使用该方式）
     */
    @Test
    public void simpleRead1() {
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        // 指定用哪个class去读，然后读取第一个sheet，文件流会自动关闭
        List<DemoData> data = EasyExcel.read(fileName).head(DemoData.class).sheet().doReadSync();
        // 打印解析的数据
        log.info(JSONObject.toJSONString(data));
    }

    /**
     * 示例2: 解析数据量比较大的excel，分批解析处理（推荐数据量大于3000时使用该方式）
     */
    @Test
    public void simpleRead2() {
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        // 创建解析监听器，指定解析数量上限 和 回调处理器
        BatchImportListener<DemoData> listener = new BatchImportListener<>(3, data -> {
            // 打印解析的数据
            log.info(JSONObject.toJSONString(data));
        });
        EasyExcel.read(fileName, DemoData.class, listener).sheet(0).doRead();
    }

    /**
     * 示例3: 读全部sheet
     */
    @Test
    public void simpleRead3() {
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        // 读取全部sheet
        SyncReadListener syncReadListener = new SyncReadListener();
        EasyExcel.read(fileName, DemoData.class, syncReadListener).doReadAll();
        // 打印解析的数据
        log.info(JSONObject.toJSONString(syncReadListener.getList()));
    }

    /**
     * 示例4: 解析指定标题头行数。headRowNumber参数默认值为1
     */
    @Test
    public void simpleRead4() {
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        // 解析第3个sheet，标题头在第2行
        List<DemoData> data = EasyExcel.read(fileName).head(DemoData.class).sheet(2).headRowNumber(2).doReadSync();
        // 打印解析的数据
        log.info(JSONObject.toJSONString(data));
    }
}