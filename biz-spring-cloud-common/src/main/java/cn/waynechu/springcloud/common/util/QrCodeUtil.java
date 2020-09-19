package cn.waynechu.springcloud.common.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import lombok.experimental.UtilityClass;
import org.springframework.util.Base64Utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.EnumMap;
import java.util.Map;

/**
 * @author zhuwei
 * @since 2019/6/21 19:46
 */
@UtilityClass
public class QrCodeUtil {

    /**
     * 获取base64二维码字符串
     * <p>
     * 前端拼接前缀即可展示base64图片
     * <img src="data:image/jpg;base64,${return-string}">
     *
     * @param content 需编码的内容
     * @param width   宽度
     * @param height  高度
     * @return base64处理的二维码字符串
     */
    public static String generateRrCode(String content, int width, int height) {
        String binary = "";
        Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
        hints.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8);
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            // 读取文件转换为字节数组
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            BufferedImage image = toBufferedImage(bitMatrix);
            // 转换成jpg格式的IO流
            ImageIO.write(image, "jpg", out);
            byte[] bytes = out.toByteArray();
            // 将字节数组转为二进制
            binary = Base64Utils.encodeToString(bytes);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        return binary;
    }

    private static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return image;
    }

    public static void main(String[] args) {
        String qrCode = generateRrCode("http://www.waynechu.cn", 1000, 1000);
        System.out.println("data:image/jpg;base64," + qrCode);
    }
}
