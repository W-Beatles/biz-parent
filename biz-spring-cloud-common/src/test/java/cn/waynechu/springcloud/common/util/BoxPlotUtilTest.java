package cn.waynechu.springcloud.common.util;

import junit.framework.TestCase;
import org.junit.Test;


/**
 * @author zhuwei
 * @since 2022/1/6 11:12 上午
 */
public class BoxPlotUtilTest {

    @Test
    public void testPlot() {
        double[] nums = new double[]{2710, 2755, 2850, 2880, 2880, 2890, 2920, 2940, 2950, 3050, 3130, 3325};
        BoxPlot plot = BoxPlotUtil.plot(nums);
        TestCase.assertNotNull(plot);
        TestCase.assertEquals(3202.5D, plot.getMaxRegion());
        TestCase.assertEquals(3000.0D, plot.getQ3());
        TestCase.assertEquals(2905.0, plot.getMedian());
        TestCase.assertEquals(2865.0, plot.getQ1());
        TestCase.assertEquals(2662.5D, plot.getMinRegion());
    }

    @Test
    public void testNullPlot() {
        double[] nums = new double[]{2710, 2755};
        BoxPlot plot = BoxPlotUtil.plot(nums);
        TestCase.assertNull(plot);
    }
}