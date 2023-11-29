package com.me.bol.assignment.mancala.application.service.impl;

import static com.me.bol.assignment.mancala.domain.Board.PITS_MAX_SIZE;
import static com.me.bol.assignment.mancala.domain.Board.DOWN_PLAYER_BIG_PIT_INDEX;
import static com.me.bol.assignment.mancala.domain.Board.UP_PLAYER_BIG_PIT_INDEX;
import static com.me.bol.assignment.mancala.domain.Board.START_PIT_INDEX;
import static com.me.bol.assignment.mancala.domain.Player.DOWN;
import static com.me.bol.assignment.mancala.domain.Player.UP;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.me.bol.assignment.mancala.domain.Pit;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BoardHelperImplTest {

  @InjectMocks
  private BoardHelperImpl boardHelperImpl;

  @Test
  void pitShouldNotEmpty() {
    var pits = generatePits(List.of(1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1));
    assertAll(() -> {
      assertFalse(boardHelperImpl.isSelectedPitEmpty(pits, 1));
      assertFalse(boardHelperImpl.isSelectedPitEmpty(pits, 2));
      assertFalse(boardHelperImpl.isSelectedPitEmpty(pits, 8));
      assertTrue(boardHelperImpl.isSelectedPitEmpty(pits, 4));
      assertTrue(boardHelperImpl.isSelectedPitEmpty(pits, 9));
    });
  }

  @Test
  void shouldReturnBigBoxForPlayerOne() {
    var pits = generatePits(List.of(1, 1, 1, 1, 0, 1, 100, 1, 1, 0, 1, 1, 1, 1));
    var nextPitByPlayer = boardHelperImpl.getNextPitIndexByPlayer(pits, DOWN, 5);
    assertAll(() -> {
      assertEquals(pits[DOWN_PLAYER_BIG_PIT_INDEX].getStones(), pits[nextPitByPlayer].getStones());
      assertEquals(100, pits[nextPitByPlayer].getStones());
    });
  }

  @Test
  void shouldReturnBigBoxForPlayerTwo() {
    var pits = generatePits(List.of(1, 1, 1, 1, 0, 1, 100, 1, 1, 0, 1, 1, 1, 500));
    var nextPitByPlayer = boardHelperImpl.getNextPitIndexByPlayer(pits, UP, 12);
    assertAll(() -> {
      assertEquals(pits[UP_PLAYER_BIG_PIT_INDEX].getStones(), pits[nextPitByPlayer].getStones());
      assertEquals(500, pits[nextPitByPlayer].getStones());
    });
  }

  @Test
  void shouldReturnStartPitIndex() {
    var pits = generatePits(List.of(70, 1, 1, 1, 0, 1, 100, 1, 1, 0, 1, 1, 1, 500));
    var nextPitByPlayerTwo = boardHelperImpl.getNextPitIndexByPlayer(pits, UP, 13);
    var nextPitByPlayerOne = boardHelperImpl.getNextPitIndexByPlayer(pits, DOWN, 13);
    assertAll(() -> {
      assertEquals(pits[START_PIT_INDEX].getStones(), pits[nextPitByPlayerTwo].getStones());
      assertEquals(70, pits[nextPitByPlayerTwo].getStones());
      assertEquals(pits[START_PIT_INDEX].getStones(), pits[nextPitByPlayerOne].getStones());
      assertEquals(70, pits[nextPitByPlayerOne].getStones());
    });
  }

  @Test
  void shouldNotGoToBigPitForPlayerOne() {
    var pits = generatePits(List.of(1, 1, 1, 1, 0, 1, 100, 7, 1, 0, 1, 1, 1, 500));
    var nextPitByPlayer = boardHelperImpl.getNextPitIndexByPlayer(pits, UP, 5);
    assertAll(() -> {
      assertEquals(pits[DOWN_PLAYER_BIG_PIT_INDEX].getStones(), pits[nextPitByPlayer].getStones());
      assertEquals(100,pits[nextPitByPlayer].getStones());
    });
  }

  @Test
  void shouldNotGoToBigPitForPlayerTwo() {
    var pits = generatePits(List.of(17, 1, 1, 1, 0, 1, 100, 7, 1, 0, 1, 1, 1, 500));
    var nextPitByPlayer = boardHelperImpl.getNextPitIndexByPlayer(pits, DOWN, 12);
    assertAll(() -> {
      assertEquals(pits[UP_PLAYER_BIG_PIT_INDEX].getStones(), pits[nextPitByPlayer].getStones());
      assertEquals(500, pits[nextPitByPlayer].getStones());
    });
  }

  @Test
  void testGetOppositePitNumber() {
    generatePits(List.of(17, 1, 1, 1, 0, 1, 100, 7, 1, 0, 1, 1, 1, 500));
    assertAll(() -> {
      assertEquals(0, boardHelperImpl.getOppositePitNumber(12));
      assertEquals(8, boardHelperImpl.getOppositePitNumber(4));
      assertEquals(-1, boardHelperImpl.getOppositePitNumber(DOWN_PLAYER_BIG_PIT_INDEX));
      assertEquals(-1, boardHelperImpl.getOppositePitNumber(UP_PLAYER_BIG_PIT_INDEX));
    });
  }

  @Test
  void shouldBeCaptureRule() {
    var pits1 = generatePits(List.of(0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 10, 0, 0, 0));
    assertTrue(boardHelperImpl.isCaptureRule(pits1, DOWN, 1));

    var pits2 = generatePits(List.of(0, 0, 2, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0));
    assertTrue(boardHelperImpl.isCaptureRule(pits2, UP, 8));
  }

  @Test
  void shouldApplyCaptureRule() {
    var pits1 = generatePits(List.of(0, 1, 0, 0, 0, 0, 20, 0, 0, 0, 10, 0, 0, 0));
    assertTrue(boardHelperImpl.isCaptureRule(pits1, DOWN, 1));
    boardHelperImpl.applyCaptureRule(pits1, DOWN, 1);
    assertEquals(31, pits1[DOWN_PLAYER_BIG_PIT_INDEX].getStones());
    assertEquals(0, pits1[11].getStones());
    assertEquals(0, pits1[1].getStones());

    var pits2 = generatePits(List.of(5, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 20));
    assertTrue(boardHelperImpl.isCaptureRule(pits2, UP, 11));
    boardHelperImpl.applyCaptureRule(pits2, UP, 11);
    assertEquals(26, pits2[UP_PLAYER_BIG_PIT_INDEX].getStones());
    assertEquals(0, pits2[0].getStones());
    assertEquals(0, pits2[11].getStones());
  }

  @Test
  void shouldGameIsNotFinished() {
    var pits1 = generatePits(List.of(0, 0, 0, 0, 1, 0, 20, 0, 0, 0, 10, 0, 0, 0));
    assertFalse(boardHelperImpl.isGameFinished(pits1));
  }
  @Test
  void shouldGameIsFinished() {
    var pits1 = generatePits(List.of(0, 0, 0, 0, 0, 0, 20, 0, 0, 0, 10, 0, 0, 0));
    assertTrue(boardHelperImpl.isGameFinished(pits1));
  }

  @Test
  void shouldOneTheNextPlayer() {
    assertEquals(DOWN, boardHelperImpl.getNextPlayer(UP, 4));
    assertEquals(DOWN, boardHelperImpl.getNextPlayer(UP, DOWN_PLAYER_BIG_PIT_INDEX));
    assertEquals(DOWN, boardHelperImpl.getNextPlayer(UP, 9));
    assertEquals(DOWN, boardHelperImpl.getNextPlayer(DOWN, DOWN_PLAYER_BIG_PIT_INDEX));
  }

  @Test
  void shouldTwoTheNextPlayer() {
    assertEquals(UP, boardHelperImpl.getNextPlayer(DOWN, 4));
    assertEquals(UP, boardHelperImpl.getNextPlayer(DOWN, UP_PLAYER_BIG_PIT_INDEX));
    assertEquals(UP, boardHelperImpl.getNextPlayer(DOWN, 9));
    assertEquals(UP, boardHelperImpl.getNextPlayer(UP, UP_PLAYER_BIG_PIT_INDEX));
  }

  private Pit[] generatePits(List<Integer> stoneValues) {
    Pit[] pits = new Pit[PITS_MAX_SIZE];
    IntStream.range(0, PITS_MAX_SIZE)
        .forEach(index ->
            pits[index] = new Pit(stoneValues.get(index)));
    return pits;
  }
}