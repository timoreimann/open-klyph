package com.abewy.openklyph.items;

import com.abewy.android.apps.openklyph.core.graph.GraphObject;

public class Progress extends GraphObject
{
	public Progress()
	{

	}

	@Override
	public boolean isSelectable(int layout)
	{
		return false;
	}
	
	@Override
	public int getItemViewType()
	{
		return ItemType.PROGRESS;
	}
}
