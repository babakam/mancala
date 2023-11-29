package com.me.bol.assignment.mancala.application.service;

import static com.me.bol.assignment.mancala.domain.Board.END_PIT_INDEX;
import static com.me.bol.assignment.mancala.domain.Board.PLAYER_ONE_BIG_PIT_INDEX;
import static com.me.bol.assignment.mancala.domain.Board.PLAYER_TWO_BIG_PIT_INDEX;
import static com.me.bol.assignment.mancala.domain.Player.ONE;
import static com.me.bol.assignment.mancala.domain.Player.TWO;

import com.me.bol.assignment.mancala.application.port.in.BoardPort;
import com.me.bol.assignment.mancala.application.port.in.requests.BoardUpdateRequest;
import com.me.bol.assignment.mancala.domain.Board;
import com.me.bol.assignment.mancala.domain.Pit;
import com.me.bol.assignment.mancala.domain.Player;
import com.me.bol.assignment.mancala.infrastructure.util.BoardArrayUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class BoardService implements BoardPort {

  private final BoardHelperApi boardHelperApi;

  @Override
  public Board updateBoard(BoardUpdateRequest boardUpdateRequest) {
    var pits = BoardArrayUtil.copyPitArray(boardUpdateRequest.getBoard().getPits());
    var selectedPitNumber = boardUpdateRequest.getSelectedPitNumber();
    var currentPlayer = boardUpdateRequest.getBoard().getCurrentPlayer();

    if (boardHelperApi.isGameFinished(pits) ||
        isPlayerRequestInvalid(currentPlayer, selectedPitNumber) ||
        boardHelperApi.isSelectedPitEmpty(pits, selectedPitNumber)) {

      return boardUpdateRequest.getBoard();
    }

    var currentPit = pits[selectedPitNumber];
    var currentPitAllStones = currentPit.getStones();
    if (boardHelperApi.isCaptureRule(pits, currentPlayer, selectedPitNumber)) {
      boardHelperApi.applyCaptureRule(pits, currentPlayer, selectedPitNumber);
      return new Board(pits, ONE == currentPlayer ? Player.TWO : ONE, boardHelperApi.isGameFinished(pits));
    }
    return generateUpdatedBoard(currentPitAllStones, pits, currentPlayer, selectedPitNumber);
  }

  @Override
  public Board createNewBoard(int stonesForEachPit) {
    return new Board(stonesForEachPit);
  }

  private Board generateUpdatedBoard(int currentPitAllStones, Pit[] pits, Player currentPlayer, int selectedPitNumber) {
    pits[selectedPitNumber].setStones(0);
    int lastPitNumber = selectedPitNumber;
    while (currentPitAllStones != 0) {
      var nextPit = boardHelperApi.getNextPitByPlayer(pits, currentPlayer, selectedPitNumber);
      nextPit.setStones(nextPit.getStones() + 1);
      selectedPitNumber = selectedPitNumber + 1;
      lastPitNumber = selectedPitNumber;
      if (selectedPitNumber > END_PIT_INDEX) {
        selectedPitNumber = 0;
      }
      --currentPitAllStones;
    }
    return new Board(pits, boardHelperApi.getNextPlayer(currentPlayer, lastPitNumber), boardHelperApi.isGameFinished(pits));
  }

  private boolean isPlayerRequestInvalid(Player player, int selectedPitNumber) {
    return (ONE == player && selectedPitNumber >= PLAYER_ONE_BIG_PIT_INDEX)
        || (TWO == player &&
        (selectedPitNumber <= PLAYER_ONE_BIG_PIT_INDEX || selectedPitNumber >= PLAYER_TWO_BIG_PIT_INDEX));
  }
}
