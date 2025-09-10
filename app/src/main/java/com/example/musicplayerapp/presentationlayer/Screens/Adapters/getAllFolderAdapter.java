package com.example.musicplayerapp.presentationlayer.Screens.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayerapp.databinding.FoldercardBinding;
import com.example.musicplayerapp.domainlayer.model.FolderModel;

public class getAllFolderAdapter  extends  ListAdapter<FolderModel ,  getAllFolderAdapter.getAllFolderViewHolder>{

    public interface OnItemClickListener {
        void onItemClick(FolderModel model); }
    private final getAllFolderAdapter.OnItemClickListener listener;
    public getAllFolderAdapter(getAllFolderAdapter.OnItemClickListener onItemClickListener) {
        super(DIFF_CALLBACK);
        this.listener = onItemClickListener;
    }

    private static final DiffUtil.ItemCallback<FolderModel> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<FolderModel>() {
                @Override
                public boolean areItemsTheSame(@NonNull FolderModel oldItem, @NonNull FolderModel newItem) {
                    return oldItem.getPath() == newItem.getPath();
                }

                @Override
                public boolean areContentsTheSame(@NonNull FolderModel oldItem, @NonNull FolderModel newItem) {
                    return oldItem.getFoldername().equals(newItem.getFoldername());
                }
            };


    @NonNull
    @Override
    public getAllFolderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        FoldercardBinding binding = FoldercardBinding.inflate(inflater, parent, false);
        return new getAllFolderViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull getAllFolderViewHolder holder, int position) {

        FolderModel folderModel = getItem(position);
        holder.bind(folderModel); // agar yeh call hi na ho to UI update nahi hoga
        Log.d("Adapter", "Size: " + folderModel.getSizeOfFolder());


        // Card on click
        holder.foldercardBinding.cardofGetAllFolders.setOnClickListener(View ->{
            Log.d("pathing" ,"Path In onclick: " +folderModel.getFolderItems());
            if (listener != null) listener.onItemClick(folderModel);
        });
    }

    static class getAllFolderViewHolder extends RecyclerView.ViewHolder{

          private FoldercardBinding foldercardBinding;
        public getAllFolderViewHolder(@NonNull  FoldercardBinding foldercardBinding) {
            super(foldercardBinding.getRoot());
            this.foldercardBinding = foldercardBinding;
        }

        void bind(FolderModel model){
            foldercardBinding.tvFolderName.setText(model.getFoldername());
            foldercardBinding.tvfolderSizes.setText(String.valueOf(model.getSizeOfFolder()+" Audios"));
        }
    }
}
