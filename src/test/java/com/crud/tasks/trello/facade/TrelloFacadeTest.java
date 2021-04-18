package com.crud.tasks.trello.facade;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.validator.TrelloValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrelloFacadeTest {
    @InjectMocks
    private TrelloFacade trelloFacade;

    @Mock
    private TrelloService trelloService;

    @Mock
    private TrelloMapper trelloMapper;

    @Mock
    private TrelloValidator trelloValidator;

    @Test
    void shouldFetchEmptyList() {
        //Given
        List<TrelloListDto> trelloListsDto = List.of(new TrelloListDto("1", "Tast_List", false));
        List<TrelloBoardDto> trelloBoards = List.of(new TrelloBoardDto("2", "Test", trelloListsDto));
        List<TrelloList> mappedTrelloLists = List.of(new TrelloList("3", "test_list", false));
        List<TrelloBoard> mappedTrelloBoards = List.of(new TrelloBoard("4", "test", mappedTrelloLists));
        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoards);
        when(trelloMapper.mapToTrelloBoardsList(trelloBoards)).thenReturn(mappedTrelloBoards);
        when(trelloMapper.mapToTrelloBoardsDtoList(anyList())).thenReturn(List.of());
        when(trelloValidator.validateTrelloBoards(mappedTrelloBoards)).thenReturn(List.of());
        //When
        List<TrelloBoardDto> trelloBoardDtos = trelloFacade.fetchTrelloBoards();
        //Then
        assertNotNull(trelloBoardDtos);
        assertEquals(0, trelloBoardDtos.size());
    }

    @Test
    void shouldFetchTrelloBoards() {
        // Given
        List<TrelloListDto> trelloLists = List.of(new TrelloListDto("1", "test_list", false));
        List<TrelloList> mappedTrelloLists = List.of(new TrelloList("1", "test_list", false));

        List<TrelloBoardDto> trelloBoardsDto = List.of(new TrelloBoardDto("test", "1", trelloLists));
        List<TrelloBoard> mappedTrelloBoards = List.of(new TrelloBoard("1", "test", mappedTrelloLists));

        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoardsDto);
        when(trelloMapper.mapToTrelloBoardsList(trelloBoardsDto)).thenReturn(mappedTrelloBoards);
        when(trelloMapper.mapToTrelloBoardsDtoList(anyList())).thenReturn(List.of());
        when(trelloValidator.validateTrelloBoards(mappedTrelloBoards)).thenReturn(List.of());
        //When
        List<TrelloBoardDto> trelloBoardDtos = trelloFacade.fetchTrelloBoards();
        //Then
        assertNotNull(trelloBoardDtos);
        assertEquals(0, trelloBoardDtos.size());

        trelloBoardDtos.forEach(boardDto -> {
            assertEquals("1", boardDto.getId());
            assertEquals("test", boardDto.getName());
            boardDto.getLists().forEach(trelloListDto -> {
                assertEquals("1", trelloListDto.getId());
                assertEquals("test_list", trelloListDto.getName());
                assertFalse(trelloListDto.isClosed());
            });
        });

    }

    @Test
    void shouldCreateCard() {
        //Given
        Trello trello = new Trello(1, 1);
        AttachmentsByType attachments = new AttachmentsByType(trello);
        Badges badges = new Badges(1, attachments);
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("1", "test_card",
                "http://test.com", badges);
        TrelloCard trelloCard = new TrelloCard("test_card", "description", "bottom", "4");
        TrelloCardDto trelloCardDto = new TrelloCardDto("test_card", "description", "bottom", "4");

        when(trelloService.createTrelloCard(trelloCardDto)).thenReturn(createdTrelloCardDto);
        when(trelloMapper.mapToTrelloCard(trelloCardDto)).thenReturn(trelloCard);
        when(trelloMapper.mapToTrelloCardDto(trelloCard)).thenReturn(trelloCardDto);
        doNothing().when(trelloValidator).validateCard(any(TrelloCard.class));
        //When
        CreatedTrelloCardDto result = trelloFacade.createCard(trelloCardDto);
        //Then
        verify(trelloValidator, times(1)).validateCard(trelloCard);
        assertEquals(createdTrelloCardDto, result);
    }
}