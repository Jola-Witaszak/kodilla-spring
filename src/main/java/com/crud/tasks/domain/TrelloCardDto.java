package com.crud.tasks.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class TrelloCardDto {
    @NonNull
    private String name;
    @NonNull
    private String description;
    @NonNull
    private String pos;
    @NonNull
    private String idList;
}
