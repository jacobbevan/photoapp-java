package com.jacobbevan.photoapp.service;

import org.springframework.web.client.HttpServerErrorException;

public class BucketOptions {

    private String fullImage;
    private String thumbNail;

    public BucketOptions() {
        //TODO just till i work out how to do configuration
        this.fullImage ="photoapp-originals-dev";
        this.thumbNail = "photoapp-originals-dev";
    }

    public BucketOptions(String fullImage, String thumbNail) {
        this.fullImage = fullImage;
        this.thumbNail = thumbNail;
    }

    public String getFullImage() {
        return fullImage;
    }

    public String getThumbNail() {
        return thumbNail;
    }


    public String getBucket(ImageProvider.ImageType imageType) {
        switch (imageType) {
            case FullImage:
                return fullImage;
            case Thumbnail:
                return thumbNail;
            default:
                throw new UnsupportedOperationException("Image type " + imageType + " is not supported");
        }
    }
}
