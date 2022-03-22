package com.kmmoon.assignment.auth;

import com.kmmoon.assignment.entity.User;
import com.kmmoon.assignment.exception.CustomException;
import com.kmmoon.assignment.exception.ErrorStatus;
import com.kmmoon.assignment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class TokenFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = httpServletRequest.getHeader("Authorization");

        try {
            if (authorizationHeader != null) {
                String[] headerArray = validateAuthorization(authorizationHeader);

                User.AccountType accountType = accountTypeValueOf(headerArray[0]);
                long userId = userIdParseLong(headerArray[1]);

                Authentication auth = getAuthentication(accountType, userId);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (CustomException e) {
            SecurityContextHolder.clearContext();
            httpServletResponse.sendError(e.getErrorStatus().getHttpStatus().value(), e.getMessage());
            return;
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String[] validateAuthorization(String authorizationHeader) {
        String[] headerArray = splitAuthorizationHeader(authorizationHeader);

        if (headerArray == null)
            throw new CustomException(ErrorStatus.INVALID_ACCESS, "Please check your authentication information.");

        accountTypeValueOf(headerArray[0]);

        userIdParseLong(headerArray[1]);

        return headerArray;
    }

    private String[] splitAuthorizationHeader(String authorizationHeader) {
        String[] headerArray = authorizationHeader.split(" ");
        return headerArray.length == 2 ? headerArray : null;
    }

    private User.AccountType accountTypeValueOf(String checkValue) {
        User.AccountType accountType = null;

        try {
            accountType = User.AccountType.valueOf(checkValue.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new CustomException(ErrorStatus.INVALID_ACCESS, "Please check your authentication information.");
        }

        return accountType;
    }

    private Long userIdParseLong(String checkValue){
        Long userId = null;
        try {
            userId = Long.parseLong(checkValue);
        } catch (NumberFormatException | NullPointerException e) {
            throw new CustomException(ErrorStatus.INVALID_ACCESS, "Please check your authentication information.");
        }

        return userId;
    }

    private Authentication getAuthentication(User.AccountType accountType, Long userId) {
        UserDetails userDetails = userRepository.findByIdAndAccountTypeAndQuit(userId, accountType, false).orElseThrow(() -> new CustomException(ErrorStatus.USER_NOT_FOUND, "The user does not exist."));
        System.out.println("getAuthentication : " + userDetails.getAuthorities());

        return new UsernamePasswordAuthenticationToken(userDetails, userId, userDetails.getAuthorities());
    }


}
