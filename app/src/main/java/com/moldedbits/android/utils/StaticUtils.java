package com.moldedbits.android.utils;

public class StaticUtils {

    public static int add(int a, int b) {
        return a + b;
    }

    public static void staticWithCallback(CustomCallback callback) {
        callback.onResponse("actual response");
    }

    public interface CustomCallback {
        void onResponse(String response);
    }
}
