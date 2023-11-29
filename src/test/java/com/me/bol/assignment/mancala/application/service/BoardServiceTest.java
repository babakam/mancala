package com.me.bol.assignment.mancala.application.service;

import static com.me.bol.assignment.mancala.domain.Board.PITS_MAX_SIZE;
import static com.me.bol.assignment.mancala.domain.Board.DOWN_PLAYER_BIG_PIT_INDEX;
import static com.me.bol.assignment.mancala.domain.Board.UP_PLAYER_BIG_PIT_INDEX;
import static com.me.bol.assignment.mancala.domain.Player.DOWN;
import static com.me.bol.assignment.mancala.domain.Player.UP;
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
      if (index == DOWN_PLAYER_BIG_PIT_INDEX || index == UP_PLAYER_BIG_PIT_INDEX) {
        assertEquals(0, newBoard.getPits()[index].getStones());
      } else {
        assertEquals(6, newBoard.getPits()[index].getStones());
      }
    });
    assertEquals(UP, newBoard.getCurrentPlayer());
  }

  @Test
  void testNormalMoveForPlayerOne() {

    when(boardHelperImpl.isSelectedPitEmpty(any(), anyInt())).thenReturn(false);
    when(boardHelperImpl.isCaptureRule(any(), any(), anyInt())).thenReturn(false);
    when(boardHelperImpl.getNextPitIndexByPlayer(any(), any(), anyInt())).thenReturn(1);
    when(boardHelperImpl.getNextPlayer(any(), anyInt())).thenReturn(UP);

    BoardUpdateRequest boardUpdateRequest =
        genrateBoardUpdateRequest(List.of(6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0));
    Board board = boardService.updateBoard(boardUpdateRequest);
    assertAll(() -> {
      assertEquals(0, board.getPits()[0].getStones());
      assertEquals(12, board.getPits()[1].getStones());
      assertEquals(UP, board.getCurrentPlayer());
    });
  }

  @Test
  void shouldDoNothingDueToSelectedPitIsEmpty() {

    when(boardHelperImpl.isSelectedPitEmpty(any(), anyInt())).thenReturn(true);

    BoardUpdateRequest boardUpdateRequest =
        genrateBoardUpdateRequest(List.of(6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0));
    Board board = boardService.updateBoard(boardUpdateRequest);
    assertAll(() -> {
      assertEquals(6, board.getPits()[0].getStones());
      assertEquals(6, board.getPits()[1].getStones());
      assertEquals(DOWN, board.getCurrentPlayer());
    });
  }

  @Test
  void shouldCoverApplyCaptureRule() {

    when(boardHelperImpl.isSelectedPitEmpty(any(), anyInt())).thenReturn(false);
    when(boardHelperImpl.isCaptureRule(any(), any(), anyInt())).thenReturn(true);

    doNothing().when(boardHelperImpl).applyCaptureRule(any(), any(), anyInt());

    BoardUpdateRequest boardUpdateRequest =
        genrateBoardUpdateRequest(List.of(6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0));

    Board board = boardService.updateBoard(boardUpdateRequest);
    assertAll(() -> {
      assertEquals(6, board.getPits()[0].getStones());
      assertEquals(6, board.getPits()[1].getStones());
      assertEquals(UP, board.getCurrentPlayer());
    });
  }

  private BoardUpdateRequest genrateBoardUpdateRequest(List<Integer> stoneValues) {

    Pit[] pits = new Pit[PITS_MAX_SIZE];
    IntStream.range(0, PITS_MAX_SIZE)
        .forEach(index ->
            pits[index] = new Pit(stoneValues.get(index)));

    return BoardUpdateRequest.builder()
        .board(new Board(pits, DOWN,false))
        .selectedPitNumber(0)
        .build();
  }
}