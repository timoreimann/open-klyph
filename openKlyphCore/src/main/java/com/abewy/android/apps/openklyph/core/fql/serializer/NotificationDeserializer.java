package com.abewy.android.apps.openklyph.core.fql.serializer;

import org.json.JSONObject;
import com.abewy.android.apps.openklyph.core.fql.Comment;
import com.abewy.android.apps.openklyph.core.fql.Event;
import com.abewy.android.apps.openklyph.core.fql.Friend;
import com.abewy.android.apps.openklyph.core.fql.Group;
import com.abewy.android.apps.openklyph.core.fql.Notification;
import com.abewy.android.apps.openklyph.core.fql.Page;
import com.abewy.android.apps.openklyph.core.graph.GraphObject;

public class NotificationDeserializer extends Deserializer
{
	@Override
	public GraphObject deserializeObject(JSONObject data)
	{
		Notification notification = new Notification();
		
		deserializePrimitives(notification, data);
		
		notification.setFriend((Friend) new FriendDeserializer().deserializeObject(getJsonObject(data, "friend")));
		notification.setEvent((Event) new EventDeserializer().deserializeObject(getJsonObject(data, "event")));
		notification.setPage((Page) new PageDeserializer().deserializeObject(getJsonObject(data, "page")));
		notification.setGroup((Group) new GroupDeserializer().deserializeObject(getJsonObject(data, "group")));
		notification.setComment((Comment) new CommentDeserializer().deserializeObject(getJsonObject(data, "comment")));
		
		return notification;
	}
}
