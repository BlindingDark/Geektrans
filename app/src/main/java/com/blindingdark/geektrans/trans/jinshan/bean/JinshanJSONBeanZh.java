package com.blindingdark.geektrans.trans.jinshan.bean;

/**
 * Created by blindingdark on 17-5-10.
 */

import android.text.TextUtils;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JinshanJSONBeanZh {
    @SerializedName("word_name")
    @Expose
    private String wordName;

    @SerializedName("symbols")
    @Expose
    private List<SymbolZh> symbols = null;

    public String getWordName() {
        return wordName;
    }

    public void setWordName(String wordName) {
        this.wordName = wordName;
    }

    public List<SymbolZh> getSymbols() {
        return symbols;
    }

    public void setSymbols(List<SymbolZh> symbols) {
        this.symbols = symbols;
    }

    @Override
    public String toString() {
        String result = "";
        if (!TextUtils.isEmpty(wordName)) {
            result += wordName;
        }
        if (null != symbols) {
            for (SymbolZh symbol : symbols) {
                String temp = symbol.toString();
                if (!TextUtils.isEmpty(temp)) {
                    result += "\n";
                    result += temp;
                }
            }
        }

        return result;
    }

}
