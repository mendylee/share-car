package com.jinzhun.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式手机号码校验类
 */
public class PhoneUtil {
	private static String REGEX = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
	private static Pattern PATTERN = Pattern.compile(REGEX);
	
    private PhoneUtil() {
        throw new IllegalStateException("Phone Utility class");
    }

	public static boolean checkPhone(String phone) {
		if (phone == null || phone.length() != 11) {
			return Boolean.FALSE;
		}
		Matcher m = PATTERN.matcher(phone);
		return m.matches();
	}
}
