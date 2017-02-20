package com.james;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * 在Android中经常需要创建一个循环线程，有耗时操作时候，放到里面去操作，如果没有耗时操作，就让该线程处于等待，但是不要杀死它，
 * 最好不要一旦有耗时任务，就立刻创建一个新线程，因为会有性能问题,所以就出现了handlerThread 这种东西,handlerThread 也是一个线程
 * 只不过里面封装了looper,当然也存在messageQueue,多次传入任务时,会按队列顺序执行(可多次点击按钮查看log信息)
 * 使用步骤
 * 1.实例化handlerThread
 * 2.start 该线程
 * 3.创建Handler,传入handlerThread 的looper 对象,重新handlemessage 方法
 * 4.使用handler.sendMessage等方法发送消息
 * 5.不使用时,可以调用handlerThread.quit() 退出线程
 */
public class HandlerThreadTestActivity extends AppCompatActivity {

    private Handler mHandler;
    private int mCount;
    private final int DO_WORKING = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_thread_test);
        Button button = (Button) findViewById(R.id.bt);
        HandlerThread handlerThread = new HandlerThread("handlerThread");
        handlerThread.start();
        mCount = 0;
        mHandler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                mCount++;
                Log.i("james", "第" + mCount + "次计算开始了...当前时间" + System.currentTimeMillis() + "..." +
                        "当前线程:" + Thread.currentThread().getName());
                SystemClock.sleep(5000);
                Log.i("james", "第" + mCount + "次计算结束了...当前时间" + System.currentTimeMillis() + "..." +
                        "当前线程:" + Thread.currentThread().getName());
            }
        };
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.sendEmptyMessage(DO_WORKING);
            }
        });
        //调用此方法可以退出handlerThread Looper.loop 方法,将会结束线程
        //   handlerThread.quit();
    }

    @Override
    protected void onPause() {
        mHandler.removeMessages(DO_WORKING);
        super.onPause();
    }
}
