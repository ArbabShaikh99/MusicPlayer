package com.example.musicplayerapp.domainlayer.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MusicPlayerModel implements Parcelable {
    private Long size;
    private String songName, Path, Album;

    public MusicPlayerModel(Long size, String songName, String path, String artist) {
        this.size = size;
        this.songName = songName;
        Path = path;
        Album = artist;
    }

    // ---- Parcelable Constructor ----
    protected MusicPlayerModel(Parcel in) {
        if (in.readByte() == 0) {
            size = null;
        } else {
            size = in.readLong();
        }
        songName = in.readString();
        Path = in.readString();
        Album = in.readString();
    }

    public static final Creator<MusicPlayerModel> CREATOR = new Creator<MusicPlayerModel>() {
        @Override
        public MusicPlayerModel createFromParcel(Parcel in) {
            return new MusicPlayerModel(in);
        }

        @Override
        public MusicPlayerModel[] newArray(int size) {
            return new MusicPlayerModel[size];
        }
    };

    // ---- Getters / Setters ----
    public Long getSize() { return size; }
    public void setSize(Long size) { this.size = size; }

    public String getSongName() { return songName; }
    public void setSongName(String songName) { this.songName = songName; }

    public String getPath() { return Path; }
    public void setPath(String path) { Path = path; }

    public String getAlbum() { return Album; }
    public void setAlbum(String album) { Album = album; }

    // ---- Parcelable ----
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (size == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(size);
        }
        dest.writeString(songName);
        dest.writeString(Path);
        dest.writeString(Album);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // ---- Equals / hashCode (optional) ----
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) return true;
//        if (!(obj instanceof MusicPlayerModel)) return false;
//        MusicPlayerModel other = (MusicPlayerModel) obj;
//        return Objects.equals(Path, other.Path) &&
//                Objects.equals(size, other.size) &&
//                Objects.equals(songName, other.songName) &&
//                Objects.equals(Artist, other.Artist);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(Path, songName, Artist, size);
//    }
}
