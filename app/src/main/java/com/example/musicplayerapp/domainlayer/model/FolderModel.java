package com.example.musicplayerapp.domainlayer.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import java.util.List;
import java.util.ArrayList;

public class FolderModel implements Parcelable {
    private String foldername, Path;
    private int sizeOfFolder;
    private List<MusicPlayerModel> folderItems;

    public FolderModel(String foldername, String path, List<MusicPlayerModel> folderItems) {
        this.foldername = foldername;
        Path = path;
        this.folderItems = folderItems != null ? folderItems : new ArrayList<>();
        this.sizeOfFolder = this.folderItems.size();
    }

    protected FolderModel(Parcel in) {
        foldername = in.readString();
        Path = in.readString();
        sizeOfFolder = in.readInt();
        folderItems = in.createTypedArrayList(MusicPlayerModel.CREATOR); // ✅ READ LIST
    }

    public static final Creator<FolderModel> CREATOR = new Creator<FolderModel>() {
        @Override
        public FolderModel createFromParcel(Parcel in) {
            return new FolderModel(in);
        }

        @Override
        public FolderModel[] newArray(int size) {
            return new FolderModel[size];
        }
    };

    public String getFoldername() { return foldername; }
    public void setFoldername(String foldername) { this.foldername = foldername; }

    public String getPath() { return Path; }
    public void setPath(String path) { Path = path; }

    public int getSizeOfFolder() { return sizeOfFolder; }
    public void setSizeOfFolder(int sizeOfFolder) { this.sizeOfFolder = sizeOfFolder; }

    public List<MusicPlayerModel> getFolderItems() {
        return folderItems != null ? folderItems : new ArrayList<>(); // ✅ null-safe
    }
    public void setFolderItems(List<MusicPlayerModel> folderItems) {
        this.folderItems = folderItems;
        this.sizeOfFolder = folderItems != null ? folderItems.size() : 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int flags) {
        parcel.writeString(foldername);
        parcel.writeString(Path);
        parcel.writeInt(sizeOfFolder);
        parcel.writeTypedList(folderItems); // ✅ WRITE LIST
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
