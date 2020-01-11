package com.example.utils;

import org.apache.http.util.EncodingUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileUtils {
    private static class FileUtilsHolder{
        private static final FileUtils INSTANCE=new FileUtils();
    }
    private FileUtils(){

    }
    public static final FileUtils getInstance(){
        return FileUtilsHolder.INSTANCE;
    }


//    public String readSDFile(String fileName) throws IOException {
//
//        File file = new File(fileName);
//
//        FileInputStream fis = new FileInputStream(file);
//
//        int length = fis.available();
//
//        byte [] buffer = new byte[length];
//        fis.read(buffer);
//
//        String res = EncodingUtils.getString(buffer, "UTF-8");
//
//        fis.close();
//        return res;
//    }

    public String readFileSdcardFile(String fileName){
        String res="";
        try{
            FileInputStream fin = new FileInputStream(fileName);

            int length = fin.available();

            byte [] buffer = new byte[length];
            fin.read(buffer);

            res = EncodingUtils.getString(buffer, "UTF-8");

            fin.close();
        }

        catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }


}