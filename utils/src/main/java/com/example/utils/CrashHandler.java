package com.example.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import org.w3c.dom.Text;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "CrashHandler";
    private static final boolean DEBUG = true;
    //文件路径
    private static String PATH = Environment.getExternalStorageDirectory().getPath() + File.separator + "crash";
    private static final String FILE_NAME = "crash";
    private static final String FILE_NAME_SUFEIX = ".trace";
    private static Thread.UncaughtExceptionHandler mDefaultCrashHandler;
    private static CrashHandler mCrashHandler = new CrashHandler();
    private Context mContext;

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        return mCrashHandler;
    }

    public void init(Context context) {
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        mContext = context.getApplicationContext();
    }

    public void init(Context context, String path) {
        PATH = path;
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        mContext = context.getApplicationContext();
        //检测是否存在日志文件
        checkFolder();


    }

    private void checkFolder() {
        File filedir = new File(PATH);
        if (filedir.exists()) {
            File[] files=filedir.listFiles();
            if(files!=null){//判断权限
                for (File file : files) {
                    if (file.isFile()) {
                        String _name=file.getName();
                        String filePath = file.getAbsolutePath();//获取文件路径
                        String fileName = file.getName().substring(0,_name.length()-4);//获取文件名
                        //该日志文件是否上传
                        String isUpload=SpUtils.getInstance().getString(mContext,_name,"");
                        if (!TextUtils.isEmpty(isUpload)) {
                            //上传
                            if (iReportEx!=null) {
                                if (!SpUtils.getInstance().getBoolean(mContext,_name,true)) {
                                    iReportEx.report2Server(_name, FileUtils.getInstance().readFileSdcardFile(filePath));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        try {
            //将文件写入sd卡
            writeToSDcard(ex);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        ex.printStackTrace();
        //如果系统提供了默认异常处理就交给系统进行处理，否则自己进行处理。
        if (mDefaultCrashHandler != null) {
            mDefaultCrashHandler.uncaughtException(thread, ex);
        } else {
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    public interface IReportEx {
        void report2Server(String flieName, String result);
    }

    public IReportEx iReportEx = null;

    public void setReportExListener(IReportEx iReportEx) {
        this.iReportEx = iReportEx;
    }

    private void reportEx(String flieName, Throwable ex) {
        if (iReportEx != null) {
            try {
                StringBuffer stringBuffer = new StringBuffer();
                String scx = exception(ex);
                stringBuffer.append(scx);
                PackageManager pm = mContext.getPackageManager();
                PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
                stringBuffer.append("App Version:" + pi.versionName + "_" + pi.versionCode);
                stringBuffer.append("OS version:" + Build.VERSION.RELEASE + "_" + Build.VERSION.SDK_INT);
                stringBuffer.append("Vendor:" + Build.MANUFACTURER);
                stringBuffer.append("Model:" + Build.MODEL);
                stringBuffer.append("Vendor:" + Build.MANUFACTURER);
                stringBuffer.append("CPU ABI:" + Build.CPU_ABI);
                iReportEx.report2Server(flieName,stringBuffer.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
   public void setOverUpload(String fileName,boolean isok){
        SpUtils.getInstance().putBoolean(mContext,fileName,isok);
    }

    public static String exception(Throwable t) throws IOException {
        if (t == null)
            return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            t.printStackTrace(new PrintStream(baos));
        } finally {
            baos.close();
        }
        return baos.toString();
    }

    //将异常写入文件
    private void writeToSDcard(Throwable ex) throws IOException, PackageManager.NameNotFoundException {
        //如果没有SD卡，直接返回
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return;
        }
        File filedir = new File(PATH);
        if (!filedir.exists()) {
            filedir.mkdirs();
        }
        long currenttime = System.currentTimeMillis();
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(currenttime));

        File exfile = new File(PATH + File.separator + FILE_NAME + time + FILE_NAME_SUFEIX);
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(exfile)));
        //Log.e("错误日志文件路径", "" + exfile.getAbsolutePath());
        pw.println(time);
        PackageManager pm = mContext.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
        //当前版本号
        pw.println("App Version:" + pi.versionName + "_" + pi.versionCode);
        //当前系统
        pw.println("OS version:" + Build.VERSION.RELEASE + "_" + Build.VERSION.SDK_INT);
        //制造商
        pw.println("Vendor:" + Build.MANUFACTURER);
        //手机型号
        pw.println("Model:" + Build.MODEL);
        //CPU架构
        pw.println("CPU ABI:" + Build.CPU_ABI);

        ex.printStackTrace(pw);
        pw.close();
        //写入后在这里可以进行上传操作
        reportEx(exfile.getName(),ex);
    }
}