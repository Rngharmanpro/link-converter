package com.talhakum.linkconverter.service;

/**
 * @author Talha Kum
 */
public interface ConverterService {

	String convertWebUrlToDeeplink(String url);

	String convertDeeplinkToWebUrl(String url);

}
