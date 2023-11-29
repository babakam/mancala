package com.me.bol.assignment.mancala.application.port.in.requests;

import com.me.bol.assignment.mancala.domain.Board;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@EqualsAndHashCode
@ToString
public class BoardUpdateRequest {

  private int selectedPitNumber;
  private Board board;
}
