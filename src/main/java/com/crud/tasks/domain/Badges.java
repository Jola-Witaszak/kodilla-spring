package com.crud.tasks.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class Badges {

    @JsonProperty("votes")
    private int votes;

    @JsonProperty("attachmentsByType")
    private AttachmentsByType attachmentsByType;
}
