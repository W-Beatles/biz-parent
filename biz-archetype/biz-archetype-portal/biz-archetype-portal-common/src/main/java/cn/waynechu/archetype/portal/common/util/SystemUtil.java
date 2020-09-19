package cn.waynechu.archetype.portal.common.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zhuwei
 * @since 2020-06-21 18:40
 */
@Slf4j
public class SystemUtil {

    /**
     * 是否为Windows环境
     *
     * @return 是否为Windows环境
     */
    public static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("windows");
    }

    /**
     * 是否为Linux环境
     *
     * @return 是否为Linux环境
     */
    public static boolean isLinux() {
        return System.getProperty("os.name").toLowerCase().contains("linux");
    }

    /**
     * 脚本授权775
     *
     * @param scriptPaths 脚本路径
     */
    public static void setShellPermission(String... scriptPaths) {
        for (String scriptPath : scriptPaths) {
            if (!SystemUtil.isWindows()) {
                Set<PosixFilePermission> permissions = new HashSet<>();
                permissions.add(PosixFilePermission.OWNER_READ);
                permissions.add(PosixFilePermission.OWNER_WRITE);
                permissions.add(PosixFilePermission.OWNER_EXECUTE);
                permissions.add(PosixFilePermission.GROUP_READ);
                permissions.add(PosixFilePermission.GROUP_EXECUTE);
                permissions.add(PosixFilePermission.OTHERS_READ);
                permissions.add(PosixFilePermission.OTHERS_EXECUTE);

                try {
                    Path shellPath = Paths.get(new File(scriptPath).getAbsolutePath());
                    Files.setPosixFilePermissions(shellPath, permissions);
                    log.info("Script authorization succeed, path: {}", shellPath.toString());
                } catch (Exception e) {
                    log.error("Script authorization failed: {}", e.getMessage());
                }
            }
        }
    }
}
