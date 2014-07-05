package com.abewy.android.apps.openklyph.adapter.fql;

import android.view.View;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;
import com.abewy.android.apps.openklyph.adapter.KlyphAdapter;
import com.abewy.android.apps.openklyph.adapter.holder.FriendPickerHolder;
import com.abewy.android.apps.openklyph.core.fql.Friend;
import com.abewy.android.apps.openklyph.core.graph.GraphObject;
import com.abewy.android.apps.openklyph.core.util.AttrUtil;
import com.abewy.android.apps.openklyph.R;

public class FriendPickerAdapter extends KlyphAdapter
{
	private int placeHolder = -1;
	
	public FriendPickerAdapter()
	{
		super();
	}
	
	@Override
	protected int getLayout()
	{
		return R.layout.item_friend_picker;
	}
	
	@Override
	protected void attachHolder(View view)
	{
		ImageView friendPicture = (ImageView) view.findViewById(R.id.friend_profile_picture);
		TextView friendName = (TextView) view.findViewById(R.id.friend_name);
		CheckBox checkBox = (CheckBox) view.findViewById(android.R.id.checkbox);

		setHolder(view, new FriendPickerHolder(friendPicture, friendName, checkBox));
	}
	
	@Override
	protected void mergeViewWithData(View view, GraphObject data)
	{
		super.mergeViewWithData(view, data);
		
		setData(view, data);
		
		FriendPickerHolder holder = (FriendPickerHolder) getHolder(view);
		
		//holder.getFriendPicture().setImageDrawable(null);
		
		Friend friend = (Friend) data;

		holder.getFriendName().setText(friend.getName());

		//holder.getCheckbox().setSelected(user.isSelected());
		
		((Checkable) view).setChecked(friend.isSelected());
		
		if (placeHolder == -1)
			placeHolder = AttrUtil.getResourceId(getContext(holder.getFriendPicture()), R.attr.squarePlaceHolderIcon);
		
		loadImage(holder.getFriendPicture(), friend.getPic(), placeHolder, data);
	}
}
