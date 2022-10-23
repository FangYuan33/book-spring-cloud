package com.cloud.mall.fy.common.utils;

import com.cloud.mall.fy.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.security.MessageDigest;

@Slf4j
public class TokenUtil {

    public static String generateToken(Long userId) {
        try {
           String str =  "" + System.currentTimeMillis() + userId;
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            String result = new BigInteger(1, md.digest()).toString(16);
            if (result.length() == 31) {
                result = result + "-";
            }

            log.info("User: {}'s token is {}", userId, result);
            return result;
        } catch (Exception e) {
            log.error("生成Token异常", e);
            throw new BusinessException("生成Token异常");
        }
    }
}
