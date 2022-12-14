package com.claudio.mediastreamingms.service.impl;

import com.claudio.mediastreamingms.service.StreamingService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Log4j2
public class StreamingServiceImpl implements StreamingService {

    private static final long CHUNK_SIZE = 1000000L;

    @Override
    public ResponseEntity<ResourceRegion> getVideo(String fileName, String fileType, String range) throws IOException {
        FileUrlResource videoResource = new FileUrlResource("videos/" + fileName + "." + fileType);
        log.info("File resource: " + videoResource.getFile().getAbsolutePath());
        ResourceRegion resourceRegion = getResourceRegion(videoResource, range);
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .contentType(MediaTypeFactory.getMediaType(videoResource).orElse(MediaType.APPLICATION_OCTET_STREAM))
                .body(resourceRegion);
    }

    private ResourceRegion getResourceRegion(UrlResource video, String httpHeaders) throws IOException {
        ResourceRegion resourceRegion;

        long contentLength = video.contentLength();
        log.info("Content lenght: " + contentLength);
        long fromRange = 0;
        long toRange = 0;
        if (StringUtils.isNotBlank(httpHeaders)) {
            String[] ranges = httpHeaders.substring("bytes=".length()).split("-");
            log.info("Ranges: " + rangesToString(ranges));
            fromRange = Long.parseLong(ranges[0]);
            if (ranges.length > 1) {
                toRange = Long.parseLong(ranges[1]);
            } else {
                toRange = contentLength;
            }
        }

        log.info("fromRange: " + fromRange + " toRange: " + toRange);

        if (fromRange > 0) {
            resourceRegion = new ResourceRegion(video, fromRange, toRange);
        } else {
            //long rangeLength = min(CHUNK_SIZE, contentLength);
            resourceRegion = new ResourceRegion(video, 0, toRange);
        }

        return resourceRegion;
    }

    private String rangesToString(String[] ranges) {
        StringBuilder string = new StringBuilder();
        string.append("[");
        for (String range : ranges) {
            string.append(range).append(", ");
        }
        string.append("]");
        return string.toString();
    }
}
