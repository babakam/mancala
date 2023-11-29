package com.me.bol.assignment.mancala.domain;

import static com.me.bol.assignment.mancala.domain.Player.TWO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Board {

  public static final int PLAYER_ONE_BIG_PIT_INDEX = 6;
  public static final int PLAYER_TWO_BIG_PIT_INDEX = 13;
  public static final int START_PIT_INDEX = 0;
  public static final int END_PIT_INDEX = 13;
  public static final int PITS_MAX_SIZE = 14;

  private final Pit[] pits;

  private Player currentPlayer;

  private boolean isGameFinished;

  public Board(int stoneForeEachPit) {
    pits = new Pit[PITS_MAX_SIZE];
    for (int i = START_PIT_INDEX; i <= END_PIT_INDEX; i++) {
      if (i == PLAYER_ONE_BIG_PIT_INDEX || i == PLAYER_TWO_BIG_PIT_INDEX) {
        pits[i] = new Pit(0);
      } else {
        pits[i] = new Pit(stoneForeEachPit);
      }
    }
    currentPlayer = TWO;
  }
}
