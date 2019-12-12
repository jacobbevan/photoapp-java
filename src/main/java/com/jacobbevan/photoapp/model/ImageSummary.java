package com.jacobbevan.photoapp.model;

import com.adobe.internal.xmp.impl.Base64;
import org.springframework.hateoas.RepresentationModel;
import java.time.ZonedDateTime;

public class ImageSummary extends RepresentationModel {

    private String id;
    private String caption;
    private ZonedDateTime created;
    private ZonedDateTime updated;
    private long width;
    private long height;

    public ImageSummary(String id, String caption, ZonedDateTime created, ZonedDateTime updated, long width, long height) {
        this.id = id;
        this.caption = caption;
        this.created = created;
        this.updated = updated;
        this.width = width;
        this.height = height;
    }

    public String getId() {
        return id;
    }


    public String getEncodedId() {
        return Base64.encode(id);
    }

    public static String getDecodedId(String id) {
        return Base64.decode(id);
    }

    public String getCaption() {
        return caption;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public ZonedDateTime getUpdated() {
        return updated;
    }

    public long getWidth() {
        return width;
    }

    public long getHeight() {
        return height;
    }
}

