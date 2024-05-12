package com.maidscc.librarymanagementsystem.util.Response;

public class ResponseMaker {
    public static <T> Response<T> successRes(String message, T body) {
        return new Response<>("success", message, body);
    }

    public static <T> Response<T> failRes(String message) {
        return new Response<>("failure", message, (T) "No data");
    }

    public static <T> Response<T> failRes(String message, T body) {
        return new Response<>("failure", message, body);
    }
}
