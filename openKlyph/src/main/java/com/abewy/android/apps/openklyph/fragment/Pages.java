package com.abewy.android.apps.openklyph.fragment;

import android.os.Bundle;
import android.view.View;
import com.abewy.android.apps.openklyph.Klyph;
import com.abewy.android.apps.openklyph.core.fql.Page;
import com.abewy.android.apps.openklyph.request.AsyncRequest.Query;
import com.abewy.android.apps.openklyph.widget.KlyphGridView;
import com.abewy.android.apps.openklyph.R;

public class Pages extends KlyphFakeHeaderGridFragment
{
	public Pages()
	{
		setRequestType(Query.ELEMENT_PAGES);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		defineEmptyText(R.string.empty_list_no_page);
		
		setListVisible(false);
		
		setRequestType(Query.ELEMENT_PAGES);
		
		super.onViewCreated(view, savedInstanceState);
	}
	
	@Override
	public void onGridItemClick(KlyphGridView l, View v, int position, long id)
	{
		Page page = (Page) l.getItemAtPosition(position);

		startActivity(Klyph.getIntentForGraphObject(getActivity(), page));		
	}
}
