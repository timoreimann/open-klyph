package com.abewy.android.apps.openklyph;

import java.lang.reflect.Method;
import java.util.Calendar;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.abewy.android.apps.openklyph.core.KlyphFlags;
import com.abewy.android.apps.openklyph.service.BirthdayService;
import com.abewy.android.apps.openklyph.service.NotificationService;

public class KlyphService
{
	public static void startBirthdayService()
	{
		Log.d("KlyphService", "Start birthday");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MINUTE, 0);
		
		int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
		int targetHour = KlyphPreferences.getBirthdayNotificationTime();
		
		calendar.set(Calendar.HOUR_OF_DAY, targetHour);
		
		if (currentHour > targetHour)
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		
		Intent service = new Intent(KlyphApplication.getInstance(), BirthdayService.class);
		PendingIntent pendingService = PendingIntent.getService(KlyphApplication.getInstance(), 0, service,
				PendingIntent.FLAG_UPDATE_CURRENT);

		AlarmManager alarmManager = (AlarmManager) KlyphApplication.getInstance()
				.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(pendingService);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingService);
	}

	public static void stopBirthdayService()
	{
		Log.d("KlyphService", "Stop birthday");
		Intent service = new Intent(KlyphApplication.getInstance(), BirthdayService.class);
		PendingIntent pendingService = PendingIntent.getService(KlyphApplication.getInstance(), 0, service,
				PendingIntent.FLAG_UPDATE_CURRENT);

		AlarmManager alarmManager = (AlarmManager) KlyphApplication.getInstance()
				.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(pendingService);
	}

	public static void startSelectedServices()
	{
		if (KlyphPreferences.areNotificationsEnabled() == true)
		{
			if (KlyphPreferences.arePushNotificationsEnabled())
			{
				startPushNotificationsService();
			}
			
			if (KlyphPreferences.arePeriodicNotificationsEnabled())
			{
				KlyphService.startPeriodicNotificationService();
			}
			
			if (KlyphPreferences.areBirthdayNotificationsEnabled())
			{
				KlyphService.startBirthdayService();
			}
		}
	}
	
	public static void startPushNotificationsService()
	{
		Log.d("KlyphService", "Start push");
		if (KlyphFlags.IS_AMAZON_VERSION)
		{
			try {
				Class clazz = Class.forName("com.abewy.android.apps.klyph.KlyphADM");
				Method method = clazz.getMethod("registerIfNecessary");
				Log.d("KlyphService", "startPushNotificationsService: starting ADM");
				method.invoke(null);
			} catch (Exception e) {
				Log.e("KlyphService", "startPushNotificationsService: failed to start ADM", e);
			}
		}
		else
		{
			try {
				Class clazz = Class.forName("com.abewy.android.apps.klyph.KlyphGCM");
				Method method = clazz.getMethod("registerIfNecessary");
				Log.d("KlyphService", "startPushNotificationsService: starting GCM");
				method.invoke(null);
			} catch (Exception e) {
				Log.e("KlyphService", "startPushNotificationsService: failed to start ADM", e);
			}
		}
	}
	
	public static void stopPushNotificationsService()
	{
		Log.d("KlyphService", "Stop push");
		
		if (KlyphFlags.IS_AMAZON_VERSION)
		{
			try {
				Class clazz = Class.forName("com.abewy.android.apps.klyph.KlyphADM");
				Method method = clazz.getMethod("unregister", Context.class);
				Log.d("KlyphService", "stopPushNotificationsService: stopping ADM");
				method.invoke(null,KlyphApplication.getInstance());
			} catch (Exception e) {
				Log.e("KlyphService", "stopPushNotificationsService: failed to stop ADM", e);
			}
		}
		else
		{
			try {
				Class clazz = Class.forName("com.abewy.android.apps.klyph.KlyphGCM");
				Method method = clazz.getMethod("unregister", Context.class);
				Log.d("KlyphService", "stopPushNotificationsService: stopping GCM");
				method.invoke(null,KlyphApplication.getInstance());
			} catch (Exception e) {
				Log.e("KlyphService", "stopPushNotificationsService: failed to stop GCM", e);
			}
		}
		
	}
	
	public static void startPeriodicNotificationService()
	{
		Log.d("KlyphService", "Start periodic");
		int interval = KlyphPreferences.getPeriodicNotificationsInterval();

		Intent service = new Intent(KlyphApplication.getInstance(), NotificationService.class);
		PendingIntent pendingService = PendingIntent.getService(KlyphApplication.getInstance(), 0, service,
				PendingIntent.FLAG_UPDATE_CURRENT);

		AlarmManager alarmManager = (AlarmManager) KlyphApplication.getInstance()
				.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(pendingService);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval * 60 * 1000,
				pendingService);
	}

	public static void stopPeriodicNotificationService()
	{
		Log.d("KlyphService", "Stop periodic");
		Intent service = new Intent(KlyphApplication.getInstance(), NotificationService.class);
		PendingIntent pendingService = PendingIntent.getService(KlyphApplication.getInstance(), 0, service,
				PendingIntent.FLAG_UPDATE_CURRENT);

		AlarmManager alarmManager = (AlarmManager) KlyphApplication.getInstance()
				.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(pendingService);
	}
	
	public static void stopServices()
	{
		Log.d("KlyphService", "Stop services");
		stopPushNotificationsService();
		stopBirthdayService();
		stopPeriodicNotificationService();
	}
}
