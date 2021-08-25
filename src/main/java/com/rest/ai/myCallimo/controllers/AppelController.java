package com.rest.ai.myCallimo.controllers;

import com.flickr4java.flickr.FlickrException;
import com.rest.ai.myCallimo.request.AppelDto;
import com.rest.ai.myCallimo.response.AppelResponse;
import com.rest.ai.myCallimo.services.facade.AppelService;
import com.rest.ai.myCallimo.services.facade.FlickrService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RequestMapping("/appel")
@RestController()
public class AppelController {

    private final AppelService appelService;
    private final FlickrService flickrService;

    public AppelController(AppelService appelService, FlickrService flickrService) {
        this.appelService = appelService;
        this.flickrService = flickrService;
    }

    @PostMapping("")
    public AppelResponse save(@Valid @RequestBody() AppelDto appelDto) {
        System.out.println(appelDto.getDate());
        return appelService.save(appelDto);
    }

    @PostMapping("/caller-id/{id}/offre/{id_offre}")
    public AppelResponse saveWithCaller(@RequestBody() AppelDto appelDto, @PathVariable() Integer id, @PathVariable() Integer id_offre) {
        return appelService.saveWithCaller(appelDto, id, id_offre);
    }

    @PostMapping("/supervisor-id/{id}/offre/{id_offre}")
    public AppelResponse saveWithSupervisor(@RequestBody() AppelDto appelDto, @PathVariable() Integer id, @PathVariable() Integer id_offre) {
        return appelService.saveWithSupervisor(appelDto, id, id_offre);
    }

    @DeleteMapping("/id/{id}")
    public int delete(@PathVariable Integer id) {
        return appelService.delete(id);
    }

    @GetMapping()
    public List<AppelResponse> findAll() {
        return appelService.findAll();
    }


    @PostMapping()
    public ResponseEntity<String> uploadAudio(@RequestPart("audio") MultipartFile audio) throws IOException, FlickrException, ExecutionException, InterruptedException {
        flickrService.uploadAudio(audio.getInputStream());
        // TODO save audio to correct Appele;
        return null;
    }
}
