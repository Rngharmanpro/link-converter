package com.talhakum.linkconverter.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author Talha Kum
 */
@RunWith(MockitoJUnitRunner.class)
public class ConverterServiceImplTest {

	@InjectMocks
	private ConverterServiceImpl converterService;

	/**
	 * Web Url Constants
	 */
	private static final String WEB_URL_PRODUCT_BOUTIQUE_ID_MERCHANT_ID = "https://www.ecommerce.com/casio/saat-p-1925865?boutiqueId=439892&merchantId=105064";
	private static final String WEB_URL_PRODUCT = "https://www.ecommerce.com/casio/saat-p-1925865";
	private static final String WEB_URL_PRODUCT_MERCHANT_ID = "https://www.ecommerce.com/casio/erkek-kol-saati-p-1925865?merchantId=105064";
	private static final String WEB_URL_PRODUCT_BOUTIQUE_ID = "https://www.ecommerce.com/casio/erkek-kol-saati-p-1925865?boutiqueId=439892";

	private static final String WEB_URL_SEARCH = "https://www.ecommerce.com/sr?q=elbise";
	private static final String WEB_URL_SEARCH_ENCODED = "https://www.ecommerce.com/sr?q=%C3%BCt%C3%BC";

	private static final String WEB_URL_OTHER_FAVORILER = "https://www.ecommerce.com/Hesabim/Favoriler";
	private static final String WEB_URL_OTHER_SIPARISLERIM = "https://www.ecommerce.com/Hesabim/#/Siparislerim";
	private static final String WEB_URL_HOME = "https://www.ecommerce.com";

	private static final String WEB_URL_BRAND_BOUTIQUE_ID_MERCHANT_ID = "https://www.ecommerce.com/brand/name-p-1925865?boutiqueId=439892&merchantId=105064";
	private static final String WEB_URL_BRAND = "https://www.ecommerce.com/brand/name-p-1925865";
	private static final String WEB_URL_BRAND_MERCHANT_ID = "https://www.ecommerce.com/brand/name-p-1925865?merchantId=105064";
	private static final String WEB_URL_BRAND_BOUTIQUE_ID = "https://www.ecommerce.com/brand/name-p-1925865?boutiqueId=439892";

	/**
	 * Deeplink Constants
	 */
	private static final String DEEPLINK_PRODUCT_CAMPAIGN_ID_MERCHANT_ID = "ty://?Page=Product&ContentId=1925865&CampaignId=439892&MerchantId=105064";
	private static final String DEEPLINK_PRODUCT = "ty://?Page=Product&ContentId=1925865";
	private static final String DEEPLINK_PRODUCT_MERCHANT_ID = "ty://?Page=Product&ContentId=1925865&MerchantId=105064";
	private static final String DEEPLINK_PRODUCT_CAMPAIGN_ID = "ty://?Page=Product&ContentId=1925865&CampaignId=439892";

	private static final String DEEPLINK_SEARCH = "ty://?Page=Search&Query=elbise";
	private static final String DEEPLINK_SEARCH_ENCODED = "ty://?Page=Search&Query=%C3%BCt%C3%BC";

	private static final String DEEPLINK_OTHER_FAVORITES = "ty://?Page=Favorites";
	private static final String DEEPLINK_OTHER_ORDERS = "ty://?Page=Orders";
	private static final String DEEPLINK_HOME = "ty://?Page=Home";

	@Test
	public void shouldConvertWebUrlToDeeplinkWhenBoutiqueIdAndMerchantIdPassed() {
		String deeplinkUrl = converterService.convertWebUrlToDeeplink(WEB_URL_PRODUCT_BOUTIQUE_ID_MERCHANT_ID);
		assertThat(deeplinkUrl, equalTo(DEEPLINK_PRODUCT_CAMPAIGN_ID_MERCHANT_ID));
	}

	@Test
	public void shouldConvertWebUrlToDeeplinkWhenBoutiqueIdAndMerchantIdNotPassed() {
		String deeplinkUrl = converterService.convertWebUrlToDeeplink(WEB_URL_PRODUCT);
		assertThat(deeplinkUrl, equalTo(DEEPLINK_PRODUCT));
	}

	@Test
	public void shouldConvertWebUrlToDeeplinkWhenBoutiqueIdPassed() {
		String deeplinkUrl = converterService.convertWebUrlToDeeplink(WEB_URL_PRODUCT_BOUTIQUE_ID);
		assertThat(deeplinkUrl, equalTo(DEEPLINK_PRODUCT_CAMPAIGN_ID));
	}

