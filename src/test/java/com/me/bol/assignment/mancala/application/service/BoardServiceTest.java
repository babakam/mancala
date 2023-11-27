package com.me.bol.assignment.mancala.application.service;

import static com.me.bol.assignment.mancala.domain.Board.PITS_MAX_SIZE;
import static com.me.bol.assignment.mancala.domain.Board.PLAYER_ONE_BIG_PIT_INDEX;
import static com.me.bol.assignment.mancala.domain.Board.PLAYER_TWO_BIG_PIT_INDEX;
import static com.me.bol.assignment.mancala.domain.Player.ONE;
import static com.me.bol.assignment.mancala.domain.Player.TWO;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.me.bol.assignment.mancala.application.port.in.requests.BoardUpdateRequest;
import com.me.bol.assignment.mancala.application.service.impl.BoardHelperImpl;
import com.me.bol.assignment.mancala.domain.Board;
import com.me.bol.assignment.mancala.domain.Pit;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

  @InjectMocks
  private BoardService boardService;

  @Mock
  private BoardHelperImpl boardHelperImpl;

  @Test
  void shouldGiveMeANewBoard() {
    Board newBoard = boardService.createNewBoard(6);
    assertEquals(PITS_MAX_SIZE, newBoard.getPits().length);
    IntStream.range(0, PITS_MAX_SIZE).forEach(index -> {
      if (index == PLAYER_ONE_BIG_PIT_INDEX || index == PLAYER_TWO_BIG_PIT_INDEX) {
        assertEquals(0, newBoard.getPits()[index].getStones());
      } else {
        assertEquals(6, newBoard.getPits()[index].getStones());
      }
    });
    assertEquals(ONE, newBoard.getCurrentPlayer());
  }

  @Test
  void testNormalMoveForPlayerOne() {

    when(boardHelperImpl.isPitNotEmpty(any(), anyInt())).thenReturn(true);
    when(boardHelperImpl.isCaptureRule(any(), any(), anyInt())).thenReturn(false);
    when(boardHelperImpl.getNextPitByPlayer(any(), any(), anyInt())).thenReturn(new Pit(1));
    when(boardHelperImpl.getNextPlayer(any(), anyInt())).thenReturn(TWO);

    BoardUpdateRequest boardUpdateRequest =
        genrateBoardUpdateRequest(List.of(6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0));
    Board board = boardService.updateBoard(boardUpdateRequest);
    assertAll(() -> {
      assertEquals(0, board.getPits()[0].getStones());
      assertEquals(6, board.getPits()[1].getStones());
      assertEquals(TWO, board.getCurrentPlayer());
    });
  }

  @Test
  void shouldDoNothingDueToSelectedPitIsEmpty() {

    when(boardHelperImpl.isPitNotEmpty(any(), anyInt())).thenReturn(false);

    BoardUpdateRequest boardUpdateRequest =
        genrateBoardUpdateRequest(List.of(6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0));
    Board board = boardService.updateBoard(boardUpdateRequest);
    assertAll(() -> {
      assertEquals(6, board.getPits()[0].getStones());
      assertEquals(6, board.getPits()[1].getStones());
      assertEquals(TWO, board.getCurrentPlayer());
    });
  }

  @Test
  void shouldCoverApplyCaptureRule() {

    when(boardHelperImpl.isPitNotEmpty(any(), anyInt())).thenReturn(true);
    when(boardHelperImpl.isCaptureRule(any(), any(), anyInt())).thenReturn(true);

    doNothing().when(boardHelperImpl).applyCaptureRule(any(), any(), anyInt());

    BoardUpdateRequest boardUpdateRequest =
        genrateBoardUpdateRequest(List.of(6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0));

    Board board = boardService.updateBoard(boardUpdateRequest);
    assertAll(() -> {
      assertEquals(6, board.getPits()[0].getStones());
      assertEquals(6, board.getPits()[1].getStones());
      assertEquals(TWO, board.getCurrentPlayer());
    });
  }

  private BoardUpdateRequest genrateBoardUpdateRequest(List<Integer> stoneValues) {

    Pit[] pits = new Pit[PITS_MAX_SIZE];
    IntStream.range(0, PITS_MAX_SIZE)
        .forEach(index ->
            pits[index] = new Pit(stoneValues.get(index)));

    return BoardUpdateRequest.builder()
        .board(new Board(pits, ONE,false))
        .selectedPitNumber(0)
        .build();
  }
}