package com.example.utils;


import androidx.exifinterface.media.ExifInterface;



import java.io.File;

/**
 * 图片工具类
 */
public class PictureUtils {

    private static class PictureUtilsHolder {
        private static final PictureUtils INSTANCE = new PictureUtils();
    }
    private PictureUtils() {
    }
    public static final PictureUtils getInstance() {
        return PictureUtilsHolder.INSTANCE;
    }
    /**
     * 将经纬度信息写入JPEG图片文件里
     *
     * @param picPath      JPEG图片文件路径
     * @param dLat         纬度
     * @param dLon         经度
     * @param upLoadStatus "0":待上传
     *                     "1":上传中
     *                     "2":已上传
     *                     "3":排队中
     */
    @Deprecated
    public void writeLatLonIntoJpeg(String picPath, double dLat, double dLon, String upLoadStatus) {
        File file = new File(picPath);
        if (file.exists()) {
            try {
                ExifInterface exif = new ExifInterface(picPath);
                exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE,
                        decimalToDMS(dLat));
                exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF,
                        dLat > 0 ? "N" : "S"); // 区分南北半球
                exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE,
                        decimalToDMS(dLon));
                exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF,
                        dLon > 0 ? "E" : "W"); // 区分东经西经
                exif.setAttribute(ExifInterface.TAG_DATETIME, TimeUtils.getInstance().getData("yyyy-MM-dd HH:mm:ss") + "");
                exif.setAttribute(ExifInterface.TAG_MAKER_NOTE, upLoadStatus);
                exif.saveAttributes();
                LogUtil.i("TAG_GPS_LATITUDE", exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE));
                LogUtil.i("TAG_GPS_LONGITUDE", exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE));
                LogUtil.i("TAG_GPS_mark", exif.getAttribute(ExifInterface.TAG_MAKER_NOTE));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  将经纬度信息写入JPEG图片文件里
     * @param picPath 图片路径
     * @param dLat   维度
     * @param dLon   经度
     * @param timestamp 时间戳
     * @param customStr 自定义字段
     */
    public void writeLatLon2Pic(String picPath, double dLat, double dLon, String timestamp,String customStr) {
        File file = new File(picPath);
        if (file.exists()) {
            try {
                ExifInterface exif = new ExifInterface(picPath);
                exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE,
                        decimalToDMS(dLat));
                exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF,
                        dLat > 0 ? "N" : "S"); // 区分南北半球
                exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE,
                        decimalToDMS(dLon));
                exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF,
                        dLon > 0 ? "E" : "W"); // 区分东经西经
                exif.setAttribute(ExifInterface.TAG_DATETIME, timestamp);
                exif.setAttribute(ExifInterface.TAG_MAKER_NOTE, customStr);
                exif.saveAttributes();
                LogUtil.i("TAG_GPS_LATITUDE", exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE));
                LogUtil.i("TAG_GPS_LONGITUDE", exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE));
                LogUtil.i("TAG_GPS_mark", exif.getAttribute(ExifInterface.TAG_MAKER_NOTE));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 浮点型经纬度值转成度分秒格式
     *
     * @param coord
     * @return
     */
    public String decimalToDMS(double coord) {
        String output, degrees, minutes, seconds;
        double mod = coord % 1;
        int intPart = (int) coord;
        degrees = String.valueOf(intPart);
        coord = mod * 60;
        mod = coord % 1;
        intPart = (int) coord;
        if (intPart < 0) {
            intPart *= -1;
        }
        minutes = String.valueOf(intPart);
        coord = mod * 60;
        intPart = (int) coord;
        if (intPart < 0) {
            intPart *= -1;
        }
        seconds = String.valueOf(intPart);
        output = degrees + "/1," + minutes + "/1," + seconds + "/1";
        return output;
    }

}
