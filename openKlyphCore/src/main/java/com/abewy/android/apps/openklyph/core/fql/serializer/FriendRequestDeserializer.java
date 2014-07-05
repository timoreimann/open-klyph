package com.abewy.android.apps.openklyph.core.fql.serializer;

import org.json.JSONObject;
import com.abewy.android.apps.openklyph.core.fql.FriendRequest;
import com.abewy.android.apps.openklyph.core.graph.GraphObject;

public class FriendRequestDeserializer extends Deserializer
{
	@Override
	public GraphObject deserializeObject(JSONObject data)
	{
		FriendRequest fq = new FriendRequest();
		
		deserializePrimitives(fq, data);
		
		return fq;
	}
}
