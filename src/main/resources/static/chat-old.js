const friendList = document.querySelectorAll('.friend');
let usernameTo = "";
for(let friend of friendList){
    friend.addEventListener('click',function(){
        updateChatWindow(this);
    });
}
function updateChatWindow(element){
    let friendUsername = element.children[1].innerText;
    const nav = document.querySelector('.name');
    nav.innerText = friendUsername;
    usernameTo = friendUsername;
}

const sendButton = document.querySelector('.send-icon');
sendButton.addEventListener('click', sendMessagePressed);
function sendMessagePressed(e){
    e.preventDefault();
    const messageBox = document.querySelector('.message-text');
    const message = messageBox.value;
    // console.log(message);
    //this function will send the message to the other client
    sendMessage(message);

    
    messageBox.value='';

    //adding new element to chat window
    const el = document.createElement('div');
    el.classList.add("chat-window-element-left");
    el.innerText = message;
    const window = document.querySelector(".chat-window");
    window.appendChild(el);

}
let stompClient = null;
// function setConnected(connected) {
//     $("#connect").prop("disabled", connected);
//     $("#disconnect").prop("disabled", !connected);
//     if (connected) {
//         $("#conversation").show();
//     }
//     else {
//         $("#conversation").hide();
//     }
//     $("#greetings").html("");
// }

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        // setConnected(true);
        console.log('Connected: ' + frame);
        const username = document.querySelector("#username").innerText;
        console.log('here');
        stompClient.subscribe('/topic/receive', function (message) {
            showMessage(JSON.parse(message.body).content);
            // console.log('message received');
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
function sendMessage(message){
    // const message = document.querySelector('.message-text').value;
    stompClient.send("/app/send/"+usernameTo, {}, JSON.stringify({'message': message}));
}
function showMessage(message){
    console.log(message);
}
connect();