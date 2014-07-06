package com.abewy.android.apps.openklyph.app;

import android.os.Bundle;
import com.abewy.android.apps.openklyph.R;

public class AllPhotosActivity extends TitledFragmentActivity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setTitle(getResources().getString(R.string.gallery_activity_title));

	}

	@Override
	protected int getLayout()
	{
		return R.layout.activity_all_photos;
	}
}
