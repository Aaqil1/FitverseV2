package com.fitverse.media.service;

import com.fitverse.media.dto.MediaAssetResponse;
import com.fitverse.media.dto.MediaUploadRequest;
import com.fitverse.media.entity.MediaAsset;
import com.fitverse.media.exception.MediaAssetNotFoundException;
import com.fitverse.media.mapper.MediaAssetMapper;
import com.fitverse.media.repository.MediaAssetRepository;
import com.fitverse.shared.storage.MinioConfiguration;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MediaService {

    private static final Logger log = LoggerFactory.getLogger(MediaService.class);

    private final MediaAssetRepository repository;
    private final MediaAssetMapper mapper;
    private final MinioClient minioClient;
    private final MinioConfiguration.MinioProperties minioProperties;

    public MediaService(MediaAssetRepository repository, MediaAssetMapper mapper, MinioClient minioClient,
            MinioConfiguration.MinioProperties minioProperties) {
        this.repository = repository;
        this.mapper = mapper;
        this.minioClient = minioClient;
        this.minioProperties = minioProperties;
    }

    public MediaAssetResponse upload(MediaUploadRequest request) {
        ensureBucket();
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .object(request.getFilename())
                    .contentType(request.getContentType())
                    .stream(new java.io.ByteArrayInputStream(new byte[0]), 0, -1)
                    .build());
        } catch (Exception e) {
            log.error("Failed to upload {} to MinIO", request.getFilename(), e);
            throw new IllegalStateException("Unable to upload media asset", e);
        }
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

    private void ensureBucket() {
        try {
            boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioProperties.getBucket()).build());
            if (!exists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioProperties.getBucket()).build());
            }
        } catch (Exception e) {
            log.error("Failed to ensure MinIO bucket {} exists", minioProperties.getBucket(), e);
            throw new IllegalStateException("Unable to initialize media bucket", e);
        }
    }
}
