package am.itspace.projectscope.util;

import org.apache.commons.codec.digest.DigestUtils;

public class EncriptionUtil {

    public EncriptionUtil() {
    }

    public static String encrypt(String text) {
        return DigestUtils.sha256Hex(text);
    }
}
