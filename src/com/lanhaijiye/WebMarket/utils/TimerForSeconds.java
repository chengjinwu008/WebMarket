package com.lanhaijiye.WebMarket.utils;

/**
 * Created by CJQ on 2015/5/7.
 */
public class TimerForSeconds extends Thread {

    private int timeLeft;
    private TimerListener listener;

    public TimerForSeconds(int timeLeft, TimerListener listener) {
        /**
         * 每秒调用
         */
        this.timeLeft = timeLeft;
        /**
         * 计时完毕再调用
         */
        this.listener = listener;
    }

    public interface TimerListener {
        void onEverySeconds(int timeLeft);

        void onTimeUp();
    }

    @Override
    public void run() {
        while(timeLeft>=1){
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            listener.onEverySeconds(--timeLeft);
        }
        listener.onTimeUp();
    }
}
