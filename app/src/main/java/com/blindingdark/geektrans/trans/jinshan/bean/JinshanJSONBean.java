package com.blindingdark.geektrans.trans.jinshan.bean;

/**
 * Created by blindingdark on 17-5-10.
 */

import android.text.TextUtils;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JinshanJSONBean {

    @SerializedName("word_name")
    @Expose
    private String wordName;
    @SerializedName("is_CRI")
    @Expose
    private int isCRI;
    /*    @SerializedName("exchange")
        @Expose
        private Exchange exchange;*/
    @SerializedName("symbols")
    @Expose
    private List<Symbol> symbols = null;
    @SerializedName("items")
    @Expose
    private List<String> items = null;

    public String getWordName() {
        return wordName;
    }

    public void setWordName(String wordName) {
        this.wordName = wordName;
    }

    public int getIsCRI() {
        return isCRI;
    }

    public void setIsCRI(int isCRI) {
        this.isCRI = isCRI;
    }

    public List<Symbol> getSymbols() {
        return symbols;
    }

    public void setSymbols(List<Symbol> symbols) {
        this.symbols = symbols;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        String result = "";

        if (!TextUtils.isEmpty(wordName)) {
            result += wordName;
        }

        if (null != symbols) {
            for (Symbol symbol : symbols) {
                result += "\n";
                result += symbol.toString();
            }
        }

        return result;
    }
}