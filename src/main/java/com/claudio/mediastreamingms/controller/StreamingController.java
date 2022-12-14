package com.claudio.mediastreamingms.controller;

import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.io.IOException;

public interface StreamingController {
    @GetMapping(value = "/stream/{fileType}/{fileName}", produces = "application/octet-stream")
    ResponseEntity<ResourceRegion> getVideo(@RequestHeader(value = "Range", required = false) String httpRangeList,
                                            @PathVariable("fileType") String fileType,
                                            @PathVariable("fileName") String fileName) throws IOException;
}
