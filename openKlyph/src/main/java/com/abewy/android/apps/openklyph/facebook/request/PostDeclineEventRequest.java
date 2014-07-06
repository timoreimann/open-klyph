package com.abewy.android.apps.openklyph.facebook.request;

import com.facebook.HttpMethod;

public class PostDeclineEventRequest extends KlyphQuery
{
	@Override
	public boolean isFQL()
	{
		return false;
	}
	
	@Override
	public String getQuery(String id, String offset)
	{
		return "/" + id + "/declined";
	}
	
	public HttpMethod getHttpMethod()
	{
		return HttpMethod.POST;
	}
}
