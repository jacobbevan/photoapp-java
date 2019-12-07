package com.jacobbevan.photoapp.service;

import com.google.cloud.Timestamp;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public interface EntityConverter<T> {


    Entity from(Key key, T other);
    T to(Entity other);

    static Timestamp fromZonedDateTime(ZonedDateTime dt) {
        return Timestamp.of(Date.from(dt.toInstant()));
    }

    //TODO this needs to be fixed
    static Timestamp fromLocalDateTime(LocalDateTime dt) {
        return Timestamp.of(Date.from(dt.toInstant(ZoneOffset.UTC)));
    }

    static ZonedDateTime toZonedDateTime(Timestamp timestamp) {
        return ZonedDateTime.ofInstant(timestamp.toDate().toInstant(), ZoneOffset.UTC);
    }

}

