//package com.productms.filters;
//
//import java.io.IOException;
//import java.util.Enumeration;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//@Component
//public class LoginCheckFilter extends OncePerRequestFilter {
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain)
//            throws ServletException, IOException {
//
//    	String path = request.getRequestURI();
//
//        // Skip JWT validation for public endpoints
//        if (path.startsWith("/products") || path.matches("/product/\\w+")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//    	
//        String authHeader = request.getHeader("Authorization");
//        String token = null;
//
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            token = authHeader.substring(7);
//
//            try {
//                // Call UserMS to validate the token
//                Boolean isValid = restTemplate.postForObject(
//                        "http://localhost:8000/auth/validate",
//                        token,
//                        Boolean.class
//                );
//
//                if (Boolean.TRUE.equals(isValid)) {
//                    // Proceed with request
//                    filterChain.doFilter(request, response);
//                    return;
//                }
//
//            } catch (Exception ex) {
//                // Log or handle exception
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token validation failed");
//                return;
//            }
//        }
//
//        // No token or invalid token
//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
//    }
//}
