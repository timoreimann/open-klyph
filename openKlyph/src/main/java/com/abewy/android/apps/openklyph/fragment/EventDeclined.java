package com.abewy.android.apps.openklyph.fragment;

import android.os.Bundle;
import android.view.View;
import com.abewy.android.apps.openklyph.Klyph;
import com.abewy.android.apps.openklyph.adapter.MultiObjectAdapter;
import com.abewy.android.apps.openklyph.core.fql.Friend;
import com.abewy.android.apps.openklyph.request.AsyncRequest.Query;
import com.abewy.android.apps.openklyph.widget.KlyphGridView;
import com.abewy.android.apps.openklyph.R;

public class EventDeclined extends KlyphFragment2
{
	public EventDeclined()
	{
		setRequestType(Query.EVENT_DECLINED);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		setListAdapter(new MultiObjectAdapter(getListView()));
		
		defineEmptyText(R.string.empty_list_no_user);
		
		setListVisible(false);

		setRequestType(Query.EVENT_DECLINED);
		
		super.onViewCreated(view, savedInstanceState);
	}
	
	@Override
	public void onGridItemClick(KlyphGridView gridView, View v, int position, long id)
	{
		Friend friend = (Friend) gridView.getItemAtPosition(position);

		startActivity(Klyph.getIntentForGraphObject(getActivity(), friend));
	}
}
