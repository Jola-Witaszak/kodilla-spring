package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.facade.TrelloFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/trello")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class TrelloController {
    @Autowired
    private final TrelloFacade trelloFacade;

    @PostMapping("createTrelloCard")
    public CreatedTrelloCardDto createTrelloCard(@RequestBody TrelloCardDto trelloCardDto) {
        return trelloFacade.createCard(trelloCardDto);
    }
    @GetMapping("getTrelloBoards")
    public List<TrelloBoardDto> getTrelloBoards() {
        return trelloFacade.fetchTrelloBoards();
    }
/*
    @GetMapping("getTrelloBoardsSample")
    public List<TrelloBoardDto> getTrelloBoardsSample() {
        //GET request
        List<TrelloBoardDto> trelloBoards = trelloFacade.fetchTrelloBoards();

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
        List<TrelloBoardDto> trelloBoards = trelloFacade.fetchTrelloBoards();

        trelloBoards.forEach(trelloBoardDto -> {
                System.out.println(trelloBoardDto.getId() + " " + trelloBoardDto.getName());

                System.out.println("This board contains lists: '");

                trelloBoardDto.getLists().forEach(trelloListDto -> System.out.println(trelloListDto.getName() + " - " +
                        trelloListDto.getId() + " - " + trelloListDto.isClosed()));
        });
    }*/
}
