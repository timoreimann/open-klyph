package com.abewy.android.apps.openklyph.core.fql.serializer;

import org.json.JSONObject;
import com.abewy.android.apps.openklyph.core.fql.Attachment;
import com.abewy.android.apps.openklyph.core.fql.Checkin;
import com.abewy.android.apps.openklyph.core.fql.Media;
import com.abewy.android.apps.openklyph.core.graph.GraphObject;

public class AttachmentDeserializer extends Deserializer
{
	@Override
	public GraphObject deserializeObject(JSONObject data)
	{
		Attachment attachment = new Attachment();
		
		deserializePrimitives(attachment, data);
		attachment.setMedia(new MediaDeserializer().deserializeArray(getJsonArray(data, "media"), Media.class));
		attachment.setFb_checkin((Checkin) new CheckinDeserializer().deserializeObject(getJsonObject(data, "fb_checkin")));
		
		return attachment;
	}
}
