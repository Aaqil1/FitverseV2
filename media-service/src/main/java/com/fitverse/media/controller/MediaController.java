package com.fitverse.media.controller;

import com.fitverse.media.dto.MediaAssetResponse;
import com.fitverse.media.dto.MediaUploadRequest;
import com.fitverse.media.service.MediaService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/media")
public class MediaController {

    private final MediaService service;

    public MediaController(MediaService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MediaAssetResponse upload(@Valid @RequestBody MediaUploadRequest request) {
        return service.upload(request);
    }

    @GetMapping("/user/{userId}")
    public List<MediaAssetResponse> list(@PathVariable Long userId) {
        return service.listForUser(userId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
