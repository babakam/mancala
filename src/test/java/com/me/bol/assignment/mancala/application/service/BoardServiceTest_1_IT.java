package com.me.bol.assignment.mancala.application.service;

import static com.me.bol.assignment.mancala.domain.Board.DOWN_PLAYER_BIG_PIT_INDEX;
import static com.me.bol.assignment.mancala.domain.Board.UP_PLAYER_BIG_PIT_INDEX;
import static com.me.bol.assignment.mancala.domain.Player.DOWN;
import static com.me.bol.assignment.mancala.domain.Player.UP;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.me.bol.assignment.mancala.application.port.in.requests.BoardUpdateRequest;
import com.me.bol.assignment.mancala.domain.Board;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BoardServiceTest_1_IT {

  @Autowired
  private BoardService boardService;

  @Test
  void testOneNormalMoveForPlayerOne() {
    var newBoard = boardService.createNewBoard(4);

    var updateBoard_1 = boardService.updateBoard(BoardUpdateRequest.builder()
        .board(newBoard)
        .selectedPitNumber(7)
        .build());

    firstAssertation(updateBoard_1, newBoard);

    Board updateBoard_2 = boardService.updateBoard(BoardUpdateRequest.builder()
        .board(updateBoard_1)
        .selectedPitNumber(0)
        .build());

    secondAssertion(updateBoard_2, updateBoard_1);

    Board updateBoard_3 = boardService.updateBoard(BoardUpdateRequest.builder()
        .board(updateBoard_2)
        .selectedPitNumber(11)
        .build());

    thirdAssertion(updateBoard_3, updateBoard_2);

    Board updateBoard_4 = boardService.updateBoard(BoardUpdateRequest.builder()
        .board(updateBoard_3)
        .selectedPitNumber(4)
        .build());

    fourthAssertion(updateBoard_4, updateBoard_3);
  }

  private void fourthAssertion(Board updateBoard_4, Board updateBoard_3) {
    assertAll(() -> {
      assertEquals(UP, updateBoard_4.getCurrentPlayer());
      assertEquals(updateBoard_3.getPits()[0].getStones(), updateBoard_4.getPits()[0].getStones());
      assertEquals(updateBoard_3.getPits()[1].getStones(), updateBoard_4.getPits()[1].getStones());
      assertEquals(updateBoard_3.getPits()[2].getStones(), updateBoard_4.getPits()[2].getStones());
      assertEquals(updateBoard_3.getPits()[3].getStones(), updateBoard_4.getPits()[3].getStones());
      assertEquals(0, updateBoard_4.getPits()[4].getStones());
      assertEquals(updateBoard_3.getPits()[5].getStones() + 1, updateBoard_4.getPits()[5].getStones());
      assertEquals(updateBoard_3.getPits()[DOWN_PLAYER_BIG_PIT_INDEX].getStones() + 1, updateBoard_4.getPits()[DOWN_PLAYER_BIG_PIT_INDEX].getStones());
      assertEquals(updateBoard_3.getPits()[7].getStones() + 1, updateBoard_4.getPits()[7].getStones());
      assertEquals(updateBoard_3.getPits()[8].getStones() + 1, updateBoard_4.getPits()[8].getStones());
      assertEquals(updateBoard_3.getPits()[9].getStones() + 1, updateBoard_4.getPits()[9].getStones());
      assertEquals(updateBoard_3.getPits()[10].getStones(), updateBoard_4.getPits()[10].getStones());
      assertEquals(0, updateBoard_4.getPits()[11].getStones());
      assertEquals(updateBoard_3.getPits()[12].getStones(), updateBoard_4.getPits()[12].getStones());
      assertEquals(updateBoard_3.getPits()[UP_PLAYER_BIG_PIT_INDEX].getStones(), updateBoard_4.getPits()[UP_PLAYER_BIG_PIT_INDEX].getStones());
    });
  }

  private void thirdAssertion(Board updateBoard_3, Board updateBoard_2) {
    assertAll(() -> {
      assertEquals(DOWN, updateBoard_3.getCurrentPlayer());
      assertEquals(updateBoard_2.getPits()[0].getStones() + 1, updateBoard_3.getPits()[0].getStones());
      assertEquals(updateBoard_2.getPits()[1].getStones() + 1, updateBoard_3.getPits()[1].getStones());
      assertEquals(updateBoard_2.getPits()[2].getStones() + 1, updateBoard_3.getPits()[2].getStones());
      assertEquals(updateBoard_2.getPits()[3].getStones(), updateBoard_3.getPits()[3].getStones());
      assertEquals(updateBoard_2.getPits()[4].getStones(), updateBoard_3.getPits()[4].getStones());
      assertEquals(updateBoard_2.getPits()[5].getStones(), updateBoard_3.getPits()[5].getStones());
      assertEquals(updateBoard_2.getPits()[DOWN_PLAYER_BIG_PIT_INDEX].getStones(), updateBoard_3.getPits()[DOWN_PLAYER_BIG_PIT_INDEX].getStones());
      assertEquals(updateBoard_2.getPits()[7].getStones(), updateBoard_3.getPits()[7].getStones());
      assertEquals(updateBoard_2.getPits()[8].getStones(), updateBoard_3.getPits()[8].getStones());
      assertEquals(updateBoard_2.getPits()[9].getStones(), updateBoard_3.getPits()[9].getStones());
      assertEquals(updateBoard_2.getPits()[10].getStones(), updateBoard_3.getPits()[10].getStones());
      assertEquals(0, updateBoard_3.getPits()[11].getStones());
      assertEquals(updateBoard_2.getPits()[12].getStones() + 1, updateBoard_3.getPits()[12].getStones());
      assertEquals(updateBoard_2.getPits()[UP_PLAYER_BIG_PIT_INDEX].getStones() + 1, updateBoard_3.getPits()[UP_PLAYER_BIG_PIT_INDEX].getStones());
    });
  }

  private void secondAssertion(Board updateBoard_2, Board updateBoard_1) {
    assertAll(() -> {
      assertEquals(UP, updateBoard_2.getCurrentPlayer());
      assertEquals(0, updateBoard_2.getPits()[0].getStones());
      assertEquals(updateBoard_1.getPits()[1].getStones() + 1, updateBoard_2.getPits()[1].getStones());
      assertEquals(updateBoard_1.getPits()[2].getStones() + 1, updateBoard_2.getPits()[2].getStones());
      assertEquals(updateBoard_1.getPits()[3].getStones() + 1, updateBoard_2.getPits()[3].getStones());
      assertEquals(updateBoard_1.getPits()[4].getStones() + 1, updateBoard_2.getPits()[4].getStones());
      assertEquals(updateBoard_1.getPits()[5].getStones(), updateBoard_2.getPits()[5].getStones());
      assertEquals(updateBoard_1.getPits()[DOWN_PLAYER_BIG_PIT_INDEX].getStones(), updateBoard_2.getPits()[DOWN_PLAYER_BIG_PIT_INDEX].getStones());
      assertEquals(0, updateBoard_2.getPits()[7].getStones());
      assertEquals(updateBoard_1.getPits()[8].getStones(), updateBoard_2.getPits()[8].getStones());
      assertEquals(updateBoard_1.getPits()[9].getStones(), updateBoard_2.getPits()[9].getStones());
      assertEquals(updateBoard_1.getPits()[10].getStones(), updateBoard_2.getPits()[10].getStones());
      assertEquals(updateBoard_1.getPits()[11].getStones(), updateBoard_2.getPits()[11].getStones());
      assertEquals(updateBoard_1.getPits()[12].getStones(), updateBoard_2.getPits()[12].getStones());
      assertEquals(updateBoard_1.getPits()[UP_PLAYER_BIG_PIT_INDEX].getStones(), updateBoard_2.getPits()[UP_PLAYER_BIG_PIT_INDEX].getStones());
    });
  }

  private void firstAssertation(Board updateBoard_1, Board newBoard) {
    assertAll(() -> {
      assertEquals(DOWN, updateBoard_1.getCurrentPlayer());
      assertEquals(newBoard.getPits()[0].getStones(), updateBoard_1.getPits()[0].getStones());
      assertEquals(newBoard.getPits()[1].getStones(), updateBoard_1.getPits()[1].getStones());
      assertEquals(newBoard.getPits()[2].getStones(), updateBoard_1.getPits()[2].getStones());
      assertEquals(newBoard.getPits()[3].getStones(), updateBoard_1.getPits()[3].getStones());
      assertEquals(newBoard.getPits()[4].getStones(), updateBoard_1.getPits()[4].getStones());
      assertEquals(newBoard.getPits()[5].getStones(), updateBoard_1.getPits()[5].getStones());
      assertEquals(newBoard.getPits()[DOWN_PLAYER_BIG_PIT_INDEX].getStones(), updateBoard_1.getPits()[DOWN_PLAYER_BIG_PIT_INDEX].getStones());
      assertEquals(0, updateBoard_1.getPits()[7].getStones());
      assertEquals(newBoard.getPits()[8].getStones() + 1, updateBoard_1.getPits()[8].getStones());
      assertEquals(newBoard.getPits()[9].getStones() + 1, updateBoard_1.getPits()[9].getStones());
      assertEquals(newBoard.getPits()[10].getStones() + 1, updateBoard_1.getPits()[10].getStones());
      assertEquals(newBoard.getPits()[11].getStones() + 1, updateBoard_1.getPits()[11].getStones());
      assertEquals(newBoard.getPits()[12].getStones(), updateBoard_1.getPits()[12].getStones());
      assertEquals(newBoard.getPits()[UP_PLAYER_BIG_PIT_INDEX].getStones(), updateBoard_1.getPits()[UP_PLAYER_BIG_PIT_INDEX].getStones());
    });
  }
}
