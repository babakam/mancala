package com.me.bol.assignment.mancala.application.service;

import com.me.bol.assignment.mancala.domain.Pit;
import com.me.bol.assignment.mancala.domain.Player;

public interface BoardHelperApi {

  boolean isPitNotEmpty(Pit[] pits, int pitNumber);

  Pit getNextPitByPlayer(Pit[] pits, Player player, int currentPitNumber);

  boolean isCaptureRule(Pit[] pits, Player player, int currentPitNumber);

  void applyCaptureRule(Pit[] pits, Player currentPlayer, int currentPitNumber);

  int getOppositePitNumber(int currentPitNumber);

  Player getNextPlayer(Player currentPlayer, int lastPitNumber);

  boolean isGameFinished(Pit[] pits);
}
