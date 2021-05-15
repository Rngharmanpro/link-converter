package com.talhakum.linkconverter.service;

import com.talhakum.linkconverter.util.ConverterConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Talha Kum
 */
@Service
public class ConverterServiceImpl implements ConverterService {

	private static final Pattern webUrlProductPattern = Pattern.compile(ConverterConstants.webUrlProductPatternStr);
	private static final Pattern webUrlSearchPattern = Pattern.compile(ConverterConstants.webUrlSearchPatternStr);

	private static final Pattern deeplinkProductPattern = Pattern.compile(ConverterConstants.deeplinkProductPatternStr);
	private static final Pattern deeplinkSearchPattern = Pattern.compile(ConverterConstants.deeplinkSearchPatternStr);

	/**
	 * Returns converted deeplink from web url. A deeplink can be one of product,
	 * search or home page.
	 *
	 * @param url
	 *            a web url including contentId, boutiqueId, merchantId and search
	 *            query
	 *
	 * @return deeplink corresponds to given url
	 */
	@Override
	public String convertWebUrlToDeeplink(String url) {
		Matcher productMatcher = webUrlProductPattern.matcher(url);
		Matcher searchMatcher = webUrlSearchPattern.matcher(url);

		UriComponentsBuilder deeplinkBuilder = UriComponentsBuilder.newInstance().scheme(ConverterConstants.DEEPLINK_SCHEME).host(StringUtils.EMPTY).path(StringUtils.EMPTY);

		buildDeeplink(productMatcher, searchMatcher, deeplinkBuilder);
		return deeplinkBuilder.build(false).toUriString();
	}

	/**
	 * Returns converted web url from web deeplink. A web url can be one of product,
	 * search or home page.
	 *
	 * @param url
	 *            a deeplink including ContentId, CampaignId, MerchantId and search
	 *            Query
	 *
	 * @return web url corresponds to given deeplink
	 */
	@Override
	public String convertDeeplinkToWebUrl(String url) {
		Matcher productMatcher = deeplinkProductPattern.matcher(url);
		Matcher searchMatcher = deeplinkSearchPattern.matcher(url);

		UriComponentsBuilder webUrlBuilder = UriComponentsBuilder.newInstance().scheme(ConverterConstants.WEB_URL_SCHEME).host(ConverterConstants.WEB_URL_HOST);

		buildWebUrl(webUrlBuilder, productMatcher, searchMatcher);
		return webUrlBuilder.build(false).toUriString();
	}

	private void buildDeeplink(Matcher productMatcher, Matcher searchMatcher, UriComponentsBuilder deeplinkUriBuilder) {
		if (productMatcher.find()) {
			deeplinkUriBuilder.queryParam(ConverterConstants.DEEPLINK_PAGE, ConverterConstants.DEEPLINK_PRODUCT);
			String contentId = productMatcher.group(1);
			Optional<String> boutiqueId = Optional.ofNullable(productMatcher.group(2));
			// There are two merchant ids grouped in order to avoid collapse order.
			// merchantId can place after boutiqueId or after contentId.
			// Only one of them can take value at the same time because of the structure of
			// regex.
			Optional<String> firstMerchantId = Optional.ofNullable(productMatcher.group(3));
			Optional<String> secondMerchantId = Optional.ofNullable(productMatcher.group(4));

			deeplinkUriBuilder.queryParam(ConverterConstants.DEEPLINK_CONTENT_ID, contentId);
			deeplinkUriBuilder.queryParamIfPresent(ConverterConstants.DEEPLINK_CAMPAIGN_ID, boutiqueId);
			deeplinkUriBuilder.queryParamIfPresent(ConverterConstants.DEEPLINK_MERCHANT_ID, firstMerchantId);
			deeplinkUriBuilder.queryParamIfPresent(ConverterConstants.DEEPLINK_MERCHANT_ID, secondMerchantId);
		} else {
			if (searchMatcher.find()) {
				deeplinkUriBuilder.queryParam(ConverterConstants.DEEPLINK_PAGE, ConverterConstants.DEEPLINK_SEARCH);

				String query = searchMatcher.group(1);

				deeplinkUriBuilder.queryParam(ConverterConstants.DEEPLINK_QUERY, query);
			} else {
				deeplinkUriBuilder.queryParam(ConverterConstants.DEEPLINK_PAGE, ConverterConstants.DEEPLINK_HOME);
			}
		}
	}

	private void buildWebUrl(UriComponentsBuilder webUrlBuilder, Matcher productMatcher, Matcher searchMatcher) {
		if (productMatcher.find()) {
			webUrlBuilder.pathSegment(ConverterConstants.WEB_URL_PATH_PRODUCT);
			String contentId = productMatcher.group(1);
			Optional<String> campaignId = Optional.ofNullable(productMatcher.group(2));
			// There are two merchant ids grouped in order to avoid collapse order.
			// merchantId can place after boutiqueId or after contentId.
			// Only one of them can take value at the same time because of the structure of
			// regex.
			Optional<String> firstMerchantId = Optional.ofNullable(productMatcher.group(3));
			Optional<String> secondMerchantId = Optional.ofNullable(productMatcher.group(4));

			webUrlBuilder.pathSegment(getContentIdPath(contentId));

			webUrlBuilder.queryParamIfPresent(ConverterConstants.WEB_URL_BOUTIQUE_ID, campaignId);
			webUrlBuilder.queryParamIfPresent(ConverterConstants.WEB_URL_MERCHANT_ID, firstMerchantId);
			webUrlBuilder.queryParamIfPresent(ConverterConstants.WEB_URL_MERCHANT_ID, secondMerchantId);
		} else {
			if (searchMatcher.find()) {
				webUrlBuilder.pathSegment(ConverterConstants.WEB_URL_PATH_SEARCH);

				String query = searchMatcher.group(1);

				webUrlBuilder.queryParam(ConverterConstants.WEB_URL_QUERY, query);
			}
		}
	}

	private String getContentIdPath(String contentId) {
		return ConverterConstants.WEB_URL_PATH_CONTENT_ID + contentId;
	}

}
