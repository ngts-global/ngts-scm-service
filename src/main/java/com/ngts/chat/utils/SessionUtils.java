package com.ngts.chat.utils;
import com.ngts.chat.vo.SessionRoles;
import com.ngts.chat.vo.res.UserResponseVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import static com.ngts.chat.utils.ChatConstants.*;

@Slf4j
@Component
public class SessionUtils {

    public UserResponseVO getCustomerProfile(HttpServletRequest httpServletRequest){
        HttpSession session =  httpServletRequest.getSession(false);
        if(session != null){
            log.error(" Session id in getCustomerProfile method " + session.getId());
            UserResponseVO responseVO = (UserResponseVO) session.getAttribute(CUSTOMER_PROFILE);
            return responseVO;
        }else {
        return null;
        }
    }

    public void setCustomerProfile(HttpServletRequest httpServletRequest, UserResponseVO userResponseVO) {
        HttpSession session = httpServletRequest.getSession(true);
        if (session != null) {
            log.error(" Session id in setCustomerProfile method " + session.getId());
            session.setAttribute(CUSTOMER_PROFILE, userResponseVO);
        }
    }

    public void setCurrentRole(HttpServletRequest httpServletRequest, SessionRoles sessionRole){
        HttpSession session = httpServletRequest.getSession(false);
        if (session != null) {
            log.error(" Session id in setCurrentRole method " + session.getId());
            session.setAttribute(SESSION_ROLE, sessionRole);
        }
    }

    public  SessionRoles getCurrentRoles(HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession(false);
        SessionRoles sessionRole = SessionRoles.VISITOR;
        if (session != null) {
            log.error(" Session id in getCurrentRoles method " + session.getId());
            sessionRole = (SessionRoles)session.getAttribute(SESSION_ROLE);
        }
        return sessionRole;
    }
}