	@Test
	public void shouldConvertWebUrlToDeeplinkWhenMerchantIdPassed() {
		String deeplinkUrl = converterService.convertWebUrlToDeeplink(WEB_URL_PRODUCT_MERCHANT_ID);
		assertThat(deeplinkUrl, equalTo(DEEPLINK_PRODUCT_MERCHANT_ID));
	}

	@Test
	public void shouldConvertSearchWebUrlToDeeplink() {
		String deeplinkUrl = converterService.convertWebUrlToDeeplink(WEB_URL_SEARCH);
		assertThat(deeplinkUrl, equalTo(DEEPLINK_SEARCH));
	}

	@Test
	public void shouldConvertSearchWebUrlToDeeplinkWithEncoded() {
		String deeplinkUrl = converterService.convertWebUrlToDeeplink(WEB_URL_SEARCH_ENCODED);
		assertThat(deeplinkUrl, equalTo(DEEPLINK_SEARCH_ENCODED));
	}

	@Test
	public void shouldConvertOtherFavorilerWebUrlToDeeplink() {
		String deeplinkUrl = converterService.convertWebUrlToDeeplink(WEB_URL_OTHER_FAVORILER);
		assertThat(deeplinkUrl, equalTo(DEEPLINK_HOME));
	}

	@Test
	public void shouldConvertOtherSiparislerimWebUrlToDeeplink() {
		String deeplinkUrl = converterService.convertWebUrlToDeeplink(WEB_URL_OTHER_SIPARISLERIM);
		assertThat(deeplinkUrl, equalTo(DEEPLINK_HOME));
	}

	@Test
	public void shouldConvertDeeplinkToWebUrlWhenCampaignIdAndMerchantIdPassed() {
		String deeplinkUrl = converterService.convertDeeplinkToWebUrl(DEEPLINK_PRODUCT_CAMPAIGN_ID_MERCHANT_ID);
		assertThat(deeplinkUrl, equalTo(WEB_URL_BRAND_BOUTIQUE_ID_MERCHANT_ID));
	}

	@Test
	public void shouldConvertDeeplinkToWebUrlWhenCampaignIdAndMerchantIdNotPassed() {
		String deeplinkUrl = converterService.convertDeeplinkToWebUrl(DEEPLINK_PRODUCT);
		assertThat(deeplinkUrl, equalTo(WEB_URL_BRAND));
	}

	@Test
	public void shouldConvertDeeplinkToWebUrlWhenCampaignIdPassed() {
		String deeplinkUrl = converterService.convertDeeplinkToWebUrl(DEEPLINK_PRODUCT_CAMPAIGN_ID);
		assertThat(deeplinkUrl, equalTo(WEB_URL_BRAND_BOUTIQUE_ID));
	}

	@Test
	public void shouldConvertDeeplinkToWebUrlWhenMerchantIdPassed() {
		String deeplinkUrl = converterService.convertDeeplinkToWebUrl(DEEPLINK_PRODUCT_MERCHANT_ID);
		assertThat(deeplinkUrl, equalTo(WEB_URL_BRAND_MERCHANT_ID));
	}

	@Test
	public void shouldConvertSearchDeeplinkToWebUrl() {
		String deeplinkUrl = converterService.convertDeeplinkToWebUrl(DEEPLINK_SEARCH);
		assertThat(deeplinkUrl, equalTo(WEB_URL_SEARCH));
	}

	@Test
	public void shouldConvertSearchDeeplinkToWebUrlWithEncoded() {
		String deeplinkUrl = converterService.convertDeeplinkToWebUrl(DEEPLINK_SEARCH_ENCODED);
		assertThat(deeplinkUrl, equalTo(WEB_URL_SEARCH_ENCODED));
	}

	@Test
	public void shouldConvertOtherFavoritesDeeplinkToWebUrlWithEncoded() {
		String deeplinkUrl = converterService.convertDeeplinkToWebUrl(DEEPLINK_OTHER_FAVORITES);
		assertThat(deeplinkUrl, equalTo(WEB_URL_HOME));
	}

	@Test
	public void shouldConvertOtherOrdersDeeplinkToWebUrlWithEncoded() {
		String deeplinkUrl = converterService.convertDeeplinkToWebUrl(DEEPLINK_OTHER_ORDERS);
		assertThat(deeplinkUrl, equalTo(WEB_URL_HOME));
	}

}
