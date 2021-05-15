package com.talhakum.linkconverter.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Talha Kum
 */
public class ApiControllerBase {

	private transient final Logger logger = LogManager.getLogger(getClass());

	protected void logRequest(WebRequest request, String body) {
		if (body == null) {
			body = StringUtils.EMPTY;
		}

		String url = getRequestUrl(request);

		logger.info("url::{}::payload::{}", url, StringUtils.deleteWhitespace(body));
	}

	protected String getRequestUrl(WebRequest request) {
		if (request instanceof ServletWebRequest) {
			ServletWebRequest httpRequest = (ServletWebRequest) request;
			return httpRequest.getRequest().getRequestURI();
		}

		return null;
	}

	/**
	 * Adds Cross-origin resource sharing related headers, sets
	 * Access-Control-Allow-Origin to * sets Access-Control-Allow-Headers to Origin,
	 * X-Requested-With, Content-Type, Accept sets Access-Control-Allow-Methods to
	 * GET, POST, PUT, OPTIONS
	 *
	 * @param response
	 */
	protected void addCorsHeadersToResponse(HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, OPTIONS");
	}

	protected void addSecurityHeadersToResponse(HttpServletResponse response) {
		response.setHeader("x-content-type-options", "nosniff");
		response.setHeader("X-XSS-Protection", "1; mode=block");
	}

}
