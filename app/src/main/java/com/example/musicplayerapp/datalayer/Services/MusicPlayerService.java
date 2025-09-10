package com.example.musicplayerapp.datalayer.Services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.musicplayerapp.R;
import com.example.musicplayerapp.domainlayer.model.MusicPlayerModel;

import java.io.IOException;

public class MusicPlayerService extends Service {

   private final IBinder binder  = new MusicBinder();  // IBinder ek bridge hai Service aur Activity ke beech.
    private MediaPlayer mediaPlayer;  // MediaPlayer:  Android ka audio/video playback engine.
    private String currentPath;


    public class MusicBinder extends Binder {
       //     Binder pattern
        // . Custom MusicBinder banaya gaya hai jo Service ka instance Activity ko deta hai.
        // . Activity ke paas direct access hota hai → musicService.play(path) jaisi calls kar sakti hai.
            public MusicPlayerService getServices(){
                return MusicPlayerService.this;
            }
        }

//    @Override
//    public void onCreate() {
//        super.onCreate();
//        mediaPlayer = MediaPlayer.create(this, R.raw.sample); // res/raw/sample.mp3
//        mediaPlayer.setLooping(true);
//    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    private void ensurePlayer(){
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnCompletionListener(mp -> {
                // TODO: notify UI if needed
            });
            //OnCompletionListener → song khatam hone par callback milega (yaha tum UI ko notify kar sakte ho:
            // "Next song play karo" etc.).
        }
    }

//    public void play(String path) {
//        Log.d("pathing", "Path In Play Service: " + path);
//
//        if (mediaPlayer != null) {
//            mediaPlayer.reset();
//        } else {
//            mediaPlayer = new MediaPlayer();
//        }
//
//        try {
//            mediaPlayer.setDataSource(path);
//            mediaPlayer.prepare();
//            mediaPlayer.start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void play(String path) {
        try {
            ensurePlayer();  //    ensurePlayer() → MediaPlayer ready karo.
            if (mediaPlayer.isPlaying()) mediaPlayer.stop();  // Agar already koi song play ho raha hai → stop() karo.
            mediaPlayer.reset();     //    reset() → purana state clear.
            mediaPlayer.setDataSource(getApplicationContext(), Uri.parse(path));  //   setDataSource() → naya song load karo (URI/path se).
           mediaPlayer.prepare();  //    prepare() → file ko decode ke liye ready karo.
            mediaPlayer.start();  // start() → playback begin.
            currentPath = path;   //  currentPath = path → record karo konsa song chal raha hai
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pause(){
        if (mediaPlayer != null && mediaPlayer.isPlaying()) mediaPlayer.pause();
    }
    public void resume(){
        if (mediaPlayer !=null && !mediaPlayer.isPlaying()) mediaPlayer.start();
    }
    public void seekTo(int ms){
      if (mediaPlayer != null) mediaPlayer.seekTo(ms);}
    public boolean isPlaying(){
        return mediaPlayer  != null && mediaPlayer.isPlaying();
    }

    public int getDuration() {
        if (mediaPlayer == null) return 0;
        try {
            return mediaPlayer.getDuration();}
        catch (Exception e)
        { return 0;
        }
    }
    public int getCurrentPosition(){
        if (mediaPlayer == null) return 0;
        try {
            return mediaPlayer.getCurrentPosition();}
        catch (Exception e)
        { return 0;
        }
    }
    public String getCurrentPath(){
       return currentPath;
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        if(mediaPlayer  != null){
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null ;
        }
    }

}
