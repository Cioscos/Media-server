package com.claudio.mediastreamingms.controller.impl;

import com.claudio.mediastreamingms.controller.StreamingController;
import com.claudio.mediastreamingms.service.StreamingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Log4j2
public class StreamingControllerImpl implements StreamingController {

    private final StreamingService streamingService;


    @Override
    public ResponseEntity<ResourceRegion> getVideo(
            String httpRangeList,
            String fileType,
            String fileName) throws IOException {
        log.info("HTTP RANGE LIST: " + httpRangeList);
        return streamingService.getVideo(fileName, fileType, httpRangeList);
    }
}
