package com.example.musicplayerapp.presentationlayer.Screens.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.musicplayerapp.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
//
//public class PlayerBottomSheet extends BottomSheetDialogFragment {
//
//    // Ye ek contract hai jo BottomSheet ko service ke saath interact karne deta hai.
//    public interface MusicPlayerController {
//        boolean isPlaying();
//        void pause();
//        void resume();
//        void seekTo(int ms);
//        int getDuration();
//        int getCurrentPosition();
//        String getNowPlayingTitle(); // optional for UI title
//    }
//
//
//    private MusicPlayerController controller;
//    private TextView tvTitle, tvTime;
//    private ImageButton btnPlayPause;
//    private SeekBar seekBar;
//
//    // getCurrentPosition() lekar SeekBar aur time update karta hai.
//    // Agar playing hai to pause ka icon, warna play ka icon.
//    //Ye ek loop hai jo tab tak chalta hai jab tak sheet open hai.
//    private final Handler handler = new Handler(Looper.getMainLooper());
//    private final Runnable progressTask = new Runnable() {
//        @Override public void run() {
//            if (controller != null) {
//                int pos = controller.getCurrentPosition();
//                int dur = controller.getDuration();
//                seekBar.setMax(dur);
//                seekBar.setProgress(pos);
//                tvTime.setText(formatTime(pos) + " / " + formatTime(dur));
//                btnPlayPause.setImageResource(controller.isPlaying()
//                        ? android.R.drawable.ic_media_pause
//                        : android.R.drawable.ic_media_play);
//            }
//            handler.postDelayed(this, 500);
//        }
//    };
//
//    // Is method se tum Service (jo MusicController implement karegi) ko BottomSheet ke saath attach karte ho.
//    public void attachController(MusicPlayerController c) { this.controller = c; }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.sheet_player, container, false);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(v, savedInstanceState);
//        tvTitle = v.findViewById(R.id.tvSongTitle);
//        tvTime = v.findViewById(R.id.tvTimes);
//        btnPlayPause = v.findViewById(R.id.btnPlayPauses);
//        seekBar = v.findViewById(R.id.seekBa);
//
//        tvTitle.setText(controller != null ? controller.getNowPlayingTitle() : "Now Playing");
//
//        btnPlayPause.setOnClickListener(view -> {
//            if (controller == null) return;
//            if (controller.isPlaying()) controller.pause();
//            else controller.resume();
//        });
//
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            boolean userTouch = false;
//            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {}
//            @Override public void onStartTrackingTouch(SeekBar seekBar) { userTouch = true; }
//            @Override public void onStopTrackingTouch(SeekBar sb) {
//                userTouch = false;
//                if (controller != null) controller.seekTo(sb.getProgress());
//            }
//        });
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
////        Dialog dialog = getDialog();
////        if (dialog != null) {
////            View bottomSheet = dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
////            if (bottomSheet != null) {
////                BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(bottomSheet);
////                behavior.setState(BottomSheetBehavior.STATE_EXPANDED); // fullscreen open
////                 behavior.setSkipCollapsed(false); // skip half screen
////                bottomSheet.setLayoutParams(new CoordinatorLayout.LayoutParams(
////                        ViewGroup.LayoutParams.MATCH_PARENT,
////                        ViewGroup.LayoutParams.MATCH_PARENT
////                ));
////            }
////        }
//        handler.post(progressTask);
//    }
//
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        handler.removeCallbacks(progressTask);
//    }
//
//    @NonNull
//    @Override
//    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
//
//
//        // Apply the custom background
//        dialog.setOnShowListener(dialogInterface -> {
//            BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialogInterface;
//            View bottomSheet = bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
//            if (bottomSheet != null) {
//                bottomSheet.setBackgroundResource(R.drawable.atifimage);
//                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) bottomSheet.getLayoutParams();
//                params.setMargins(10, 0, 10, 10); // Adjust values as needed
//
//                // Apply the updated layout params
//                bottomSheet.setLayoutParams(params);
//                BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(bottomSheet);
//                behavior.setFitToContents(true);
//                behavior.setHalfExpandedRatio(0.6f);
//                behavior.setState(BottomSheetBehavior.STATE_EXPANDED); // Expand fully
//                behavior.setSkipCollapsed(true); // Prevent collapsing
//
//            }
//        });
//        return dialog;
//    }
//
//    private String formatTime(int ms) {
//        int totalSec = ms / 1000;
//        int m = totalSec / 60;
//        int s = totalSec % 60;
//        return String.format("%d:%02d", m, s);
//    }
//
//
//
//
//}


