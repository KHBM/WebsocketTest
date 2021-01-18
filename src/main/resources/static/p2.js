function check()
{
    console.log("hi");
    var answer = $('#ans').val();
    console.log("hi", answer);
    $.ajax('/p2check',
    {
      data: JSON.stringify({value:answer}),
      complete:function(e){
            if (e.responseText != "false")
            {
              console.log(e.responseText);
              location.href = "/"+e.responseText;
            }
            else
                        {
                          alert('틀렸습니다.');
                        }
             },
     method:'POST',
     dataType:'json',
     contentType:'application/json'});
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#ok" ).click(function() { console.log("hi2"); check(); });
});

