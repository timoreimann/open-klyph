package com.abewy.android.apps.openklyph.adapter.fql;

import android.view.View;
import android.widget.TextView;
import com.abewy.android.apps.openklyph.R;
import com.abewy.android.apps.openklyph.adapter.KlyphAdapter;
import com.abewy.android.apps.openklyph.adapter.holder.TextItemHolder;
import com.abewy.android.apps.openklyph.core.graph.GraphObject;
import com.abewy.openklyph.items.TextItem;

public class StreamLikeAdapter extends KlyphAdapter
{
	public StreamLikeAdapter()
	{
		super();
	}

	@Override
	protected int getLayout()
	{
		return R.layout.item_stream_like_count;
	}

	@Override
	protected void attachHolder(View view)
	{
		view.setTag(new TextItemHolder((TextView) view.findViewById(R.id.text), null));
	}

	@Override
	protected void mergeViewWithData(View view, GraphObject data)
	{
		TextItemHolder holder = (TextItemHolder) view.getTag();

		TextItem item = (TextItem) data;

		holder.getText().setText(item.getText());
	}
}
