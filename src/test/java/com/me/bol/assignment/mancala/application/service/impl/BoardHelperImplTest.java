package com.me.bol.assignment.mancala.application.service.impl;

import static com.me.bol.assignment.mancala.domain.Board.PITS_MAX_SIZE;
import static com.me.bol.assignment.mancala.domain.Board.PLAYER_ONE_BIG_PIT_INDEX;
import static com.me.bol.assignment.mancala.domain.Board.PLAYER_TWO_BIG_PIT_INDEX;
import static com.me.bol.assignment.mancala.domain.Board.START_PIT_INDEX;
import static com.me.bol.assignment.mancala.domain.Player.ONE;
import static com.me.bol.assignment.mancala.domain.Player.TWO;
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
  void pitShouldNotEmptyOrEmpty() {
    var pits = generatePits(List.of(1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1));
    assertAll(() -> {
      assertTrue(boardHelperImpl.isPitNotEmpty(pits, 1));
      assertTrue(boardHelperImpl.isPitNotEmpty(pits, 2));
      assertTrue(boardHelperImpl.isPitNotEmpty(pits, 8));
      assertFalse(boardHelperImpl.isPitNotEmpty(pits, 4));
      assertFalse(boardHelperImpl.isPitNotEmpty(pits, 9));
    });
  }

  @Test
  void shouldReturnBigBoxForPlayerOne() {
    var pits = generatePits(List.of(1, 1, 1, 1, 0, 1, 100, 1, 1, 0, 1, 1, 1, 1));
    var nextPitByPlayer = boardHelperImpl.getNextPitByPlayer(pits, ONE, 5);
    assertAll(() -> {
      assertEquals(pits[PLAYER_ONE_BIG_PIT_INDEX].getStones(), nextPitByPlayer.getStones());
      assertEquals(100, nextPitByPlayer.getStones());
    });
  }

  @Test
  void shouldReturnBigBoxForPlayerTwo() {
    var pits = generatePits(List.of(1, 1, 1, 1, 0, 1, 100, 1, 1, 0, 1, 1, 1, 500));
    var nextPitByPlayer = boardHelperImpl.getNextPitByPlayer(pits, TWO, 12);
    assertAll(() -> {
      assertEquals(pits[PLAYER_TWO_BIG_PIT_INDEX].getStones(), nextPitByPlayer.getStones());
      assertEquals(500, nextPitByPlayer.getStones());
    });
  }

  @Test
  void shouldReturnStartPitIndex() {
    var pits = generatePits(List.of(70, 1, 1, 1, 0, 1, 100, 1, 1, 0, 1, 1, 1, 500));
    var nextPitByPlayerTwo = boardHelperImpl.getNextPitByPlayer(pits, TWO, 13);
    var nextPitByPlayerOne = boardHelperImpl.getNextPitByPlayer(pits, ONE, 13);
    assertAll(() -> {
      assertEquals(pits[START_PIT_INDEX].getStones(), nextPitByPlayerTwo.getStones());
      assertEquals(70, nextPitByPlayerTwo.getStones());
      assertEquals(pits[START_PIT_INDEX].getStones(), nextPitByPlayerOne.getStones());
      assertEquals(70, nextPitByPlayerOne.getStones());
    });
  }

  @Test
  void shouldNotGoToBigPitForPlayerOne() {
    var pits = generatePits(List.of(1, 1, 1, 1, 0, 1, 100, 7, 1, 0, 1, 1, 1, 500));
    var nextPitByPlayer = boardHelperImpl.getNextPitByPlayer(pits, TWO, 5);
    assertAll(() -> {
      assertEquals(pits[PLAYER_ONE_BIG_PIT_INDEX + 1].getStones(), nextPitByPlayer.getStones());
      assertEquals(7, nextPitByPlayer.getStones());
    });
  }

  @Test
  void shouldNotGoToBigPitForPlayerTwo() {
    var pits = generatePits(List.of(17, 1, 1, 1, 0, 1, 100, 7, 1, 0, 1, 1, 1, 500));
    var nextPitByPlayer = boardHelperImpl.getNextPitByPlayer(pits, ONE, 12);
    assertAll(() -> {
      assertEquals(pits[START_PIT_INDEX].getStones(), nextPitByPlayer.getStones());
      assertEquals(17, nextPitByPlayer.getStones());
    });
  }

  @Test
  void testGetOppositePitNumber() {
    generatePits(List.of(17, 1, 1, 1, 0, 1, 100, 7, 1, 0, 1, 1, 1, 500));
    assertAll(() -> {
      assertEquals(0, boardHelperImpl.getOppositePitNumber(12));
      assertEquals(8, boardHelperImpl.getOppositePitNumber(4));
      assertEquals(-1, boardHelperImpl.getOppositePitNumber(PLAYER_ONE_BIG_PIT_INDEX));
      assertEquals(-1, boardHelperImpl.getOppositePitNumber(PLAYER_TWO_BIG_PIT_INDEX));
    });
  }

  @Test
  void shouldBeCaptureRule() {
    var pits1 = generatePits(List.of(0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 10, 0, 0, 0));
    assertTrue(boardHelperImpl.isCaptureRule(pits1, ONE, 1));

    var pits2 = generatePits(List.of(0, 0, 2, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0));
    assertTrue(boardHelperImpl.isCaptureRule(pits2, TWO, 8));
  }

  @Test
  void shouldApplyCaptureRule() {
    var pits1 = generatePits(List.of(0, 1, 0, 0, 0, 0, 20, 0, 0, 0, 10, 0, 0, 0));
    assertTrue(boardHelperImpl.isCaptureRule(pits1, ONE, 1));
    boardHelperImpl.applyCaptureRule(pits1, ONE, 1);
    assertEquals(31, pits1[PLAYER_ONE_BIG_PIT_INDEX].getStones());
    assertEquals(0, pits1[11].getStones());
    assertEquals(0, pits1[1].getStones());

    var pits2 = generatePits(List.of(5, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 20));
    assertTrue(boardHelperImpl.isCaptureRule(pits2, TWO, 11));
    boardHelperImpl.applyCaptureRule(pits2, TWO, 11);
    assertEquals(26, pits2[PLAYER_TWO_BIG_PIT_INDEX].getStones());
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
    assertEquals(ONE, boardHelperImpl.getNextPlayer(TWO, 4));
    assertEquals(ONE, boardHelperImpl.getNextPlayer(TWO, PLAYER_ONE_BIG_PIT_INDEX));
    assertEquals(ONE, boardHelperImpl.getNextPlayer(TWO, 9));
    assertEquals(ONE, boardHelperImpl.getNextPlayer(ONE, PLAYER_ONE_BIG_PIT_INDEX));
  }

  @Test
  void shouldTwoTheNextPlayer() {
    assertEquals(TWO, boardHelperImpl.getNextPlayer(ONE, 4));
    assertEquals(TWO, boardHelperImpl.getNextPlayer(ONE, PLAYER_TWO_BIG_PIT_INDEX));
    assertEquals(TWO, boardHelperImpl.getNextPlayer(ONE, 9));
    assertEquals(TWO, boardHelperImpl.getNextPlayer(TWO, PLAYER_TWO_BIG_PIT_INDEX));
  }

  private Pit[] generatePits(List<Integer> stoneValues) {
    Pit[] pits = new Pit[PITS_MAX_SIZE];
    IntStream.range(0, PITS_MAX_SIZE)
        .forEach(index ->
            pits[index] = new Pit(stoneValues.get(index)));
    return pits;
  }
}