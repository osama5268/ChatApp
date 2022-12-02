const friendList = document.querySelectorAll('.friend');
let usernameTo = "";
const messageQueue = {};
const messageCount = {};
for (let friend of friendList) {
    let friendUsername = friend.children[1].innerText;
    messageQueue[friendUsername] = [];
    messageCount[friendUsername] = 0;
    friend.addEventListener('click', function () {
        updateChatWindow(this);
    });
}
function updateChatWindow(element) {
    let friendUsername = element.children[1].innerText;
    const nav2 = document.querySelector('.name');
    nav2.innerText = friendUsername;
    usernameTo = friendUsername;
    //fetch latest old messages from db if not already fetched
    if (messageCount[friendUsername] == 0) {
        messageCount[friendUsername] = 10;
        fetch('/chat/getHistory?username1=' + friendUsername).then(response => response.json()).then(function (messageHistory) {
            console.log(messageHistory);
            for(m of messageHistory.messages){
                if(m.userFrom.username == username){
                    m.type = "sent";
                }
                else{
                    m.type = "received";
                }
                messageQueue[friendUsername].push(m);
            }
            // messageQueue[friendUsername].push(...messageHistory.messages);
        }).then(showMessage);
    }
    else{
        showMessage();
    }
    
}

const sendButton = document.querySelector('.send-icon');
sendButton.addEventListener('click', sendMessagePressed);
function sendMessagePressed(e) {
    e.preventDefault();
    const messageBox = document.querySelector('.message-text');
    const message = messageBox.value;
    // console.log(message);
    //this function will send the message to the other client
    sendMessage(message);


    messageBox.value = '';

    //adding new element to chat window
    // const el = document.createElement('div');
    // el.classList.add("chat-window-element-left");
    // el.innerText = message;
    // const window = document.querySelector(".chat-window");
    // window.appendChild(el);
    // showMessage();
}
let stompClient = null;
function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
}
const username = document.querySelector("#username").innerText;
function onConnected() {

    stompClient.subscribe('/user/' + username + '/reply', function (message) {
        console.log('message received');
        messageReceived(JSON.parse(message.body));
    });
}
function onError(error) {
    console.log('error while connecting!!');
    // alert("could not connect to the server, Refresh!");
    document.location.reload();
}
function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    // setConnected(false);
    console.log("Disconnected");
}
function sendMessage(message) {
    // const message = document.querySelector('.message-text').value;
    stompClient.send("/app/send/" + usernameTo, {}, JSON.stringify({ 'message': message }));
    let date = new Date();
    let time = date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate() + 'T' + date.getHours() + ":"
        + date.getMinutes() + ":" + date.getSeconds();
    messageObject = { "message": message, "type": "sent", "time": time, "sentTo": usernameTo, "sentFrom": username };
    messageQueue[usernameTo].push(messageObject);
    showMessage();
}
function messageReceived(message) {
    // console.log(message);
    //this message object has 4 key-value pairs. keys -> message,  time, sentTo, sentFrom
    if (username == message.sentTo) {
        //store this message in the queue of sentFrom;
        message.type = "received";
        messageQueue[message.sentFrom].push(message);
        console.log(messageQueue);
        showMessage();
    }
    else {
        //invalid response
        document.location.reload();
    }
}

function showMessage() {
    //display message queue on display
    console.log('here in show message')
    const window = document.querySelector(".chat-window");
    //clear the chat window
    window.innerHTML = "";
    const messages = messageQueue[usernameTo];
    // console.log(messages.length);
    for (let i = messages.length - 1; i >= 0; i--) {
        let message = messages[i];
        let el = document.createElement('div');
        el.innerText = message.message;


        let messageElement = document.createElement('div');
        // messageElement.innerText = message.message;
        let timeElement = document.createElement('div');
        timeElement.innerText = trimTime(message.time);
        el.classList.add('flex');
        el.classList.add('justify-between');
        // messageElement.classList.add('')
        timeElement.classList.add('self-end');
        timeElement.classList.add('text-xs');
        // timeElement.classList.add('');





        if (message.type == 'sent') {
            // el.classList.add('chat-window-element-right');
            el.classList.add('self-end');
        }
        else {
            el.classList.add('self-start');
            // el.classList.add('chat-window-element-left');
        }
        el.classList.add('w-1/3')
        el.classList.add('py-2');
        el.classList.add('px-4');
        el.classList.add('m-4');
        el.classList.add('border');
        el.classList.add('border-sky-500');
        el.classList.add('border-solid')
        el.classList.add('rounded-md')

        el.appendChild(messageElement);
        el.appendChild(timeElement);
        window.appendChild(el);
    }
}
function trimTime(time) {
    let t = '';
    let i = 0;
    for (i = 0; i < time.length; i++) {
        if (time[i] == 'T')
            break;
    }
    for (i = i + 1; i < time.length; i++) {
        if (time[i] == '.')
            break;
        t += time[i];
    }
    return t;
}
connect();

//logout
const logoutForm = document.querySelector('.logout');
logoutForm.addEventListener('click', function (e) {
    e.preventDefault();
    disconnect();
    logoutForm.submit();
})

