package com.abewy.android.apps.openklyph.messenger.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.abewy.android.apps.openklyph.core.fql.Friend;
import com.abewy.android.apps.openklyph.core.graph.GraphObject;
import com.abewy.android.apps.openklyph.core.imageloader.ImageLoader;
import com.abewy.android.apps.openklyph.core.util.AttrUtil;
import com.abewy.android.apps.openklyph.messenger.R;
import com.abewy.android.apps.openklyph.messenger.adapter.holder.PicturePrimaryTextHolder;

public class FriendAdapter extends KlyphAdapter
{
	public FriendAdapter()
	{
		super();
	}
	
	@Override
	protected int getLayoutRes()
	{
		//return R.layout.item_picture_primary_text;
		return R.layout.item_picture_primary_text;
	}
	
	@Override
	protected void attachViewHolder(View view)
	{
		ImageView friendPicture = (ImageView) view.findViewById(R.id.picture);
		TextView friendName = (TextView) view.findViewById(R.id.primary_text);

		setHolder(view, new PicturePrimaryTextHolder(friendPicture, friendName));
	}
	
	@Override
	public void bindData(View view, GraphObject data)
	{
		PicturePrimaryTextHolder holder = (PicturePrimaryTextHolder) getHolder(view);
		
		//holder.getPicture().setImageDrawable(null);
		
		Friend friend = (Friend) data;

		holder.getPrimaryText().setText(friend.getName());

		String url = friend.getPic();//FacebookUtil.getProfilePictureURLForId(friend.getUid());
		ImageLoader.display(holder.getPicture(), url, AttrUtil.getResourceId(getContext(view), R.attr.picturePlaceHolder));
	}
	
	@Override
	protected Boolean isCompatible(View view)
	{
		return getHolder(view) instanceof PicturePrimaryTextHolder;
	}
}
