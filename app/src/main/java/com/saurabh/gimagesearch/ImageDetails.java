package com.saurabh.gimagesearch;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageDetails implements Serializable {
    private static final String TAG = "ImageDetails";

    @JsonProperty("pagemap")
    private PageMap pageMap;
    private String title;

    public String getTitle() {
        return title;
    }

    public boolean isPageMapNull() {
        return pageMap == null;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PageMap getPageMap() {
        return pageMap;
    }

    public void setPageMap(PageMap pageMap) {
        this.pageMap = pageMap;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class PageMap implements Serializable{

    @JsonProperty("cse_thumbnail")
    private List<Thumbnail> thumbnails;
    @JsonProperty("cse_image")
    private List<Image> images;
    @JsonProperty("metatags")
    private List<MetaData> metaData;

    public List<Thumbnail> getThumbnails() {
        return thumbnails;
    }

    public boolean isThumbnailsNULL() {
        return thumbnails == null;
    }

    public boolean isImageNULL() {
        return images == null;
    }

    public boolean isMetaDataNULL() {
        return metaData == null;
    }

    public void setThumbnails(List<Thumbnail> thumbnails) {
        this.thumbnails = thumbnails;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<MetaData> getMetaData() {
        return metaData;
    }

    public void setMetaData(List<MetaData> metaData) {
        this.metaData = metaData;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Thumbnail implements Serializable{

    private String width, height, src;

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class MetaData implements Serializable {

    private String author, viewport;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getViewport() {
        return viewport;
    }

    public void setViewport(String viewport) {
        this.viewport = viewport;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Image implements Serializable {

    private String src;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

}