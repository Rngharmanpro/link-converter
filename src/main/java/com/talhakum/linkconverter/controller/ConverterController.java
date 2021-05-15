package com.talhakum.linkconverter.controller;

import com.talhakum.linkconverter.kafka.KafkaRequestPublisher;
import com.talhakum.linkconverter.kafka.model.ConverterLogEvent;
import com.talhakum.linkconverter.service.ConverterService;
import com.talhakum.linkconverter.validator.ConverterValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author Talha Kum
 */
@Controller
@RequestMapping("convert")
public class ConverterController extends ApiControllerBase {

	@Autowired
	private ConverterService converterService;

	@Autowired
	private ConverterValidator converterValidator;

	@Autowired
	protected KafkaRequestPublisher requestPublisher;

	@RequestMapping(value = "/web-to-deeplink", method = RequestMethod.POST, consumes = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> convertWebUrlToDeeplink(@RequestBody String url, WebRequest webRequest, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		logRequest(webRequest, url);
		converterValidator.validateConvertRequest(url);

		String deeplinkUrl = converterService.convertWebUrlToDeeplink(url);

		publishConverterLogEvent(webRequest, httpServletRequest, url, deeplinkUrl);

		addCorsHeadersToResponse(httpServletResponse);
		addSecurityHeadersToResponse(httpServletResponse);
		return new ResponseEntity<>(deeplinkUrl, HttpStatus.OK);
	}

	@RequestMapping(value = "/deeplink-to-web", method = RequestMethod.POST, consumes = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> convertDeeplinkToWebUrl(@RequestBody String url, WebRequest webRequest, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		logRequest(webRequest, url);
		converterValidator.validateConvertRequest(url);

		String webUrl = converterService.convertDeeplinkToWebUrl(url);

		publishConverterLogEvent(webRequest, httpServletRequest, url, webUrl);

		addCorsHeadersToResponse(httpServletResponse);
		addSecurityHeadersToResponse(httpServletResponse);
		return new ResponseEntity<>(webUrl, HttpStatus.OK);
	}

	private void publishConverterLogEvent(WebRequest webRequest, HttpServletRequest httpServletRequest, String request, String response) {
		ConverterLogEvent converterLogEvent = ConverterLogEvent.builder().accessUrl(getRequestUrl(webRequest)).clientIp(httpServletRequest.getRemoteAddr()).createDate(new Date())
				.request(request).response(response).build();
		requestPublisher.publish(converterLogEvent);
	}

}
