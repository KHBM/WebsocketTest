function check()
{
    console.log("hi");
    var answer = $('#ans').val();
    console.log("hi", answer);
    $.ajax('/acheck',
    {
        data: JSON.stringify({value:answer}),
        complete:function(e){
            console.log(e.responseText);
            $('#code').html(e.responseText);
        },
        method:'POST',
        dataType:'json',
        contentType:'application/json'});
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    check();
});

