package com.me.bol.assignment.mancala.application.service;

import static com.me.bol.assignment.mancala.domain.Board.UP_PLAYER_BIG_PIT_INDEX;
import static com.me.bol.assignment.mancala.domain.Player.DOWN;
import static com.me.bol.assignment.mancala.domain.Player.UP;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.me.bol.assignment.mancala.application.port.in.requests.BoardUpdateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BoardServiceTest_2_IT {

  @Autowired
  private BoardService boardService;

  @Test
  void shouldThePlayerOnePlayAgain() {
    var newBoard = boardService.createNewBoard(4);

    var updateBoard_1 = boardService.updateBoard(BoardUpdateRequest.builder()
        .board(newBoard)
        .selectedPitNumber(9)
        .build());

    assertEquals(UP, updateBoard_1.getCurrentPlayer());
  }

  @Test
  void shouldThePlayerTwoPlayAgain() {
    var newBoard = boardService.createNewBoard(4);

    var updateBoard_1 = boardService.updateBoard(BoardUpdateRequest.builder()
        .board(newBoard)
        .selectedPitNumber(8)
        .build());

    var updateBoard_2 = boardService.updateBoard(BoardUpdateRequest.builder()
        .board(updateBoard_1)
        .selectedPitNumber(2)
        .build());

    assertEquals(DOWN, updateBoard_2.getCurrentPlayer());
  }

  @Test
  void shouldCaptureRule() {
    var newBoard = boardService.createNewBoard(1);

    var updateBoard_1 = boardService.updateBoard(BoardUpdateRequest.builder()
        .board(newBoard)
        .selectedPitNumber(9)
        .build());

    var updateBoard_2 = boardService.updateBoard(BoardUpdateRequest.builder()
        .board(updateBoard_1)
        .selectedPitNumber(4)
        .build());

    var updateBoard_3 = boardService.updateBoard(BoardUpdateRequest.builder()
        .board(updateBoard_2)
        .selectedPitNumber(8)
        .build());

    assertAll(() ->
        assertEquals(2, updateBoard_3.getPits()[UP_PLAYER_BIG_PIT_INDEX].getStones())
    );
  }
}
