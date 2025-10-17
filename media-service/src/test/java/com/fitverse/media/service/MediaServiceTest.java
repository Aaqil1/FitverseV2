package com.fitverse.media.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fitverse.media.dto.MediaUploadRequest;
import com.fitverse.media.entity.MediaAsset;
import com.fitverse.media.exception.MediaAssetNotFoundException;
import com.fitverse.media.mapper.MediaAssetMapper;
import com.fitverse.media.repository.MediaAssetRepository;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MediaServiceTest {

    @Mock
    private MediaAssetRepository repository;

    private final MediaAssetMapper mapper = Mappers.getMapper(MediaAssetMapper.class);

    private MediaService service;

    @BeforeEach
    void setUp() {
        service = new MediaService(repository, mapper);
    }

    @Test
    void uploadCreatesMediaAsset() {
        MediaUploadRequest request = new MediaUploadRequest();
        request.setUserId(1L);
        request.setFilename("image.png");
        request.setContentType("image/png");

        when(repository.save(any(MediaAsset.class))).thenAnswer(invocation -> invocation.getArgument(0));

        assertThat(service.upload(request).getFilename()).isEqualTo("image.png");
    }

    @Test
    void listForUserReturnsAssets() {
        when(repository.findByUserId(1L)).thenReturn(Collections.singletonList(new MediaAsset(1L, "image.png", "image/png")));
        assertThat(service.listForUser(1L)).hasSize(1);
    }

    @Test
    void deleteThrowsWhenMissing() {
        when(repository.findById(10L)).thenReturn(Optional.empty());
        assertThrows(MediaAssetNotFoundException.class, () -> service.delete(10L));
    }

    @Test
    void deleteRemovesAsset() {
        MediaAsset asset = new MediaAsset(1L, "img.png", "image/png");
        when(repository.findById(1L)).thenReturn(Optional.of(asset));

        service.delete(1L);
        verify(repository).delete(asset);
    }
}
