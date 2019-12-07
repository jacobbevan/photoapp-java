package com.jacobbevan.photoapp.service;

import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.jacobbevan.photoapp.model.ImageSummary;

public class ImageSummaryConverter implements EntityConverter<ImageSummary> {

    private final String ACCOUNT = "account";
    private final String CAPTION = "caption";
    private final String CREATED = "created";
    private final String UPDATED = "updated";
    private final String WIDTH = "width";
    private final String HEIGHT = "height";
    private final String ID = "id";

    @Override
    public Entity from(Key key, ImageSummary imageSummary) {

        Entity entity = Entity.newBuilder(key)
                .set(ACCOUNT, "azb")
                .set(ID, imageSummary.getId())
                .set(CAPTION, imageSummary.getCaption())
                .set(CREATED,  EntityConverter.fromZonedDateTime(imageSummary.getCreated()))
                .set(UPDATED, EntityConverter.fromZonedDateTime(imageSummary.getUpdated()))
                .set(WIDTH, imageSummary.getWidth())
                .set(HEIGHT, imageSummary.getHeight())
                .build();

        return entity;

    }

    @Override
    public ImageSummary to(Entity other) {

        return new ImageSummary(

                other.getString(ID),
                other.getString(CAPTION),
                EntityConverter.toZonedDateTime(other.getTimestamp(CREATED)),
                EntityConverter.toZonedDateTime(other.getTimestamp(UPDATED)),
                other.getLong(WIDTH),
                other.getLong(HEIGHT));
    }
}