public class MusicPlayerBottomSheet extends BottomSheetDialogFragment {

    // Contract: Service ko control karne ke liye
    public interface MusicPlayerController {
        boolean isPlaying();
        void pause();
        void resume();
        void seekTo(int ms);
        int getDuration();
        int getCurrentPosition();
        String getNowPlayingTitle();
    }

    private MusicPlayerController controller;
    private TextView tvTitle, tvTime;
    private ImageButton btnPlayPause ,  btnCloseAudio;
    private SeekBar seekBar;

    private final Handler handler = new Handler(Looper.getMainLooper());
    // Yeh ek Handler banata hai jo Main Thread (UI Thread) pe kaam karega.
   // Iska kaam hai background me koi kaam repeat karwana ya future me schedule karna.
    private final Runnable progressTask = new Runnable() {
        @Override public void run() {
            if (controller != null) {
                int pos = controller.getCurrentPosition();
                int dur = controller.getDuration();
                seekBar.setMax(dur);
                seekBar.setProgress(pos);
                tvTime.setText(formatTime(pos) + " / " + formatTime(dur));
                btnPlayPause.setImageResource(controller.isPlaying()
                        ? android.R.drawable.ic_media_pause
                        : android.R.drawable.ic_media_play);
            }
            handler.postDelayed(this, 500);
        }
    };

    public void attachController(MusicPlayerController c) { this.controller = c; }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        dialog.setOnShowListener(dialogInterface -> {
            BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialogInterface;
            View bottomSheet = bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);

            if (bottomSheet != null) {
                bottomSheet.setBackgroundColor(Color.WHITE);

                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) bottomSheet.getLayoutParams();
               params.setMargins(0, 0, 0, 0);
                params.height = getWindowHeight();
                bottomSheet.setLayoutParams(params);


                BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(bottomSheet);

                behavior.setFitToContents(false);
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
               // behavior.setSkipCollapsed(true);
               behavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                // Listen for drag-down
                behavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                    @Override
                    public void onStateChanged(@NonNull View bottom, int newState) {
                        Log.d("TAG", "onStateChanged: "+newState);

//                        if (newState == BottomSheetBehavior.STATE_HALF_EXPANDED) {
//                            Log.d("TAG", "onStateChanged: STATE_HALF_EXPANDED");
//                            dismiss(); // Full hide → dismiss
//                        }else if (newState == BottomSheetBehavior.STATE_HIDDEN) {
//                            Log.d("TAG", "onStateChanged: STATE_HIDDEN");
//                            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
////                            dismiss(); // Full hide → dismiss
//                        }
                    }
                       /** Arbab */
                    @Override
                    public void onSlide(@NonNull View bottom, float slideOffset) {
                        // Animate transparency while sliding
                        if (getDialog() != null && getDialog().getWindow() != null) {

//                            getDialog().getWindow().getDecorView()
//                                    .setAlpha(0.5f + (slideOffset / 2));
                        }
                    }
                });
            }
        });

        return dialog;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sheet_player, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        tvTitle = v.findViewById(R.id.tvSongTitle);
        tvTime = v.findViewById(R.id.tvTimes);
        btnPlayPause = v.findViewById(R.id.btnPlayPauses);
        seekBar = v.findViewById(R.id.seekBa);
        btnCloseAudio = v.findViewById(R.id.btnClose);

        tvTitle.setText(controller != null ? controller.getNowPlayingTitle() : "Now Playing");

        // Play and Pause
        btnPlayPause.setOnClickListener(view -> {
            if (controller == null) return;
            if (controller.isPlaying()) controller.pause();
            else controller.resume();
        });
           // SeekBar
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {}
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar sb) {

                if (controller != null) controller.seekTo(sb.getProgress());
            }
        });
        // close bottom sheet
        btnCloseAudio.setOnClickListener(view -> {
            if (controller != null) {
                controller.pause();
                controller.seekTo(0);
            }
            dismiss(); // close bottom sheet
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        handler.post(progressTask);
    }

    @Override
    public void onStop() {
        super.onStop();
        handler.removeCallbacks(progressTask);
    }



    private String formatTime(int ms) {
        int totalSec = ms / 1000;
        int m = totalSec / 60;
        int s = totalSec % 60;
        return String.format("%d:%02d", m, s);
    }

    private int getWindowHeight() {
        // Calculate window height for fullscreen use
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }
}
