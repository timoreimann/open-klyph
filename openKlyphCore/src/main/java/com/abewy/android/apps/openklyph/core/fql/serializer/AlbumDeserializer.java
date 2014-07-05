package com.abewy.android.apps.openklyph.core.fql.serializer;

import org.json.JSONObject;
import com.abewy.android.apps.openklyph.core.fql.Album;
import com.abewy.android.apps.openklyph.core.fql.Photo.Image;
import com.abewy.android.apps.openklyph.core.fql.serializer.PhotoDeserializer.ImageDeserializer;
import com.abewy.android.apps.openklyph.core.graph.GraphObject;

public class AlbumDeserializer extends Deserializer
{
	@Override
	public GraphObject deserializeObject(JSONObject data)
	{
		Album album = new Album();
		
		deserializePrimitives(album, data);
		album.setCover_images(new ImageDeserializer().deserializeArray(getJsonArray(data, "cover_images"), Image.class));
		
		return album;
	}
}
