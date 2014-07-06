package com.abewy.android.apps.openklyph.fragment;

import android.os.Bundle;
import android.view.View;
import com.abewy.android.apps.openklyph.Klyph;
import com.abewy.android.apps.openklyph.adapter.MultiObjectAdapter;
import com.abewy.android.apps.openklyph.adapter.SpecialLayout;
import com.abewy.android.apps.openklyph.core.fql.Friend;
import com.abewy.android.apps.openklyph.request.AsyncRequest.Query;
import com.abewy.android.apps.openklyph.widget.KlyphGridView;
import com.abewy.android.apps.openklyph.R;

public class GroupMembers extends KlyphFakeHeaderGridFragment
{
	public GroupMembers()
	{
		setRequestType(Query.GROUP_MEMBERS);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		defineEmptyText(R.string.empty_list_no_member);
		
		setListVisible(false);

		setRequestType(Query.GROUP_MEMBERS);
		
		super.onViewCreated(view, savedInstanceState);
		setListAdapter(new MultiObjectAdapter(getListView(), SpecialLayout.MEMBER));
	}
	
	@Override
	public void onGridItemClick(KlyphGridView l, View v, int position, long id)
	{
		Friend friend = (Friend) l.getItemAtPosition(position);

		startActivity(Klyph.getIntentForGraphObject(getActivity(), friend));
	}
}
