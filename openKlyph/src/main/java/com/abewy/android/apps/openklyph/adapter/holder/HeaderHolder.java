package com.abewy.android.apps.openklyph.adapter.holder;

import android.view.View;
import android.widget.TextView;
import com.abewy.android.apps.openklyph.R;

public class HeaderHolder
{
	private TextView	headerTitle;

	public HeaderHolder(TextView headerTitle)
	{
		this.headerTitle = headerTitle;
	}

	public void setView(View view)
	{
		headerTitle = (TextView) view.findViewById(R.id.header_title);
	}

	public TextView getHeaderTitle()
	{
		return headerTitle;
	}
}
