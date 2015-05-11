package com.lanhaijiye.WebMarket.activities.inter;

/**
 * Created by android on 2015/5/11.
 */
public interface TimerFlagChangeable {
    /**
     * 使用了自定义的计时器可以实现这个接口，便于其他类控制该类的计时器关停
     * @param flag false则停止计时器
     */
    void changeTimerFlag(boolean flag);
}
