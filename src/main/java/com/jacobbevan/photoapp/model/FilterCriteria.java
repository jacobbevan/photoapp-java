package com.jacobbevan.photoapp.model;

public class FilterCriteria {

    private String albumId;
    private String textSearch;
    private String startKey;

    public FilterCriteria(String albumId, String textSearch, String startKey) {
        this.albumId = albumId;
        this.textSearch = textSearch;
        this.startKey = startKey;
    }

    public String getAlbumId() {
        return albumId;
    }

    public String getTextSearch() {
        return textSearch;
    }

    public String getStartKey() {
        return startKey;
    }

}
