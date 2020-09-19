/**
 * Copyright © 2018 organization waynechu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.waynechu.bootstarter.dynamicdatasource.aspect;

import cn.waynechu.bootstarter.dynamicdatasource.annotion.SwitchDataSource;
import cn.waynechu.bootstarter.dynamicdatasource.toolkit.DynamicDataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * 动态数据源切面
 *
 * @author zhuwei
 * @since 2019/12/27 13:35
 */
@Slf4j
@Aspect
public class DynamicDataSourceAspect {

    @Before("@within(switchDataSource) || @annotation(switchDataSource)")
    public void switchDataSource(JoinPoint point, SwitchDataSource switchDataSource) {
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        SwitchDataSource annotation = method.getAnnotation(SwitchDataSource.class);

        if (annotation == null) {
            annotation = point.getTarget().getClass().getAnnotation(SwitchDataSource.class);
            if (annotation == null) {
                return;
            }
        }

        String value = annotation.value();
        if (!StringUtils.isEmpty(value)) {
            DynamicDataSourceContextHolder.push(value);
        }
    }
}
