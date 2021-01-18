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
//        stompClient.subscribe('/ftopic/greetings', function (greeting) {
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
        stompClient.subscribe('/topic/queue/notify', function (greeting) {
                            console.log("LOG BRAODCAST", greeting);
                        }, function(error) {
                          alert('error');
                          console.log(error);
                        });
        stompClient.subscribe('/queue/queue/notify', function (greeting) {
                                    console.log("LOG111111 BRAODCAST", greeting);
                                }, function(error) {
                                  alert('error');
                                  console.log(error);
                                });
        stompClient.subscribe('/queue/notify', function (greeting) {
                  console.log("LOG2222222 BRAODCAST", greeting);
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
    for (var y = 0; y < message.length; y++)
    {
      for (var x = 0; x < message[0].length; x++)
      {
        var divs = $("<div class='blc " + (message[y][x] == 0 ? "bblc" : "wblc") + "' data-ix='"+(x+y*message[0].length)+"'>X</div>");
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
    $( "#gohome" ).click(function() {location.href= "/";})
});