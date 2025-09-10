package com.example.musicplayerapp.presentationlayer.Screens.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.TextView;
import com.example.musicplayerapp.R;

import com.example.musicplayerapp.databinding.FragmentAllAudiosBinding;
import com.example.musicplayerapp.datalayer.Services.MusicPlayerService;
import com.example.musicplayerapp.datalayer.repoImpl.MusicPlayerRepoImpl;
import com.example.musicplayerapp.domainlayer.model.FolderModel;
import com.example.musicplayerapp.domainlayer.model.MusicPlayerModel;
import com.example.musicplayerapp.presentationlayer.Screens.Adapters.getAllAudioAdapter;
import java.util.List;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class getAllAudiosFragments extends Fragment implements MusicPlayerBottomSheet.MusicPlayerController {

    private FragmentAllAudiosBinding binding;
    private getAllAudioAdapter getAllAudioAdapter;
    private MusicPlayerService musicService;
    private boolean bound = false;
    private String nowPlayingTitle = "";

    private final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MusicPlayerService.MusicBinder binder = (MusicPlayerService.MusicBinder) iBinder;
            musicService = binder.getServices();
            bound = true;
            Log.d("pathing", "Service Connected (Fragment)");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            bound = false;
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAllAudiosBinding.inflate(inflater, container, false);

        // Adapter setup
        getAllAudioAdapter = new getAllAudioAdapter(model -> {
            Log.d("pathing", "Path In Fragment: " + model.getPath());

            if (!bound) {
                Toast.makeText(requireContext(), "Service not bound yet", Toast.LENGTH_SHORT).show();
                return;
            }

            musicService.play(model.getPath());
            nowPlayingTitle = model.getSongName();

            // Bottom Sheet show
            MusicPlayerBottomSheet sheet = new MusicPlayerBottomSheet();
            sheet.attachController(this);
            sheet.show(getParentFragmentManager(), "player_sheet");
        });
        getAllAudioAdapter.setOnInfoClickListener(model -> {
            String sizeText = android.text.format.Formatter.formatFileSize(requireContext(), model.getSize());
            LayoutInflater inflater1 = LayoutInflater.from(requireContext());
            View dialogView = inflater1.inflate(R.layout.dialog_audio_details, null, false);

            TextView tvTitle = dialogView.findViewById(R.id.tvTitle);
            TextView tvPath = dialogView.findViewById(R.id.tvPath);
            TextView tvSize = dialogView.findViewById(R.id.tvSize);

            tvTitle.setText(model.getSongName());
            tvPath.setText(model.getPath());
            tvSize.setText(sizeText);

            new AlertDialog.Builder(requireContext())
                    .setView(dialogView)
                    .setPositiveButton("OK", (d, w) -> d.dismiss())
                    .show();
        });

        binding.getAllAudiosRecylerview.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.getAllAudiosRecylerview.setAdapter(getAllAudioAdapter);

        // Load audios
        MusicPlayerRepoImpl repo = new MusicPlayerRepoImpl();
        List<MusicPlayerModel> audioList = repo.getAllAudio(requireContext());
        getAllAudioAdapter.submitList(audioList);
        Log.d("pathing", "Songs fetched: " + audioList.size());

        List<FolderModel> foldersList = repo.getAllAudioFoldersWithAudios(requireContext());
        Log.d("pathing", "Folders fetched: " + foldersList.size());

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("pathing", "onResume() → Binding service (Fragment)");
        Intent i = new Intent(requireContext(), MusicPlayerService.class);
        requireContext().startService(i);
        requireContext().bindService(i, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("pathing", "onPause() → Unbinding service (Fragment)");
        if (bound) {
            requireContext().unbindService(connection);
            bound = false;
        }
    }

    // ===== Controller impl for BottomSheet =====
    @Override public boolean isPlaying() { return musicService != null && musicService.isPlaying(); }
    @Override public void pause() { if (musicService != null) musicService.pause(); }
    @Override public void resume() { if (musicService != null) musicService.resume(); }
    @Override public void seekTo(int ms) { if (musicService != null) musicService.seekTo(ms); }
    @Override public int getDuration() { return musicService != null ? musicService.getDuration() : 0; }
    @Override public int getCurrentPosition() { return musicService != null ? musicService.getCurrentPosition() : 0; }
    @Override public String getNowPlayingTitle() { return nowPlayingTitle; }
}
