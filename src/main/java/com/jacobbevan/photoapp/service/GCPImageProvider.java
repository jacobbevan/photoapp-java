package com.jacobbevan.photoapp.service;

import com.google.cloud.Timestamp;
import com.google.cloud.datastore.*;
import com.google.cloud.storage.*;
import com.jacobbevan.photoapp.model.AlbumSummary;
import com.jacobbevan.photoapp.model.FilterCriteria;
import com.jacobbevan.photoapp.model.ImageSummary;
import com.jacobbevan.photoapp.model.SearchResult;
import com.jacobbevan.photoapp.utility.ImageTransform;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Service
public class GCPImageProvider implements ImageProvider {

    private final String ENTITY_ALBUM_SUMMARY = "AlbumSummary";
    private final String ENTITY_IMAGE_SUMMARY = "ImageSummary";

    private final Storage storage;
    private final Datastore datastore ;


    private final BucketOptions options;

    private final KeyFactory albumKeyFactory;
    private final KeyFactory imageKeyFactory;

    private final ImageSummaryConverter imageConverter;
    private final AlbumSummaryConverter albumConverter;

    public GCPImageProvider()
    {
        this.options = new BucketOptions();
        this.storage = StorageOptions.getDefaultInstance().getService();
        this.datastore = DatastoreOptions.getDefaultInstance().getService();
        this.imageConverter = new ImageSummaryConverter();
        this.albumConverter = new AlbumSummaryConverter();
        this.imageKeyFactory = datastore.newKeyFactory().setKind(ENTITY_IMAGE_SUMMARY);
        this.albumKeyFactory = datastore.newKeyFactory().setKind(ENTITY_ALBUM_SUMMARY);
    }

    @Override
    public byte[] getImage(ImageType imageType, String id) throws IOException {
        //TODO must be possible to return as a stream rather than fully realising the byte array

        try(var byteStream = new ByteArrayOutputStream()) {

            var blob = storage.get(BlobId.of(options.getBucket(imageType), id));
            blob.downloadTo(byteStream);

            var imageBytes =  byteStream.toByteArray();

            return ImageTransform.createThumbnail(imageBytes);


        }
    }

    @Override
    public AlbumSummary getAlbumSummary(String id) {
        return null;
    }

    @Override
    public List<ImageSummary> getImageSummaries(FilterCriteria filter) {

        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind("ImageSummary")
                .setFilter(StructuredQuery.PropertyFilter.eq("account", "azb"))
                .setOrderBy(StructuredQuery.OrderBy.desc("updated"))
                .build();

        var ret = new ArrayList<ImageSummary>();

        QueryResults<Entity> entities = datastore.run(query);
        entities.forEachRemaining(t->ret.add(this.imageConverter.to(t)));

        return ret;
    }

    @Override
    public List<AlbumSummary> getAlbumSummaries() {

        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind(ENTITY_ALBUM_SUMMARY)
                .setOrderBy(StructuredQuery.OrderBy.desc("updated"))
                .build();

        var results = this.datastore.run(query);

        List<AlbumSummary> ret = new ArrayList<>();

        while(results.hasNext()) {
            ret.add(albumConverter.to(results.next()));
        }

        return ret;
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
    public void updateImage(ImageSummary value) {

    }


    @Override
    public void updateAlbum(AlbumSummary value) {

        Key key = albumKeyFactory.newKey(value.getId());
        var entity = albumConverter.from(key, value);
        datastore.put(entity);
    }

    @Override
    public AlbumSummary createAlbum(AlbumSummary value) {

        var key = datastore.allocateId(albumKeyFactory.newKey());
        var entity = albumConverter.from(key, value);
        datastore.put(entity);
        value.setId(key.getId());
        return value;
    }

    @Override
    public List<SearchResult> search(String text) {
        return null;
    }


    private Key putImageRecord(ImageSummary imageSummary)
    {

        Key key = imageKeyFactory.newKey(imageSummary.getId());
        Entity entity = imageConverter.from(key, imageSummary);
        datastore.put(entity);
        return entity.getKey();
    }


    //TODO this needs to be fixed
    private Timestamp fromLocalDateTime(LocalDateTime dt) {

        return Timestamp.of(Date.from(dt.toInstant(ZoneOffset.UTC)));
    }
}
