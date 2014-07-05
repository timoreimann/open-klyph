package com.abewy.android.apps.openklyph.core.fql.serializer;

import org.json.JSONObject;
import com.abewy.android.apps.openklyph.core.fql.LikeInfo;
import com.abewy.android.apps.openklyph.core.graph.GraphObject;

public class LikesDeserializer extends Deserializer
{
	@Override
	public GraphObject deserializeObject(JSONObject data)
	{
		LikeInfo likes = new LikeInfo();
		
		deserializePrimitives(likes, data);
		
		return likes;
	}
}
