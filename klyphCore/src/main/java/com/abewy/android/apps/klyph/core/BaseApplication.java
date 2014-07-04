package com.abewy.android.apps.klyph.core;

import android.annotation.TargetApi;
import android.app.Application;
import android.util.Log;
import java.lang.reflect.Method;

public abstract class BaseApplication extends Application
{
	private static BaseApplication instance;
	
	@Override
	public void onCreate()
	{
		instance = this;
		
		initGlobals();
		initBugReport();
		initPreferences();
		initAds();
		initOthers();

		super.onCreate();
	}
	
	public static BaseApplication getInstance()
	{
		return instance;
	}

	private void initBugReport()
	{
		if (KlyphFlags.ENABLE_BUG_REPORT)
		{
			try {
				Class clazz = Class.forName("com.crashlytics.android.Crashlytics");
				Method method = clazz.getMethod("start", android.content.Context.class);
				method.invoke(null, this);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	protected abstract void initPreferences();

	protected abstract void initGlobals();
	
	protected abstract void initAds();

	protected abstract void initOthers();
	
	public abstract void onLogout();

	@Override
	public void onLowMemory()
	{
		super.onLowMemory();
		Log.i("BaseApplication", "onLowMemory");
	}

	@Override
	@TargetApi(14)
	public void onTrimMemory(int level)
	{
		super.onTrimMemory(level);
		Log.i("BaseApplication", "onTrimMemory");
	}
}

