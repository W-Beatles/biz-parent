package cn.waynechu.springcloud.common.util;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * JMH测试示例
 *
 * <pre>
 * https://dunwu.github.io/javatech/test/jmh.html
 *
 * BenchmarkMode 基准测试模式
 *     Throughput: 整体吞吐量，例如”1秒内可以执行多少次调用”。
 *     AverageTime: 调用的平均时间，例如”每次调用平均耗时xxx毫秒”。
 *     SampleTime: 随机取样，最后输出取样结果的分布，例如”99%的调用在xxx毫秒以内，99.99%的调用在xxx毫秒以内”
 *     SingleShotTime: 以上模式都是默认一次 iteration 是 1s，唯有 SingleShotTime 是只运行一次。往往同时把 warmup 次数设为0，用于测试冷启动时的性能。
 *     All(“all”, “All benchmark modes”);
 * Warmup 预热
 * Measurement 度量，配置测试次数和时长
 * Threads 线程数
 * Fork 进程数
 * Setup 测试开始前准备
 * </pre>
 *
 * @author zhuwei
 * @since 2022/1/6 12:55 下午
 */
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 3, time = 3)
@Threads(4)
@Fork(1)
@State(value = Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class DesensitizeUtilBenchmark {

    private final List<String> phones = new ArrayList<>();

    @Setup
    public void init() {
        int total = 99999;
        for (int i = 0; i < total; i++) {
            phones.add(getPhone());
        }
    }

    @Benchmark
    public void mobilePhone() {
        for (String phone : phones) {
            DesensitizeUtil.mobilePhone(phone);
        }
    }

    @Benchmark
    public void phone11() {
        for (String phone : phones) {
            System.out.println(DesensitizeUtil.phone11(phone));
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(DesensitizeUtilBenchmark.class.getSimpleName())
                //.result("result.json")
                .resultFormat(ResultFormatType.JSON).build();
        new Runner(opt).run();
    }


    public static String getPhone() {
        String[] start = {"133", "149", "153", "173", "177",
                "180", "181", "189", "199", "130", "131", "132",
                "145", "155", "156", "166", "171", "175", "176", "185", "186", "166", "134", "135",
                "136", "137", "138", "139", "147", "150", "151", "152", "157", "158", "159", "172",
                "178", "182", "183", "184", "187", "188", "198", "170", "171"};

        String prefix = start[(int) (Math.random() * start.length)];
        StringBuilder content = new StringBuilder();
        final int length = 8;
        for (int i = 0; i < length; i++) {
            content.append((int) (Math.random() * 10));
        }
        return prefix + content;
    }
}