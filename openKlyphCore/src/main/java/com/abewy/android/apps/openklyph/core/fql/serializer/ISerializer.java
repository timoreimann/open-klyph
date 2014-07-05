package com.abewy.android.apps.openklyph.core.fql.serializer;

import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import com.abewy.android.apps.openklyph.core.graph.GraphObject;

public interface ISerializer
{
	public JSONObject serializeObject(GraphObject object);
	public JSONArray serializeArray(List<? extends GraphObject> data);

}
