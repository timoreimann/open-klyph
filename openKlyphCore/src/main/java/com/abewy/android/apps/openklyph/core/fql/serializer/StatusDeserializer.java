package com.abewy.android.apps.openklyph.core.fql.serializer;

import org.json.JSONObject;
import com.abewy.android.apps.openklyph.core.fql.LikeInfo;
import com.abewy.android.apps.openklyph.core.fql.Status;
import com.abewy.android.apps.openklyph.core.fql.Stream.CommentInfo;
import com.abewy.android.apps.openklyph.core.graph.GraphObject;

public class StatusDeserializer extends Deserializer
{
	@Override
	public GraphObject deserializeObject(JSONObject data)
	{
		Status status = new Status();

		deserializePrimitives(status, data);
		
		status.setLike_info((LikeInfo) new LikesDeserializer().deserializeObject(getJsonObject(data, "like_info")));
		status.setComment_info((CommentInfo) new StreamDeserializer.CommentsDeserializer().deserializeObject(getJsonObject(data, "comment_info")));

		return status;
	}
}
