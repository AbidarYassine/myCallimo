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
        return appelService.save(appelDto);
    }


    @DeleteMapping("/id/{id}")
    public int delete(@PathVariable Integer id) {
        return appelService.delete(id);
    }

    @GetMapping()
    public List<AppelResponse> findAll() {
        return appelService.findAll();
    }

    @PostMapping(value = "/supervisor-id/{id}/offre/{id_offre}")
    public ResponseEntity<AppelResponse> saveAppelWithSup(@RequestParam("audio") MultipartFile audio,
                                                          @PathVariable("id") Integer id,
                                                          @PathVariable("id_offre") Integer id_offre,
                                                          @RequestParam("duree") String duree,
                                                          @RequestParam("typeAppel") String typeAppel
    ) {
        AppelDto appelDto = new AppelDto();
        appelDto.setTypeAppel(typeAppel);
        appelDto.setDuree(duree);
        AppelResponse appelResponse = appelService.saveWithSupervisor(appelDto, id, id_offre);
        AppelResponse res = appelService.updateAudio(audio, appelResponse.getId());
        return ResponseEntity.ok(res);
    }

    @PostMapping("/caller-id/{id}/offre/{id_offre}")
    public ResponseEntity<AppelResponse> saveWithCaller(@RequestParam("audio") MultipartFile audio,
                                                        @PathVariable("id") Integer id,
                                                        @PathVariable("id_offre") Integer id_offre,
                                                        @RequestParam("duree") String duree,
                                                        @RequestParam("typeAppel") String typeAppel) {
        AppelDto appelDto = new AppelDto();
        appelDto.setTypeAppel(typeAppel);
        appelDto.setDuree(duree);
        AppelResponse appelResponse = appelService.saveWithCaller(appelDto, id, id_offre);
        AppelResponse res = appelService.updateAudio(audio, appelResponse.getId());
        return ResponseEntity.ok(res);
    }

    @PostMapping("/no-audio/supervisor-id/{id}/offre/{id_offre}")
    public ResponseEntity<AppelResponse> saveWithSup(@RequestBody AppelDto appelDto, @PathVariable("id") Integer id, @PathVariable("id_offre") Integer id_offre) {
        AppelResponse appelResponse = appelService.saveWithSupervisor(appelDto, id, id_offre);
        return ResponseEntity.ok(appelResponse);
    }

    @PostMapping("/no-audio/caller-id/{id}/offre/{id_offre}")
    public ResponseEntity<AppelResponse> saveWithCaller(@RequestBody AppelDto appelDto, @PathVariable("id") Integer id, @PathVariable("id_offre") Integer id_offre) {
        AppelResponse appelResponse = appelService.saveWithCaller(appelDto, id, id_offre);
        return ResponseEntity.ok(appelResponse);
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
