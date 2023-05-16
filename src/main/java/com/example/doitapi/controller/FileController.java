package com.example.doitapi.controller;

import com.example.doitapi.DoitApiApplication;
import com.example.doitapi.model.File;
import com.example.doitapi.payload.response.FileResponse;
import com.example.doitapi.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
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

    @GetMapping("/file/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long id) {


        File file = fileService.getFile(id);
        System.out.println("file "+file.getName());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(file.getContent());
    }

    @PostMapping("/file")
    public ResponseEntity<FileResponse> addFile(@RequestParam("file") MultipartFile file, @RequestParam("user") Long userId, @RequestParam("task") Long taskId) {
        FileResponse saved = null;
        try {
            saved = fileService.addFile(file, userId, taskId);
        } catch (Exception e) {
            DoitApiApplication.logger.info(e.getMessage());
        }
        if (saved == null) {
            return (ResponseEntity<FileResponse>) ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FileResponse.builder().errorMessage("Error occured").build());
        }
        System.out.println("file saved "+saved.toString());
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/files")
    public ResponseEntity<ArrayList<FileResponse>> addFile(@RequestParam("files") ArrayList<MultipartFile> files, @RequestParam("user") Long userId, @RequestParam("task") Long taskId) {
        ArrayList<FileResponse> saved = null;
        try {
            saved = fileService.addFiles(files,userId,taskId);
        } catch (Exception e) {
            DoitApiApplication.logger.info(e.getMessage());
        }
        if (saved == null) {
            ArrayList<FileResponse> er = new ArrayList<>();
            er.add(FileResponse.builder().errorMessage("Error occured").build());
            return (ResponseEntity<ArrayList<FileResponse>>) ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er);
        }

        return ResponseEntity.ok(saved);
    }
}
