package com.talhakum.linkconverter.util;

/**
 * @author Talha Kum
 */
public interface ConverterConstants {

	/**
	 * Deeplink constants
	 */
	String DEEPLINK_SCHEME = "ty";
	String DEEPLINK_PRODUCT = "Product";
	String DEEPLINK_SEARCH = "Search";
	String DEEPLINK_PAGE = "Page";
	String DEEPLINK_HOME = "Home";
	String DEEPLINK_CONTENT_ID = "ContentId";
	String DEEPLINK_CAMPAIGN_ID = "CampaignId";
	String DEEPLINK_MERCHANT_ID = "MerchantId";
	String DEEPLINK_QUERY = "Query";

	/**
	 * Web URL constants
	 */
	String WEB_URL_SCHEME = "https";
	String WEB_URL_PATH_PRODUCT = "brand";
	String WEB_URL_PATH_SEARCH = "sr";
	String WEB_URL_PATH_CONTENT_ID = "name-p-";
	String WEB_URL_HOST = "www.ecommerce.com";
	String WEB_URL_BOUTIQUE_ID = "boutiqueId";
	String WEB_URL_MERCHANT_ID = "merchantId";
	String WEB_URL_QUERY = "q";

	// We can use some special characters in the url.
	// More information:http://www.ietf.org/rfc/rfc1738.txt

	String webUrlProductPatternStr = "https://www.ecommerce.com/(?:[a-zA-Z0-9 $\\-_.+!*'(),]+)/(?:[a-zA-Z0-9 $\\-_.+!*'(),]+)-p-(\\d+)(?:(?:\\?boutiqueId=(\\d+)(?:&merchantId=(\\d+))?)|(?:\\?merchantId=(\\d+)))?";
	String webUrlSearchPatternStr = "https://www.ecommerce.com/sr\\?q=([a-zA-Z0-9:/?#\\[\\]@!$&'()*+,;=\\-._~%]+)";

	String deeplinkProductPatternStr = "ty://\\?Page=Product&ContentId=(\\d+)(?:(?:&CampaignId=(\\d+)(?:&MerchantId=(\\d+))?)|(?:&MerchantId=(\\d+)))?";
	String deeplinkSearchPatternStr = "ty://\\?Page=Search&Query=([a-zA-Z0-9:/?#\\[\\]@!$&'()*+,;=\\-._~%]+)";

}
