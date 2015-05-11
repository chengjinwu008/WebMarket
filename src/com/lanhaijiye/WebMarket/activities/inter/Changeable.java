package com.lanhaijiye.WebMarket.activities.inter;

import com.lanhaijiye.WebMarket.fragments.abs.BaseFragment;

/**
 * Created by Administrator on 2015/5/7.
 */
public interface Changeable {
    /**
     * 由碎片调用的跳转函数
     * @param nowFragment 当前的碎片，输入this即可
     */
    void change(BaseFragment nowFragment);
}
