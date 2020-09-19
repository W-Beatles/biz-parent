package cn.waynechu.utility.domain.service;

import cn.waynechu.facade.common.enums.BizErrorCodeEnum;
import cn.waynechu.facade.common.exception.BizException;
import cn.waynechu.facade.common.response.BizResponse;
import cn.waynechu.springcloud.common.model.ExportResultResponse;
import cn.waynechu.springcloud.common.util.JsonBinder;
import cn.waynechu.springcloud.common.util.StringUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static cn.waynechu.springcloud.common.excel.ExcelHelper.EXPORT_CACHE_KEY;

/**
 * @author zhuwei
 * @since 2020-03-22 23:10
 */
@Slf4j
@Service
public class ExcelService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 获取sid
     *
     * @param url    导出URL
     * @param params 导出参数
     * @return 导出唯一标识
     */
    public String getSid(String url, JSONObject params) {
        if (url.startsWith("/")) {
            url = url.substring(1);
        }

        // 通过服务名转发请求到具体的项目 如: http://biz-archetype-portal/archetypes/export
        String serviceUrl = "http://" + url;
        ResponseEntity<BizResponse<String>> responseEntity;
        try {
            responseEntity = restTemplate.exchange(serviceUrl, HttpMethod.POST
                    , new HttpEntity<>(params), new ParameterizedTypeReference<BizResponse<String>>() {
                    });
        } catch (Exception e) {
            log.warn("导出失败, url: {}, params: {}", url, JsonBinder.toJson(params));
            throw new BizException(BizErrorCodeEnum.OPERATION_FAILED, "导出失败: " + e.getMessage());
        }

        if (HttpStatus.OK.equals(responseEntity.getStatusCode())
                && responseEntity.getBody() != null
                && BizErrorCodeEnum.SUCCESS.getCode().equals(responseEntity.getBody().getCode())) {
            return responseEntity.getBody().getData();
        } else {
            log.warn("导出失败, url: {}, params: {}", url, JsonBinder.toJson(params));
            throw new BizException(BizErrorCodeEnum.OPERATION_FAILED, "导出失败");
        }
    }

    /**
     * 查询导出状态
     *
     * @param sid 导出唯一标识
     * @return 导出状态
     */
    public ExportResultResponse status(String sid) {
        String key = EXPORT_CACHE_KEY + sid;
        String valueStr = redisTemplate.opsForValue().get(key);
        if (StringUtil.isEmpty(valueStr)) {
            throw new BizException(BizErrorCodeEnum.DATA_NOT_EXIST);
        }

        ExportResultResponse response = JsonBinder.fromJson(valueStr, ExportResultResponse.class);
        if (!sid.equals(response.getSid())) {
            throw new BizException(BizErrorCodeEnum.ILLEGAL_STATE_ERROR);
        }
        return response;
    }
}
