package com.crud.tasks.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.Date;

@Builder
@Getter
public class Mail {
    private final String mailFrom;
    private final String replyTo;
    private final String mailTo;
    private final String toCc;
    private final Date sentDate;
    private final String subject;
    private final String message;
}
