package com.jacobbevan.photoapp.service;

import com.google.cloud.storage.*;
import com.jacobbevan.photoapp.model.AlbumSummary;
import com.jacobbevan.photoapp.model.FilterCriteria;
import com.jacobbevan.photoapp.model.ImageSummary;
import com.jacobbevan.photoapp.model.SearchResult;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class GCPImageProvider implements ImageProvider {

    private final Storage storage;
    private final BucketOptions options;

    public GCPImageProvider()
    {
        this.options = new BucketOptions();
        this.storage = StorageOptions.getDefaultInstance().getService();
    }

    @Override
    public byte[] getImage(ImageType imageType, String id) throws IOException {
        //TODO must be possible to return as a stream rather than fully realising the byte array
        try(var byteStream = new ByteArrayOutputStream()) {

            var blob = storage.get(BlobId.of(options.getBucket(imageType), id));
            blob.downloadTo(byteStream);
            return byteStream.toByteArray();
        }
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

        var summary = new AlbumSummary("a", "test album", "edited to verify cd pipeline", null, LocalDateTime.now(), LocalDateTime.now());

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
