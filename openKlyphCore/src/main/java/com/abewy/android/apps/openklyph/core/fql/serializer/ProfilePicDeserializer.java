package com.abewy.android.apps.openklyph.core.fql.serializer;

import org.json.JSONObject;
import com.abewy.android.apps.openklyph.core.fql.ProfilePic;
import com.abewy.android.apps.openklyph.core.graph.GraphObject;

public class ProfilePicDeserializer extends Deserializer
{
	@Override
	public GraphObject deserializeObject(JSONObject data)
	{
		ProfilePic profilePic = new ProfilePic();
		
		deserializePrimitives(profilePic, data);
		
		return profilePic;
	}
}
