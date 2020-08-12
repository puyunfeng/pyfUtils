package com.example.pyfutils

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.utils.LogUtil
import com.example.utils.RxTimerUtils

class MainActivity : AppCompatActivity() {
    private val mTag:String=this.javaClass.simpleName;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        Log.d("当前线程","主线程="+ThreadUtlis.getInstance().isMainThread)
//        Log.d("当前时间=",TimeUtils.getInstance().getData("yyyy-MM-dd HH:mm:ss"))
//        Log.d("当前时间=",TimeUtils.getInstance().time.toString()+"")
//        Log.d("当前时间=",TimeUtils.getInstance().getTime("2019.11.11 12:12","yyyy.MM.dd HH:mm").toString())
//        Log.d("当前时间=",TimeUtils.getInstance().getCalendarBoundT("yyyy-MM-dd HH:mm:ss"))
//        Log.d("当前时间=",TimeUtils.getInstance().getCalendarBoundB("yyyy-MM-dd HH:mm:ss"))
//        Log.d("当前时间=",TimeUtils.getInstance().getTimeBoundT("yyyy-MM-dd HH:mm:ss").toString())
//        Log.d("当前时间=",TimeUtils.getInstance().getTimeBoundB("yyyy-MM-dd HH:mm:ss").toString())
//        var time= TimeUtils.getInstance().getDiffDate(TimeUtils.getInstance().getTime()-(TimeUtils.getInstance().getTime()-1000*5))

    }

    fun click(view: View) {
        LogUtil.d(mTag,"发生点击事件")
//        RxTimerUtils.getInstance().interval(0,1000, {
//            Log.d(mTag,"RxTimerUtils执行")
//        },"Test")
        RxTimerUtils.getInstance().onlyOneDelay(1000,{
            Log.d(mTag,"RxTimerUtils执行")
        },"Test")
    }

    fun cancelClick(view: View) {
        LogUtil.d(mTag,"取消点击事件")
        RxTimerUtils.getInstance().cancelTarget("Test")
    }
}
