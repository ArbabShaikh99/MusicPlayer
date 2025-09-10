package com.example.musicplayerapp.presentationlayer.Screens.Activities;

import android.os.Bundle;

import com.example.musicplayerapp.datalayer.Services.MusicPlayerService;
import com.example.musicplayerapp.domainlayer.model.FolderModel;
import com.example.musicplayerapp.domainlayer.model.MusicPlayerModel;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import com.example.musicplayerapp.databinding.ActivityFolderByAudiosBinding;
import java.util.List;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.musicplayerapp.presentationlayer.Screens.Adapters.getAllAudioAdapter;
import  com.example.musicplayerapp.presentationlayer.Screens.Fragments.MusicPlayerBottomSheet;

public class FolderByAudiosActivity extends AppCompatActivity implements MusicPlayerBottomSheet.MusicPlayerController {

    private ActivityFolderByAudiosBinding binding;
    private getAllAudioAdapter audioAdapter;
    private MusicPlayerService musicService;
    private boolean bound = false;
    private String nowPlayingTitle = "";
    private FolderModel folderModel;
    private TextView tvFolderName;
    private ImageView btnBack;

    private final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            MusicPlayerService.MusicBinder musicBinder = (MusicPlayerService.MusicBinder) binder;
            musicService = musicBinder.getServices();
            bound = true;
            Log.d("pathing", "Service Connected (Activity)");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound = false;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFolderByAudiosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // Back button ka listener
       binding.btnBack.setOnClickListener(v -> finish());

        // --- Get Parcelable Folder object ---
        folderModel = getIntent().getParcelableExtra("folder");

        if (folderModel == null) {
            Toast.makeText(this, "No folder found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
       binding.tvFolderName.setText(folderModel.getFoldername());

        // --- Setup RecyclerView + Adapter ---
        audioAdapter = new getAllAudioAdapter(model -> {
            if (!bound) {
                Toast.makeText(this, "Service not bound yet", Toast.LENGTH_SHORT).show();
                return;
            }

            musicService.play(model.getPath());
            nowPlayingTitle = model.getSongName();

            // show bottomsheet
            MusicPlayerBottomSheet sheet = new MusicPlayerBottomSheet();
            sheet.attachController(this);
            sheet.show(getSupportFragmentManager(), "player_sheet");
        });

        binding.FolderByAudiosRecyle.setLayoutManager(new LinearLayoutManager(this));
        binding.FolderByAudiosRecyle.setAdapter(audioAdapter);

        // --- Load only this folder’s audios ---
        List<MusicPlayerModel> audioList = folderModel.getFolderItems();
        audioAdapter.submitList(audioList);
        Log.d("CheckAll", "Folder: " + folderModel.getFoldername() + " → " + audioList.size() + " songs");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent i = new Intent(this, MusicPlayerService.class);
        startService(i);
        bindService(i, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (bound) {
            unbindService(connection);
            bound = false;
        }
    }

    // ===== BottomSheet Controls =====
    @Override public boolean isPlaying() { return musicService != null && musicService.isPlaying(); }
    @Override public void pause() { if (musicService != null) musicService.pause(); }
    @Override public void resume() { if (musicService != null) musicService.resume(); }
    @Override public void seekTo(int ms) { if (musicService != null) musicService.seekTo(ms); }
    @Override public int getDuration() { return musicService != null ? musicService.getDuration() : 0; }
    @Override public int getCurrentPosition() { return musicService != null ? musicService.getCurrentPosition() : 0; }
    @Override public String getNowPlayingTitle() { return nowPlayingTitle; }
}
