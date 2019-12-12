package com.jacobbevan.photoapp.controller;
import com.jacobbevan.photoapp.model.FilterCriteria;
import com.jacobbevan.photoapp.model.ImageSummary;
import com.jacobbevan.photoapp.service.ImageProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ImageController
{
    private final static Logger logger = LoggerFactory.getLogger(ImageController.class);
    private final ImageProvider imageProvider;

    @Autowired
    public ImageController(ImageProvider imageProvider)
    {
        this.imageProvider = imageProvider;
        logger.info("ImageController created");
    }

    @RequestMapping(value = "/api/images", method = RequestMethod.GET)
    public List<ImageSummary> getImageSummaries(FilterCriteria filter) throws IOException {
        var results = imageProvider.getImageSummaries(filter);

        for(ImageSummary s : results) {
            Link thumbLink = linkTo(methodOn(ImageController.class).getThumb(s.getEncodedId())).withRel("thumb");
            Link fullLink = linkTo(methodOn(ImageController.class).getFull(s.getEncodedId())).withRel("image");
            s.add(fullLink);
            s.add(thumbLink);
        }
        return results;
    }

    @RequestMapping(value = "/api/images/thumbnail/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody HttpEntity<byte[]> getThumb(@PathVariable String id) throws IOException {

        String decodedId = ImageSummary.getDecodedId(id);
        logger.info("getThumb "+ decodedId );

        try {
            return new HttpEntity<>(imageProvider.getImage(ImageProvider.ImageType.Thumbnail,  decodedId));
        } catch (IOException e) {

            logger.error("Exception when requesting id " + decodedId, e);
            throw e;
        }
    }

    @RequestMapping(value = "/api/images/full/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody HttpEntity<byte[]> getFull(@PathVariable String id) throws IOException {

        String decodedId = ImageSummary.getDecodedId(id);
        logger.info("getThumb "+ decodedId );

        try {
            return new HttpEntity<>(imageProvider.getImage(ImageProvider.ImageType.FullImage, decodedId));
        } catch (IOException e) {

            logger.error("Exception when requesting id " + decodedId, e);
            throw e;
        }
    }

    /*

        [HttpPut("{id}")]
public Task Put(int id, [FromBody] ImageSummary value)
        {
        return _imageProvider.UpdateImage(value);
        }

        [HttpDelete("{id}")]
public Task Delete(string id)
        {
        return _imageProvider.DeleteImage(id);
        }

public static ImageSummary EnrichImageUris(ImageSummary summary)
        {
        summary.Thumbnail = new Uri($"{API_ROUTE}/Thumbnail/{summary.Id}", UriKind.Relative);
        summary.FullImage = new Uri($"{API_ROUTE}/Fullimage/{summary.Id}", UriKind.Relative);
        return summary;
        }
     */
}