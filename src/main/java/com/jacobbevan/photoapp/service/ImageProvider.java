package com.jacobbevan.photoapp.service;

import com.jacobbevan.photoapp.model.AlbumSummary;
import com.jacobbevan.photoapp.model.FilterCriteria;
import com.jacobbevan.photoapp.model.ImageSummary;
import com.jacobbevan.photoapp.model.SearchResult;

import java.util.List;

public interface ImageProvider {
    public enum ImageType
    {
        Thumbnail,
        FullImage
    }

    byte[] getImage(ImageType imageType, String id);
    AlbumSummary getAlbumSummary(String id);
    List<ImageSummary> getImageSummaries(FilterCriteria filter);
    List<AlbumSummary> getAlbumSummaries();
    void reIndex();
    ImageSummary putImage(byte[] fileContent, String fileName, String contentType, String folder);
    void deleteImage(String id);
    void deleteAlbum(String id);
    void updateAlbum(AlbumSummary value);
    void updateImage(ImageSummary value);
    AlbumSummary createAlbum(AlbumSummary value);
    List<SearchResult> search(String text);

}
