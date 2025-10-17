package com.fitverse.media.exception;

public class MediaAssetNotFoundException extends RuntimeException {

    public MediaAssetNotFoundException(Long id) {
        super("Media asset %d not found".formatted(id));
    }
}
