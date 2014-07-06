package com.abewy.android.apps.openklyph.facebook.request;

import java.util.ArrayList;
import org.json.JSONArray;
import com.abewy.android.apps.openklyph.core.fql.serializer.VideoDeserializer;
import com.abewy.android.apps.openklyph.core.graph.GraphObject;

public class AlbumVideosRequest extends KlyphQuery
{
	@Override
	public String getQuery(String id, String offset)
	{
		String query = "SELECT album_id, created_time, description, embed_html, format, length, link, owner, src, src_hq, thumbnail_link, title, updated_time, vid";
		query += " FROM video WHERE owner = \"" + id + "\"";
		
		if (isOffsetDefined(offset))
		{
			query += " AND created_time < " + offset;
		}
		
		query += " ORDER BY created_time DESC LIMIT 50";
		
		return query;
	}

	@Override
	public ArrayList<GraphObject> handleResult(JSONArray result)
	{
		VideoDeserializer deserializer = new VideoDeserializer();
		ArrayList<GraphObject> videos = (ArrayList<GraphObject>) deserializer.deserializeArray(result);

		setHasMoreData(videos.size() >= 50);
		
		return videos;
	}
}
