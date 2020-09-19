package cn.waynechu.springcloud.common.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @author zhuwei
 * @since 2018/11/5 16:18
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
            // noinspection ResultOfMethodCallIgnored
            fileDir.setWritable(true);
            // noinspection ResultOfMethodCallIgnored
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
        // noinspection ResultOfMethodCallIgnored
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
        if (StringUtil.isNotEmpty(fileName) && fileName.contains(".")) {
            // 获取文件的扩展名
            String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
            // 使用UUID作为上传的文件名称
            return UUID.randomUUID().toString() + "." + fileExtensionName;
        }
        return UUIDUtil.getRandomUUID();
    }

    /**
     * 下载文件
     *
     * @param response resp
     * @param filePath 文件路径
     * @param fileName 文件名
     */
    public static void download(HttpServletResponse response, String filePath, String fileName) {
        download(response, filePath, fileName, false);
    }

    /**
     * 下载文件
     *
     * @param response   resp
     * @param filePath   文件路径
     * @param fileName   文件名
     * @param removeFile 下载后移除文件
     */
    public static void download(HttpServletResponse response, String filePath, String fileName, boolean removeFile) {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition",
                "attachment; filename=" + new String(fileName.getBytes(), StandardCharsets.ISO_8859_1));
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");

        OutputStream os;
        File file = new File(filePath + File.separator + fileName);

        try (FileInputStream fis = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(fis)) {
            os = response.getOutputStream();

            byte[] buff = new byte[1024];
            int len;
            while ((len = bis.read(buff)) != -1) {
                os.write(buff, 0, len);
                os.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (removeFile && file.exists()) {
                // noinspection ResultOfMethodCallIgnored
                file.delete();
            }
        }
    }
}
