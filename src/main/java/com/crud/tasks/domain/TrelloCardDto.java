package com.crud.tasks.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TrelloCardDto {
    private final String name;
    private final String description;
    private final String pos;
    private final String idList;
}
