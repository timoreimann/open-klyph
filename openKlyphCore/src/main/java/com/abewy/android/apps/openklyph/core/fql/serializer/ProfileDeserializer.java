package com.abewy.android.apps.openklyph.core.fql.serializer;

import org.json.JSONObject;
import com.abewy.android.apps.openklyph.core.fql.Profile;
import com.abewy.android.apps.openklyph.core.graph.GraphObject;

public class ProfileDeserializer extends Deserializer
{
	@Override
	public GraphObject deserializeObject(JSONObject data)
	{
		Profile profile = new Profile();

		deserializePrimitives(profile, data);

		return profile;
	}
}
