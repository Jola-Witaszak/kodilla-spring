package com.crud.tasks.trello.client;

import com.crud.tasks.domain.*;
import com.crud.tasks.trello.config.TrelloConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrelloClientTest {
    @InjectMocks
    private TrelloClient trelloClient;
    @Mock
    private TrelloConfig trelloConfig;
    @Mock
    private RestTemplate restTemplate;

    @MockitoSettings(strictness = Strictness.WARN)
    @Test
    public void shouldFetchTrelloBoards() throws URISyntaxException {
        // Given
        when(trelloConfig.getTrelloApiEndpoint()).thenReturn("http://test.com");
        when(trelloConfig.getTrelloAppKey()).thenReturn("test");
        when(trelloConfig.getTrelloAppToken()).thenReturn("test");
        when(trelloConfig.getTrelloUsername()).thenReturn("test");

        TrelloBoardDto[] trelloBoards = new TrelloBoardDto[1];
        trelloBoards[0] = new TrelloBoardDto("test_board","test_id", new ArrayList<>());

        URI uri = new URI("http://test.com/members/test/boards?fields=name,id&lists=all&key=test&token=test");

        when(restTemplate.getForObject(uri, TrelloBoardDto[].class)).thenReturn(trelloBoards);
        // When
        List<TrelloBoardDto> fetchedTrelloBoards = trelloClient.getTrelloBoards();
        // Then
        assertEquals(1, fetchedTrelloBoards.size());
        assertEquals("test_id", fetchedTrelloBoards.get(0).getId());
        assertEquals("test_board", fetchedTrelloBoards.get(0).getName());
        assertEquals(new ArrayList<>(), fetchedTrelloBoards.get(0).getLists());
    }

    @Test
    void shouldCreateCard() throws URISyntaxException {
        //Given
        when(trelloConfig.getTrelloApiEndpoint()).thenReturn("http://test.com");
        when(trelloConfig.getTrelloAppKey()).thenReturn("test");
        when(trelloConfig.getTrelloAppToken()).thenReturn("test");
        TrelloCardDto trelloCardDto = new TrelloCardDto(
                "Test Task",
                "Test description",
                "top",
                "test_id"
        );
        URI uri = new URI("http://test.com/cards?key=test&token=test&name=Test%20Task&desc=Test%20description&pos=top&idList=test_id");

        Trello trello = new Trello(6, 7);
        AttachmentsByType attachmentsByType = new AttachmentsByType(trello);
        Badges badges = new Badges(5, attachmentsByType);
        CreatedTrelloCardDto createdTrelloCard = new CreatedTrelloCardDto(
                "1", "Test Task", "http://test.com", badges);

        when(restTemplate.postForObject(uri, null, CreatedTrelloCardDto.class)).thenReturn(createdTrelloCard);
        //When
        CreatedTrelloCardDto newCard = trelloClient.createNewCard(trelloCardDto);
        //Then
        assertEquals("1", newCard.getId());
        assertEquals("Test Task", newCard.getName());
        assertEquals("http://test.com", newCard.getShortUrl());
    }

    @Test
    void shouldReturnEmptyList() throws URISyntaxException{
        //Given
        when(trelloConfig.getTrelloApiEndpoint()).thenReturn("http://test.com");
        when(trelloConfig.getTrelloAppKey()).thenReturn("test");
        when(trelloConfig.getTrelloAppToken()).thenReturn("test");
        when(trelloConfig.getTrelloUsername()).thenReturn("test");

        URI uri = new URI("http://test.com/members/test/boards?fields=name,id&lists=all&key=test&token=test");

        when(restTemplate.getForObject(uri, TrelloBoardDto[].class)).thenReturn(null);
        //When
        List<TrelloBoardDto> fetchedTrelloBoards = trelloClient.getTrelloBoards();
        //Then
        assertEquals(0, fetchedTrelloBoards.size());
    }
}