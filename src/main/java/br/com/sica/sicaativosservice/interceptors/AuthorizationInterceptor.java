package br.com.sica.sicaativosservice.interceptors;

import br.com.sica.sicaativosservice.exceptions.UnauthorizedException;
import br.com.sica.sicaativosservice.service.AuthorizationService;
import br.com.sica.sicaativosservice.validators.RequestAuthValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final Logger LOGGER = LoggerFactory.getLogger(AuthorizationInterceptor.class);

    @Autowired
    private AuthorizationService authorizationService;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {
        LOGGER.info("Iniciando preHandle...");
        final RequestAuthValidator validation = getAnnotation(handler);

        if (validation != null && validation.authorization()) {
            validateAuthorization(request);
        }
        return true;
    }

    private void validateAuthorization(final HttpServletRequest request) {
        String tokenJwt = parseJwt(request);
        LOGGER.info("token recebido tokenJwt...");
        if (tokenJwt != null) {
            try {
                authorizationService.checkToken(tokenJwt);
                return;
            } catch (Exception ex) {
                LOGGER.error("Exception ao chamar auth service...", ex);
            }
        }
        throw new UnauthorizedException();
    }

    private RequestAuthValidator getAnnotation(final Object object) {
        if (object instanceof HandlerMethod) {
            LOGGER.info("Objeto é um HandlerMethod");
            final HandlerMethod handler = (HandlerMethod) object;
            return getAnnotationFromMethod(handler) == null
                    ? getAnnotationFromClass(handler)
                    : getAnnotationFromMethod(handler);
        }
        LOGGER.info("nao e HandlerMethod");
        return null;
    }

    private RequestAuthValidator getAnnotationFromMethod(final HandlerMethod handler) {
        LOGGER.info("Iniciando getAnnotationFromMethod...");
        final Method beanMethod = handler.getMethod();
        if (beanMethod.isAnnotationPresent(RequestAuthValidator.class)) {
            return beanMethod.getAnnotation(RequestAuthValidator.class);
        }
        return null;
    }

    private RequestAuthValidator getAnnotationFromClass(final HandlerMethod handler) {
        LOGGER.info("Iniciando getAnnotationFromClass...");
        final Class<?> beanClass = getUserClass(handler.getBean().getClass());
        if (beanClass.isAnnotationPresent(RequestAuthValidator.class)) {
            return beanClass.getAnnotation(RequestAuthValidator.class);
        }
        return null;
    }

    public Class<?> getUserClass(Class<?> clazz) {
        if (clazz.getName().contains("$$")) {
            Class<?> superclass = clazz.getSuperclass();
            if (superclass != null && superclass != Object.class) {
                return superclass;
            }
        }
        return clazz;
    }

    private String parseJwt(HttpServletRequest request) {
        LOGGER.info("Iniciando parseJwt...");
        String headerAuth = request.getHeader("Authorization");
        LOGGER.info("headerAuth: " + headerAuth);

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }

        return null;
    }
}
