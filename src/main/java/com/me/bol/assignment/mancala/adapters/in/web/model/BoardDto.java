package com.me.bol.assignment.mancala.adapters.in.web.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
public class BoardDto {

  private List<PitDto> pits;

  private String nextPlayer;

  private boolean finished;
}
