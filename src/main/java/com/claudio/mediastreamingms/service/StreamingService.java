package com.claudio.mediastreamingms.service;

import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface StreamingService {
    ResponseEntity<ResourceRegion> getVideo(final String fileName, final String fileType, final String range) throws IOException;
}
