package com.abewy.android.apps.openklyph.core.fql.serializer;

import org.json.JSONObject;
import com.abewy.android.apps.openklyph.core.fql.Friend;
import com.abewy.android.apps.openklyph.core.fql.MessageThread;
import com.abewy.android.apps.openklyph.core.graph.GraphObject;

public class MessageThreadDeserializer extends Deserializer
{
	@Override
	public GraphObject deserializeObject(JSONObject data)
	{
		MessageThread messageThread = new MessageThread();
		
		deserializePrimitives(messageThread, data);
		messageThread.setRecipients(deserializeStringList(getJsonArray(data, "recipients")));
		messageThread.setRecipients_friends(new FriendDeserializer().deserializeArray(getJsonArray(data, "recipients_friends"), Friend.class));
		
		return messageThread;
	}
}
