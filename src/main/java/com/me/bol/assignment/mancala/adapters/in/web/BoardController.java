package com.me.bol.assignment.mancala.adapters.in.web;

import com.me.bol.assignment.mancala.adapters.in.web.mappers.PitMapper;
import com.me.bol.assignment.mancala.adapters.in.web.model.BoardDto;
import com.me.bol.assignment.mancala.adapters.in.web.model.PitDto;
import com.me.bol.assignment.mancala.adapters.in.web.model.UpdateBoardDto;
import com.me.bol.assignment.mancala.application.port.in.BoardPort;
import com.me.bol.assignment.mancala.application.port.in.requests.BoardUpdateRequest;
import com.me.bol.assignment.mancala.domain.Board;
import com.me.bol.assignment.mancala.domain.Player;
import jakarta.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Objects;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class BoardController {

  public static final String BOARD = "board";
  private final BoardPort boardPort;
  private final PitMapper pitMapper;

  @GetMapping("/")
  public BoardDto homeRequestHandler(HttpSession httpSession) {
    var boardDto = getFromSession(httpSession);
    if (Objects.nonNull(boardDto)) {
      return boardDto;
    } else {
      var newBoard = boardPort.createNewBoard(6);

      var createdBoardDto = BoardDto.builder()
          .pits(Arrays.stream(newBoard.getPits()).map(pit -> PitDto.builder().stones(pit.getStones()).build()).toList())
          .nextPlayer(newBoard.getCurrentPlayer().name())
          .finished(newBoard.isGameFinished())
          .build();
      httpSession.setAttribute(BOARD, createdBoardDto);

      return createdBoardDto;
    }
  }

  @PutMapping("/update/{selectedPit}")
  @ResponseBody
  public BoardDto updateBoard(@RequestBody UpdateBoardDto sentUpdateBoardDto, @PathVariable int selectedPit, HttpSession httpSession) {
    var updatedBoard = boardPort.updateBoard(BoardUpdateRequest.builder()
        .board(new Board(pitMapper.toPit(sentUpdateBoardDto.getPits()),
            Player.valueOf(sentUpdateBoardDto.getCurrentPLayer()),
            sentUpdateBoardDto.isFinished()))
        .selectedPitNumber(selectedPit)
        .build());

    var updateBoardDto = BoardDto.builder()
        .pits(Arrays.stream(updatedBoard.getPits()).map(pit -> PitDto.builder().stones(pit.getStones()).build()).toList())
        .nextPlayer(updatedBoard.getCurrentPlayer().name())
        .finished(updatedBoard.isGameFinished())
        .build();

    httpSession.setAttribute(BOARD, updateBoardDto);

    return updateBoardDto;
  }

  private BoardDto getFromSession(HttpSession httpSession) {
    var attribute = httpSession.getAttribute(BOARD);
    return Objects.nonNull(attribute) ? (BoardDto) attribute : null;
  }
}
