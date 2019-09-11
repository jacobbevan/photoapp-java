package com.jacobbevan.photoapp.model;

import java.net.URL;
import java.time.LocalDateTime;

public class ImageSummary {


    private String id;
    private URL thumbnail;
    private URL fullImage;
    private String caption;
    private LocalDateTime created;
    private LocalDateTime updated;
    private Integer width;
    private Integer height;
}

