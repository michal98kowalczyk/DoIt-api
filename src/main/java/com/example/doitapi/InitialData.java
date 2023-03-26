package com.example.doitapi;

import com.example.doitapi.model.Role;
import com.example.doitapi.model.TaskStatus;
import com.example.doitapi.model.TaskType;
import com.example.doitapi.model.User;
import com.example.doitapi.repository.TaskStatusRepository;
import com.example.doitapi.repository.TaskTypeRepository;
import com.example.doitapi.repository.UserRepository;
import com.example.doitapi.service.TaskStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Component
@RequiredArgsConstructor
public class InitialData {
    @Value("${doit.app.admin.password}")
    private String adminPassword;
    @Value("${doit.app.admin.username}")
    private String adminUsername;
    @Value("${doit.app.admin.email}")
    private String adminEmail;


    @Value("${doit.app.task}")
    private String task;

    @Value("${doit.app.status}")
    private String status;

    private final UserRepository userRepository;

    private final TaskTypeRepository taskTypeRepository;
    private final TaskStatusRepository taskStatusRepository;
    private final TaskStatusService taskStatusService;

    private final PasswordEncoder passwordEncoder;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        createAdminUser();
        createInitialTaskStatuses();
        createInitialTaskTypes();
    }



    private void createInitialTaskStatuses() {
        final String[] taskStatuses = status.split(",");
        Integer sequence = 1;
        ArrayList<TaskStatus> taskStatusArrayList = new ArrayList<>();
        for (String s: taskStatuses) {
            TaskStatus ts = TaskStatus.builder()
                    .name(s)
                    .sequence(sequence++)
                    .build();
            taskStatusArrayList.add(ts);
        }
        final ArrayList<TaskStatus> saved = taskStatusService.addStatus(taskStatusArrayList);
//        System.out.println("saved1  "+saved);
//        System.out.println("saved2  "+taskStatusRepository.findByName("New"));



    }

    private void createInitialTaskTypes() {
        ArrayList<TaskType> taskStatusArrayList = new ArrayList<>();

        System.out.println("task "+task);
        final String[] taskTypes = task.split(";");
        for (String taskType: taskTypes) {
            final String[] taskNameAndStatuses = taskType.split(":");
            String taskName = taskNameAndStatuses[0];
            String[] taskStatuses = taskNameAndStatuses[1].split(",");
            ArrayList<String> arl = new ArrayList<>();
            arl.addAll(List.of(taskStatuses));
            final ArrayList<TaskStatus> allByNameIn = taskStatusRepository.findAllByNameIn(arl);
            System.out.println("allByNameIn "+allByNameIn.toString());

            TaskType tt = TaskType.builder()
                    .name(taskName)
                    .availableTaskStatuses(allByNameIn)
                    .build();
            taskTypeRepository.save(tt);
            System.out.println("saved tt "+tt.toString());
        }
    }
    private void createAdminUser() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("Timestamp.valueOf(now) "+Timestamp.valueOf(now));

        Optional<User> userOptional = userRepository.findByEmail(adminEmail);
        Optional<User> userOptional2 = userRepository.findByEmail(adminUsername);
        if (!userOptional.isPresent() && !userOptional2.isPresent()) {
            var user = User.builder()
                    .username(adminUsername)
                    .firstName(adminUsername)
                    .lastName(adminUsername)
                    .email(adminEmail)
                    .password(passwordEncoder.encode(adminPassword))
                    .role(Role.ADMIN)
                    .isSuperUser(true)
                    .createdDate(Timestamp.valueOf(now))
                    .build();
            var savedUser = userRepository.save(user);
        }
    }
}
