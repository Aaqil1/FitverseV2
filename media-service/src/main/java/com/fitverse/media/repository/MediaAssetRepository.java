package com.fitverse.media.repository;

import com.fitverse.media.entity.MediaAsset;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaAssetRepository extends JpaRepository<MediaAsset, Long> {

    List<MediaAsset> findByUserId(Long userId);
}
