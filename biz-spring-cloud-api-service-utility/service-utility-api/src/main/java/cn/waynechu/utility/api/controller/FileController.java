package cn.waynechu.utility.api.controller;

import cn.waynechu.facade.common.response.BizResponse;
import cn.waynechu.utility.domain.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author zhuwei
 * @since 2020/7/2 15:17
 */
@RestController
@Api(tags = "文件服务")
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @ApiOperation("文件上传")
    @PostMapping("/upload")
    public BizResponse<String> upload(@RequestParam("file") MultipartFile file) {
        String url = fileService.fileUpload(file);
        return BizResponse.success(url);
    }

    @ApiOperation("文件下载")
    @GetMapping("/download/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) {
        fileService.download(fileName, response);
    }
}
