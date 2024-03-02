package com.ngts.chat.vo.res;

public class AuthenticationResponse {

    private String statusMsg;
    private String isValid;
    private String actionCode;
    private String token;
    private UserResponseVO userResponseVO;

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserResponseVO getUserResponseVO() {
        return userResponseVO;
    }

    public void setUserResponseVO(UserResponseVO userResponseVO) {
        this.userResponseVO = userResponseVO;
    }
}
