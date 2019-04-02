package cn.waynechu.springcloud.common.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author zhuwei
 * @date 2018/11/5 16:18
 */
@Slf4j
@UtilityClass
public class FileUploadUtil {

    /**
     * 上传文件
     *
     * @param file 待上传的文件
     * @param path 绝对路径
     * @return 上传后的文件名
     */
    public static String upload(MultipartFile file, String path) {
        String uploadFileName = getUploadFileName(file);
        // 创建目录
        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        // 指定文件
        File targetFile = new File(path, uploadFileName);

        try {
            // 上传文件
            file.transferTo(targetFile);
        } catch (IOException e) {
            log.error("上传文件异常", e);
            return null;
        }
        return targetFile.getName();
    }

    /**
     * 删除指定文件
     *
     * @param targetFileName 文件名称
     * @param path           绝对路径
     */
    public static void delete(String targetFileName, String path) {
        File file = new File(path, targetFileName);
        file.delete();
    }

    /**
     * 使用UUID生成文件名
     *
     * @param file 待上传的文件
     * @return 新的文件名
     */
    private static String getUploadFileName(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        // 获取文件的扩展名
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        // 使用UUID作为上传的文件名称
        return UUID.randomUUID().toString() + "." + fileExtensionName;
    }
}
