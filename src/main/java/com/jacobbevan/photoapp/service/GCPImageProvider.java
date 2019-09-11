package com.jacobbevan.photoapp.service;

import com.jacobbevan.photoapp.model.AlbumSummary;
import com.jacobbevan.photoapp.model.FilterCriteria;
import com.jacobbevan.photoapp.model.ImageSummary;
import com.jacobbevan.photoapp.model.SearchResult;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class GCPImageProvider implements ImageProvider {


    public void GCPImageProvider()
    {

    }

    @Override
    public byte[] getImage(ImageType imageType, String id) {
        return new byte[0];
    }

    @Override
    public AlbumSummary getAlbumSummary(String id) {
        return null;
    }

    @Override
    public List<ImageSummary> getImageSummaries(FilterCriteria filter) {
        return null;
    }

    @Override
    public List<AlbumSummary> getAlbumSummaries() {

        var summary = new AlbumSummary("a", "test album", "a stub method", null, LocalDateTime.now(), LocalDateTime.now());

        return Arrays.asList(summary);
    }

    @Override
    public void reIndex() {

    }

    @Override
    public ImageSummary putImage(byte[] fileContent, String fileName, String contentType, String folder) {
        return null;
    }

    @Override
    public void deleteImage(String id) {

    }

    @Override
    public void deleteAlbum(String id) {

    }

    @Override
    public void updateAlbum(AlbumSummary value) {

    }

    @Override
    public void updateImage(ImageSummary value) {

    }

    @Override
    public AlbumSummary createAlbum(AlbumSummary value) {
        return null;
    }

    @Override
    public List<SearchResult> search(String text) {
        return null;
    }
}
