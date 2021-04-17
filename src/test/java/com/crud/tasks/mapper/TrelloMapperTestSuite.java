package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TrelloMapperTestSuite {

    @Autowired
    private TrelloMapper trelloMapper;

    private List<TrelloList> getTrelloLists() {
        TrelloList toDo = new TrelloList("1", "ToDo", false);
        TrelloList inProgress = new TrelloList("2", "In Progress", false);
        TrelloList done = new TrelloList("3", "Done", false);
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(toDo);
        trelloLists.add(inProgress);
        trelloLists.add(done);
        return trelloLists;
    }

    private List<TrelloListDto> getTrelloListDtos() {
        TrelloListDto forMe = new TrelloListDto("21", "For Me", false);
        TrelloListDto forMyDog = new TrelloListDto("22", "For My Dog", true);
        TrelloListDto forMyCar = new TrelloListDto("23", "For My Car", false);
        List<TrelloListDto> trelloListDtos = new ArrayList<>();
        trelloListDtos.add(forMe);
        trelloListDtos.add(forMyDog);
        trelloListDtos.add(forMyCar);
        return trelloListDtos;
    }

    @Test
    void testMapToTrelloBoard() {
        //Given
        List<TrelloListDto> trelloListDtos = getTrelloListDtos();
        TrelloBoardDto myBoard = new TrelloBoardDto("My Board", "31", trelloListDtos);
        //When
        TrelloBoard trelloBoard = trelloMapper.mapToTrelloBoard(myBoard);
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
        assertEquals("Globott", trelloBoardDto.getName());
        assertNotNull(trelloBoardDto);
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
        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("test", "6", trelloListDtos);
        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        trelloBoardDtos.add(trelloBoardDto);
        //When
        List<TrelloBoard> mappedList = trelloMapper.mapToTrelloBoardsList(trelloBoardDtos);
        //Then
        assertNotNull(mappedList);
        assertEquals("test", mappedList.get(0).getName());
    }

    @Test
    void testMapToTrelloBoardsDtoList() {
        //Given
        List<TrelloList> trelloLists = getTrelloLists();
        TrelloBoard globott = new TrelloBoard("41", "Globott", trelloLists);
        TrelloBoard shopping = new TrelloBoard("42", "Shopping", new ArrayList<>());
        TrelloBoard kodilla = new TrelloBoard("44", "kodilla", new LinkedList<>());
        List<TrelloBoard> trelloBoardsList = new ArrayList<>();
        trelloBoardsList.add(globott);
        trelloBoardsList.add(shopping);
        trelloBoardsList.add(kodilla);
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
    }

    @Test
    void mapToTrelloCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("find my dog", "black dog", "bottom", "1");
        //When
        TrelloCard mappedCard = trelloMapper.mapToTrelloCard(trelloCardDto);
        //Then
        assertNotNull(mappedCard);
        assertEquals("bottom", mappedCard.getPos());
    }
}