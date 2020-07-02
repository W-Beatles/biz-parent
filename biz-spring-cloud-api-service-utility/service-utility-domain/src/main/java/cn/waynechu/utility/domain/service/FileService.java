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

    public String fileUpload(MultipartFile file) {
        String fileName = FileUploadUtil.upload(file, filePath);
        return gatewayUrl + "/" + applicationName + "/files/download/" + fileName;
    }

    public void download(String fileName, HttpServletResponse response) {
        FileUploadUtil.download(response, filePath, fileName);
    }
}
