package com.abewy.android.apps.openklyph.facebook.request.base;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import com.abewy.android.apps.openklyph.Klyph;
import com.abewy.android.apps.openklyph.core.fql.serializer.StreamDeserializer;
import com.abewy.android.apps.openklyph.core.graph.GraphObject;
import com.abewy.android.apps.openklyph.facebook.request.KlyphQuery;

public class ElementTimelineRequest extends KlyphQuery
{
	@Override
	public boolean isMultiQuery()
	{
		return true;
	}
	
	protected boolean isNewest()
	{
		return false;
	}

	@Override
	public String getQuery(String id, String offset)
	{
		String query1 = "SELECT post_id, source_id, actor_id, target_id, app_id, created_time, message, message_tags, ";
		query1 += "attachment, description, description_tags, type, privacy, is_hidden, parent_post_id, place, permalink, comment_info, ";
		query1 += "like_info, action_links, tagged_ids, app_data FROM stream WHERE ";

		if (offset != null && offset.length() > 0)
			query1 += "created_time " + (isNewest() ? "> " : "< ") + offset + " AND ";

		query1 += getWhereCondition(id);
		
		//Get sub newsfeed
		String query2 = "SELECT post_id, source_id, actor_id, target_id, app_id, created_time, message, message_tags, attachment, description, ";
		query2 += "description_tags, type, privacy, parent_post_id, place, permalink, comment_info, like_info, action_links, tagged_ids, ";
		query2 += "app_data FROM stream WHERE post_id IN (SELECT parent_post_id FROM #query1 WHERE type = 257 OR type = 245)";
		
		// Get liked links
		String query3 = "SELECT caption, comment_info, created_time, image_urls, like_info, link_id, owner, owner_comment, picture, summary, title, url, via_id "
				+ "FROM link WHERE link_id IN "
				+ "(SELECT substr(post_id, strpos(post_id, \"_\") + 1, strlen(post_id)) FROM #query1)";

		// Get liked photos
		String query4 = "SELECT caption, caption_tags, comment_info, created, images, like_info, modified, object_id, owner, page_story_id, pid, place_id, "
				+ "src, src_big, src_big_height, src_big_width, src_small, src_small_height, src_small_width, src_width, target_id, target_type "
				+ "FROM photo WHERE object_id IN "
				+ "(SELECT substr(post_id, strpos(post_id, \"_\") + 1, strlen(post_id)) from #query1)";

		// Get Liked videos
		String query5 = "SELECT created_time, description, format, length, link, owner, src, src_hq, thumbnail_link, title, updated_time, vid"
				+ " FROM video WHERE vid IN "
				+ "(SELECT substr(post_id, strpos(post_id, \"_\") + 1, strlen(post_id)) from #query1)"
				+ "OR vid IN (SELECT attachment.fb_object_id FROM #query1 "
				+ "WHERE attachment.fb_object_type = \"video\") ";

		// Get shared status
		String query6 = "SELECT comment_info, like_info, message, place_id, source, status_id, time, uid"
					+ " FROM status WHERE status_id IN (SELECT substr(post_id, strpos(post_id, \"_\") + 1, strlen(post_id)) FROM #query1 WHERE type = 257) OR status_id IN "
					+ "(SELECT substr(attachment.href, strpos(attachment.href, \"posts/\") + 6, strlen(attachment.href)) FROM #query1 WHERE strlen(attachment.href) > 0)";

		// Get events
		String query7 = "SELECT eid, name, description, start_time, pic_big, pic_cover "
				+ "FROM event WHERE eid IN "
				+ "(SELECT substr(post_id, strpos(post_id, \"_\") + 1, strlen(post_id)) FROM #query1)"
				+ " OR eid IN (SELECT substr(substr(attachment.media.href, strpos(attachment.media.href, \"events/\") + 7, " +
				"strlen(attachment.media.href)), 0, strlen(substr(attachment.media.href, strpos(attachment.media.href, \"events/\") + 7, " +
				"strlen(attachment.media.href))) - 1) FROM #query1 WHERE (attachment.media.href) > 0)";

		// Get source/target users and pages
		String query8 = "SELECT id, name, type from profile "
				+ "WHERE id IN (SELECT actor_id FROM #query1) "
				+ "OR id IN (SELECT target_id FROM #query1) "
				+ "OR id IN (SELECT tagged_ids FROM #query1) "
				+ "OR id IN (SELECT actor_id FROM #query2) "
				+ "OR id IN (SELECT target_id FROM #query2) "
				+ "OR id IN (SELECT tagged_ids FROM #query2) "
				+ "OR id IN (SELECT owner FROM #query3) "
				+ "OR id IN (SELECT via_id FROM #query3) "
				+ "OR id IN (SELECT owner FROM #query4)"
				+ "OR id IN (SELECT target_id FROM #query4)"
				+ "OR id IN (SELECT owner FROM #query5)"
				+ "OR id IN (SELECT uid FROM #query6)";

		// Get liked pages
		String query9 = "SELECT page_id, name, about, description, pic_cover FROM page "
				+ "WHERE page_id IN (SELECT description_tags.id FROM #query1) OR page_id IN (SELECT id FROM #query8)";

		// Get profile pics
		String query10 = "SELECT id, url FROM square_profile_pic "
				+ "WHERE (id IN (SELECT actor_id FROM #query1) "
				+ "OR id IN (SELECT actor_id FROM #query2) "
				+ "OR id IN (SELECT owner FROM #query3) "
				+ "OR id IN (SELECT owner FROM #query4) "
				+ "OR id IN (SELECT owner FROM #query5) "
				+ "OR id IN (SELECT uid FROM #query6) "
				+ "OR id IN (SELECT page_id FROM #query9)) " + "AND size = "
				+ Klyph.getStandardImageSizeForRequest();

		// Get places
		String query11 = "SELECT page_id, name FROM place "
				+ "WHERE page_id IN (SELECT place FROM #query1)"
				+ "OR page_id IN (SELECT place_id FROM #query4)"
				+ "OR page_id IN (SELECT place_id FROM #query6)";
		
		// Get Apps
		String query12 = "SELECT app_id, app_name, app_type, appcenter_icon_url, category, company_name, description, display_name, icon_url, link, logo_url, subcategory" +
				" FROM application" +
				" WHERE app_id IN (SELECT app_id FROM #query1)";
		
		List<String> queries = new ArrayList<String>();
		queries.add(query1);
		queries.add(query2);
		queries.add(query3);
		queries.add(query4);
		queries.add(query5);
		queries.add(query6);
		queries.add(query7);
		queries.add(query8);
		queries.add(query9);
		queries.add(query10);
		queries.add(query11);
		queries.add(query12);
		
		queries.addAll(getAdditionalQueries(id, offset));
		
		return multiQuery(queries.toArray(new String[queries.size()]));
	}

