package com.example.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class BitmapUtils {
    private static final String SD_PATH = "/sdcard/dispatcher/pic/";
    private static final String IN_PATH = "/dispatcher/pic/";

    private static class ColorsUtilsHolder{
        private static final BitmapUtils INSTANCE=new BitmapUtils();
    }
    private BitmapUtils(){

    }
    public static final BitmapUtils getInstance(){
        return ColorsUtilsHolder.INSTANCE;
    }

    /**
     * 修改bitmap的颜色
     * @param inBitmap
     * @param tintColor
     * @return
     */
    public  Bitmap tintBitmap(Bitmap inBitmap , int tintColor) {
        if (inBitmap == null) {
            return null;
        }
        Bitmap outBitmap = Bitmap.createBitmap (inBitmap.getWidth(), inBitmap.getHeight() , inBitmap.getConfig());
        Canvas canvas = new Canvas(outBitmap);
        Paint paint = new Paint();
        paint.setColorFilter( new PorterDuffColorFilter(tintColor, PorterDuff.Mode.SRC_IN)) ;
        canvas.drawBitmap(inBitmap , 0, 0, paint) ;
        return outBitmap ;
    }

    /**
     * 将bitmap保存为图片
     * @param context1
     * @param mBitmap
     * @return
     */
    public  String saveBitmap2jpg(Context context1, Bitmap mBitmap){
        Context context= WeakReferenceUtils.getInstance().getContext(context1);
        String savePath;
        File filePic;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            savePath = SD_PATH;
        } else {
            savePath = context.getApplicationContext().getFilesDir()
                    .getAbsolutePath()
                    + IN_PATH;
        }
        try {
            filePic = new File(savePath + generateFileName() + ".jpg");
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
    }
    /**
     * 随机生产文件名
     *
     * @return
     */
    private  String generateFileName() {
        return UUID.randomUUID().toString();
    }
}
