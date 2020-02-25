$(function(){   
    var url = 'wss://'+ window.location.host + '/chatChannel/' + document.getElementById("form:gameId").value +'_'
    + document.getElementById("form:roomId").value;
    
    ws = new WebSocket(url);
    
    var url2 = 'wss://'+ window.location.host + '/gameChatChannel/' + document.getElementById("form:gameId").value +'_'
    + document.getElementById("form:roomId").value;
    
    ws2 = new WebSocket(url2);
    
    ws.onopen = function(){
        console.log("connect");
    };
    ws.onmessage = function() {
        console.log("push");
        pushChat();
    };
    ws.onclose = function() {
        console.log("close");
    };
    
    ws2.onopen = function(){
        console.log("connect2");
    };
    ws2.onmessage = function() {
        console.log("push2");
        getChat();
    };
    ws2.onclose = function() {
        console.log("close2");
    };
});