package com.abewy.android.apps.openklyph.adapter;

import com.abewy.android.apps.openklyph.core.graph.GraphObject;
import android.view.View;

public interface IGraphAdapter
{
	public int getLayout();
	public void setView(View view);
	public void setData(GraphObject graphObject);
	public boolean isCompatible(GraphObject graphObject);
}
