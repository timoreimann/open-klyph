package com.abewy.android.apps.openklyph.adapter.holder;

import android.widget.ImageView;
import android.widget.TextView;

public class GroupHolder
{
	private ImageView	groupCover;
	private TextView	groupName;
	private TextView	groupDescription;

	public GroupHolder(ImageView groupCover, TextView groupName, TextView groupDescription)
	{
		this.groupCover = groupCover;
		this.groupName = groupName;
		this.groupDescription = groupDescription;
	}

	public ImageView getGroupCover()
	{
		return groupCover;
	}

	public TextView getGroupName()
	{
		return groupName;
	}

	public TextView getGroupDescription()
	{
		return groupDescription;
	}
}
