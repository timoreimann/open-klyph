package com.abewy.android.apps.openklyph.core.graph.serializer;

import org.json.JSONObject;
import com.abewy.android.apps.openklyph.core.graph.GraphObject;
import com.abewy.android.apps.openklyph.core.graph.Place;
import com.abewy.android.apps.openklyph.core.graph.Place.Location;

public class PlaceDeserializer extends Deserializer
{
	@Override
	public GraphObject deserializeObject(JSONObject data)
	{
		Place place = new Place();

		deserializePrimitives(place, data);

		place.setLocation((Location) new LocationDeserializer().deserializeObject(getJsonObject(data, "location")));
		
		return place;
	}

	private static class LocationDeserializer extends Deserializer
	{
		@Override
		public GraphObject deserializeObject(JSONObject data)
		{
			Location location = new Location();

			deserializePrimitives(location, data);

			return location;
		}
	}
}
