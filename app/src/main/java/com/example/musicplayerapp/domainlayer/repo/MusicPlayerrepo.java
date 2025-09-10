package com.example.musicplayerapp.domainlayer.repo;

import android.content.Context;
import com.example.musicplayerapp.domainlayer.model.FolderModel;
import com.example.musicplayerapp.domainlayer.model.MusicPlayerModel;
import java.util.List;

public interface MusicPlayerrepo {

    List<MusicPlayerModel> getAllAudio(Context context);
    List<FolderModel> getAllAudioFoldersWithAudios(Context context);



}
