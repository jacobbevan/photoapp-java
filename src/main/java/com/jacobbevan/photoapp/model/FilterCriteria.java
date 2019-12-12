package com.jacobbevan.photoapp.model;

import org.springframework.hateoas.RepresentationModel;

public class FilterCriteria extends RepresentationModel {

    private String albumId;
    private String textSearch;
    private String startKey;

    public FilterCriteria() {
    }

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
