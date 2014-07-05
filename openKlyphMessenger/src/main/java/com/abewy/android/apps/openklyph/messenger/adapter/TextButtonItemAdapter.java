package com.abewy.android.apps.openklyph.messenger.adapter;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.abewy.android.apps.openklyph.core.graph.GraphObject;
import com.abewy.android.apps.openklyph.messenger.R;
import com.abewy.android.apps.openklyph.messenger.adapter.holder.TextButtonItemHolder;
import com.abewy.openklyph.items.TextButtonItem;

public class TextButtonItemAdapter extends KlyphAdapter
{
	public TextButtonItemAdapter()
	{
		super();
	}

	@Override
	protected int getLayoutRes()
	{
		return R.layout.item_text_button_item;
	}

	@Override
	protected void attachViewHolder(View view)
	{
		view.setTag(new TextButtonItemHolder((TextView) view.findViewById(R.id.text), (ImageButton) view.findViewById(R.id.button)));
	}

	@Override
	public void bindData(View view, GraphObject data)
	{
		TextButtonItemHolder holder = (TextButtonItemHolder) view.getTag();

		TextButtonItem item = (TextButtonItem) data;

		holder.getText().setText(item.getText());
		
		if (item.getButtonListener() != null)
		{
			holder.getButton().setOnClickListener(item.getButtonListener());
		}
	}

	@Override
	protected Boolean isCompatible(View view)
	{
		return view.getTag() instanceof TextButtonItemHolder;
	}
}
