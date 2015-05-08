package com.lanhaijiye.WebMarket.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.lanhaijiye.WebMarket.R;
import com.lanhaijiye.WebMarket.activities.inter.Changeable;
import com.lanhaijiye.WebMarket.activities.inter.Storeable;
import com.lanhaijiye.WebMarket.fragments.abs.BaseFragment;

/**
 * Created by Administrator on 2015/5/7.
 */
public class SignUpMobileFragment extends BaseFragment implements View.OnClickListener, TextWatcher {

    private Button button;
    private EditText mobile_num;
    private SignUpMobileVerifyFragment mobile_verify_fragment;
    private String num;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mobile_sign_up_layout,container,false);

        //todo �¼�����


        //ע��back����
        view.findViewById(R.id.sign_up_back).setOnClickListener(this);
        //ע���ѯ�����б���
        view.findViewById(R.id.sign_up_view_country_list).setOnClickListener(this);
        //ע����һ����ť
        button = (Button) view.findViewById(R.id.sign_up_nexn_step_btn);
        button.setOnClickListener(this);

        //ע�������
        mobile_num = (EditText)view. findViewById(R.id.sign_up_mobile_num);
        mobile_num.addTextChangedListener(this);

        return view;
    }

    @Override
    public boolean canGoBack() {
        return false;
    }

    @Override
    public void goBack() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_up_back:
                this.getActivity().finish();
                break;
            case R.id.sign_up_view_country_list:
                break;
            case R.id.sign_up_nexn_step_btn:
                //��ȡ������,�����ֻ�����
                num = mobile_num.getText().toString();
                ((Storeable)getActivity()).SaveNum(num);
                //todo ʵ����Ƭ�л�
                ((Changeable)getActivity()).change(this);
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String x = mobile_num.getText().toString().trim();

        if (!"".equals(x)) {
            if (!button.isEnabled()) {
                button.setEnabled(true);
            }
        } else {
            if (button.isEnabled()) {
                button.setEnabled(false);
            }
        }
    }
}
