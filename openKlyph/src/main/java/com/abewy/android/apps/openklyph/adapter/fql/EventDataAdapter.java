package com.abewy.android.apps.openklyph.adapter.fql;

import android.view.View;
import android.widget.TextView;
import com.abewy.android.apps.openklyph.R;
import com.abewy.android.apps.openklyph.adapter.KlyphAdapter;
import com.abewy.android.apps.openklyph.adapter.holder.EventDataHolder;
import com.abewy.android.apps.openklyph.core.fql.Event;
import com.abewy.android.apps.openklyph.core.graph.GraphObject;
import com.abewy.android.apps.openklyph.fragment.EventFragment.EventData;
import com.abewy.android.apps.openklyph.util.DateUtil;

public class EventDataAdapter extends KlyphAdapter
{
	public EventDataAdapter()
	{
		super();
	}

	@Override
	protected int getLayout()
	{
		return R.layout.item_event_data;
	}

	@Override
	protected void attachHolder(View view)
	{
		TextView date = (TextView) view.findViewById(R.id.event_date);
		TextView place = (TextView) view.findViewById(R.id.event_place);
		TextView description = (TextView) view.findViewById(R.id.description);

		view.setTag(new EventDataHolder(date, place, description));
	}

	@Override
	protected void mergeViewWithData(View view, GraphObject data)
	{
		EventDataHolder holder = (EventDataHolder) view.getTag();

		Event event = ((EventData) data).getEvent();
		
		String date = DateUtil.getFormattedDateTimeWithYear(DateUtil.getUnixTimeFromDate(event.getStart_time()));

		if (event.getEnd_time().length() > 0)
		{
			if (DateUtil.areSameDay(event.getStart_time(), event.getEnd_time(), true))
			{
				date += " - " + DateUtil.getTime(DateUtil.getUnixTimeFromDate(event.getEnd_time()));
			}
			else
			{
				date += " - " + DateUtil.getFormattedDateTimeWithYear(DateUtil.getUnixTimeFromDate(event.getEnd_time()));
			}
		}
		
		holder.getDate().setText(date);
		
		if (event.getVenue().getName().length() > 0)
			holder.getPlace().setText(event.getVenue().getName());
		else
			holder.getPlace().setText(event.getLocation());
			
		holder.getDescription().setText(event.getDescription());
	}
}