	protected String getWhereCondition(String id)
	{
		return " source_id = "
				+ id
				+ " AND is_hidden = 0 AND strlen(parent_post_id) = 0 ORDER BY created_time DESC LIMIT 30";
	}

	protected List<String> getAdditionalQueries(String id, String offset)
	{
		return new ArrayList<String>();
	}

	@Override
	public ArrayList<GraphObject> handleResult(JSONArray[] result)
	{
		JSONArray data = result[0];
		JSONArray data2 = result[1];
		JSONArray links = result[2];
		JSONArray photos = result[3];
		JSONArray videos = result[4];
		JSONArray status = result[5];
		JSONArray events = result[6];
		JSONArray profiles = result[7];
		JSONArray pages = result[8];
		JSONArray pics = result[9];
		JSONArray places = result[10];
		JSONArray apps = result[11];

		assocData2(links, profiles, "owner", "id", "owner_name", "name",
				"owner_type", "type");
		assocData2(links, profiles, "via_id", "id", "via_name", "name",
				"via_type", "type");
		assocData(links, pics, "owner", "id", "owner_pic", "url");

		assocData2(photos, profiles, "owner", "id", "owner_name", "name",
				"owner_type", "type");
		assocData2(photos, profiles, "target", "id", "target_name", "name",
				"target_type", "type");
		assocData(photos, pics, "owner", "id", "owner_pic", "url");
		assocData(photos, places, "place", "page_id", "place_name", "name");

		assocData2(videos, profiles, "owner", "id", "owner_name", "name",
				"owner_type", "type");
		assocData(videos, pics, "owner", "id", "owner_pic", "url");

		assocData2(status, profiles, "uid", "id", "uid_name", "name",
				"uid_type", "type");
		assocData(status, pics, "uid", "id", "uid_pic", "url");
		assocData(status, places, "place_id", "page_id", "place_name", "name");

		assocData(pages, pics, "page_id", "id", "pic", "url");

		assocData2(data, profiles, "actor_id", "id", "actor_name", "name",
				"actor_type", "type");
		assocData2(data, profiles, "target_id", "id", "target_name", "name",
				"target_type", "type");
		assocData2(data2, profiles, "actor_id", "id", "actor_name", "name",
				"actor_type", "type");
		assocData2(data2, profiles, "target_id", "id", "target_name", "name",
				"target_type", "type");
		
		assocData(data, pics, "actor_id", "id", "actor_pic", "url");
		assocData(data2, pics, "actor_id", "id", "actor_pic", "url");
		assocData(data, places, "place", "page_id", "place_name", "name");
		assocData3(data, profiles, "tagged_ids", "id", "tagged_tags");
		assocStreamToEvent(data, events);
		assocStreamToLikedPages(data, pages);
		assocStreamToObjectBySubPostId(data, links, "link_id", "link");
		assocStreamToObjectBySubPostId(data, photos, "object_id", "photo");
		assocStreamToObjectBySubPostId(data, videos, "vid", "video");
		assocStreamToStatus(data, status);
		assocStreamToObjectById(data, apps, "app_id", "app_id", "application");
		assocStreamToObjectById(data, data2, "parent_post_id", "post_id", "parent_stream");

		StreamDeserializer deserializer = new StreamDeserializer();
		ArrayList<GraphObject> streams = (ArrayList<GraphObject>) deserializer
				.deserializeArray(data);

		return streams;
	}
}
