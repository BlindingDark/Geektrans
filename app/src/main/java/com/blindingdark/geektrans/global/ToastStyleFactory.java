package com.blindingdark.geektrans.global;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.blindingdark.geektrans.R;

/**
 * Created by BlindingDark on 2016/12/1 0001.
 */

public class ToastStyleFactory {
    LayoutInflater inflater;

    public ToastStyleFactory(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @Nullable
    public View getSimpleToastStyleById(String id) {
        View view = null;
        switch (id) {
            case "0":
                view = inflater.inflate(R.layout.toast_simple, null);// 得到自定气泡
                break;
            case "1":
                view = inflater.inflate(R.layout.toast_simple_card_write, null);// 得到自定气泡
                break;
            default:
                view = inflater.inflate(R.layout.toast_simple, null);// 得到自定气泡
                break;
        }
        return view;
    }

    @Nullable
    public View getSoundToastStyleById(String id) {
        View view = null;
        switch (id) {
            case "0":
                view = inflater.inflate(R.layout.toast_with_sound, null);// 得到自定气泡
                break;
            case "1":
                view = inflater.inflate(R.layout.toast_with_sound_card_write, null);// 得到自定气泡

                break;
            default:
                view = inflater.inflate(R.layout.toast_with_sound, null);// 得到自定气泡
                break;
        }
        return view;
    }


}
