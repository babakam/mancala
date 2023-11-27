package com.me.bol.assignment.mancala.application.service;

import static com.me.bol.assignment.mancala.domain.Board.PLAYER_ONE_BIG_PIT_INDEX;
import static com.me.bol.assignment.mancala.domain.Player.ONE;
import static com.me.bol.assignment.mancala.domain.Player.TWO;
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
        .selectedPitNumber(2)
        .build());

    assertEquals(ONE, updateBoard_1.getCurrentPlayer());
  }

  @Test
  void shouldThePlayerTwoPlayAgain() {
    var newBoard = boardService.createNewBoard(4);

    var updateBoard_1 = boardService.updateBoard(BoardUpdateRequest.builder()
        .board(newBoard)
        .selectedPitNumber(1)
        .build());

    var updateBoard_2 = boardService.updateBoard(BoardUpdateRequest.builder()
        .board(updateBoard_1)
        .selectedPitNumber(9)
        .build());

    assertEquals(TWO, updateBoard_2.getCurrentPlayer());
  }

  @Test
  void shouldCaptureRule() {
    var newBoard = boardService.createNewBoard(1);

    var updateBoard_1 = boardService.updateBoard(BoardUpdateRequest.builder()
        .board(newBoard)
        .selectedPitNumber(2)
        .build());

    var updateBoard_2 = boardService.updateBoard(BoardUpdateRequest.builder()
        .board(updateBoard_1)
        .selectedPitNumber(11)
        .build());

    var updateBoard_3 = boardService.updateBoard(BoardUpdateRequest.builder()
        .board(updateBoard_2)
        .selectedPitNumber(1)
        .build());

    assertAll(() ->
        assertEquals(2, updateBoard_3.getPits()[PLAYER_ONE_BIG_PIT_INDEX].getStones())
    );
  }
}
