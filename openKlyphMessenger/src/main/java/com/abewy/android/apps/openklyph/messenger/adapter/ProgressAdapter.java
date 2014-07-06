package com.abewy.android.apps.openklyph.messenger.adapter;

import android.view.View;
import com.abewy.android.apps.openklyph.core.graph.GraphObject;
import com.abewy.android.apps.openklyph.messenger.R;

public class ProgressAdapter extends KlyphAdapter
{
	public ProgressAdapter()
	{
		super();
	}
	
	@Override
	protected int getLayoutRes()
	{
		return R.layout.item_progress;
	}
	
	@Override
	protected Boolean isCompatible(View view)
	{
		return view.getId() == R.id.progress_item;
	}

	@Override
	public void bindData(View view, GraphObject data)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void attachViewHolder(View view)
	{
		// TODO Auto-generated method stub
		
	}
}
