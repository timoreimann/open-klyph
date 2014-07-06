package com.abewy.android.apps.openklyph.messenger.app;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.abewy.android.apps.openklyph.messenger.R;
import com.abewy.util.ApplicationUtil;
import com.abewy.util.PhoneUtil;

public class FeedBackActivity extends TitledActivity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		ImageView companyLogo = (ImageView) findViewById(R.id.company_logo);
		Button suggestButton = (Button) findViewById(R.id.suggest_button);
		Button bugButton = (Button) findViewById(R.id.bug_button);
		Button gPlusButton = (Button) findViewById(R.id.bug_g_plus);

		setTitle(R.string.menu_feedback);
		
		suggestButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_SEND);
				intent.setType("message/rfc822");
				intent.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.feedback_support_mail_address)});
				intent.putExtra(Intent.EXTRA_SUBJECT,"Suggest feedback " + getString(R.string.about_version, ApplicationUtil.getAppVersion(FeedBackActivity.this)));
				intent.putExtra(Intent.EXTRA_TEXT, "");
				startActivity(Intent.createChooser(intent, getString(R.string.feedback_send_mail))); 
			}
		});

		bugButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_SEND);
				intent.setType("message/rfc822");
				intent.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.feedback_support_mail_address)});
				intent.putExtra(Intent.EXTRA_SUBJECT,"Bug feedback " + getString(R.string.about_version, ApplicationUtil.getAppVersion(FeedBackActivity.this)));
				
				intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(new StringBuilder()
                .append("<p>Android Version : " + Build.VERSION.RELEASE + " (Api Level " + Build.VERSION.SDK_INT + ")</p>")
                .append("<p>Device model : " + Build.BRAND + " " + Build.MODEL + "</p>")
                .toString()));
				startActivity(Intent.createChooser(intent, getString(R.string.feedback_send_mail))); 
			}
		});
		
		gPlusButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v)
			{
				try {
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.google_plus_community_url))));
				}
				catch (ActivityNotFoundException e)
				{
					PhoneUtil.openURL(FeedBackActivity.this, getString(R.string.google_plus_community_url));
				}
			}
		});

		companyLogo.setClickable(true);
		companyLogo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v)
			{
				PhoneUtil.openURL(FeedBackActivity.this, getString(R.string.company_url));
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		return false;
	}

	@Override
	protected int getLayout()
	{
		return R.layout.activity_feedback;
	}
}
