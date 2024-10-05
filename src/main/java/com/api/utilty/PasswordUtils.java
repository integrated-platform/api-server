package com.api.utilty;
import org.apache.commons.codec.digest.DigestUtils;


public class PasswordUtils {

    // 비밀번호 암호화
    public static String encryptPassword(String password) {
        return DigestUtils.sha512Hex(password); // SHA-512 해시
    }

    // 비밀번호 검증
    public static boolean verifyPassword(String rawPassword, String encryptedPassword) {
        String hashedRawPassword = encryptPassword(rawPassword); // 입력된 비밀번호 해시
        return hashedRawPassword.equals(encryptedPassword); // 해시 비교
    }
}