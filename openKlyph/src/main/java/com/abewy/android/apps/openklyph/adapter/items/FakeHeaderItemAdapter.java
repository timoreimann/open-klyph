package com.abewy.android.apps.openklyph.adapter.items;

import android.view.View;
import android.view.ViewGroup.LayoutParams;
import com.abewy.android.apps.openklyph.R;
import com.abewy.android.apps.openklyph.adapter.KlyphAdapter;
import com.abewy.android.apps.openklyph.core.graph.GraphObject;
import com.abewy.android.apps.openklyph.items.FakeHeaderItem;

public class FakeHeaderItemAdapter extends KlyphAdapter
{
	public FakeHeaderItemAdapter()
	{
		super();
	}

	@Override
	protected int getLayout()
	{
		return R.layout.fake_header_item;
	}

	@Override
	protected void attachHolder(View view)
	{
		
	}

	@Override
	protected void mergeViewWithData(View view, GraphObject data)
	{
		FakeHeaderItem item = (FakeHeaderItem) data;

		LayoutParams params = view.getLayoutParams();
		params.height = item.getHeight();
		view.setLayoutParams(params);
	}

	@Override
	public boolean isEnabled(GraphObject object)
	{
		return false;
	}
}