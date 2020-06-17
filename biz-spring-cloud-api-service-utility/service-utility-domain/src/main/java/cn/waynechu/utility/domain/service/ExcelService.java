package cn.waynechu.utility.domain.service;

import cn.waynechu.facade.common.enums.BizErrorCodeEnum;
import cn.waynechu.facade.common.exception.BizException;
import cn.waynechu.facade.common.response.BizResponse;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author zhuwei
 * @date 2020-03-22 23:10
 */
@Slf4j
@Service
public class ExcelService {

    @Value("${utility.gateway-url}")
    private String gatewayUrl;

    @Autowired
    private RestTemplate restTemplate;

    public String getSid(JSONObject params, String dataExportUrl) {
        String url = gatewayUrl + dataExportUrl;
        ResponseEntity<BizResponse<String>> responseEntity = restTemplate.exchange(url,
                HttpMethod.POST, new HttpEntity<>(params), new ParameterizedTypeReference<BizResponse<String>>() {
                });
        if (HttpStatus.OK.equals(responseEntity.getStatusCode()) && responseEntity.getBody() != null) {
            return responseEntity.getBody().getData();
        } else {
            if (HttpStatus.NOT_FOUND.equals(responseEntity.getStatusCode())) {
                throw new IllegalArgumentException("导出地址不存在 " + dataExportUrl);
            }
            log.warn("导出失败 url: {} params: {}", url, JSONObject.toJSONString(params));
            throw new BizException(BizErrorCodeEnum.OPERATION_FAILED, "导出失败");
        }
    }
}
