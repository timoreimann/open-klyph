package com.abewy.android.apps.openklyph.adapter.fql;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.abewy.android.apps.openklyph.adapter.KlyphAdapter;
import com.abewy.android.apps.openklyph.adapter.holder.PicturePrimarySecondaryTextHolder;
import com.abewy.android.apps.openklyph.core.fql.Page;
import com.abewy.android.apps.openklyph.core.graph.GraphObject;
import com.abewy.android.apps.openklyph.core.util.AttrUtil;
import com.abewy.android.apps.openklyph.R;

public class PageAdapter extends KlyphAdapter
{
	private int placeHolder = -1;
	
	public PageAdapter()
	{
		super();
	}

	@Override
	protected int getLayout()
	{
		//return R.layout.item_picture_primary_secondary_text;
		return R.layout.item_grid_picture_primary_secondary_text;
	}

	@Override
	protected void attachHolder(View view)
	{
		ImageView pagePicture = (ImageView) view.findViewById(R.id.picture);
		TextView pageName = (TextView) view.findViewById(R.id.primary_text);
		TextView pageCategory = (TextView) view.findViewById(R.id.secondary_text);

		setHolder(view, new PicturePrimarySecondaryTextHolder(pagePicture, pageName, pageCategory));
	}

	@Override
	protected void mergeViewWithData(View view, GraphObject data)
	{
		super.mergeViewWithData(view, data);
		
		PicturePrimarySecondaryTextHolder holder = (PicturePrimarySecondaryTextHolder) getHolder(view);
		
		//holder.getPicture().setImageDrawable(null);

		Page page = (Page) data;

		holder.getPrimaryText().setText(page.getName());
		holder.getSecondaryText().setText(page.getType().toUpperCase());
		
		if (placeHolder == -1)
			placeHolder = AttrUtil.getResourceId(getContext(holder.getPicture()), R.attr.squarePlaceHolderIcon);

		loadImage(holder.getPicture(), page.getPic(), placeHolder, data);
	}
}
