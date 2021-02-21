package com.crud.tasks.controller;

import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.trello.client.TrelloClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/trello")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class TrelloController {
    private final TrelloClient trelloClient;

    @GetMapping("getTrelloBoards")
    public List<TrelloBoardDto> getTrelloBoards() {
        List<TrelloBoardDto> trelloBoards = trelloClient.getTrelloBoards();

        trelloBoards.stream()
                .filter(e -> e.getName().startsWith("Kodilla"))
                .filter(n -> n.getId() != null)
                .forEach(trelloBoardDto -> System.out.println(trelloBoardDto.getId() + " " +
                trelloBoardDto.getName()));

        return Optional.of(trelloBoards)
                .orElse(Collections.emptyList());
    }
}
