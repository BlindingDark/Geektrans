package com.blindingdark.geektrans.tools;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;


import java.io.IOException;
import java.util.List;

/**
 * Created by BlindingDark on 2016/8/24 0024.
 */
public class SoundPlayer extends Service {

    List<String> soundList;

    public SoundPlayer setSoundList(List<String> soundList) {
        this.soundList = soundList;
        return this;
    }


    int nowPlay = -1;

    private boolean haveNext() {
        if (soundList != null) {
            if (soundList.isEmpty()) {
                return false;
            }
            if ((nowPlay + 1) < soundList.size()) {
                return true;
            }
        }
        return false;
    }

    synchronized boolean playNext() {
        if (haveNext()) {
            nowPlay++;
            play(nowPlay);
            return true;
        }
        return false;
    }


    private void play(int i) {
        MediaPlayer player = new MediaPlayer();
        player.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                mp.release();
                return false;
            }
        });
        try {
            player.setDataSource(this.soundList.get(i));
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
            playNext();
        }
        player.start();
    }


    public void playOneSoundFromList() {
        playNext();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.setSoundList(intent.getStringArrayListExtra("soundList"));
        this.playOneSoundFromList();
        return super.onStartCommand(intent, flags, startId);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
