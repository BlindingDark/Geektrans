package com.blindingdark.geektrans.global;

import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.blindingdark.geektrans.api.TransEngine;

/**
 * Created by BlindingDark on 2016/11/21 0021.
 */

public class TransEngineFactory {

    @Nullable
    public static TransEngine getTransEngine(String enginePackageName) {
        Class c = null;
        TransEngine transEngine = null;
        try {
            c = Class.forName(enginePackageName);
            transEngine = (TransEngine) c.newInstance();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return transEngine;
    }


    @Nullable
    public static TransEngine getTransEngine(String enginePackageName, SharedPreferences preferences) {
        Class c = null;
        TransEngine transEngine = null;
        try {
            c = Class.forName(enginePackageName);
            transEngine = (TransEngine) c.newInstance();
            transEngine.setPreferences(preferences);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return transEngine;
    }
}
