package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.client.TrelloClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/trello")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class TrelloController {
    private final TrelloService trelloService;

    @PostMapping("createTrelloCard")
    public CreatedTrelloCard createTrelloCard(@RequestBody TrelloCardDto trelloCardDto) {
        return trelloService.createTrelloCard(trelloCardDto);
    }
    @GetMapping("getTrelloBoards")
    public List<TrelloBoardDto> getTrelloBoards() {
        return trelloService.fetchTrelloBoards();
    }

    @GetMapping("getTrelloBoardsSample")
    public List<TrelloBoardDto> getTrelloBoardsSample() {
        //GET request
        List<TrelloBoardDto> trelloBoards = trelloService.fetchTrelloBoards();

        trelloBoards.stream()
                .filter(n -> n.getId() != null)
                .forEach(trelloBoardDto -> System.out.println(trelloBoardDto.getId() + " " +
                trelloBoardDto.getName()));

        return Optional.of(trelloBoards)
                .orElse(Collections.emptyList());
    }

    @GetMapping("getTrelloBoardsAndLists")
    public void getTrelloBoardsAndLists() {
        //GET request
        List<TrelloBoardDto> trelloBoards = trelloService.fetchTrelloBoards();

        trelloBoards.forEach(trelloBoardDto -> {
                System.out.println(trelloBoardDto.getId() + " " + trelloBoardDto.getName());

                System.out.println("This board contains lists: '");

                trelloBoardDto.getLists().forEach(trelloListDto -> System.out.println(trelloListDto.getName() + " - " +
                        trelloListDto.getId() + " - " + trelloListDto.isClosed()));
        });
    }
}
