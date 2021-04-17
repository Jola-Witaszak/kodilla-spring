package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class TrelloMapper {
    public TrelloBoard mapToTrelloBoard(final TrelloBoardDto trelloBoardDto) {
        return new TrelloBoard(
                trelloBoardDto.getId(),
                trelloBoardDto.getName(),
                mapToListTrelloLists(trelloBoardDto.getLists()));
    }

    public TrelloBoardDto mapToTrelloBoardDto(final TrelloBoard trelloBoard) {
        return new TrelloBoardDto(trelloBoard.getName(),
                trelloBoard.getId(),
                mapToListTrelloListDtos(trelloBoard.getLists()));
    }

    public List<TrelloList> mapToListTrelloLists(final List<TrelloListDto> trelloListDto) {
        return trelloListDto.stream()
                .map(trelloList -> new TrelloList(trelloList.getId(), trelloList.getName(), trelloList.isClosed()))
                .collect(Collectors.toList());
    }

    public List<TrelloListDto> mapToListTrelloListDtos (final List<TrelloList> trelloLists) {
        return trelloLists.stream()
                .map(list -> new TrelloListDto(list.getId(), list.getName(), list.isClosed()))
                .collect(Collectors.toList());
    }

    public List<TrelloBoard> mapToTrelloBoardsList(final List<TrelloBoardDto> trelloBoardDtos) {
        return trelloBoardDtos.stream()
                .map(trelloBoard -> new TrelloBoard(trelloBoard.getId(), trelloBoard.getName(),
                        mapToListTrelloLists(trelloBoard.getLists())))
                .collect(Collectors.toList());
    }

    public List<TrelloBoardDto> mapToTrelloBoardsDtoList(final List<TrelloBoard> trelloBoards) {
        return trelloBoards.stream()
                .map(board -> new TrelloBoardDto(board.getId(), board.getName(),
                        mapToListTrelloListDtos(board.getLists())))
                .collect(Collectors.toList());
    }

    public TrelloCardDto mapToTrelloCardDto(final TrelloCard trelloCard) {
        return new TrelloCardDto(trelloCard.getName(),
                trelloCard.getDescription(),
                trelloCard.getPos(),
                trelloCard.getIdList());
    }

    public TrelloCard mapToTrelloCard(TrelloCardDto trelloCardDto) {
        return new TrelloCard(trelloCardDto.getName(),
                trelloCardDto.getDescription(),
                trelloCardDto.getPos(),
                trelloCardDto.getIdList());
    }
}
