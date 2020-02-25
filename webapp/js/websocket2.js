$(function(){   
    var url = 'wss://'+ window.location.host + '/updateGamePageChannel/' + document.getElementById("form:gameId").value;
    
    ws = new WebSocket(url);
    
    ws.onopen = function(){
        console.log("connect");
    };
    ws.onmessage = function() {
        console.log("push");
        updateGame();
    };
    ws.onclose = function() {
        console.log("close");
    };

});