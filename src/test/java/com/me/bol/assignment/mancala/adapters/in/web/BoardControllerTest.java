package com.me.bol.assignment.mancala.adapters.in.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.me.bol.assignment.mancala.adapters.in.web.model.BoardDto;
import com.me.bol.assignment.mancala.adapters.in.web.model.UpdateBoardDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class BoardControllerTest {

  @Autowired
  private MockMvc mockMvc;

  private final ObjectMapper mapper = new ObjectMapper();

  @Test
  void shouldCreateANewBoard() throws Exception {
    var mockResult = mockMvc.perform(get("/")
            .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print())
        .andExpect(status().isOk())
        .andReturn();

    var boardDto = mapper.readValue(mockResult.getResponse().getContentAsString(), BoardDto.class);
    Assertions.assertAll(() -> {
      assertEquals(14, boardDto.getPits().size());
      assertEquals(0, boardDto.getPits().get(6).getStones());
      assertEquals(0, boardDto.getPits().get(13).getStones());
      assertEquals("ONE", boardDto.getNextPlayer());
      assertFalse(boardDto.isFinished());
    });
  }

  @Test
  void shouldUpdate() throws Exception {
    var firstMockResult = mockMvc.perform(get("/")
            .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print())
        .andExpect(status().isOk())
        .andReturn();

    var firstOne = mapper.readValue(firstMockResult.getResponse().getContentAsString(), BoardDto.class);

    var updateOne = UpdateBoardDto.builder().pits(firstOne.getPits()).currentPLayer("ONE").isFinished(false).build();

    var secondMockResult = mockMvc.perform(put("/update/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(updateOne))
        ).andDo(print())
        .andExpect(status().isOk())
        .andReturn();

    var secondOne = mapper.readValue(secondMockResult.getResponse().getContentAsString(), BoardDto.class);

    Assertions.assertAll(() ->
        assertNotEquals(firstOne.getPits().get(1).getStones(), secondOne.getPits().get(1).getStones())
    );
  }
}