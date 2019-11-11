package com.example.utils;

public class ObjectUtils {
    private static class ObjectUtilsHolder{
        private static final ObjectUtils INSTANCE=new ObjectUtils();
    }
    private ObjectUtils(){

    }
    public static final ObjectUtils getInstance(){
        return ObjectUtilsHolder.INSTANCE;
    }
}
