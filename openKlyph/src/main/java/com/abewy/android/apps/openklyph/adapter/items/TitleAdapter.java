package com.abewy.android.apps.openklyph.adapter.items;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.abewy.android.apps.openklyph.adapter.KlyphAdapter;
import com.abewy.android.apps.openklyph.adapter.holder.TitleHolder;
import com.abewy.android.apps.openklyph.core.graph.GraphObject;
import com.abewy.openklyph.items.Title;
import com.abewy.android.apps.openklyph.R;

public class TitleAdapter extends KlyphAdapter
{
	public TitleAdapter()
	{
		super();
	}

	@Override
	protected int getLayout()
	{
		return R.layout.item_title;
	}

	@Override
	protected void attachHolder(View view)
	{
		view.setTag(new TitleHolder((TextView) view.findViewById(R.id.title), (RelativeLayout) view.findViewById(R.id.item_shadow)));
	}

	@Override
	protected void mergeViewWithData(View view, GraphObject data)
	{
		TitleHolder holder = (TitleHolder) view.getTag();

		Title title = (Title) data;

		holder.getTitle().setText(title.getName());
		
		holder.getShadow().setVisibility(title.getShadow() == true ? View.VISIBLE : View.GONE);
	}
}
