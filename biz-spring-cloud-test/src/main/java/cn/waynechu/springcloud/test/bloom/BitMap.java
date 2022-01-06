package cn.waynechu.springcloud.test.bloom;

import lombok.extern.slf4j.Slf4j;

/**
 * bitmap简单实现
 *
 * @author Z-Beatles
 * @since 2021-06-09 08:30
 */
@Slf4j
public class BitMap {

    /**
     * 创建bitmap数组
     */
    public byte[] create(int n) {
        byte[] bits = new byte[getIndex(n) + 1];

        for (int i = 0; i < n; i++) {
            add(bits, i);
        }

        log.info(String.valueOf(contains(bits, 11)));

        int index = 1;
        for (byte bit : bits) {
            log.info("-------" + index++ + "-------");
            showByte(bit);
        }

        return bits;
    }

    /**
     * 标记指定数字（num）在bitmap中的值，标记其已经出现过
     * 将1左移position后，那个位置自然就是1，然后和以前的数据做|，这样，那个位置就替换成1了
     */
    public void add(byte[] bits, int num) {
        bits[getIndex(num)] |= 1 << getPosition(num);
    }

    /**
     * 判断指定数字num是否存在<br/>
     * 将1左移position后，那个位置自然就是1，然后和以前的数据做&，判断是否为0即可
     */
    public boolean contains(byte[] bits, int num) {
        return (bits[getIndex(num)] & 1 << getPosition(num)) != 0;
    }

    /**
     * num/8得到byte[]的index
     */
    public int getIndex(int num) {
        return num >> 3;
    }

    /**
     * num%8得到在byte[index]的位置
     */
    public int getPosition(int num) {
        return num & 0x07;
    }

    /**
     * 重置某一数字对应在bitmap中的值<br/>
     * 对1进行左移，然后取反，最后与byte[index]作与操作。
     */
    public void clear(byte[] bits, int num) {
        bits[getIndex(num)] &= ~(1 << getPosition(num));
    }

    /**
     * 打印byte类型的变量<br/>
     * 将byte转换为一个长度为8的byte数组，数组每个值代表bit
     */
    public void showByte(byte b) {
        byte[] array = new byte[8];
        for (int i = 7; i >= 0; i--) {
            array[i] = (byte) (b & 1);
            b = (byte) (b >> 1);
        }

        StringBuilder sb = new StringBuilder();
        for (byte b1 : array) {
            sb.append(b1).append(" ");
        }
        log.info(sb.toString());
    }
}
