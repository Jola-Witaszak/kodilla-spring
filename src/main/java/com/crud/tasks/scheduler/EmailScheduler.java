package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailScheduler {
    private static final String SUBJECT = "Tasks: Once a day email";
    private final SimpleEmailService emailService;
    private final TaskRepository taskRepository;
    private final AdminConfig adminConfig;

    @Scheduled(cron = "0 0 8-12 * * *")
    public void sendInformationEmail() {
        long size = taskRepository.count();
        String taskOne = "Currently in the database you got: " + size + " task";
        String tasksMany = "Currently in the database you got: " + size + " tasks";
        emailService.send(
                Mail.builder()
                        .mailTo(adminConfig.getAdminMail())
                        .subject(SUBJECT)
                        .message(size == 1 ? taskOne : tasksMany)
                        .build()
        );
    }

    public static String getSUBJECT() {
        return SUBJECT;
    }
}
