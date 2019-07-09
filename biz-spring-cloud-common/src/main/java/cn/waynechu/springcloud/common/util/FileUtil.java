package cn.waynechu.springcloud.common.util;

import lombok.experimental.UtilityClass;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * @author zhuwei
 * @date 2019/6/26 23:01
 */
@UtilityClass
public class FileUtil {

    public static void byte2File(byte[] buf, String filePath, String fileName) {
        File dir = new File(filePath);
        if (!dir.exists() && dir.isDirectory()) {
            dir.mkdirs();
        }
        File file = new File(filePath + File.separator + fileName);

        try (FileOutputStream fos = new FileOutputStream(file);
             BufferedOutputStream bos = new BufferedOutputStream(fos)) {
            bos.write(buf);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
