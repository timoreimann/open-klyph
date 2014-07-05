package com.abewy.android.apps.openklyph.app;

import java.util.ArrayList;
import java.util.List;
import com.abewy.android.ads.BannerAdManager;
import com.abewy.android.ads.IBannerAd;
import com.abewy.android.apps.openklyph.R;

public class KlyphApplication extends com.abewy.android.apps.openklyph.KlyphApplication
{
	@Override
	protected void initAds()
	{
		//AdRegistration.setAppKey("038faf04326f4ccbb7ab1c107e3dbd25");
		//AdRegistration.enableTesting(true);
		
		List<IBannerAd> bannerAds = new ArrayList<IBannerAd>();

		//bannerAds.add(new AmazonBanner());

		try {
			Class clazz = Class.forName("com.abewy.android.apps.klyph.ads.AdmobBanner");
			bannerAds.add(
				(IBannerAd)
					clazz.getConstructor(String.class)
						.newInstance(getString(R.string.admob_id))
			);
		} catch (Exception e) {
		}
		BannerAdManager.setBannerAds(bannerAds);
	}
}
