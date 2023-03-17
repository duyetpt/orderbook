package com.vn.onus;

public final class ComparingResult {
    public static boolean isEqual(int result) {
        return result == 0;
    }

    public static boolean isGreater(int result) {
        return result < 0;
    }

    public static boolean isLess(int result) {
        return result > 0;
    }

    public static boolean isEqualOrGreater(int result) {
        return isGreater(result) || isEqual(result);
    }

    public static boolean isEqualOrLess(int result) {
        return isLess(result) || isEqual(result);
    }
}
