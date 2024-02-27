package com.ngts.chat.utils;
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
        HttpSession session =  httpServletRequest.getSession();
        if(session != null){
            UserResponseVO responseVO = (UserResponseVO) session.getAttribute(CUSTOMER_PROFILE);
            return responseVO;
        }else {
        return null;
        }
    }

    public void setCustomerProfile(HttpServletRequest httpServletRequest, UserResponseVO userResponseVO) {
        HttpSession session = httpServletRequest.getSession();
        if (session != null) {
            session.setAttribute(CUSTOMER_PROFILE, userResponseVO);
        }
    }
}
