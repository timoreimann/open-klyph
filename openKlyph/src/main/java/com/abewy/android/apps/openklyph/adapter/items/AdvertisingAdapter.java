package com.abewy.android.apps.openklyph.adapter.items;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import com.abewy.android.ads.BannerAdManager;
import com.abewy.android.apps.openklyph.R;
import com.abewy.android.apps.openklyph.adapter.KlyphAdapter;
import com.abewy.android.apps.openklyph.core.graph.GraphObject;

public class AdvertisingAdapter extends KlyphAdapter
{
	public AdvertisingAdapter()
	{
		super();
	}

	@Override
	protected int getLayout()
	{
		return R.layout.item_ad;
	}

	@Override
	protected void attachHolder(View view)
	{
		view.setTag(new BannerAdManager((Activity) view.getContext(), (ViewGroup) view.findViewById(R.id.ad_container)));
	}

	@Override
	protected void mergeViewWithData(View view, GraphObject data)
	{
		BannerAdManager holder = (BannerAdManager) view.getTag();

		holder.loadAd();
	}
}
