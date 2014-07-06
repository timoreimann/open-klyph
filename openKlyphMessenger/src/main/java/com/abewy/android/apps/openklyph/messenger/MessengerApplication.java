package com.abewy.android.apps.openklyph.messenger;

import java.util.ArrayList;
import java.util.List;
import android.preference.PreferenceManager;
import android.util.Log;

import com.abewy.android.ads.BannerAdManager;
import com.abewy.android.ads.IBannerAd;
import com.abewy.android.apps.openklyph.core.BaseApplication;
import com.abewy.android.apps.openklyph.core.KlyphLocale;
import com.abewy.android.apps.openklyph.core.imageloader.ImageLoader;

public class MessengerApplication extends BaseApplication
{
	public static boolean IS_PRO_VERSION = false;
	public static boolean PRO_VERSION_CHECKED = false;
	@Override
	public void onCreate()
	{
		super.onCreate();
	}

	public static MessengerApplication getInstance()
	{
		return (MessengerApplication) BaseApplication.getInstance();
	}

	@Override
	protected void initPreferences()
	{
		//PreferenceManager.setDefaultValues(this, MessengerPreferences.PREFERENCES_FILE_NAME, Context.MODE_PRIVATE, R.xml.preferences, true);
		PreferenceManager.setDefaultValues(getBaseContext(), R.xml.preferences, false);
	}

	@Override
	protected void initGlobals()
	{
		// Klyph.defineFacebookId();

		KlyphLocale.setAppLocale(KlyphLocale.getAppLocale());
	}
	

	@Override
	protected void initAds()
	{
		List<IBannerAd> bannerAds = new ArrayList<IBannerAd>();
        try {
            Class clazz = Class.forName("com.abewy.android.apps.openklyph.messenger.ads.AdmobBanner");
            bannerAds.add((IBannerAd)
                    clazz.getConstructor(String.class).newInstance(getString(R.string.admob_id)));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	@Override
	protected void initOthers()
	{
		ImageLoader.initImageLoader(getApplicationContext());
	}

	@Override
	public void onLogout()
	{

	}
}
