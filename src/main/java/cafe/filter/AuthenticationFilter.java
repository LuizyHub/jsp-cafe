package cafe.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;

//@WebFilter("/*")
public class AuthenticationFilter implements Filter {
    private static final Logger log = Logger.getLogger(AuthenticationFilter.class.getName());
    // 허용된 URL 패턴 리스트
    private static final List<String> ALLOWED_PATHS = Arrays.asList("/static/user/login.html", "/static/user/register.html", "/auth");
    private static final Pattern STATIC_RESOURCES = Pattern.compile("/static/.*");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 필터 초기화
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) req;
        HttpServletResponse httpRes = (HttpServletResponse) res;
        HttpSession session = httpReq.getSession(false);

        String path = httpReq.getRequestURI();
        String requestURI = httpReq.getRequestURI();
        log.info("Request URI: " + requestURI);

        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);
        boolean isAllowedPath = ALLOWED_PATHS.contains(path) || STATIC_RESOURCES.matcher(path).matches();

        if (isLoggedIn || isAllowedPath) {
            // 사용자가 로그인되었거나 허용된 경로인 경우 요청을 계속 진행
            chain.doFilter(req, res);
        } else {
            // 사용자 인증되지 않음, 로그인 페이지로 리디렉션
            httpRes.sendRedirect(httpReq.getContextPath() + "/static/user/login.html");
        }
    }

    @Override
    public void destroy() {
        // 필터 해제
    }
}