package com.rest.ai.myCallimo.controllers;

import com.rest.ai.myCallimo.entities.AppelEntity;
import com.rest.ai.myCallimo.request.AppelDto;
import com.rest.ai.myCallimo.response.AppelResponse;
import com.rest.ai.myCallimo.services.facade.AppelService;
import com.rest.ai.myCallimo.services.facade.FlickrService;
import com.rest.ai.myCallimo.services.facade.OffreService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/appel")
@RestController()
public class AppelController {

    private final AppelService appelService;
    private final FlickrService flickrService;

    public AppelController(AppelService appelService, FlickrService flickrService, OffreService offreService) {
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

    @PutMapping(value = "/upload-audio/id/{id_appel}")
    public ResponseEntity<String> uploadFile(@RequestPart("audio") MultipartFile audio, @PathVariable("id_appel") Integer id_appel) {
        appelService.updateAudio(audio, id_appel);
        return ResponseEntity.ok("Done");
//        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/downloadFile/")
//                .path(appelResponse.getFileName())
//                .toUriString();
    }


    @GetMapping("/downloadAudio/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Integer id) {
        // Load file as Resource
        AppelEntity appelEntity = appelService.findById(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(appelEntity.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + appelEntity.getFileName() + "\"")
                .body(new ByteArrayResource(appelEntity.getData()));
    }
}
