package com.abewy.android.apps.openklyph.facebook.request;

public class UserTimelineFeedNewestRequest extends UserTimelineFeedRequest
{
	@Override
	protected boolean isNewest()
	{
		return true;
	}
}
