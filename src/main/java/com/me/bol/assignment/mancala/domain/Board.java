package com.me.bol.assignment.mancala.domain;

import static com.me.bol.assignment.mancala.domain.Player.UP;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Board {

  public static final int DOWN_PLAYER_BIG_PIT_INDEX = 6;
  public static final int UP_PLAYER_BIG_PIT_INDEX = 13;
  public static final int START_PIT_INDEX = 0;
  public static final int END_PIT_INDEX = 13;
  public static final int PITS_MAX_SIZE = 14;

  private final Pit[] pits;

  private Player currentPlayer;

  private boolean isGameFinished;

  public Board(int stoneForeEachPit) {
    pits = new Pit[PITS_MAX_SIZE];
    for (int i = START_PIT_INDEX; i <= END_PIT_INDEX; i++) {
      if (i == DOWN_PLAYER_BIG_PIT_INDEX || i == UP_PLAYER_BIG_PIT_INDEX) {
        pits[i] = new Pit(0);
      } else {
        pits[i] = new Pit(stoneForeEachPit);
      }
    }
    currentPlayer = UP;
  }
}
