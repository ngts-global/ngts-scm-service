//const url = 'https://vmi240110.contaboserver.net:7070';
const url = 'http://localhost:6060';
const chatUrl = url + '/chat';
const loginUrl = url+"/comm/chat/login";
const registrationUrl = url+"/comm/chat/register";
const fetchAllUsersUrl = url + "/comm/chat/fetchAllUsers";

const LOGGED_IN_USERNAME = "LOGGED_IN_USERNAME";
const LOGGED_IN_CHATID = "LOGGED_IN_CHATID";

let stompClient;
let selectedUser;
const usernamePage = document.querySelector('#username-page');
const chatPage = document.querySelector('#idchatpage');

let selectedUserUUID;

let newMessages = new Map();

function getCookie(cookieName){
    var cookieArray = document.cookie.split(';');
    for(var i=0; i<cookieArray.length; i++){
      var cookie = cookieArray[i];
      while (cookie.charAt(0)==' '){
        cookie = cookie.substring(1);
      }
      cookieHalves = cookie.split('=');
      if(cookieHalves[0]== cookieName){
        return cookieHalves[1];
      }
    }
    return "";
  }


function connectToChat(userName) {
    console.log("connecting to chat...")
    let socket = new SockJS(chatUrl);
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log("connected to: " + frame);
        stompClient.subscribe("/topic/messages/" + userName, function (response) {
            let data = JSON.parse(response.body);
            receiveMessage(data);
            if (selectedUser === data.fromLogin) {
                render(data.message, data.fromLogin);
            } else {
                newMessages.set(data.fromLogin, data.message);
                $('#userNameAppender_' + data.fromLogin).append('<span id="newMessage_' + data.fromLogin + '" style="color: red">+1</span>');
            }
        });
         stompClient.subscribe("/users", function (response) {
                    // we can show who is online here
                     console.log(response);
                     fetchAll();
                });
    });
}

function sendMsg() {
    //let loggedInUserName = document.getElementById("email").value;
    let typedMsg = document.getElementById("idchattxt").value;
    stompClient.send("/app/chat/" + selectedUserUUID, {}, JSON.stringify({
        fromLogin: store.get(LOGGED_IN_CHATID),
        message: typedMsg
    }));
}
function convertTime(time){

        var d = new Date(null)
        d.setMilliseconds(time)
        return d.toLocaleTimeString("en-US")

}



function receiveMessage(receivedMsg){
    var from = receivedMsg.fromName;
    var recMsg = receivedMsg.message;
    var recTime = convertTime(receivedMsg.receivedTime);

    const chatMessages = document.getElementById('idchatmessages');

    if (recMsg.trim() !== '') {
        const message = document.createElement('message-user-right');
        message.innerHTML = `<div class="message-user-right">
            <div class="message-user-right-img">
                <img src="/imgs/chat-user.jpeg" alt="">
                <p class="mt-0 mb-0"><strong>${from}</strong></p>
                <small>${recTime}</small>
            </div>
            <div class="message-user-right-text">
                <strong>${recMsg}</strong>
            </div>
        </div>`;
        
        chatMessages.appendChild(message);
        document.getElementById("idchattxt").value = '';
        chatMessages.scrollTop = chatMessages.scrollHeight; // Auto-scroll to the bottom
    }
}

function sendMessage() {
    const messageInput = document.getElementById("idchattxt").value;
    const chatMessages = document.getElementById('idchatmessages');
    let displayname = store.get(LOGGED_IN_USERNAME);
    sendMsg(); // Sending msg to server

    if (messageInput.trim() !== '') {
        const message = document.createElement('message-user-left');
        message.innerHTML = `<div class="message-user-left">
            <div class="message-user-left-img">
                <img src="/imgs/chat-user.jpeg" alt="">
                <p class="mt-0 mb-0"><strong>'${displayname}'</strong></p>
                <small>${getCurrentTime()}</small>
            </div>
            <div class="message-user-left-text">
                <strong>${messageInput}</strong>
            </div>
        </div>`;
        
        chatMessages.appendChild(message);
        document.getElementById("idchattxt").value = '';
        chatMessages.scrollTop = chatMessages.scrollHeight; // Auto-scroll to the bottom
    }
}

function chatlogin(){
 let email = document.getElementById("email").value;
 let loginpassword = document.getElementById("loginpassword").value;
 if(email=='' || loginpassword == ''){
    $("#errDialog").dialog("open").html ( "Enter details" );
    return;
 }

    $.ajax({
              url: loginUrl,
              type: "POST",
              "timeout": 0,
              "contentType" : 'application/json',
              "data": JSON.stringify({
                  "email": email,
                  "password": loginpassword,
              }),
              beforeSend: function(x) {
                if (x && x.overrideMimeType) {
                  x.overrideMimeType("application/json;charset=UTF-8");
                }
              },
              success: function(response, textStatus, request){
                    loginSuccessEvent(response, textStatus, request);
              },
              error: function (request, status, error) {
                  if(request.responseText != null ){
                    $("#errDialog").dialog("open").html ( JSON.parse(request.responseText).message);
                    }
                   else {
                    $("#errDialog").dialog("open").html ( "Error making network call" );
                    //alert("Error making network call");
                    }
                   },
              complete: function () {

                 }
    }); 
}

