var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}
function getUrlParams() {
    var params = {};
    window.location.search.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(str, key, value) { params[key] = value; });
    console.log("PARAMS!", params);
    return params;
}

function connect() {

    var socket = new SockJS('/gs-foxrain?token='+getUrlParams().token);
    stompClient = Stomp.over(socket);
//    stompClient.debug = null
    stompClient.connect({}, function (frame) {
        setConnected(true);
        sendName();
        console.log('Connected: ' + frame);
        stompClient.subscribe('/user/queue/greetings', function (greeting) {
            console.log('1');
            showGreeting(JSON.parse(greeting.body));
        }, function(error) {
          alert('error');
          console.log(error);
        });
        stompClient.subscribe('/user/queue/errors', function (greeting) {
                    location.href="/";
                }, function(error) {
                  alert('error');
                  console.log(error);
                });
        stompClient.subscribe('/queue/notify', function (greeting) {
                  console.log("LOG2222222 BRAODCAST", greeting);
                  Drawer.init.draw(greeting);
              }, function(error) {
                alert('error');
                console.log(error);
              });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
//    console.log("what msg", JSON.stringify({'name': $("#name").val()}));
    stompClient.send("/app/hello", {}, JSON.stringify({'id': $("#name").val()}));
}

function showGreeting(json) {
    $("#greetings").empty();

    var message = json.mapInfo;
    if (message.length == 0)
    {
      $('#greetings').append('<p>올 클리어 '+json.code+'</p>');
    }
    for (var y = 0; y < message.length; y++)
    {
      for (var x = 0; x < message[0].length; x++)
      {
        var divs = $("<div class='blc " + (message[y][x] == 0 ? "wblc" : "bblc") + "' data-ix='"+(x+y*message[0].length)+"'></div>");
        if (json.clear === false)
        {
          divs.on('click', function(e, i)
          {
            console.log($(e.target).data('ix'));
            stompClient.send("/app/click", {}, JSON.stringify({'id': $(e.target).data('ix')}));
          });
        } else {

        }
        $('#greetings').append(divs);
      }
      $('#greetings').append("<br>");
    }
    if (json.clear === true)
    {
      setTimeout(function() {
        alert('clear');
      }, 200);
      var nextBtn = $('<button class="btn btn-default">NEXT</button>');
      nextBtn.on('click', function()
      {
        stompClient.send("/app/cleard", {}, "clear");
        console.log('go next');
      });
      $('#greetings').append(nextBtn);
    }
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
    $( "#gohome" ).click(function() { location.href= "/";});
    $( "#refresh" ).click(function() { stompClient.send("/app/click", {}, JSON.stringify({'id': -1})); });
});

function Drawer(ids)
{
  var id = ids;
}

Drawer.init = {

  draw : function(bodys)
  {
      var json = JSON.parse(bodys.body);
      var message = json.mapInfo;
      var idd = json.id;
      var nick = json.name;

      var target = $('#other'+idd);
      var toDiv = target;
      console.log('what', target);
      if (target.length == 0)
      {
        console.log('undefined');
        toDiv = $('<div id="other'+idd+'"></div>')
      }
      toDiv.empty();
      if (nick) {
        if (message.length == 0)
        {
          toDiv.append('<p>이 사람은 올 클리어</p>');
        }
        toDiv.append("<p>"+nick+"</p>")
      }
      for (var y = 0; y < message.length; y++)
      {
        for (var x = 0; x < message[0].length; x++)
        {
          var divs = $("<div class='oblc " + (message[y][x] == 0 ? "owblc" : "obblc") + "' data-ix='"+(x+y*message[0].length)+"'></div>");
          toDiv.append(divs);
        }
        toDiv.append("<br>");
      }
      if (target.length == 0)
      {
        $('#greetings_other').append(toDiv).append('<hr>');
      }

  }

}