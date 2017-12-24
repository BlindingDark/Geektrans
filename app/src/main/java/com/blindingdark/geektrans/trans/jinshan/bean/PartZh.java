package com.blindingdark.geektrans.trans.jinshan.bean;

import android.text.TextUtils;

import com.blindingdark.geektrans.tools.MyStringUnits;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by blindingdark on 17-5-10.
 */

public class PartZh {
    /**
     * 词性 如 动 名
     */
    @SerializedName("part_name")
    @Expose
    private String partName;

    @SerializedName("means")
    @Expose
    private List<Mean> means = null;

    @Override
    public String toString() {
        String result = "";
        if (!TextUtils.isEmpty(partName)) {
            result += partName;
        }
        if (!(result.length() == 0)) {
            result += " ";
        }
        if (null != means) {
            for (Mean mean : means) {
                result += mean.toString();
                result += "；";
            }
            result = MyStringUnits.cutLast(result);
        }
        return result;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public List<Mean> getMeans() {
        return means;
    }

    public void setMeans(List<Mean> means) {
        this.means = means;
    }
}
