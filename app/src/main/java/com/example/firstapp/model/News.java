package com.example.firstapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class News implements Parcelable {
    private int id;
    private String title, newsContent, newsLink, category, thumbnailUrl, pubDate, createdAt, updatedAt;

    protected News(Parcel in) {
        id = in.readInt();
        title = in.readString();
        newsContent = in.readString();
        newsLink = in.readString();
        category = in.readString();
        thumbnailUrl = in.readString();
        pubDate = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public String getNewsLink() {
        return newsLink;
    }

    public String getCategory() {
        return category;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(newsContent);
        dest.writeString(newsLink);
        dest.writeString(category);
        dest.writeString(thumbnailUrl);
        dest.writeString(pubDate);
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
    }
}
