package com.ngts.auth.jwt;

import com.ngts.common.jwt.JwtUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;


@Component
public class AuthJwtUtils {

  private static final Logger logger = LoggerFactory.getLogger(AuthJwtUtils.class);

  @Autowired
  private JwtUtils jwtUtils;

  @Value("${ngtsscm.app.jwtSecret}")
  private String jwtSecret;

  @Value("${ngtsscm.app.jwtExpirationMs}")
  private int jwtExpirationMs;

  @Value("${ngtsscm.app.jwtCookieName}")
  private String jwtCookie;

  public String getJwtFromCookies(HttpServletRequest request) {
    Cookie cookie = WebUtils.getCookie(request, jwtCookie);
    if (cookie != null) {
      return cookie.getValue();
    } else {
      return null;
    }
  }

  public ResponseCookie generateJwtCookie(String  email) {
    String jwt = jwtUtils.generateTokenFromUsername(email);
    ResponseCookie cookie = ResponseCookie.from(jwtCookie, jwt).path("/").maxAge(24 * 60 * 60).httpOnly(true).build();
    return cookie;
  }

  public ResponseCookie getCleanJwtCookie() {
    System.out.println("Token while logout " + jwtCookie);
    ResponseCookie cookie = ResponseCookie.from(jwtCookie, null).path("/").build();
    return cookie;
  }

}
