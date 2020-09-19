package cn.waynechu.springcloud.common.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.codec.Charsets;

import java.io.*;
import java.nio.charset.Charset;

/**
 * @author zhuwei
 * @since 2019/6/26 23:01
 */
@UtilityClass
public class FileUtil {

    public static void writeStringToFile(File file, String data, Charset encoding) throws IOException {
        writeStringToFile(file, data, encoding, false);
    }

    public static void writeStringToFile(final File file, final String data, final Charset encoding, boolean append) throws IOException {
        try (OutputStream out = openOutputStream(file, append)) {
            if (data != null) {
                out.write(data.getBytes(Charsets.toCharset(encoding)));
            }
        }
    }

    public static String readFileToString(File file, Charset encoding) throws IOException {
        StringBuilder jsonString = new StringBuilder();
        try (InputStream in = openInputStream(file)) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, encoding));
            String thisLine;
            while ((thisLine = br.readLine()) != null) {
                jsonString.append(thisLine);
            }
            in.close();
            return jsonString.toString();
        }
    }

    public static FileOutputStream openOutputStream(final File file, final boolean append) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (!file.canWrite()) {
                throw new IOException("File '" + file + "' cannot be written to");
            }
        } else {
            final File parent = file.getParentFile();
            if (parent != null) {
                if (!parent.mkdirs() && !parent.isDirectory()) {
                    throw new IOException("Directory '" + parent + "' could not be created");
                }
            }
        }
        return new FileOutputStream(file, append);
    }

    public static FileInputStream openInputStream(final File file) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (!file.canRead()) {
                throw new IOException("File '" + file + "' cannot be read");
            }
        } else {
            throw new FileNotFoundException("File '" + file + "' does not exist");
        }
        return new FileInputStream(file);
    }

    /**
     * 删除文件或文件夹
     *
     * @param file 文件或文件夹
     */
    public void delDir(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File childFile : files) {
                    delDir(childFile);
                }
            }
        }
        // noinspection ResultOfMethodCallIgnored
        file.delete();
    }
}
