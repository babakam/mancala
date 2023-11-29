package com.me.bol.assignment.mancala.application.service;

import static com.me.bol.assignment.mancala.domain.Board.END_PIT_INDEX;
import static com.me.bol.assignment.mancala.domain.Board.DOWN_PLAYER_BIG_PIT_INDEX;
import static com.me.bol.assignment.mancala.domain.Board.UP_PLAYER_BIG_PIT_INDEX;
import static com.me.bol.assignment.mancala.domain.Player.DOWN;
import static com.me.bol.assignment.mancala.domain.Player.UP;

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
    log.info("request for updating from player {}", boardUpdateRequest.getBoard().getCurrentPlayer());
    log.debug("Request board to update '{}'", boardUpdateRequest);
    var pits = BoardArrayUtil.copyPitArray(boardUpdateRequest.getBoard().getPits());
    var selectedPitNumber = boardUpdateRequest.getSelectedPitNumber();
    var currentPlayer = boardUpdateRequest.getBoard().getCurrentPlayer();

    if (boardHelperApi.isGameFinished(pits) ||
        isPlayerRequestInvalid(currentPlayer, selectedPitNumber) ||
        boardHelperApi.isSelectedPitEmpty(pits, selectedPitNumber)) {
      log.info("No action was taken due to GameIsFinished or playerRequestInvalid or emptyPitSelected");
      return boardUpdateRequest.getBoard();
    }

    var currentPit = pits[selectedPitNumber];
    var currentPitAllStones = currentPit.getStones();
    if (boardHelperApi.isCaptureRule(pits, currentPlayer, selectedPitNumber)) {
      log.info("Capture Rule occurred...");
      boardHelperApi.applyCaptureRule(pits, currentPlayer, selectedPitNumber);
      return new Board(pits, DOWN == currentPlayer ? Player.UP : DOWN, boardHelperApi.isGameFinished(pits));
    }
    log.info("Normal updating...");
    return generateUpdatedBoard(currentPitAllStones, pits, currentPlayer, selectedPitNumber);
  }

  @Override
  public Board createNewBoard(int stonesForEachPit) {
    log.info("A new board was requested.....");
    return new Board(stonesForEachPit);
  }

  private Board generateUpdatedBoard(int currentPitAllStones, Pit[] pits, Player currentPlayer, int selectedPitNumber) {
    pits[selectedPitNumber] = new Pit(0);
    int lastPitNumber = selectedPitNumber;
    while (currentPitAllStones != 0) {
      var nextPitIndex = boardHelperApi.getNextPitIndexByPlayer(pits, currentPlayer, selectedPitNumber);
      if (isValidToAdd(currentPlayer, nextPitIndex)) {
        var nextPitStones = pits[nextPitIndex].getStones();
        pits[nextPitIndex] = new Pit(nextPitStones + 1);
        --currentPitAllStones;
      }
      selectedPitNumber = selectedPitNumber + 1;
      lastPitNumber = selectedPitNumber;
      if (selectedPitNumber > END_PIT_INDEX) {
        selectedPitNumber = 0;
      }
    }
    return new Board(pits, boardHelperApi.getNextPlayer(currentPlayer, lastPitNumber), boardHelperApi.isGameFinished(pits));
  }

  private boolean isValidToAdd(Player player, int index) {
    return DOWN == player && UP_PLAYER_BIG_PIT_INDEX != index || UP == player && DOWN_PLAYER_BIG_PIT_INDEX != index;
  }

  private boolean isPlayerRequestInvalid(Player player, int selectedPitNumber) {
    return (DOWN == player && selectedPitNumber >= DOWN_PLAYER_BIG_PIT_INDEX)
        || (UP == player &&
        (selectedPitNumber <= DOWN_PLAYER_BIG_PIT_INDEX || selectedPitNumber >= UP_PLAYER_BIG_PIT_INDEX));
  }
}
