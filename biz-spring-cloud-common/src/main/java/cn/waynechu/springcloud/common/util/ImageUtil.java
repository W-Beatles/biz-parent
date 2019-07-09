package cn.waynechu.springcloud.common.util;

import lombok.experimental.UtilityClass;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author zhuwei
 * @date 2019/6/26 21:49
 */
@UtilityClass
public class ImageUtil {

    /**
     * 对图片进行旋转
     *
     * @param src   被旋转图片
     * @param angle 旋转角度
     * @return 旋转后的图片
     */
    public static BufferedImage rotate(Image src, int angle) {
        double srcWidth = src.getWidth(null);
        double srcHeight = src.getHeight(null);
        // 计算旋转后图片的尺寸
        Rectangle rectDes = calcRotatedSize(new Rectangle(new Dimension((int) srcWidth, (int) srcHeight)), angle);
        BufferedImage res = new BufferedImage(rectDes.width, rectDes.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = res.createGraphics();
        // 进行转换
        g2.translate((rectDes.width - srcWidth) / 2, (rectDes.height - srcHeight) / 2);
        g2.rotate(Math.toRadians(angle), srcWidth / 2, srcHeight / 2);

        g2.drawImage(src, null, null);
        g2.dispose();
        return res;
    }

    /**
     * 计算旋转后的图片
     *
     * @param src   被旋转的图片
     * @param angel 旋转角度
     * @return 旋转后的图片
     */
    private static Rectangle calcRotatedSize(Rectangle src, int angel) {
        // 如果旋转的角度大于90度做相应的转换
        if (angel >= 90) {
            if (angel / 90 % 2 == 1) {
                int temp = src.height;
                src.height = src.width;
                src.width = temp;
            }
            angel = angel % 90;
        }

        double r = Math.sqrt(src.height * src.height + src.width * src.width) / 2;
        double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;
        double angelAlpha = (Math.PI - Math.toRadians(angel)) / 2;
        double angelDaltaWidth = Math.atan((double) src.height / src.width);
        double angelDaltaHeight = Math.atan((double) src.width / src.height);

        int lenDaltaWidth = (int) (len * Math.cos(Math.PI - angelAlpha - angelDaltaWidth));
        int lenDaltaHeight = (int) (len * Math.cos(Math.PI - angelAlpha - angelDaltaHeight));
        int desWidth = src.width + lenDaltaWidth * 2;
        int desHeight = src.height + lenDaltaHeight * 2;
        return new Rectangle(new Dimension(desWidth, desHeight));
    }
}
