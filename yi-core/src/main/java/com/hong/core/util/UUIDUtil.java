package com.hong.core.util;

import java.util.UUID;

public abstract class UUIDUtil {

	public static String generateUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}