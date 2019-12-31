package com.example.utils;

import android.app.Activity;

import java.util.Stack;

public class FastStackUtils {
    private final String TAG = this.getClass().getSimpleName();
    private static Stack<Activity> mActivityStack;
    private static class ObjectUtilsHolder{
        private static final FastStackUtils INSTANCE=new FastStackUtils();
    }
    private FastStackUtils(){

    }
    public static final FastStackUtils getInstance(){
        return ObjectUtilsHolder.INSTANCE;
    }
    /**
     * 获取Stack
     *
     * @return
     */
    public Stack<Activity> getStack() {
        if (mActivityStack == null) {
            mActivityStack = new Stack();
        }
        return mActivityStack;
    }

    /**
     * 获取最后一个入栈Activity理论上是应用当前停留Activity
     * (前提是所有Activity均在onCreate调用 push onDestroy调用pop)
     *
     * @return
     */
    public Activity getCurrent() {
        if (mActivityStack != null && mActivityStack.size() != 0) {
            Activity activity = mActivityStack.lastElement();
            LoggerManager.i(TAG, "get current activity:" + activity.getClass().getSimpleName());
            return activity;
        } else {
            return null;
        }
    }

    /**
     * 获取前一个Activity
     *
     * @return
     */
    public Activity getPrevious() {
        if (mActivityStack != null && mActivityStack.size() >= 2) {
            Activity activity = mActivityStack.get(mActivityStack.size() - 2);
            LoggerManager.i(TAG, "get Previous Activity:" + activity.getClass().getSimpleName());
            return activity;
        } else {
            return null;
        }
    }

    /**
     * 根据Class 获取Activity
     *
     * @param cls
     * @return
     */
    public Activity getActivity(Class cls) {
        if (mActivityStack == null || mActivityStack.size() == 0 || cls == null) {
            return null;
        }
        for (Activity activity : mActivityStack) {
            if (activity != null && activity.getClass() == cls) {
                return activity;
            }
        }
        return null;
    }

    /**
     * 入栈
     *
     * @param activity
     */
    public FastStackUtils push(Activity activity) {
        if (mActivityStack == null) {
            mActivityStack = new Stack();
        }
        mActivityStack.add(activity);
        LoggerManager.i(TAG, "push stack activity:" + activity.getClass().getSimpleName());
        return this;
    }

    public FastStackUtils pop(Activity activity) {
        return pop(activity, true);
    }

    /**
     * 出栈
     *
     * @param activity Activity对象
     * @param isFinish 是否关闭Activity 调用{@link Activity#finish()}--在生命周期onActivityDestroyed的时候建议传false不然Activity状态无法保存
     * @return
     */
    public FastStackUtils pop(Activity activity, boolean isFinish) {
        if (activity != null) {
            LoggerManager.i(TAG, "remove current activity:" + activity.getClass().getSimpleName() + ";isFinishing" + activity.isFinishing());
            if (mActivityStack != null && mActivityStack.contains(activity)) {
                mActivityStack.remove(activity);
                LoggerManager.i(TAG, "remove current activity:" + activity.getClass().getSimpleName() + ";size:" + mActivityStack.size());
            }
            if (isFinish) {
                activity.finish();
            }
        }
        return this;
    }

    /**
     * 将栈里的Activity全部清空
     */
    public FastStackUtils popAll() {
        if (mActivityStack != null) {
            while (mActivityStack.size() > 0) {
                Activity activity = this.getCurrent();
                if (activity == null) {
                    break;
                }
                pop(activity);
            }
        }
        return this;
    }

    /**
     * 将堆栈里退回某个Activity为止
     *
     * @param cls
     */
    public FastStackUtils popAllExceptCurrent(Class cls) {
        while (true) {
            Activity activity = this.getCurrent();
            if (activity == null || activity.getClass().equals(cls)) {
                return this;
            }
            pop(activity);
        }
    }

    /**
     * 只留下栈顶一个Activity
     */
    public FastStackUtils popAllExceptCurrent() {
        while (true) {
            Activity activity = this.getPrevious();
            if (activity == null) {
                return this;
            }
            pop(activity);
        }
    }

    public FastStackUtils exit() {
        return exit(true);
    }

    /**
     * 应用程序退出
     *
     * @param kill 是否杀掉进程
     * @return
     */
    public FastStackUtils exit(boolean kill) {
        try {
            popAll();
            if (kill) {
                //退出JVM(java虚拟机),释放所占内存资源,0表示正常退出(非0的都为异常退出)
                System.exit(0);
                //从操作系统中结束掉当前程序的进程
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        } catch (Exception e) {
            System.exit(-1);
            LoggerManager.e(TAG, "exit():" + e.getMessage());
        }
        return this;
    }
}
