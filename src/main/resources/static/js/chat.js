const url = 'http://localhost:8080';
const loginUrl = url+"/comm/chat/login";
const fetchAllUsersUrl = url + "/comm/fetchAllUsers";

let stompClient;
let selectedUser;
const usernamePage = document.querySelector('#username-page');
const chatPage = document.querySelector('#idchatpage');

let newMessages = new Map();

function connectToChat(userName) {
    console.log("connecting to chat...")
    let socket = new SockJS(url + '/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log("connected to: " + frame);
        stompClient.subscribe("/topic/messages/" + userName, function (response) {
            let data = JSON.parse(response.body);
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

function sendMsg(from, text) {
    stompClient.send("/app/chat/" + selectedUser, {}, JSON.stringify({
        fromLogin: from,
        message: text
    }));
}


function chatlogin(){
 let userName = document.getElementById("email").value;
 let email = document.getElementById("displayname").value;

 var jsonObjects = {email:email, username:userName};

    $.ajax({
              url: loginUrl,
              type: "POST",
              dataType: 'text',
              data: jsonObjects,
              beforeSend: function(x) {
                if (x && x.overrideMimeType) {
                  x.overrideMimeType("application/json;charset=UTF-8");
                }
              },
              success: function(response) {
                     $('#username-page').hide();
                     $('#idchatpage').show();
                    //$('#loggedusername').html(userName);
                    console.log("Response " + response);
                    connectToChat(userName);
                    fetchAll();
              },
              error: function (request, status, error) {
                  alert("Error occurred -" + request.responseText);
                    },
              complete: function () {

                 }
    });
}


function selectUser(userName) {
    var name = $(userName).attr('data-username');
    console.log("selecting users: " + name);
    selectedUser = name;

    /*let isNew = document.getElementById("newMessage_" + userName) !== null;
    if (isNew) {
        let element = document.getElementById("newMessage_" + userName);
        element.parentNode.removeChild(element);
        render(newMessages.get(userName), userName);
    } */
   // userName.classList.add('active');
    updateChatWithUserDetails(selectedUser);
    highlightselectedUser(selectedUser);
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
            let userName = document.getElementById("email").value;
            if(userName != users[i].username){
            console.log("Response " + users[i]);

                usersTemplateHTML = usersTemplateHTML + '<div class=\"user-chat\" onClick=\"selectUser(this)\" data-username=\"'+users[i].username+'\">' +
                   ' <div class=\"user-chat-img\">'+
                    '   <img src=\"/imgs/chat-user.jpeg\" alt=\"\">' +
                      '  <div class=\"offline\"></div>'+
                    '</div>'+
                    '<div class=\"user-chat-text\">'+
                        '<p class=\"mt-0 mb-0\"><strong>'+users[i].username+'</strong></p>'+
                        '<small>offline / online</small>'+
                    '</div>'+
                '</div>'

            }
        }

        $('#iduserslist').html(usersTemplateHTML);
    });
}
