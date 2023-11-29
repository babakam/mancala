package com.me.bol.assignment.mancala.application.service.impl;

import static com.me.bol.assignment.mancala.domain.Board.END_PIT_INDEX;
import static com.me.bol.assignment.mancala.domain.Board.DOWN_PLAYER_BIG_PIT_INDEX;
import static com.me.bol.assignment.mancala.domain.Board.UP_PLAYER_BIG_PIT_INDEX;
import static com.me.bol.assignment.mancala.domain.Board.START_PIT_INDEX;
import static com.me.bol.assignment.mancala.domain.Player.DOWN;
import static com.me.bol.assignment.mancala.domain.Player.UP;

import com.me.bol.assignment.mancala.application.service.BoardHelperApi;
import com.me.bol.assignment.mancala.domain.Pit;
import com.me.bol.assignment.mancala.domain.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BoardHelperImpl implements BoardHelperApi {

  @Override
  public boolean isSelectedPitEmpty(Pit[] pits, int pitNumber) {
    return pits[pitNumber].getStones() == 0;
  }

  @Override
  public int getNextPitIndexByPlayer(Pit[] pits, Player player, int currentPitNumber) {
    if (currentPitNumber == UP_PLAYER_BIG_PIT_INDEX) {
      return START_PIT_INDEX;
    }
    return currentPitNumber + 1;
  }

  @Override
  public int getOppositePitNumber(int currentPitNumber) {
    return currentPitNumber <= 12 && currentPitNumber != DOWN_PLAYER_BIG_PIT_INDEX ? 12 - currentPitNumber : -1;
  }

  @Override
  public boolean isCaptureRule(Pit[] pits, Player player, int currentPitNumber) {
    var currentPit = pits[currentPitNumber];
    var nextPit = getNextPitIndexByPlayer(pits, player, currentPitNumber);
    return
        currentPit.getStones() == 1 &&
            pits[nextPit].getStones() == 0 &&
            currentPitNumber + 1 != DOWN_PLAYER_BIG_PIT_INDEX &&
            currentPitNumber + 1 != UP_PLAYER_BIG_PIT_INDEX;
  }

  @Override
  public Player getNextPlayer(Player currentPlayer, int lastPitNumber) {
    if (DOWN == currentPlayer && lastPitNumber == DOWN_PLAYER_BIG_PIT_INDEX) {
      return DOWN;
    }
    if (UP == currentPlayer && lastPitNumber == UP_PLAYER_BIG_PIT_INDEX) {
      return UP;
    }
    return DOWN == currentPlayer ? UP : DOWN;
  }

  @Override
  public void applyCaptureRule(Pit[] pits, Player currentPlayer, int currentPitNumber) {
    pits[currentPitNumber] = new Pit(0);
    var oppositePitNumber = getOppositePitNumber(currentPitNumber + 1);
    var oppositPit = pits[oppositePitNumber];
    var oppositeAllStones = oppositPit.getStones();
    pits[oppositePitNumber] = new Pit(0);
    if (DOWN == currentPlayer) {
      var allStones = pits[DOWN_PLAYER_BIG_PIT_INDEX].getStones();
      pits[DOWN_PLAYER_BIG_PIT_INDEX] = new Pit(allStones + oppositeAllStones + 1);
    } else {
      var allStones = pits[UP_PLAYER_BIG_PIT_INDEX].getStones();
      pits[UP_PLAYER_BIG_PIT_INDEX] = new Pit(allStones + oppositeAllStones + 1);
    }
  }

  @Override
  public boolean isGameFinished(Pit[] pits) {
    var playerOneAllStones = checkPlayerStatus(pits, START_PIT_INDEX, DOWN_PLAYER_BIG_PIT_INDEX);
    var playerTwoAllStones = checkPlayerStatus(pits, 7, END_PIT_INDEX);
    if (playerOneAllStones == 0) {
      var allStones = pits[UP_PLAYER_BIG_PIT_INDEX].getStones();
      pits[UP_PLAYER_BIG_PIT_INDEX] = new Pit(allStones + playerTwoAllStones);
      emptyAll(pits);
      return true;
    } else if (playerTwoAllStones == 0) {
      int allStones = pits[DOWN_PLAYER_BIG_PIT_INDEX].getStones();
      pits[DOWN_PLAYER_BIG_PIT_INDEX] = new Pit(allStones + playerOneAllStones);
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
      if (i != DOWN_PLAYER_BIG_PIT_INDEX && i != UP_PLAYER_BIG_PIT_INDEX) {
        pits[i] = new Pit(0);
      }
    }
  }
}
