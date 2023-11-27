package com.me.bol.assignment.mancala.application.port.in;

import com.me.bol.assignment.mancala.application.port.in.requests.BoardUpdateRequest;
import com.me.bol.assignment.mancala.domain.Board;

public interface BoardPort {

  Board updateBoard(BoardUpdateRequest boardUpdateRequest);

  Board createNewBoard(int stonesForEachPit);
}
