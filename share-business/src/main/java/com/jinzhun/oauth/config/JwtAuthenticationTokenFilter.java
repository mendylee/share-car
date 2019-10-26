package com.jinzhun.oauth.config;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jinzhun.common.utils.IpAddressUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * JWT登录授权过滤器
 */
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
	
	@Resource
	private UserDetailsService userDetailsService;

	@Resource
	private JwtTokenUtil jwtTokenUtil;

	@Value("${jwt.tokenHeader}")
	private String tokenHeader;

	@Value("${jwt.tokenHead}")
	private String tokenHead;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		long startTime, endTime;
		Map<String,String[]> params = new HashMap<String,String[]>(request.getParameterMap());
		
		StringBuffer sbParams = new StringBuffer();
		sbParams.append("?");
		
		for (String key : params.keySet()) {
			if (null == key || null == params.get(key) || null == params.get(key)[0]) {
				continue;
			}
			sbParams.append(key).append("=").append(params.get(key)[0]).append("&");
		}
		
		if (sbParams.length() > 1) {
			sbParams = sbParams.delete(sbParams.length() - 1, sbParams.length());
		}
		
		String fullUrl = ((HttpServletRequest)request).getRequestURL().toString();

        String authHeader = request.getHeader(this.tokenHeader);
        if (authHeader != null && authHeader.startsWith(this.tokenHead)) {
            String authToken = authHeader.substring(this.tokenHead.length());// The part after "Bearer "
            String username = jwtTokenUtil.getUserNameFromToken(authToken);
            log.info("checking username:{}", username);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    log.info("authenticated user:{}", username);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        
        startTime = System.currentTimeMillis();
        filterChain.doFilter(request, response);
        endTime = System.currentTimeMillis();
        String requestType = ((HttpServletRequest)request).getMethod();
		if (fullUrl.contains(".css") || fullUrl.contains(".js") || fullUrl.contains(".png") || fullUrl.contains(".jpeg") || fullUrl.contains(".jpg")) {

		} else {
			logger.info(formMapKey(11, fullUrl, requestType, IpAddressUtil.getIpAddr((HttpServletRequest) request),
					sbParams.toString(), authHeader) + ",\"cost\":\"" + (endTime - startTime) + "ms\"");
		}
	}
	
	private String formMapKey(Object userId, String mothedName, String requestType, String ip, String params,String token) {
		return "\"time\"" + ":\"" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(new Date()) 
				+ "\",\"name\"" + ":\"" + mothedName + "\",\"uid\":\"" + userId
				+ "\",\"type\":\"" + requestType + "\",\"ip\":\"" + ip
				+ "\",\"token\":\"" + token + "\",\"params\":\"" + params + "\"";
	}

}
