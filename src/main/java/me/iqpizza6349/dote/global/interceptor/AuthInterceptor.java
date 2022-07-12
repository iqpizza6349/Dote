package me.iqpizza6349.dote.global.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.iqpizza6349.dote.domain.member.entity.Member;
import me.iqpizza6349.dote.global.annotations.AuthToken;
import me.iqpizza6349.dote.global.jwt.TokenProvider;
import me.iqpizza6349.dote.global.util.AuthorizationUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final TokenProvider tokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        log.info("request url: {}", request.getRequestURL());
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if (!handlerMethod.getMethod().isAnnotationPresent(AuthToken.class)) {
            return true;
        }

        String token = AuthorizationUtil.extract(request, "Bearer");
        if (token.equals("")) {
            throw new Member.UnAuthenticationException();
        }

        Member member = tokenProvider.validateToken(token);
        request.setAttribute("member", member);

        return true;
    }
}
