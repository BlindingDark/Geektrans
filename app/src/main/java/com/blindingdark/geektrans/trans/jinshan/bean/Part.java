package com.blindingdark.geektrans.trans.jinshan.bean;

/**
 * Created by blindingdark on 17-5-10.
 */


import android.text.TextUtils;

import java.util.List;

import com.blindingdark.geektrans.tools.MyStringUnits;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 英文单词意思部分
 */
public class Part {

    /**
     * 英文单词专用，如果不为空串则表示单词形式（如 vi、n）
     */
    @SerializedName("part")
    @Expose
    private String part;
    /**
     * 英文单词的意思，字符串
     */
    @SerializedName("means")
    @Expose
    private List<String> means = null;

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public List<String> getMeans() {
        return means;
    }

    public void setMeans(List<String> means) {
        this.means = means;
    }

    @Override
    public String toString() {
        /*
        * 返回值为最终结果
        * 示例
        * part == "vt.& vi.",
        * means == ["爱，热爱","爱戴","喜欢","赞美，称赞"]
        * 那么输出的应该是
        * vt.& vi.[一个空格]爱，热爱；爱戴；喜欢；赞美，称赞
        * 不同的含义用中文的分号间隔开
        * */
        String result = "";
        if (!TextUtils.isEmpty(part)) {
            result += part;
        }
        if (!(result.length() == 0)) {
            result += " ";
        }
        if (null != means) {
            for (String s : means) {
                result += s;
                result += "；";
            }
            result = MyStringUnits.cutLast(result);
        }
        return result;
    }
}