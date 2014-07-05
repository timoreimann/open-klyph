package com.abewy.android.apps.openklyph.fragment;

import android.os.Bundle;
import android.view.View;
import com.abewy.android.apps.openklyph.Klyph;
import com.abewy.android.apps.openklyph.core.fql.Event;
import com.abewy.android.apps.openklyph.request.AsyncRequest.Query;
import com.abewy.android.apps.openklyph.widget.KlyphGridView;
import com.abewy.android.apps.openklyph.R;

public class ElementEvents extends KlyphFakeHeaderGridFragment
{
	public ElementEvents()
	{
		setRequestType(Query.ELEMENT_EVENTS);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		defineEmptyText(R.string.empty_list_no_event);
		
		setListVisible(false);
		
		setRequestType(Query.ELEMENT_EVENTS);
		
		super.onViewCreated(view, savedInstanceState);
	}
	
	@Override
	public void onGridItemClick(KlyphGridView l, View v, int position, long id)
	{
		Event event = (Event) l.getItemAtPosition(position);

		startActivity(Klyph.getIntentForGraphObject(getActivity(), event));
	}
}
