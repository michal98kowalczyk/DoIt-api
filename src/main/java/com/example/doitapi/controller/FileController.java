package com.example.doitapi.controller;

import com.example.doitapi.DoitApiApplication;
import com.example.doitapi.model.File;
import com.example.doitapi.payload.response.FileResponse;
import com.example.doitapi.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping("/file")
    public ResponseEntity<ArrayList<FileResponse>> getAllFiles() {
        final ArrayList<FileResponse> files = fileService.getAllFiles();
        return ResponseEntity.ok(files);
    }

    @PostMapping("/file")
    public ResponseEntity<FileResponse> addFile(@RequestParam("file") MultipartFile file) {
        System.out.println("multipartFile "+file.getContentType());
        System.out.println("multipartFile "+file.getName());

        FileResponse saved = null;
        try {
            saved = fileService.addFile(file);
        } catch (Exception e) {
            DoitApiApplication.logger.info(e.getMessage());
        }
        if (saved == null) {
            return (ResponseEntity<FileResponse>) ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FileResponse.builder().errorMessage("Error occured").build());
        }
        System.out.println("file saved "+saved.toString());
        return ResponseEntity.ok(saved);
    }
}
