package com.jacobbevan.photoapp.model;

import org.springframework.hateoas.RepresentationModel;

import java.util.List;

public class QueryResult<T> extends RepresentationModel {

    private List<T> records;

    private String nextStartKey;

    public QueryResult(List<T> records, String nextPage) {
        this.records = records;
        this.nextStartKey = nextPage;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public String getNextStartKey() {
        return nextStartKey;
    }

    public void setNextStartKey(String nextStartKey) {
        this.nextStartKey = nextStartKey;
    }
}
