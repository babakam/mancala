package com.me.bol.assignment.mancala.adapters.in.web.mappers;

import com.me.bol.assignment.mancala.adapters.in.web.model.PitDto;
import com.me.bol.assignment.mancala.domain.Pit;
import java.util.List;
import java.util.stream.IntStream;
import org.springframework.stereotype.Component;

@Component
public class PitMapper {

  public Pit[] toPit(List<PitDto> pitDtos) {
    var result = new Pit[pitDtos.size()];
    IntStream.range(0, pitDtos.size()).forEach(index -> result[index] = new Pit(pitDtos.get(index).getStones()));
    return result;
  }
}
