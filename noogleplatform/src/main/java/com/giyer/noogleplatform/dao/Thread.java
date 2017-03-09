package com.giyer.noogleplatform.dao;

/**
 * Created by giyer7 on 3/6/17.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Thread implements Parcelable {

    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("site_full")
    @Expose
    private String siteFull;
    @SerializedName("site")
    @Expose
    private String site;
    @SerializedName("site_section")
    @Expose
    private String siteSection;
    @SerializedName("site_categories")
    @Expose
    private List<String> siteCategories = null;
    @SerializedName("section_title")
    @Expose
    private String sectionTitle;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("title_full")
    @Expose
    private String titleFull;
    @SerializedName("published")
    @Expose
    private String published;
    @SerializedName("replies_count")
    @Expose
    private Integer repliesCount;
    @SerializedName("participants_count")
    @Expose
    private Integer participantsCount;
    @SerializedName("site_type")
    @Expose
    private String siteType;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("spam_score")
    @Expose
    private Double spamScore;
    @SerializedName("main_image")
    @Expose
    private String mainImage;
    @SerializedName("performance_score")
    @Expose
    private Integer performanceScore;
    @SerializedName("domain_rank")
    @Expose
    private Integer domainRank;
    @SerializedName("social")
    @Expose
    private Social social;

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

    public String getSiteFull() {
        return siteFull;
    }

    public void setSiteFull(String siteFull) {
        this.siteFull = siteFull;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSiteSection() {
        return siteSection;
    }

    public void setSiteSection(String siteSection) {
        this.siteSection = siteSection;
    }

    public List<String> getSiteCategories() {
        return siteCategories;
    }

    public void setSiteCategories(List<String> siteCategories) {
        this.siteCategories = siteCategories;
    }

    public String getSectionTitle() {
        return sectionTitle;
    }

    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleFull() {
        return titleFull;
    }

    public void setTitleFull(String titleFull) {
        this.titleFull = titleFull;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public Integer getRepliesCount() {
        return repliesCount;
    }

    public void setRepliesCount(Integer repliesCount) {
        this.repliesCount = repliesCount;
    }

    public Integer getParticipantsCount() {
        return participantsCount;
    }

    public void setParticipantsCount(Integer participantsCount) {
        this.participantsCount = participantsCount;
    }

    public String getSiteType() {
        return siteType;
    }

    public void setSiteType(String siteType) {
        this.siteType = siteType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getSpamScore() {
        return spamScore;
    }

    public void setSpamScore(Double spamScore) {
        this.spamScore = spamScore;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public Integer getPerformanceScore() {
        return performanceScore;
    }

    public void setPerformanceScore(Integer performanceScore) {
        this.performanceScore = performanceScore;
    }

    public Integer getDomainRank() {
        return domainRank;
    }

    public void setDomainRank(Integer domainRank) {
        this.domainRank = domainRank;
    }

    public Social getSocial() {
        return social;
    }

    public void setSocial(Social social) {
        this.social = social;
    }

    protected Thread(Parcel in) {
        uuid = in.readString();
        url = in.readString();
        siteFull = in.readString();
        site = in.readString();
        siteSection = in.readString();
        if (in.readByte() == 0x01) {
            siteCategories = new ArrayList<String>();
            in.readList(siteCategories, String.class.getClassLoader());
        } else {
            siteCategories = null;
        }
        sectionTitle = in.readString();
        title = in.readString();
        titleFull = in.readString();
        published = in.readString();
        repliesCount = in.readByte() == 0x00 ? null : in.readInt();
        participantsCount = in.readByte() == 0x00 ? null : in.readInt();
        siteType = in.readString();
        country = in.readString();
        spamScore = in.readByte() == 0x00 ? null : in.readDouble();
        mainImage = in.readString();
        performanceScore = in.readByte() == 0x00 ? null : in.readInt();
        domainRank = in.readByte() == 0x00 ? null : in.readInt();
        social = (Social) in.readValue(Social.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uuid);
        dest.writeString(url);
        dest.writeString(siteFull);
        dest.writeString(site);
        dest.writeString(siteSection);
        if (siteCategories == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(siteCategories);
        }
        dest.writeString(sectionTitle);
        dest.writeString(title);
        dest.writeString(titleFull);
        dest.writeString(published);
        if (repliesCount == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(repliesCount);
        }
        if (participantsCount == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(participantsCount);
        }
        dest.writeString(siteType);
        dest.writeString(country);
        if (spamScore == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(spamScore);
        }
        dest.writeString(mainImage);
        if (performanceScore == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(performanceScore);
        }
        if (domainRank == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(domainRank);
        }
        dest.writeValue(social);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Thread> CREATOR = new Parcelable.Creator<Thread>() {
        @Override
        public Thread createFromParcel(Parcel in) {
            return new Thread(in);
        }

        @Override
        public Thread[] newArray(int size) {
            return new Thread[size];
        }
    };

}
