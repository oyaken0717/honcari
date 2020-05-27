package com.honcari.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * エラーハンドリングを行う.
 * 
 * @author shumpei
 *
 */
@Component
public class GlobalExceptionHandler implements HandlerExceptionResolver{
	
	private final static Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		LOGGER.error("システムエラーが発生しました！", ex);
		return null;
	}
	

}
