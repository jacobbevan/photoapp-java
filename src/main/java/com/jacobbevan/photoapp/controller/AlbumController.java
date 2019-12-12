package com.jacobbevan.photoapp.controller;


import com.jacobbevan.photoapp.model.AlbumSummary;
import com.jacobbevan.photoapp.model.FilterCriteria;
import com.jacobbevan.photoapp.service.ImageProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class AlbumController {


    private ImageProvider imageProvider;

    @Autowired
    public AlbumController(ImageProvider imageProvider)
    {
        this.imageProvider = imageProvider;
    }

    @RequestMapping(value = "/api/albums", method = RequestMethod.GET)
    public List<AlbumSummary> getAlbumSummaries(FilterCriteria filter)
    {
        return imageProvider.getAlbumSummaries();
    }

    @RequestMapping(value = "/api/albums", method = RequestMethod.POST)
    public AlbumSummary createAlbum(AlbumSummary value)
    {
        return imageProvider.createAlbum(value);
    }

    @RequestMapping(value = "/api/albums/{id}", method = RequestMethod.GET)
    public AlbumSummary getAlbumSummary(String id)
    {
        return imageProvider.getAlbumSummary(id);
    }

    @RequestMapping(value = "/api/albums/{id}", method = RequestMethod.DELETE)
    public void delete(String id)
    {
        imageProvider.deleteAlbum(id);
    }

    @RequestMapping(value = "/api/albums/{id}", method = RequestMethod.PUT)
    public void updateAlbum(int id, AlbumSummary value)
    {
        imageProvider.updateAlbum(value);
    }


}
