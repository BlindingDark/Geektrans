package com.blindingdark.geektrans.trans.jinshan.bean;

/**
 * Created by blindingdark on 17-5-10.
 */

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 各种时态
 * 英语单词专用
 */
public class Exchange {

    /**
     * 复数
     */
    @SerializedName("word_pl")
    @Expose
    private List<String> wordPl = null;
    @SerializedName("word_past")
    @Expose
    private List<String> wordPast = null;
    @SerializedName("word_done")
    @Expose
    private List<String> wordDone = null;
    @SerializedName("word_ing")
    @Expose
    private List<String> wordIng = null;
    @SerializedName("word_third")
    @Expose
    private List<String> wordThird = null;
    @SerializedName("word_er")
    @Expose
    private String wordEr = null;
    /**
     * todo 如果有过去式，还需要单独检测
     */
    @SerializedName("word_est")
    @Expose
    private String wordEst = null;

    public List<String> getWordPl() {
        return wordPl;
    }

    public void setWordPl(List<String> wordPl) {
        this.wordPl = wordPl;
    }

    public List<String> getWordPast() {
        return wordPast;
    }

    public void setWordPast(List<String> wordPast) {
        this.wordPast = wordPast;
    }

    public List<String> getWordDone() {
        return wordDone;
    }

    public void setWordDone(List<String> wordDone) {
        this.wordDone = wordDone;
    }

    public List<String> getWordIng() {
        return wordIng;
    }

    public void setWordIng(List<String> wordIng) {
        this.wordIng = wordIng;
    }

    public List<String> getWordThird() {
        return wordThird;
    }

    public void setWordThird(List<String> wordThird) {
        this.wordThird = wordThird;
    }

    public String getWordEr() {
        return wordEr;
    }

    public void setWordEr(String wordEr) {
        this.wordEr = wordEr;
    }

    public String getWordEst() {
        return wordEst;
    }

    public void setWordEst(String wordEst) {
        this.wordEst = wordEst;
    }

}