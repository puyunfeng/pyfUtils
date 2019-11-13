package com.example.pyfutils

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.utils.ThreadUtlis
import com.example.utils.TimeUtils
import com.hjq.toast.ToastUtils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("当前线程","主线程="+ThreadUtlis.getInstance().isMainThread)
        Log.d("当前时间=",TimeUtils.getInstance().getData("yyyy-MM-dd HH:mm:ss"))
        Log.d("当前时间=",TimeUtils.getInstance().time.toString()+"")
        Log.d("当前时间=",TimeUtils.getInstance().getTime("2019.11.11 12:12","yyyy.MM.dd HH:mm").toString())
        Log.d("当前时间=",TimeUtils.getInstance().getCalendarBoundT("yyyy-MM-dd HH:mm:ss"))
        Log.d("当前时间=",TimeUtils.getInstance().getCalendarBoundB("yyyy-MM-dd HH:mm:ss"))
        Log.d("当前时间=",TimeUtils.getInstance().getTimeBoundT("yyyy-MM-dd HH:mm:ss").toString())
        Log.d("当前时间=",TimeUtils.getInstance().getTimeBoundB("yyyy-MM-dd HH:mm:ss").toString())

    }
}
