package com.abewy.android.apps.openklyph.core.graph.serializer;

import org.json.JSONObject;
import com.abewy.android.apps.openklyph.core.graph.Application;
import com.abewy.android.apps.openklyph.core.graph.GraphObject;

public class ApplicationDeserializer extends Deserializer
{
	@Override
	public GraphObject deserializeObject(JSONObject data)
	{
		Application application = new Application();

		deserializePrimitives(application, data);

		return application;
	}
}
