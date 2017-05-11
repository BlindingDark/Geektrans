package com.blindingdark.geektrans.trans.jinshan.bean;

/**
 * Created by blindingdark on 17-5-10.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 中文含义组
 */
public class Mean {

    @SerializedName("word_mean")
    @Expose
    private String wordMean;
    @SerializedName("has_mean")
    @Expose
    private String hasMean;
    @SerializedName("split")
    @Expose
    private int split;

    @Override
    public String toString() {
        return wordMean;
    }

    public String getWordMean() {
        return wordMean;
    }

    public void setWordMean(String wordMean) {
        this.wordMean = wordMean;
    }

    public String getHasMean() {
        return hasMean;
    }

    public void setHasMean(String hasMean) {
        this.hasMean = hasMean;
    }

    public int getSplit() {
        return split;
    }

    public void setSplit(int split) {
        this.split = split;
    }

}