package com.abewy.android.apps.openklyph.messenger.adapter.holder;

import android.widget.ImageView;
import android.widget.TextView;

public class RosterEntryHolder
{
	private ImageView	picture;
	private TextView	primaryText;

	public RosterEntryHolder(ImageView picture, TextView primaryText)
	{
		this.picture = picture;
		this.primaryText = primaryText;
	}

	public ImageView getPicture()
	{
		return picture;
	}

	public TextView getPrimaryText()
	{
		return primaryText;
	}
}
