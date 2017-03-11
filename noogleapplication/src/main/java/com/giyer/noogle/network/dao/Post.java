package com.giyer.noogle.network.dao;

/**
 * Created by giyer7 on 3/6/17.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Post implements Parcelable {

    @SerializedName("thread")
    @Expose
    private Thread thread;
    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("ord_in_thread")
    @Expose
    private Integer ordInThread;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("published")
    @Expose
    private String published;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("highlightText")
    @Expose
    private String highlightText;
    @SerializedName("highlightTitle")
    @Expose
    private String highlightTitle;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("external_links")
    @Expose
    private List<Object> externalLinks = null;
    @SerializedName("entities")
    @Expose
    private Entities entities;
    @SerializedName("rating")
    @Expose
    private Object rating;
    @SerializedName("crawled")
    @Expose
    private String crawled;

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getOrdInThread() {
        return ordInThread;
    }

    public void setOrdInThread(Integer ordInThread) {
        this.ordInThread = ordInThread;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHighlightText() {
        return highlightText;
    }

    public void setHighlightText(String highlightText) {
        this.highlightText = highlightText;
    }

    public String getHighlightTitle() {
        return highlightTitle;
    }

    public void setHighlightTitle(String highlightTitle) {
        this.highlightTitle = highlightTitle;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<Object> getExternalLinks() {
        return externalLinks;
    }

    public void setExternalLinks(List<Object> externalLinks) {
        this.externalLinks = externalLinks;
    }

    public Entities getEntities() {
        return entities;
    }

    public void setEntities(Entities entities) {
        this.entities = entities;
    }

    public Object getRating() {
        return rating;
    }

    public void setRating(Object rating) {
        this.rating = rating;
    }

    public String getCrawled() {
        return crawled;
    }

    public void setCrawled(String crawled) {
        this.crawled = crawled;
    }

    protected Post(Parcel in) {
        thread = (Thread) in.readValue(Thread.class.getClassLoader());
        uuid = in.readString();
        url = in.readString();
        ordInThread = in.readByte() == 0x00 ? null : in.readInt();
        author = in.readString();
        published = in.readString();
        title = in.readString();
        text = in.readString();
        highlightText = in.readString();
        highlightTitle = in.readString();
        language = in.readString();
        if (in.readByte() == 0x01) {
            externalLinks = new ArrayList<Object>();
            in.readList(externalLinks, Object.class.getClassLoader());
        } else {
            externalLinks = null;
        }
        entities = (Entities) in.readValue(Entities.class.getClassLoader());
        rating = (Object) in.readValue(Object.class.getClassLoader());
        crawled = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(thread);
        dest.writeString(uuid);
        dest.writeString(url);
        if (ordInThread == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(ordInThread);
        }
        dest.writeString(author);
        dest.writeString(published);
        dest.writeString(title);
        dest.writeString(text);
        dest.writeString(highlightText);
        dest.writeString(highlightTitle);
        dest.writeString(language);
        if (externalLinks == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(externalLinks);
        }
        dest.writeValue(entities);
        dest.writeValue(rating);
        dest.writeString(crawled);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Post> CREATOR = new Parcelable.Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };
}