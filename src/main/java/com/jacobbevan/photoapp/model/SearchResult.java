package com.jacobbevan.photoapp.model;

public class SearchResult {

    private String id;
    private String title;
    private String type;

    public SearchResult(String id, String title, String type) {
        this.id = id;
        this.title = title;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

}
