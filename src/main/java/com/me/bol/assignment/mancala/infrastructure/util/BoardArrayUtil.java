package com.me.bol.assignment.mancala.infrastructure.util;

import com.me.bol.assignment.mancala.domain.Pit;
import java.util.stream.IntStream;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BoardArrayUtil {

  public static Pit[] copyPitArray(Pit[] pits) {
    Pit[] newPits = new Pit[pits.length];
    IntStream.range(0, pits.length)
        .forEach(index -> newPits[index] = new Pit(pits[index].getStones()));
    return newPits;
  }

}
