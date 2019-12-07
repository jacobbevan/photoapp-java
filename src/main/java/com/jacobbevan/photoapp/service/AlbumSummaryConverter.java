package com.jacobbevan.photoapp.service;

import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.ListValue;
import com.google.cloud.datastore.StringValue;
import com.jacobbevan.photoapp.model.AlbumSummary;
import com.jacobbevan.photoapp.model.ImageSummary;

import java.util.ArrayList;
import java.util.List;

public class AlbumSummaryConverter implements EntityConverter<AlbumSummary> {

    private final String ACCOUNT = "account";
    private final String NAME = "name";
    private final String CREATED = "created";
    private final String UPDATED = "updated";
    private final String DESCRIPTION = "description";
    private final String IMAGE_IDS = "imageIds";

    @Override
    public Entity from(Key key, AlbumSummary other) {
        return Entity.newBuilder(key)
                .set(ACCOUNT, "azb")
                .set(NAME, other.getName())
                .set(CREATED,  EntityConverter.fromZonedDateTime(other.getCreated()))
                .set(UPDATED, EntityConverter.fromZonedDateTime(other.getUpdated()))
                .set(DESCRIPTION, other.getDescription())
                .set(IMAGE_IDS, makeListValue(other.getImageIds()))
                .build();
    }

    @Override
    public AlbumSummary to(Entity other) {

        return new AlbumSummary(
                other.getKey().getId(),
                other.getString(NAME),
                other.getString(DESCRIPTION),
                makeList(other.getList(IMAGE_IDS)),
                EntityConverter.toZonedDateTime(other.getTimestamp(CREATED)),
                EntityConverter.toZonedDateTime(other.getTimestamp(UPDATED)));

    }

    private List<String> makeList(List<StringValue> items) {

        List<String> ret = new ArrayList<>();

        for(var lv : items) {
            ret.add(lv.get());
        }

        return  ret;
    }

    private ListValue makeListValue(List<String> list) {

        var builder = ListValue.newBuilder();

        for(var s : list) {
            builder.addValue(StringValue.of(s));
        }

        return builder.build();
    }

}
