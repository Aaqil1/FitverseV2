package com.fitverse.media.mapper;

import com.fitverse.media.dto.MediaAssetResponse;
import com.fitverse.media.entity.MediaAsset;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MediaAssetMapper {

    MediaAssetResponse toResponse(MediaAsset asset);
}
