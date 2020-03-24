package com.example.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class FileUtils {
    private static class FileUtilsHolder {
        private static final FileUtils INSTANCE = new FileUtils();
    }
    private FileUtils() {

    }
    public static final FileUtils getInstance() {
        return FileUtilsHolder.INSTANCE;
    }

    /**
     * 随机生产文件名
     *
     * @return
     */
    private String generateFileName() {
        return UUID.randomUUID().toString();
    }

/*    *//**
     * 保存bitmap到本地
     *
     * @param mBitmap
     * @return
     *//*
    @Deprecated
    public String saveBitmap(Context context1, Bitmap mBitmap) {
        Context context = WeakReferenceUtils.getInstance().getContext(context1);
        File filePic;
        try {
            filePic = new File(context.getObbDir().getAbsolutePath() + File.separator + LruUtils.getInstance().getData(BaseApp.mContext, Constants.USERNAME) + File.separator + "Photos" +File.separator+ TimeUtils.getInstance().getData("yyyyMMddHHmmss") + ".jpg");
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

        return filePic.getAbsolutePath();
    }*/
    /**
     * 保存bitmap到本地
     *
     * @param mBitmap
     * @return
     */
    /**
     *
     * @param context1
     * @param user 所属用户文件夹
     * @param fileName 文件名
     * @param mBitmap 保存对象
     * @return
     */
    public String saveBitmap(Context context1, String user,String fileName,Bitmap mBitmap) {
        Context context = WeakReferenceUtils.getInstance().getContext(context1);
        File filePic;
        try {
            filePic = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + File.separator + user + File.separator + "Photos" +File.separator+ fileName + ".jpg");
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return filePic.getAbsolutePath();
    }
    /**
     *
     * @param context
     * @param postfix "mp4"
     * @param type  Environment.DIRECTORY_MOVIES
     * @return
     */
    public File createFilePath(Context context,String postfix,String type) {
        File MOVIES=context.getExternalFilesDir(type);
        File file = new File(MOVIES + File.separator + generateFileName()+"."+postfix);
        return file;
    }
    /**
     * 适配android 10
     * @param postfix "mp4"
     * @return
     */
    public File createMovesFilePath(Context context,String postfix) {
        File MOVIES=context.getExternalFilesDir(Environment.DIRECTORY_MOVIES);
        File file = new File(MOVIES + File.separator + generateFileName()+"."+postfix);
        return file;
    }
    /**
     *
     * @param path   包名文件下的子文件夹
     * @return
     */
    public String createFilefolder(Context context,String path) {
        File DOCUMENTS=context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        File fileDir = new File(DOCUMENTS + File.separator + path+File.separator);
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }
        return fileDir.getAbsolutePath();
    }
    /**
     * 获取文件的MD5值
     * @param path
     * @return
     */
    public String getFileMD5Value(String path) {
        File file = new File(path);
        if (!file.exists() || !file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[512 * 1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }


    /**
     * @param context
     * @param tag            文件的唯一标识
     * @param sourceFilePath 要分片文件的地址
     * @param partFileLength 分片的大小 byte
     * @return
     * @throws Exception
     */
    public List<String> fileList(Context context, String tag, String sourceFilePath, int partFileLength,String user) throws Exception {

        List<String> fileList = new ArrayList<>();
        int partNumber = 1;
        File sourceFile = null;//要分片的文件
        File targetFile = null;//分片时实例出来的文件
        InputStream ips = null;//要分片文件转换的输入流
        OutputStream ops = null;//分片后，分片文件的输出流

        OutputStream configOps = null;//该文件流用于存储文件分割后的相关信息，包括分割后的每个子文件的编号和路径,以及未分割前文件名
        Properties partInfo = null;//properties用于存储文件分割的信息
        byte[] buffer = null;


        sourceFile = new File(sourceFilePath);//待分割文件
        ips = new FileInputStream(sourceFile);//找到读取源文件并获取输入流

        configOps = new FileOutputStream(new File(GetFileSplitPath(context, sourceFile.getName(),user) + "/config.properties"));
        buffer = new byte[partFileLength];//开辟缓存空间
        int tempLength = 0;
        partInfo = new Properties();//key:1开始自动编号 value:文件路径

        while ((tempLength = ips.read(buffer, 0, partFileLength)) != -1) {
            String targetFilePath = GetFileSplitPath(context, sourceFile.getName(),user) + "/" + tag + "_" + partNumber;//分割后的文件路径
            partInfo.setProperty((partNumber++) + "", targetFilePath);//将相关信息存储进properties
            targetFile = new File(targetFilePath);
            ops = new FileOutputStream(targetFile);//分割后文件
            ops.write(buffer, 0, tempLength);//将信息写入碎片文件

            ops.close();//关闭碎片文件
            fileList.add(targetFilePath);
        }
        partInfo.setProperty("name", sourceFile.getName());//存储源文件名
        partInfo.store(configOps, "ConfigFile");//将properties存储进实体文件中
        ips.close();//关闭源文件流
        return fileList;
    }
//    @Deprecated
//    public String GetPhotoSplitPath(Context context, String name) {
//        name = name.replace(".mp4", "");
//        String path = getAppPersonalPath(context) + File.separator + "fileSplit" + File.separator + name;
//        File file = new File(path);
//        if (!file.exists()) {
//            file.mkdirs();
//        }
//        return path;
//    }

    /**
     * 获取文件分片的地址
     * @param context
     * @param urlName url 地址
     * @param user  用户名
     * @return
     */
    public String GetFileSplitPath(Context context, String urlName,String user) {
        urlName = urlName.replace(".mp4", "");
        String path = getAppPersonalPath(context,user) + File.separator + "fileSplit" + File.separator + urlName;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }
/*    @Deprecated
    public String getAppPersonalPath(Context context) {
        return context.getObbDir().getAbsolutePath() + File.separator + LruUtils.getInstance().getData(context, Constants.USERNAME);
    }*/

    /**
     * 获取图片的存储路径
     * @param context
     * @param user user用户名
     * @return
     */
    public String getAppPersonalPath(Context context,String user) {
        return context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + File.separator +user;
    }
}
