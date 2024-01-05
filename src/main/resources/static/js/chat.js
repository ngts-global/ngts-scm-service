const url = 'http://localhost:8080';
let stompClient;
let selectedUser;
const usernamePage = document.querySelector('#username-page');
const chatPage = document.querySelector('#chat-page');
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
                    let data = JSON.parse(response.body);
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
 let userName = document.getElementById("userName").value;
 let email = document.getElementById("fullname").value;
 var jsonObjects = {email:email, username:userName};

    $.ajax({
              url: "/chat/login",
              type: "POST",
              dataType: 'text',
              data: jsonObjects,
              beforeSend: function(x) {
                if (x && x.overrideMimeType) {
                  x.overrideMimeType("application/json;charset=UTF-8");
                }
              },
              success: function() {
                    usernamePage.classList.add('hidden');
                    chatPage.classList.remove('hidden');
                    $('#loggedusername').html(userName);
                    connectToChat(userName);
                    fetchAll();
              },
              error: function (request, status, error) {
                  alert("Error occurred");
                    },
              complete: function () {

                 }

    });
}

function registration() {
    let userName = document.getElementById("userName").value;
    $.get(url + "/registration/" + userName, function (response) {
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');
        $('#loggedusername').html(userName);
        connectToChat(userName);
        fetchAll();
    }).fail(function (error) {
        if (error.status === 400) {
            alert("Login is already busy!")
        }
    })
}

function selectUser(userName) {
    console.log("selecting users: " + userName);
    selectedUser = userName;
    let isNew = document.getElementById("newMessage_" + userName) !== null;
    if (isNew) {
        let element = document.getElementById("newMessage_" + userName);
        element.parentNode.removeChild(element);
        render(newMessages.get(userName), userName);
    }
    $('#selectedUserId').html('');
    $('#selectedUserId').append('Chat with ' + userName);
}

function fetchAll() {
    $.get(url + "/fetchAllUsers", function (response) {
        let users = response;
        let usersTemplateHTML = "";
        for (let i = 0; i < users.length; i++) {
            var loggedInUserName = $('#loggedusername').text();
            if(loggedInUserName != users[i]){
                        usersTemplateHTML = usersTemplateHTML + '<a href="#" onclick="selectUser(\'' + users[i] + '\')"><li class="clearfix">\n' +
                            '                <img src="/imgs/icon.png" width="55px" height="55px" alt="avatar" />\n' +
                            '                <div class="about">\n' +
                            '                    <div id="userNameAppender_' + users[i] + '" class="name">' + users[i] + '</div>\n' +
                            '                    <div class="status">\n' +
                            '                        <i class="fa fa-circle offline"></i>\n' +
                            '                    </div>\n' +
                            '                </div>\n' +
                            '            </li></a>';
            }
        }
        $('#usersList').html(usersTemplateHTML);
    });
}
