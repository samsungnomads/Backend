package com.samsungnomads.wheretogo.global.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samsungnomads.wheretogo.global.error.ErrorCode;
import com.samsungnomads.wheretogo.global.error.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Slf4j
public class FilterExceptionHandler extends GenericFilterBean {
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("FilterExceptionHandler doFilter");
        try {
            chain.doFilter(request, response);

        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            log.info("Invalid JWT token");
            setErrorResponse((HttpServletResponse) response, ErrorCode.INVALID_JWT_TOKEN);

        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token");
            setErrorResponse((HttpServletResponse) response, ErrorCode.EXPIRED_JWT_TOKEN);

        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token");
            setErrorResponse((HttpServletResponse) response, ErrorCode.UNSUPPORTED_JWT_TOKEN);

        } catch (IllegalArgumentException e) {
            log.info("JWT token could not be parsed");
            setErrorResponse((HttpServletResponse) response, ErrorCode.INVALID_JWT_TOKEN);
        }
    }

    private void setErrorResponse(HttpServletResponse response, ErrorCode errorCode) {
        response.setStatus(errorCode.getStatus().value());
        response.setContentType("application/json; charset=UTF-8");
        ErrorResponse errorResponse = ErrorResponse.of(errorCode);

        try {
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }


}
