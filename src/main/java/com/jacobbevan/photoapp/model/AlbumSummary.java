package com.jacobbevan.photoapp.model;

import java.time.LocalDateTime;
import java.util.List;

public class AlbumSummary {


    private String id;
    private String name;
    private String description;
    private List<String> imageIds;
    private LocalDateTime created;
    private LocalDateTime updated;

    public AlbumSummary(String id, String name, String description, List<String> imageIds, LocalDateTime created, LocalDateTime updated) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageIds = imageIds;
        this.created = created;
        this.updated = updated;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getImageIds() {
        return imageIds;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }
}
