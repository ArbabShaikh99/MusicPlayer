package com.example.musicplayerapp.presentationlayer.Screens.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.musicplayerapp.R;
import com.example.musicplayerapp.databinding.GetallfolderfragmentBinding;
import com.example.musicplayerapp.datalayer.repoImpl.MusicPlayerRepoImpl;
import com.example.musicplayerapp.domainlayer.model.FolderModel;
import com.example.musicplayerapp.presentationlayer.Screens.Activities.FolderByAudiosActivity;
import com.example.musicplayerapp.presentationlayer.Screens.Adapters.getAllFolderAdapter;

import java.util.List;


public class getAllFoldersFragment extends Fragment {

     private GetallfolderfragmentBinding fragmentFolderByAudiosBinding;
    getAllFolderAdapter getAllFolderAdapter;

    public getAllFoldersFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       fragmentFolderByAudiosBinding =   GetallfolderfragmentBinding.inflate(
                                          inflater, container, false);

       getAllFolderAdapter = new getAllFolderAdapter( model -> {
           Intent intent = new Intent(getContext(), FolderByAudiosActivity.class);
           intent.putExtra("folder", model); // Parcelable object bhejna
           getContext().startActivity(intent);
       }

       );
       fragmentFolderByAudiosBinding.recylerViewofFoldercards.setLayoutManager(new LinearLayoutManager(requireContext()));
       fragmentFolderByAudiosBinding.recylerViewofFoldercards.setAdapter(getAllFolderAdapter);

        // Load audios
        MusicPlayerRepoImpl repo = new MusicPlayerRepoImpl();
        List<FolderModel> foldersList = repo.getAllAudioFoldersWithAudios(requireContext());
        getAllFolderAdapter.submitList(foldersList);
        Log.d("pathing", "Folders fetched: " + foldersList.size());


       return fragmentFolderByAudiosBinding.
               getRoot();
    }
//    private void openFolderDetails(FolderModel folderModel) {
//        // Bundle me folderModel bhejna hai
//        Bundle bundle = new Bundle();
//        bundle.putParcelable("folder", folderModel);
//
//        FolderByAudiosFragment fragment = new FolderByAudiosFragment();
//        fragment.setArguments(bundle);
//
//        requireActivity().getSupportFragmentManager()
//                .beginTransaction()
//                .replace(android.R.id.content, fragment) // full screen
//                .addToBackStack(null)
//                .commit();
//    }
}