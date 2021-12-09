package cn.waynechu.springcloud.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 箱形图
 *
 * <pre>
 *     https://wiki.mbalib.com/wiki/%E7%AE%B1%E7%BA%BF%E5%9B%BE
 * </pre>
 *
 * @author zw338299@alibaba-inc.com
 * @since 2021/12/9 11:18 上午
 */
@Slf4j
@UtilityClass
public class BoxPlotUtil {

    public static void main(String[] args) {
        double[] nums = new double[]{2710, 2755, 2850, 2880, 2880, 2890, 2920, 2940, 2950, 3050, 3130, 3325};
        BoxPlot plot = plot(nums);

        System.out.println(JSON.toJSONString(plot, SerializerFeature.PrettyFormat));
    }

    public static BoxPlot plot(double[] data) {
        List<Double> collect = Arrays.stream(data).boxed().sorted().collect(Collectors.toList());
        data = collect.stream().mapToDouble(i -> i).toArray();

        double max;
        double q3;
        double median;
        double q1;
        double min;
        double iqr;
        if (data.length % 2 == 0) {
            median = (data[(data.length) / 2 - 1] + data[(data.length) / 2]) / 2;
            q1 = (data[(data.length) / 4 - 1] + data[(data.length) / 4]) / 2;
            q3 = (data[((data.length) * 3) / 4 - 1] + data[((data.length) * 3) / 4]) / 2;
        } else {
            median = data[(data.length) / 2];
            q1 = data[(data.length) / 4];
            q3 = data[(data.length * 3) / 4];
        }
        max = data[data.length - 1];
        min = data[0];
        iqr = q3 - q1;

        double maxRegion = q3 + 1.5 * iqr;
        double minRegion = q1 - 1.5 * iqr;

        BoxPlot box = new BoxPlot();
        box.setMaxRegion(maxRegion);
        box.setMax(max);
        box.setQ3(q3);
        box.setMedian(median);
        box.setQ1(q1);
        box.setMin(min);
        box.setMinRegion(minRegion);
        box.setIqr(iqr);
        return box;
    }
}

@Data
class BoxPlot {
    // 最大值
    private double max;
    // 上四分位 75%
    private double q3;
    // 中位数
    private double median;
    // 下四分位 25%
    private double q1;
    // 最小值
    private double min;

    // 四分位距. iqr = q3 - q1, 即上四分位数与下四分位数之间的差, 也就是盒子的长度
    private double iqr;
    // 上限制线. q3 + 1.5iqr
    private double maxRegion;
    // 下限制线. q1 - 1.5iqr
    private double minRegion;
}
