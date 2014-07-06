package com.abewy.android.apps.openklyph.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.abewy.android.apps.openklyph.KlyphBundleExtras;
import com.abewy.android.apps.openklyph.app.AlbumActivity;
import com.abewy.android.apps.openklyph.app.EventActivity;
import com.abewy.android.apps.openklyph.app.PageActivity;
import com.abewy.android.apps.openklyph.app.UserActivity;
import com.abewy.android.apps.openklyph.core.fql.Tag;
import com.abewy.android.apps.openklyph.core.graph.GraphType;
import com.abewy.android.apps.openklyph.fragment.UserListDialog;
import com.abewy.android.apps.openklyph.text.LinkMovementMethod;

public class TextViewUtil
{
	public static interface TagCallback
	{
		public void onTagClick(List<Tag> tags);
	}

	public static void setElementClickable(Context context, TextView textView, String elementName, String elementId, String elementType,
			boolean clickable)
	{
		setElementClickable(context, textView, elementName, elementId, elementType, null, clickable);
	}

	/**
	 * Make a substring clickable and associated a tag with values elementName, elementId and elementType
	 * If callback is null, TextViewUtil.onTagClick is called
	 */
	public static void setElementClickable(Context context, TextView textView, String elementName, String elementId, String elementType,
			TagCallback callback, boolean clickable)
	{
		String text = textView.getText().toString();

		int offset = text.indexOf(elementName);

		if (offset == -1)
		{
			Log.e("TextViewUtil", "setElementClickable : element name not in text TextView : [" + elementName + "] in " + text);
		}
		else
		{
			Tag tag = new Tag();
			tag.setId(elementId);
			tag.setName(elementName);
			tag.setType(elementType);
			tag.setOffset(offset);
			tag.setLength(elementName.length());

			Map<String, List<Tag>> map = new HashMap<String, List<Tag>>();
			List<Tag> tags = new ArrayList<Tag>();
			tags.add(tag);
			map.put(String.valueOf(offset), tags);

			setTextClickableForTags(context, textView, map, callback, clickable);
		}
	}

	public static void setElementClickable(Context context, TextView textView, String elementName, List<Tag> tags, boolean clickable)
	{
		setElementClickable(context, textView, elementName, tags, null, clickable);
	}

	/**
	 * Make a substring clickable and associated with a list of tags
	 * If callback is null, TextViewUtil.onTagClick is called
	 */
	public static void setElementClickable(Context context, TextView textView, String elementName, List<Tag> tags, TagCallback callback,
			boolean clickable)
	{
		String text = textView.getText().toString();

		int offset = text.indexOf(elementName);

		if (offset == -1)
		{
			Log.e("TextViewUtil", "setElementClickable : element name not in text TextView : [" + elementName + "] in " + text);
		}
		else
		{
			Tag tag = tags.get(0);
			tag.setOffset(offset);
			tag.setLength(elementName.length());

			Map<String, List<Tag>> map = new HashMap<String, List<Tag>>();
			map.put(String.valueOf(offset), tags);

			setTextClickableForTags(context, textView, map, callback, clickable);
		}
	}

	public static void setTextClickableForTags(final Context context, TextView textView, Map<String, List<Tag>> tags, boolean clickable)
	{
		setTextClickableForTags(context, textView, tags, null, clickable);
	}

	/**
	 * Make a clickable for the tags in parameters
	 * If callback is null, TextViewUtil.onTagClick is called
	 */
	public static void setTextClickableForTags(final Context context, TextView textView, Map<String, List<Tag>> tags, final TagCallback callback,
			boolean clickable)
	{
		SpannableStringBuilder strBuilder = new SpannableStringBuilder(textView.getText());

		for (final List<Tag> tagList : tags.values())
		{
			if (tagList.size() > 0)
			{
				CharacterStyle span;

				if (clickable)
				{
					span = new ClickableSpan() {

						@Override
						public void onClick(View widget)
						{
							if (callback != null)
								callback.onTagClick(tagList);
							else
								onTagClick(context, tagList);
						}

						@Override
						public void updateDrawState(TextPaint ds)
						{
							super.updateDrawState(ds);
							ds.setUnderlineText(false);
							ds.setFakeBoldText(true);
						}
					};
				}
				else
				{
					span = new StyleSpan(Typeface.BOLD);
				}

				Tag tag = tagList.get(0);
				strBuilder.setSpan(span, tag.getOffset(), tag.getOffset() + tag.getLength(), 0);
			}
		}

		textView.setText(strBuilder);
		textView.setMovementMethod(LinkMovementMethod.getInstance());
	}

	/**
	 * Handle a tag click generated by one of the method of this class.
	 * Opens the activity/dialog assotiated with the type of tag :
	 * <ul>
	 * <li>User : UserActivity</li>
	 * <li>Page : PageActivity</li>
	 * <li>Event : EventActivity</li>
	 * <li>Album : AlbumActivity</li>
	 * <li>Several tags : UserListDialog</li>
	 */
	public static void onTagClick(Context context, List<Tag> tags)
	{
		if (tags.size() == 1)
		{
			Tag tag = tags.get(0);
			String type = tag.getType();

			Intent intent = null;

			if (type.equals(GraphType.FQL_USER.name()) || type.equals("user"))
			{
				intent = new Intent(context, UserActivity.class);
				intent.putExtra(KlyphBundleExtras.USER_ID, tag.getId());
				intent.putExtra(KlyphBundleExtras.USER_NAME, tag.getName());
			}
			else if (type.equals(GraphType.FQL_PAGE.name()) || type.equals("page"))
			{
				intent = new Intent(context, PageActivity.class);
				intent.putExtra(KlyphBundleExtras.PAGE_ID, tag.getId());
				intent.putExtra(KlyphBundleExtras.PAGE_NAME, tag.getName());
			}
			else if (type.equals(GraphType.FQL_EVENT.name()) || type.equals("event"))
			{
				intent = new Intent(context, EventActivity.class);
				intent.putExtra(KlyphBundleExtras.EVENT_ID, tag.getId());
				intent.putExtra(KlyphBundleExtras.EVENT_NAME, tag.getName());
			}
			else if (type.equals(GraphType.FQL_ALBUM.name()))
			{
				intent = new Intent(context, AlbumActivity.class);
				intent.putExtra(KlyphBundleExtras.ALBUM_ID, tag.getId());
				intent.putExtra(KlyphBundleExtras.ALBUM_NAME, tag.getName());
			}

			if (intent != null)
			{
				context.startActivity(intent);
			}
			else
			{
				Log.e("TextViewUtil", "Click on an unlisted type : " + type);
			}
		}
		else
		{
			UserListDialog uld = new UserListDialog(false);
			uld.loadList(tags);
			uld.show(((FragmentActivity) context).getFragmentManager(), "userlist");
		}
	}
}