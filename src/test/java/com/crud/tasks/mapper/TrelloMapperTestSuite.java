package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TrelloMapperTestSuite {

    @Autowired
    private TrelloMapper trelloMapper;

    private List<TrelloList> getTrelloLists() {

        return List.of(new TrelloList("1", "ToDo", false),
                new TrelloList("2", "In Progress", false),
                new TrelloList("3", "Done", false));
    }

    private List<TrelloListDto> getTrelloListDtos() {

        return List.of(new TrelloListDto("21", "For Me", false),
                new TrelloListDto("22", "For My Dog", true),
                new TrelloListDto("23", "For My Car", false));
    }

    @Test
    void testMapToTrelloBoard() {
        //Given
        List<TrelloListDto> trelloListDtos = getTrelloListDtos();
        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("31", "My Board", trelloListDtos);
        //When
        TrelloBoard trelloBoard = trelloMapper.mapToTrelloBoard(trelloBoardDto);
        //Then
        assertNotNull(trelloBoard);
        assertEquals("For My Car", trelloBoard.getLists().get(2).getName());
        assertEquals("31", trelloBoard.getId());

    }

    @Test
    void testMapToTrelloBoardDto() {
        //Given
        List<TrelloList> trelloLists = getTrelloLists();
        TrelloBoard trelloBoard = new TrelloBoard("11", "Globott", trelloLists);
        //When
        TrelloBoardDto trelloBoardDto = trelloMapper.mapToTrelloBoardDto(trelloBoard);
        //Then
        assertNotNull(trelloBoardDto);
        assertEquals("11", trelloBoardDto.getId());
        assertEquals("Globott", trelloBoardDto.getName());
        assertEquals(3, trelloBoardDto.getLists().size());
    }

    @Test
    void testMapToListTrelloLists() {
        //Given
        List<TrelloListDto> trelloListDtos = getTrelloListDtos();
        //When
        List<TrelloList> mappedList = trelloMapper.mapToListTrelloLists(trelloListDtos);
        //Then
        assertNotNull(mappedList);
        assertEquals(3, mappedList.size());
        assertEquals("For Me", mappedList.get(0).getName());
    }

    @Test
    void mapToListTrelloListDtos() {
        //Given
        List<TrelloList> trelloLists = getTrelloLists();
        //When
        List<TrelloListDto> mappedLists = trelloMapper.mapToListTrelloListDtos(trelloLists);
        //Then
        assertNotNull(mappedLists);
        assertFalse(mappedLists.get(0).isClosed());
    }

    @Test
    void mapToTrelloBoardsList() {
        //Given
        List<TrelloListDto> trelloListDtos = getTrelloListDtos();
        List<TrelloBoardDto> trelloBoardDtos = List.of(new TrelloBoardDto("6", "test", trelloListDtos));
                //When
        List<TrelloBoard> mappedList = trelloMapper.mapToTrelloBoardsList(trelloBoardDtos);
        //Then
        assertNotNull(mappedList);
        assertEquals("test", mappedList.get(0).getName());
        assertEquals("6", mappedList.get(0).getId());
        assertEquals(3, mappedList.get(0).getLists().size());
    }

    @Test
    void testMapToTrelloBoardsDtoList() {
        //Given
        List<TrelloList> trelloLists = getTrelloLists();
        List<TrelloBoard> trelloBoardsList = List.of(new TrelloBoard("41", "Globott", trelloLists),
                new TrelloBoard("42", "Shopping", List.of()),
                new TrelloBoard("44", "kodilla", List.of()));

        //When
        List<TrelloBoardDto> mappedList = trelloMapper.mapToTrelloBoardsDtoList(trelloBoardsList);
        //Then
        assertNotNull(mappedList);
        assertEquals(3, mappedList.size());
    }

    @Test
    void mapToTrelloCardDto() {
        //Given
        TrelloCard trelloCard = new TrelloCard("buying flowers", "tulips", "top", "1");
        //When
        TrelloCardDto mappedCard = trelloMapper.mapToTrelloCardDto(trelloCard);
        //Then
        assertNotNull(mappedCard);
        assertEquals("buying flowers", mappedCard.getName());
        assertEquals("tulips", mappedCard.getDescription());
        assertEquals("top", mappedCard.getPos());
        assertEquals("1", mappedCard.getIdList());
    }

    @Test
    void mapToTrelloCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("find my dog", "black dog", "bottom", "1");
        //When
        TrelloCard mappedCard = trelloMapper.mapToTrelloCard(trelloCardDto);
        //Then
        assertNotNull(mappedCard);
        assertEquals("find my dog", mappedCard.getName());
        assertEquals("black dog", mappedCard.getDescription());
        assertEquals("bottom", mappedCard.getPos());
        assertEquals("1", mappedCard.getIdList());
    }
}