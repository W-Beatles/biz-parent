package cn.waynechu.springcloud.test.bloom;

import java.util.BitSet;

/**
 * 布隆过滤器简单实现
 *
 * @author Z-Beatles
 * @since 2021-06-09 08:40
 */
public class SimpleBloomFilter {

    private static final int DEFAULT_SIZE = 2 << 24;
    private static final int[] SEEDS = new int[]{7, 11, 13, 31, 37, 61};

    private final BitSet bits = new BitSet(DEFAULT_SIZE);
    private final SimpleHash[] func = new SimpleHash[SEEDS.length];

    public SimpleBloomFilter() {
        for (int i = 0; i < SEEDS.length; i++) {
            func[i] = new SimpleHash(DEFAULT_SIZE, SEEDS[i]);
        }
    }

    public void add(String value) {
        for (SimpleHash f : func) {
            bits.set(f.hash(value), true);
        }
    }

    public boolean contains(String value) {
        if (value == null) {
            return false;
        }
        boolean ret = true;
        for (SimpleHash f : func) {
            ret = ret && bits.get(f.hash(value));
        }
        return ret;
    }

    public static class SimpleHash {
        private final int cap;
        private final int seed;

        public SimpleHash(int cap, int seed) {
            this.cap = cap;
            this.seed = seed;
        }

        public int hash(String value) {
            int result = 0;
            int len = value.length();
            for (int i = 0; i < len; i++) {
                result = seed * result + value.charAt(i);
            }
            return (cap - 1) & result;
        }
    }
}