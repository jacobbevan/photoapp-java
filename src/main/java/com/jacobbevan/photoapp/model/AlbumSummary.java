package com.jacobbevan.photoapp.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

public class AlbumSummary {


    private long id;
    private String name;
    private String description;
    private List<String> imageIds;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime created;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime updated;

    public AlbumSummary(long id, String name, String description, List<String> imageIds, ZonedDateTime created, ZonedDateTime  updated) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageIds = imageIds;
        this.created = created;
        this.updated = updated;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) { this.id = id;}

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getImageIds() {
        return imageIds;
    }

    public ZonedDateTime  getCreated() {
        return created;
    }

    public ZonedDateTime  getUpdated() {
        return updated;
    }
}
