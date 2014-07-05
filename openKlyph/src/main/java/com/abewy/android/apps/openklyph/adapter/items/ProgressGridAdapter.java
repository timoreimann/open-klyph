package com.abewy.android.apps.openklyph.adapter.items;

import android.view.View;
import android.view.ViewGroup.LayoutParams;
import com.abewy.android.apps.openklyph.Klyph;
import com.abewy.android.apps.openklyph.R;
import com.abewy.android.apps.openklyph.adapter.KlyphAdapter;

public class ProgressGridAdapter extends KlyphAdapter
{
	int columnWidth;
	
	public ProgressGridAdapter()
	{
		super();
		
		columnWidth = Klyph.getGridColumnWidth();
	}
	
	@Override
	protected int getLayout()
	{
		return R.layout.item_progress_grid;
	}
	
	@Override
	public void setLayoutParams(View view)
	{
		LayoutParams lp = view.getLayoutParams();
		lp.height = lp.width = columnWidth;
		view.setLayoutParams(lp);
	}
}
