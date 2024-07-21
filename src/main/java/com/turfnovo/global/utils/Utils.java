package com.turfnovo.global.utils;

public class Utils {
    public static boolean isValidPhoneNumber(String phoneNo) {
        for (char c : phoneNo.toCharArray()) {
            if (c < '0' || c > '9')
                return false;
        }

        return true;
    }
}
