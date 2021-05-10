package com.crud.tasks.controller;

import com.crud.tasks.domain.*;
import com.crud.tasks.trello.facade.TrelloFacade;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig //imituje realne zbudowanie aplikacji
@WebMvcTest(TrelloController.class) //automatycznie konfiguruje infrastrukturę MVC, udostępnia kontroller do testowania
class TrelloControllerTest {

    @Autowired
    private MockMvc mockMvc;  //klasa pozwalająca na wykonanie żądań http, posiaada dodatkowe asercje

    @MockBean //podobna do @mock ale udostępnia mocka, który staje się beanem(dodaje zachowania beanowi). używamy ze @springJUnitWebConfig
    private TrelloFacade trelloFacade;

    @Test
    void shouldFetchEmptyTrelloBoards() throws Exception {
        //Given
        when(trelloFacade.fetchTrelloBoards()).thenReturn(List.of());
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                    .get("/v1/trello/boards")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void shouldFetchTrelloBoards() throws Exception {
        //Given
        List<TrelloListDto> trelloListsDto = List.of(new TrelloListDto("2", "List", false));
        List<TrelloBoardDto> trelloBoardsDto = List.of(new TrelloBoardDto("1", "board", trelloListsDto));
        when(trelloFacade.fetchTrelloBoards()).thenReturn(trelloBoardsDto);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                    .get("/v1/trello/boards")
                    .contentType(MediaType.APPLICATION_JSON))
            //TrelloBoardFields
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("board")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is("1")))
            // trelloList fields
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lists", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lists[0].id", Matchers.is("2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lists[0].name", Matchers.is("List")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lists[0].closed", Matchers.is(false)));
    }

    @Test
    void shouldCreateTrelloCard() throws Exception {
        //Given
        Trello trello = new Trello(6, 7);
        AttachmentsByType attachmentsByType = new AttachmentsByType(trello);
        Badges badges = new Badges(5, attachmentsByType);
        TrelloCardDto trelloCardDto = new TrelloCardDto("Test", "Description", "top", "1");
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("232", "Test", "http://test.com", badges);
        when(trelloFacade.createCard(any(TrelloCardDto.class))).thenReturn(createdTrelloCardDto);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(trelloCardDto);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                    .post("/v1/trello/cards")
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(jsonContent))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is("232")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Test")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.shortUrl", Matchers.is("http://test.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.badges.votes", Matchers.is(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.badges.attachmentsByType.trello.board", Matchers.is(6)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.badges.attachmentsByType.trello.card", Matchers.is(7)));
    }
}
