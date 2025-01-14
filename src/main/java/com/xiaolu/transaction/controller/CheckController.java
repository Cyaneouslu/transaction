package com.xiaolu.transaction.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangxl
 * @date 2025-01-14 19:22
 */
@RestController
public class CheckController {

    @GetMapping("/check")
    public ResponseEntity<String> check() {
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
}
