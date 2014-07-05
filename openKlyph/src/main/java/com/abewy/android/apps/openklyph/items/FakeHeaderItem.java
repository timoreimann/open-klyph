package com.abewy.android.apps.openklyph.items;

import com.abewy.android.apps.openklyph.adapter.AdapterSelector;
import com.abewy.android.apps.openklyph.core.graph.GraphObject;

public class FakeHeaderItem extends GraphObject
{
	private int height;
	
	@Override
	public int getItemViewType()
	{
		return AdapterSelector.FAKE_HEADER;
	}

	public int getHeight()
	{
		return height;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}
}