package com.example.doitapi.service;

import com.example.doitapi.DoitApiApplication;
import com.example.doitapi.model.File;
import com.example.doitapi.model.Task;
import com.example.doitapi.model.User;
import com.example.doitapi.payload.response.FileResponse;
import com.example.doitapi.repository.FileRepository;
import com.example.doitapi.repository.TaskRepository;
import com.example.doitapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final FileRepository fileRepository;

    @Transactional
    public FileResponse addFile(MultipartFile multipartFile, Long userId, Long taskId) throws IOException {
        final Date currentDateTime = TimeService.getCurrentDateTime();
        User u = userRepository.findById(userId).get();
        Task t = taskRepository.findById(taskId).get();

        System.out.println("fiel time " + currentDateTime);
        File file = File.builder()
                .content(multipartFile.getBytes())
                .type(multipartFile.getContentType())
                .name(multipartFile.getOriginalFilename())
                .author(u)
                .task(t)
                .createdDate(currentDateTime)
                .build();

        File saved = null;
        try {
            saved = fileRepository.save(file);
        } catch (Exception e) {
            DoitApiApplication.logger.info(e.getMessage());
        }
        System.out.println("fiel saved " + saved.getId());

        return getFileResponse(file);
    }

    public ArrayList<FileResponse> getAllFilesByTask(Long id) {
        return getFileResponse((ArrayList<File>) fileRepository.findAllByTaskId(id));
    }

    public ArrayList<FileResponse> getAllFiles() {
        return getFileResponse((ArrayList<File>) fileRepository.findAll());
    }

    public FileResponse getFileResponse(File file) {

        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/file/")
                .path(String.valueOf(file.getId()))
                .toUriString();

        return FileResponse.builder().id(file.getId())
                .name(file.getName())
                .taskId(file.getTask() != null ? file.getTask().getId() : null)
                .authorId(file.getAuthor() != null ? file.getAuthor().getId() : null)
                .url(fileDownloadUri)
                .type(file.getType())
                .createdDate(file.getCreatedDate())
                .errorMessage(file.getErrorMessage())
                .build();
    }

    @Transactional
    public File getFile(Long id) {
        return fileRepository.findById(id).get();
    }


    public ArrayList<FileResponse> getFileResponse(ArrayList<File> files) {
        ArrayList<FileResponse> fileResponses = new ArrayList<>();
        for (File t : files) {
            fileResponses.add(getFileResponse(t));
        }
        return fileResponses;
    }

    public ArrayList<FileResponse> addFiles(ArrayList<MultipartFile> files, Long userId, Long taskId) {
        ArrayList<FileResponse> responses = new ArrayList<>();
        files.forEach(f-> {
            try {
                responses.add(addFile(f,userId,taskId));
            } catch (IOException e) {
                DoitApiApplication.logger.info(e.getMessage());
            }
        });
        return responses;
    }
}


