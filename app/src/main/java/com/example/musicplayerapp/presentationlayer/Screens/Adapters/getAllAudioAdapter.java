package com.example.musicplayerapp.presentationlayer.Screens.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.example.musicplayerapp.databinding.AudiocardBinding;
import com.example.musicplayerapp.domainlayer.model.MusicPlayerModel;


public class getAllAudioAdapter extends ListAdapter<MusicPlayerModel , getAllAudioAdapter.getAllAudioViewHolder> {

        public interface OnItemClickListener {
        void onItemClick(MusicPlayerModel model); }
        public interface OnInfoClickListener { void onInfoClick(MusicPlayerModel model); }
        private final OnItemClickListener listener;
        private OnInfoClickListener infoListener;
        public getAllAudioAdapter(OnItemClickListener onItemClickListener){
                     super(DIFF_CALLBACK);
                     this.listener = onItemClickListener;
 }

         private static final DiffUtil.ItemCallback<MusicPlayerModel> DIFF_CALLBACK =
                 new DiffUtil.ItemCallback<MusicPlayerModel>() {
                     @Override
                     public boolean areItemsTheSame(@NonNull MusicPlayerModel oldItem, @NonNull MusicPlayerModel newItem) {
                         return oldItem.getPath() == newItem.getPath();
                     }

                     @Override
                     public boolean areContentsTheSame(@NonNull MusicPlayerModel oldItem, @NonNull MusicPlayerModel newItem) {
                         return oldItem.getSongName().equals(newItem.getSongName()) &&
                                 oldItem.getAlbum().equals(newItem.getAlbum());
                     }
                 };

        public void setOnInfoClickListener(OnInfoClickListener l){ this.infoListener = l; }


    @NonNull
    @Override
    public getAllAudioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        AudiocardBinding binding = AudiocardBinding.inflate(inflater, parent, false);
        return new getAllAudioViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull getAllAudioViewHolder holder, int position) {

        MusicPlayerModel audioModel = getItem(position);
        holder.bind(audioModel); // agar yeh call hi na ho to UI update nahi hoga
        Log.d("Adapter", "Binding: " + audioModel.getSongName());

        // Binding kar do
        holder.audiocardBinding.tvSongTitle.setText(audioModel.getSongName());
        holder.audiocardBinding.tvAuthor.setText(audioModel.getAlbum());

        long sizeInBytes = audioModel.getSize();
        String sizeText = android.text.format.Formatter.formatFileSize(holder.itemView.getContext(), sizeInBytes);
        holder.audiocardBinding.tvSize.setText(sizeText);

        // Image lagani ho to
     //   holder.audiocardBinding.imgSongIcon.setImageResource(audioModel.getThumbnailResId());


        // More Button click
        holder.audiocardBinding.btnMore.setOnClickListener(v -> {
            if (infoListener != null) infoListener.onInfoClick(audioModel);
        });

        // on card click
        holder.audiocardBinding.cardofGetAllAudio.setOnClickListener(v -> {
            Log.d("pathing" ,"Path In onclick: " +audioModel.getPath());
            if (listener != null) listener.onItemClick(audioModel);
        });
    }


    static class  getAllAudioViewHolder  extends RecyclerView.ViewHolder{

         AudiocardBinding audiocardBinding;
        public getAllAudioViewHolder(@NonNull AudiocardBinding binding) {
            super(binding.getRoot());
            this.audiocardBinding = binding;

        }
        void bind(MusicPlayerModel model) {
            // Yahi jagah hai jahan UI update hota hai
            audiocardBinding.tvSongTitle.setText(model.getSongName());
            audiocardBinding.tvAuthor.setText(model.getAlbum());
        }
    }
}
