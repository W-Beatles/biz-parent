package cn.waynechu.utility.domain.service;

import cn.waynechu.springcloud.common.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author zhuwei
 * @since 2020/7/2 15:27
 */
@Service
public class FileService {

    @Value("${utility.file.path}")
    private String filePath;

    @Value("${utility.gateway-url}")
    private String gatewayUrl;

    @Value("${spring.application.name}")
    private String applicationName;

    /**
     * 文件上传
     *
     * @param file 文件
     * @return 文件地址
     */
    public String upload(MultipartFile file) {
        String fileName = FileUploadUtil.upload(file, filePath);
        return gatewayUrl + "/" + applicationName + "/files/download/" + fileName;
    }

    /**
     * 文件下载
     *
     * @param fileName 文件名
     * @param response res
     */
    public void download(String fileName, HttpServletResponse response) {
        FileUploadUtil.download(response, filePath, fileName);
    }
}
