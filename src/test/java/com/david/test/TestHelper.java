package com.david.test;

import java.util.UUID;

import org.apache.commons.lang.RandomStringUtils;

public class TestHelper {
    public static String getNewUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    //TODO
    public static String getRandomIdentityCardNo() {
        return RandomStringUtils.randomNumeric(18);
    }

    public static String getICBCDebitCardNo() {
        return "621226" + RandomStringUtils.randomNumeric(13);
    }
}
