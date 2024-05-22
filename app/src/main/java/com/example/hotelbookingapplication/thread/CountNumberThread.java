package com.example.hotelbookingapplication.thread;

import android.os.Handler;
import android.os.Message;

public class CountNumberThread extends Thread{
    Handler mHandler;

    public CountNumberThread(Handler mHandler) {
        this.mHandler = mHandler;
    }

    private static final int MSG_UPDATE_NUMBER = 100;
    private static final int MSG_UPDATE_NUMBER_DONE = 101;
    @Override
    public void run() {
        for (int i = 0; i <= 10; i++) {
            Message message = new Message();
            message.what = MSG_UPDATE_NUMBER;
            message.arg1 = i;
            mHandler.sendMessage(message);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        mHandler.sendEmptyMessage(MSG_UPDATE_NUMBER_DONE);
    }
}
