
package com.leel2415.kakaopay.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.leel2415.kakaopay.common.component.JwtComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


@Component
@Slf4j
public class JwtInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	private JwtComponent jwtComponent;
	
	@Value("${kakaopay.jwt-header-name}")
	private String AUTH_HEADER;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		// API 호출 시에 header에서 토큰 정보를 가져와 서비스 체크. 토큰에 문제가 있을 경우 Exception 발생 
		String token = request.getHeader(AUTH_HEADER);
		log.debug("Header : {}, {}", AUTH_HEADER, token);
		jwtComponent.checkJwtApi(token);
		
		return super.preHandle(request, response, handler);
	}
}
