package com.me.bol.assignment.mancala.adapters.in.web.model;

import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@EqualsAndHashCode
@Getter
public class UpdateBoardDto {

  private List<PitDto> pits;
  private String currentPLayer;
  private boolean isFinished;

}
