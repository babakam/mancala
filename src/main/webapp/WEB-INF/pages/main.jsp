<%--
  Created by IntelliJ IDEA.
  User: babak.azarmi.ext
  Date: 27/11/2023
  Time: 20:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Mancal</title>

    <script src="/webjars/jquery/3.7.1/jquery.min.js"></script>
    <link rel="stylesheet" href="/static/css/main.css" type="text/css">
</head>
<body>
<h2 id="playerTurn">-</h2>
<div class="board">
    <div class="section endsection">
        <div class="pot" id="13"></div>
    </div>
    <div class="section midsection">
        <div class="midrow topmid">
            <div class="pot" id="12" onclick="mouseClicked(this.id)"></div>
            <div class="pot" id="11" onclick="mouseClicked(this.id)"></div>
            <div class="pot" id="10" onclick="mouseClicked(this.id)"></div>
            <div class="pot" id="9" onclick="mouseClicked(this.id)"></div>
            <div class="pot" id="8" onclick="mouseClicked(this.id)"></div>
            <div class="pot" id="7" onclick="mouseClicked(this.id)"></div>
        </div>
        <div class="midrow botmid">
            <div class="pot" id="0" onclick="mouseClicked(this.id)"></div>
            <div class="pot" id="1" onclick="mouseClicked(this.id)"></div>
            <div class="pot" id="2" onclick="mouseClicked(this.id)"></div>
            <div class="pot" id="3" onclick="mouseClicked(this.id)"></div>
            <div class="pot" id="4" onclick="mouseClicked(this.id)"></div>
            <div class="pot" id="5" onclick="mouseClicked(this.id)"></div>
        </div>
    </div>
    <div class="section endsection">
        <div class="pot" id="6"></div>
    </div>
    <input type="button" value="RESET" onclick="reset()">
</div>

<script>
  let thisCurrentPlayer;
  let gameIsFinished;
  $(document).ready(function () {
    giveMeNewBoard();
  });

  function mouseClicked(id) {
    if (canPlayerOne(id)) {
      updateRequest(id, createBoard());
    }
    if (canPlayerTwo(id)) {
      updateRequest(id, createBoard());
    }
  }

  function giveMeNextPlayer() {
    return thisCurrentPlayer === 'DOWN' ? 'DOWN' : 'UP';
  }

  function createBoard() {
    let board = {
      pits: [
        {"stones": $('.pot#0').text()},
        {"stones": $('.pot#1').text()},
        {"stones": $('.pot#2').text()},
        {"stones": $('.pot#3').text()},
        {"stones": $('.pot#4').text()},
        {"stones": $('.pot#5').text()},
        {"stones": $('.pot#6').text()},
        {"stones": $('.pot#7').text()},
        {"stones": $('.pot#8').text()},
        {"stones": $('.pot#9').text()},
        {"stones": $('.pot#10').text()},
        {"stones": $('.pot#11').text()},
        {"stones": $('.pot#12').text()},
        {"stones": $('.pot#13').text()},
      ],
      currentPLayer: giveMeNextPlayer(),
      finished: gameIsFinished
    }
    return board;
  }

  function canPlayerOne(id) {
    return id >= 0 && id <= 5 && thisCurrentPlayer === 'DOWN' && gameIsFinished === false;

  }

  function canPlayerTwo(id) {
    return id >= 7 && id <= 12 && thisCurrentPlayer === 'UP' && gameIsFinished === false;
  }

  function giveMeNewBoard() {
    $.ajax({
      type: "GET",
      contentType: "application/json; charset=utf-8",
      cache: false,
      processData: false,
      dataType: 'text',
      timeout: 60000,
      url: "/new",
      success: function (data) {
        console.log(data);
        updateBoard(data);
      }, error: function (request, status, err) {
        console.log(status);
        console.log(err);
        return null;
      }
    });
  }

  function updateRequest(selected, data) {
    console.log(selected)
    console.log("sent  " + JSON.stringify(data))
    $.ajax({
      type: "PUT",
      contentType: "application/json; charset=utf-8",
      cache: false,
      processData: false,
      dataType: 'text',
      data: JSON.stringify(data),
      timeout: 60000,
      url: "/update/" + selected,
      success: function (data) {
        console.log(data);
        updateBoard(data);
      }, error: function (request, status, err) {
        console.log(status);
        console.log(err);
        return null;
      }
    });

  }

  function reset() {
    $.ajax({
      type: "GET",
      contentType: "application/json; charset=utf-8",
      cache: false,
      processData: false,
      dataType: 'text',
      timeout: 60000,
      url: "/reset",
      success: function (data) {
        console.log(data);
        updateBoard(data);
      }, error: function (request, status, err) {
        console.log(status);
        console.log(err);
        return null;
      }
    });
  }

  function updateBoard(data) {
    obj = JSON.parse(data.toString());
    thisCurrentPlayer = obj.nextPlayer;
    gameIsFinished = obj.finished
    $('#playerTurn').text(thisCurrentPlayer)

    $.each(obj.pits, function (index, element) {
      $('.pot#' + index).html(element.stones)
    });

    if (gameIsFinished === true) {
      let winner = 'Both';
      if (obj.pits[13].stones > obj.pits[6].stones) {
        winner = 'Up'
      }
      if (obj.pits[13].stones < obj.pits[6].stones) {
        winner = 'Down'
      }
      alert("Up Player Score: " + obj.pits[13].stones + " & Down Player Score: " + obj.pits[6].stones + ". The Winner is: !" + winner + "!");
    }

  }
</script>

</body>
</html>
