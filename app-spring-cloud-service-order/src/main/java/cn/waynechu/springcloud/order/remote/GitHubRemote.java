package cn.waynechu.springcloud.order.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * feign调用示例
 * <p>
 *
 * <pre>
 * 参数说明
 * name:      指定FeignClient的名称，如果项目使用了Ribbon，name属性会作为微服务的名称用于服务发现
 * url:       一般用于调试，可以手动指定FeignClient的调用地址
 * decode404: 当发生404错误时，如果该字段为true，会调用decoder进行解码，否则抛出FeignException
 * fallback:  服务降级处理类，fallback指定的类必须@FeignClient标记的接口
 * fallbackFactory: 工厂类，用于生成fallback类示例，通过这个属性可以实现通用的降级处理
 * path:      定义当前FeignClient的统一前缀
 * </pre>
 *
 * @author zhuwei
 * @date 2019/4/25 15:39
 */
@FeignClient(name = "github-client", url = "https://api.github.com")
public interface GitHubRemote {

    @GetMapping("/search/repositories")
    String searchRepo(@RequestParam("q") String name);
}
