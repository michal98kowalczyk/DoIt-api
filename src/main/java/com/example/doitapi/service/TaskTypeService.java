package com.example.doitapi.service;

import com.example.doitapi.repository.TaskTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskTypeService {

    private final TaskTypeRepository taskTypeRepository;
}
