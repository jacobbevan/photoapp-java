package com.jacobbevan.photoapp.controller;


import com.jacobbevan.photoapp.model.FilterCriteria;
import com.jacobbevan.photoapp.model.ImageSummary;
import com.jacobbevan.photoapp.service.ImageProvider;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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
    public List<ImageSummary> getImageSummaries(FilterCriteria filter)
    {
        var results = imageProvider.getImageSummaries(filter);
        //TODO think Spring will just take care of this for is if wired up correctly
        //return results.Select(s=>EnrichImageUris(s));
        return results;
    }

    @RequestMapping(value = "/api/images/thumbnail/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getThumb(@PathVariable String id) throws IOException {
        logger.info("getThumb "+ id);

        //TODO
        //if(id == null)
        //    return this.NotFound();

        try {
            return imageProvider.getImage(ImageProvider.ImageType.Thumbnail, id);
        } catch (IOException e) {

            logger.error("Exception when requesting id " + id, e);
            throw e;
        }
    }

    /*

        [HttpGet("FullImage/{id}")]
public async Task<IActionResult> GetFull(string id)
        {
        if(id == null)
        return this.NotFound();

        var  image = await _imageProvider.GetImage(ImageType.FullImage, id);

        return this.File(image, "image/jpeg");
        }


        [HttpGet("reindex")]
        public void GetReIndex()
        {
            imageProvider.reIndex();
        }

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