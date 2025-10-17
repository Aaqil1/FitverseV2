package com.fitverse.media.service;

import com.fitverse.media.dto.MediaAssetResponse;
import com.fitverse.media.dto.MediaUploadRequest;
import com.fitverse.media.entity.MediaAsset;
import com.fitverse.media.exception.MediaAssetNotFoundException;
import com.fitverse.media.mapper.MediaAssetMapper;
import com.fitverse.media.repository.MediaAssetRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MediaService {

    private final MediaAssetRepository repository;
    private final MediaAssetMapper mapper;

    public MediaService(MediaAssetRepository repository, MediaAssetMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public MediaAssetResponse upload(MediaUploadRequest request) {
        MediaAsset saved = repository.save(new MediaAsset(request.getUserId(), request.getFilename(), request.getContentType()));
        return mapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<MediaAssetResponse> listForUser(Long userId) {
        return repository.findByUserId(userId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public void delete(Long mediaId) {
        MediaAsset asset = repository.findById(mediaId)
                .orElseThrow(() -> new MediaAssetNotFoundException(mediaId));
        repository.delete(asset);
    }
}
