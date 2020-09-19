package cn.waynechu.springcloud.gateway.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author zhuwei
 * @since 2020-03-08 17:53
 */
@Data
public class TokenInfo {

    private boolean active;

    @JsonProperty("client_id")
    private String clientId;

    private String[] scope;

    @JsonProperty("user_name")
    private String userName;

    private String[] aud;

    private Date exp;

    private String[] authorities;
}
