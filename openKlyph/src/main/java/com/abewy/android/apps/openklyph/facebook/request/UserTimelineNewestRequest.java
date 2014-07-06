package com.abewy.android.apps.openklyph.facebook.request;

public class UserTimelineNewestRequest extends UserTimelineRequest
{
	@Override
	protected boolean isNewest()
	{
		return true;
	}
}
