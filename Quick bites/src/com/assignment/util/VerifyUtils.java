package com.assignment.util;

import java.util.Objects;

/*
 * Check service data
 */
public class VerifyUtils {

    /*
     * isEmpty  Determines whether the string is empty
     */
    public static Boolean isEmpty(String str) {
        return Objects.isNull(str) || "".equals(str) || "null".equals(str);
    }

    /*
     * Verify that zipCode is empty and numeric
     */
    public static Boolean isNumber(String str) {
        if (isEmpty(str) || !str.matches("\\d+")) {
            return false;
        } else return true;
    }

}
