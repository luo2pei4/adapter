package com.lp.adapter.utils;

import java.util.UUID;

import com.lp.adapter.constant.Constants;

public class StringUtil {

	public static boolean isEmpty(String str) {
		
		if (str != null && !Constants.STRING_BLANK.equals(str.trim())) {
			
			return false;
		
		} else {
			
			return true;
		}
	}

	public static String getUuid() {

		return UUID.randomUUID().toString();
	}
}