function loginSuccessEvent(response, textStatus, request){
    $('#username-page').hide();
    $('#idchatpage').show();
   //$('#loggedusername').html(userName);
  // alert("success response " + request.getResponseHeader('Set-Cookie'));
   var cookie = getCookie('Set-Cookie');
  // alert("Cookie " + cookie);
   var loggedInUserUUID = response.chatUserId;
   store.set(LOGGED_IN_USERNAME, response.username);
   store.set(LOGGED_IN_CHATID, loggedInUserUUID);
   $("#loggedInUserName").html(store.get(LOGGED_IN_USERNAME));
   console.log("Response " + response);
   connectToChat(loggedInUserUUID);
   fetchAll();
}


function register(){

    let email = document.getElementById("regemail").value;
    let userName = document.getElementById("username").value;
    let password = document.getElementById("password").value;
   
       $.ajax({
                 url: registrationUrl,
                 type: "POST",
                 "timeout": 0,
                "contentType" : 'application/json',
                "data": JSON.stringify({
                    "email": email,
                    "username": userName,
                    "password": password,                   
                }),
                 beforeSend: function(x) {
                   if (x && x.overrideMimeType) {
                    x.overrideMimeType("application/json;charset=UTF-8");
                   }
                 },
                 success: function(response, textStatus, request) {
                    alert(" "+ JSON.parse(request.responseText).message);
                 },
                 error: function (request, status, error) {
                    if(request.responseText != null )
                        $("#errDialog").dialog("open").html ( JSON.parse(request.responseText).message);
                    else {
                        $("#errDialog").dialog("open").html ( "Error making network call" );
                    }
                       },
                 complete: function () {
   
                    }
       }); 
   }


function selectUser(selectedDiv) {
    var name = $(selectedDiv).attr('data-username');
    console.log("selecting users: " + name);
    selectedUser = name;
    selectedUserUUID = $(selectedDiv).attr('chatId');
    updateChatWithUserDetails(selectedUser);
    highlightselectedUser(selectedUser);
    $('#idchattxt').removeAttr('disabled');
}

function highlightselectedUser(selectedUser){
            const userChats = document.querySelectorAll('.user-chat');
            const chatMessages = document.querySelectorAll('.content-chat-message-user');
            userChats.forEach((userChat) => {
                const selectedUsername = userChat.getAttribute('data-username');
                if(selectedUser == selectedUsername){
                    userChat.classList.add('active');
                }else {
                     userChat.classList.remove('active');
                }
            });
}

function updateChatWithUserDetails(selectedUser){
  let usersTemplateHTML = "";
    usersTemplateHTML = usersTemplateHTML +  '<div class=\"head-chat-message-user\">'+
                '<img src=\"/imgs/chat-user.jpeg\" alt=\"\">'+
                '<div class=\"message-user-profile\">'+
                    '<p class=\"mt-0 mb-0 text-white\"><strong>'+selectedUser+'</strong></p>'+
                    '<small class=\"text-white\"><p class=\"offline  mt-0 mb-0\"></p>Offline</small>'+
                '</div>'+
            '</div>'
    $('#idselecteduser').html(usersTemplateHTML);
}


function fetchAll() {
    $.get(fetchAllUsersUrl, function (response) {
        let users = response;
        let usersTemplateHTML = "";
        for (let i = 0; i < users.length; i++) {
            let loggedInUserUUID = store.get(LOGGED_IN_CHATID);
            if(loggedInUserUUID != users[i].chatUserId){
            console.log("Response " + users[i]);

                usersTemplateHTML = usersTemplateHTML + '<div class=\"user-chat\" onClick=\"selectUser(this)\" chatId=\"'+users[i].chatUserId+'\" data-username=\"'+users[i].username+'\">' +
                   ' <div class=\"user-chat-img\">'+
                    '   <img src=\"/imgs/chat-user.jpeg\" alt=\"\">' +
                      '  <div class=\"offline\"></div>'+
                    '</div>'+
                    '<div class=\"user-chat-text\">'+
                        '<p class=\"mt-0 mb-0\"><strong>'+users[i].username+'</strong></p>'+
                        '<small>status</small>'+
                    '</div>'+
                '</div>'

            }
        }

        $('#iduserslist').html(usersTemplateHTML);
    });
}




