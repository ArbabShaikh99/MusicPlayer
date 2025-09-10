package com.example.musicplayerapp.datalayer.repoImpl;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.example.musicplayerapp.domainlayer.model.FolderModel;
import com.example.musicplayerapp.domainlayer.model.MusicPlayerModel;
import com.example.musicplayerapp.domainlayer.repo.MusicPlayerrepo;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MusicPlayerRepoImpl implements MusicPlayerrepo {

        @Override
    public List<MusicPlayerModel> getAllAudio(Context context) {

        List<MusicPlayerModel> audioList = new ArrayList<>();

        // Columns jo fetch karne hain
        String[] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA
        };


        String selection = MediaStore.Audio.Media.MIME_TYPE + " LIKE 'audio/%'";

        // Query run karna
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL),
                projection,
                selection,
                null,
                MediaStore.Audio.Media.TITLE + " ASC"
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
                String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                Long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));

              //Log.d("MusicRepo", "Song: " + title + " | Author: " + artist + " | Duration: " + size + " | Path: " + path);
             //   Log.d("MusicRepo", "Folder: " + new File(path).getParent());
                   //Log.d("MusicRepo", "New field: " + artist);


                // Custom model class
                MusicPlayerModel audio = new MusicPlayerModel(size, title, path, artist);
                audioList.add(audio);
            }
           // Log.d("MusicRepo", "Total songs found: " + cursor.getCount());
            cursor.close();
        }

        return audioList;
    }





    @Override
    public List<FolderModel> getAllAudioFoldersWithAudios(Context context) {
        Map<String, List<MusicPlayerModel>> folderMap = new HashMap<>();

        Uri collection = MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
        String[] projection = {
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ALBUM
        };

        Cursor cursor = context.getContentResolver().query(collection, projection, null, null, null);

        if (cursor != null) {
            int dataCol = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
            int nameCol = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
            int durationCol = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION);

            while (cursor.moveToNext()) {
                String path = cursor.getString(dataCol);
                String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
                String name = cursor.getString(nameCol);
                long duration = cursor.getLong(durationCol);

                File file = new File(path);
                String folderPath = file.getParent(); // full path of folder

              //  Log.d("MusicRepo", "Song name : " + name + " | Artist: " + artist + " | Duration: " + duration + " | Path: " + path);

                MusicPlayerModel audio = new MusicPlayerModel(duration , name, path, artist);

                if (!folderMap.containsKey(folderPath)) {
                    folderMap.put(folderPath, new ArrayList<>());
                }
                folderMap.get(folderPath).add(audio);
            }
            cursor.close();
        }

        List<FolderModel> folders = new ArrayList<>();
        for (Map.Entry<String, List<MusicPlayerModel>> entry : folderMap.entrySet()) {
            String folderPath = entry.getKey();
            String folderName = new File(folderPath).getName(); // folder name
            List<MusicPlayerModel> audios = entry.getValue();

            Log.d("MusicRepo", "Folder name: " + folderName + " | folderPath: " + folderPath + " | Audios List: " + audios.size());

            folders.add(new FolderModel(folderName, folderPath, audios));
        }

        return folders;
    }
}



