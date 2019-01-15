/**
 * Copyright © 2018 organization waynechu
 * <pre>
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
 * <pre/>
 */
package cn.waynechu.dynamic.datasource.dynamic;

/**
 * @author zhuwei
 * @date 2018/11/7 14:03
 */
public class DataSourceTypeHolder {
    private static ThreadLocal<String> contextHolder = new ThreadLocal<>();
    public static final String DATASOURCE_TYPE_MASTER = "master";
    public static final String DATASOURCE_TYPE_SALVE = "salve";

    private DataSourceTypeHolder() {
    }

    public static String getDataSourceType() {
        return contextHolder.get();
    }

    public static void setDataSourceType(String type) {
        contextHolder.set(type);
    }

    public static void clearDataSourceType() {
        contextHolder.remove();
    }
}
