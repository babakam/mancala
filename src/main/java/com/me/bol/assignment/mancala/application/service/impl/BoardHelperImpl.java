package com.me.bol.assignment.mancala.application.service.impl;

import static com.me.bol.assignment.mancala.domain.Board.END_PIT_INDEX;
import static com.me.bol.assignment.mancala.domain.Board.PLAYER_ONE_BIG_PIT_INDEX;
import static com.me.bol.assignment.mancala.domain.Board.PLAYER_TWO_BIG_PIT_INDEX;
import static com.me.bol.assignment.mancala.domain.Board.START_PIT_INDEX;
import static com.me.bol.assignment.mancala.domain.Player.ONE;
import static com.me.bol.assignment.mancala.domain.Player.TWO;

import com.me.bol.assignment.mancala.application.service.BoardHelperApi;
import com.me.bol.assignment.mancala.domain.Pit;
import com.me.bol.assignment.mancala.domain.Player;
import org.springframework.stereotype.Service;

@Service
public class BoardHelperImpl implements BoardHelperApi {

  @Override
  public boolean isPitNotEmpty(Pit[] pits, int pitNumber) {
    return pits[pitNumber].getStones() != 0;
  }

  @Override
  public Pit getNextPitByPlayer(Pit[] pits, Player player, int currentPitNumber) {
    if (ONE == player && currentPitNumber == PLAYER_TWO_BIG_PIT_INDEX - 1) {
      return pits[START_PIT_INDEX];
    }
    if (TWO == player && currentPitNumber == PLAYER_ONE_BIG_PIT_INDEX - 1) {
      return pits[PLAYER_ONE_BIG_PIT_INDEX + 1];
    }
    if (TWO == player && currentPitNumber == PLAYER_TWO_BIG_PIT_INDEX - 1) {
      return pits[PLAYER_TWO_BIG_PIT_INDEX];
    }
    if (currentPitNumber == PLAYER_TWO_BIG_PIT_INDEX) {
      return pits[START_PIT_INDEX];
    }
    return pits[currentPitNumber + 1];
  }

  @Override
  public int getOppositePitNumber(int currentPitNumber) {
    return currentPitNumber <= 12 && currentPitNumber != PLAYER_ONE_BIG_PIT_INDEX ? 12 - currentPitNumber : -1;
  }

  @Override
  public boolean isCaptureRule(Pit[] pits, Player player, int currentPitNumber) {
    var currentPit = pits[currentPitNumber];
    var nextPit = getNextPitByPlayer(pits, player, currentPitNumber);
    return
        currentPit.getStones() == 1 &&
            nextPit.getStones() == 0 &&
            currentPitNumber + 1 != PLAYER_ONE_BIG_PIT_INDEX &&
            currentPitNumber + 1 != PLAYER_TWO_BIG_PIT_INDEX;
  }

  @Override
  public Player getNextPlayer(Player currentPlayer, int lastPitNumber) {
    if (ONE == currentPlayer && lastPitNumber == PLAYER_ONE_BIG_PIT_INDEX) {
      return ONE;
    }
    if (TWO == currentPlayer && lastPitNumber == PLAYER_TWO_BIG_PIT_INDEX) {
      return TWO;
    }
    return ONE == currentPlayer ? TWO : ONE;
  }

  @Override
  public void applyCaptureRule(Pit[] pits, Player currentPlayer, int currentPitNumber) {
    pits[currentPitNumber].setStones(0);
    var oppositePitNumber = getOppositePitNumber(currentPitNumber + 1);
    var oppositPit = pits[oppositePitNumber];
    var oppositeAllStones = oppositPit.getStones();
    oppositPit.setStones(0);
    if (ONE == currentPlayer) {
      pits[PLAYER_ONE_BIG_PIT_INDEX].setStones(pits[PLAYER_ONE_BIG_PIT_INDEX].getStones() + oppositeAllStones + 1);
    } else {
      pits[PLAYER_TWO_BIG_PIT_INDEX].setStones(pits[PLAYER_TWO_BIG_PIT_INDEX].getStones() + oppositeAllStones + 1);
    }
  }

  @Override
  public boolean isGameFinished(Pit[] pits) {
    var playerOneAllStones = checkPlayerStatus(pits, START_PIT_INDEX, PLAYER_ONE_BIG_PIT_INDEX);
    var playerTwoAllStones = checkPlayerStatus(pits, 7, END_PIT_INDEX);
    if (playerOneAllStones == 0) {
      pits[PLAYER_TWO_BIG_PIT_INDEX].setStones(pits[PLAYER_TWO_BIG_PIT_INDEX].getStones() + playerTwoAllStones);
      emptyAll(pits);
      return true;
    } else if (playerTwoAllStones == 0) {
      pits[PLAYER_ONE_BIG_PIT_INDEX].setStones(pits[PLAYER_ONE_BIG_PIT_INDEX].getStones() + playerOneAllStones);
      emptyAll(pits);
      return true;
    }
    return false;
  }

  private int checkPlayerStatus(Pit[] pits, int startPoint, int endPoint) {
    int result = 0;
    for (int index = startPoint; index < endPoint; index++) {
      result = result + pits[index].getStones();
    }
    return result;
  }

  private void emptyAll(Pit[] pits) {
    for (int i = START_PIT_INDEX; i <= END_PIT_INDEX; i++) {
      if (i != PLAYER_ONE_BIG_PIT_INDEX && i != PLAYER_TWO_BIG_PIT_INDEX) {
        pits[i].setStones(0);
      }
    }
  }
}
