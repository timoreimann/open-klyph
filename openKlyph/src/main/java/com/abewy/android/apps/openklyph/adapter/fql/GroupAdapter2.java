package com.abewy.android.apps.openklyph.adapter.fql;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.abewy.android.apps.openklyph.adapter.KlyphAdapter;
import com.abewy.android.apps.openklyph.adapter.holder.PicturePrimarySecondaryTextHolder;
import com.abewy.android.apps.openklyph.core.fql.Group;
import com.abewy.android.apps.openklyph.core.graph.GraphObject;
import com.abewy.android.apps.openklyph.core.util.AttrUtil;
import com.abewy.android.apps.openklyph.core.util.FacebookUtil;
import com.abewy.android.apps.openklyph.R;

public class GroupAdapter2 extends KlyphAdapter
{
	private int placeHolder = -1;
	
	public GroupAdapter2()
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
		ImageView friendPicture = (ImageView) view.findViewById(R.id.picture);
		TextView name = (TextView) view.findViewById(R.id.primary_text);
		TextView description = (TextView) view.findViewById(R.id.secondary_text);

		setHolder(view, new PicturePrimarySecondaryTextHolder(friendPicture, name , description));
	}
	
	@Override
	protected void mergeViewWithData(View view, GraphObject data)
	{
		super.mergeViewWithData(view, data);
		
		PicturePrimarySecondaryTextHolder holder = (PicturePrimarySecondaryTextHolder) getHolder(view);
		
		//holder.getPicture().setImageDrawable(null);
		
		Group group = (Group) data;

		holder.getPrimaryText().setText(group.getName());
		holder.getSecondaryText().setText(group.getDescription());
		
		if (placeHolder == -1)
			placeHolder = AttrUtil.getResourceId(getContext(holder.getPicture()), R.attr.squarePlaceHolderIcon);

		String url = FacebookUtil.getImageURLForId(group.getGid());
		loadImage(holder.getPicture(), url, placeHolder, data);
	}
}
