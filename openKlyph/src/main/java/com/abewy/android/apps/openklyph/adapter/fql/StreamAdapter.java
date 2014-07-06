package com.abewy.android.apps.openklyph.adapter.fql;

import it.sephiroth.android.library.widget.HListView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.abewy.android.apps.openklyph.KlyphPreferences;
import com.abewy.android.apps.openklyph.R;
import com.abewy.android.apps.openklyph.adapter.KlyphAdapter;
import com.abewy.android.apps.openklyph.adapter.MultiObjectAdapter;
import com.abewy.android.apps.openklyph.adapter.holder.StreamHolder;
import com.abewy.android.apps.openklyph.adapter.subAdapter.StreamAlbum;
import com.abewy.android.apps.openklyph.adapter.subAdapter.StreamButtonBar;
import com.abewy.android.apps.openklyph.adapter.subAdapter.StreamHeader;
import com.abewy.android.apps.openklyph.adapter.subAdapter.StreamLink;
import com.abewy.android.apps.openklyph.adapter.subAdapter.StreamMessage;
import com.abewy.android.apps.openklyph.adapter.subAdapter.StreamPhoto;
import com.abewy.android.apps.openklyph.adapter.subAdapter.StreamStatus;
import com.abewy.android.apps.openklyph.core.fql.Attachment;
import com.abewy.android.apps.openklyph.core.fql.Media;
import com.abewy.android.apps.openklyph.core.fql.Stream;
import com.abewy.android.apps.openklyph.core.graph.GraphObject;
import com.abewy.android.apps.openklyph.util.TextViewUtil;

public class StreamAdapter extends KlyphAdapter
{
	private final MultiObjectAdapter parentAdapter;
	private final int specialLayout;
	private StreamHeader	headerAdapter;
	private StreamMessage	messageAdapter;
	private StreamButtonBar	buttonBarAdapter;
	private StreamAlbum		albumAdapter;
	private StreamPhoto		photoAdapter;
	private StreamLink		linkAdapter;
	private StreamStatus	statusAdapter;

	public StreamAdapter(MultiObjectAdapter parentAdapter, int specialLayout)
	{
		super();
		this.parentAdapter = parentAdapter;
		this.specialLayout = specialLayout;

		headerAdapter = new StreamHeader(specialLayout);
		messageAdapter = new StreamMessage(false);
		albumAdapter = new StreamAlbum();
	}

	@Override
	protected int getLayout()
	{
		return R.layout.item_stream;
	}
	
	@Override
	public boolean  isEnabled(GraphObject object)
	{
		Stream stream = (Stream) object;
		return stream.getComment_info().getCan_comment() || stream.getLike_info().getCan_like();
	}

	@Override
	protected void attachHolder(View view)
	{
		ImageView authorProfileImage = (ImageView) view.findViewById(R.id.author_profile_image);
		TextView story = (TextView) view.findViewById(R.id.story);
		TextView postTime = (TextView) view.findViewById(R.id.post_time);
		ImageView sharedAuthorProfileImage = (ImageView) view.findViewById(R.id.shared_author_profile_image);
		TextView sharedStory = (TextView) view.findViewById(R.id.shared_story);
		TextView sharedPostTime = (TextView) view.findViewById(R.id.shared_post_time);
		TextView message = (TextView) view.findViewById(R.id.message);
		ImageView postPhoto = (ImageView) view.findViewById(R.id.post_photo);
		ImageView postVideoPlay = (ImageView) view.findViewById(R.id.post_video_play);
		TextView videoTitle = (TextView) view.findViewById(R.id.post_video_title);
		TextView videoUrl = (TextView) view.findViewById(R.id.post_video_url);
		ImageView postPicturePlay = (ImageView) view.findViewById(R.id.post_picture_play);
		ImageView postLinkBackground = (ImageView) view.findViewById(R.id.stream_link_image_background);
		TextView postName = (TextView) view.findViewById(R.id.post_name);
		TextView postCaption = (TextView) view.findViewById(R.id.post_caption);
		TextView postDescription = (TextView) view.findViewById(R.id.post_description);
		Button likeButton = (Button) view.findViewById(R.id.like_button);
		Button commentButton = (Button) view.findViewById(R.id.comment_button);
		ImageButton shareButton = (ImageButton) view.findViewById(R.id.share_button);
		ImageButton overflowButton = (ImageButton) view.findViewById(R.id.overflow_button);
		HListView streamAlbum = (HListView) view.findViewById(R.id.stream_album);
		ViewGroup streamLink = (ViewGroup) view.findViewById(R.id.stream_link);
		ViewGroup buttonBar = (ViewGroup) view.findViewById(R.id.button_bar);
		View buttonBarDivider = (View) view.findViewById(R.id.button_bar_divider);

		StreamHolder holder = new StreamHolder(authorProfileImage, story,
				postTime, sharedAuthorProfileImage, sharedStory,
				sharedPostTime, message, postPhoto, postVideoPlay,
				videoTitle, videoUrl, postPicturePlay, postLinkBackground, postName, postCaption,
				postDescription, likeButton, commentButton, shareButton, overflowButton, streamAlbum, streamLink, buttonBar,
				buttonBarDivider);

		setHolder(view, holder);
	}
	
	@Override
	protected void mergeViewWithData(View view, GraphObject data)
	{
		//GraphObject previousData = getData(view);
		
		/*if (previousData != null)
		{
			if (((Stream) previousData).getPost_id().equals(((Stream) data).getPost_id()))
			{
				return;
			}
		}*/
		
		super.mergeViewWithData(view, data);

		// getParentAdapter() = null on constructor
		if (buttonBarAdapter == null)
			buttonBarAdapter = new StreamButtonBar(parentAdapter, specialLayout);
		
		if (photoAdapter == null)
			photoAdapter = new StreamPhoto(parentAdapter, specialLayout);
		
		if (linkAdapter == null)
			linkAdapter = new StreamLink(parentAdapter, specialLayout);
		
		if (statusAdapter == null)
			statusAdapter = new StreamStatus(parentAdapter, specialLayout);

		final StreamHolder holder = (StreamHolder) getHolder(view);

		setData(view, data);

		setData(view, holder, (Stream) data);
	}

	public void setData(View view, StreamHolder holder, Stream stream)
	{
		//holder.getStory().setAutoLinkMask(0);
		//holder.getPostName().setAutoLinkMask(0);
		//holder.getPostCaption().setAutoLinkMask(Linkify.WEB_URLS);
		//holder.getPostDescription().setAutoLinkMask(Linkify.WEB_URLS);

		((ViewGroup) holder.getSharedAuthorProfileImage().getParent().getParent()).setVisibility(View.GONE);
		holder.getSharedAuthorProfileImage().setVisibility(View.GONE);
		holder.getSharedStory().setVisibility(View.GONE);
		holder.getSharedPostTime().setVisibility(View.GONE);
		holder.getPostPhoto().setVisibility(View.GONE);
		holder.getPostVideoPlay().setVisibility(View.GONE);
		holder.getVideoTitle().setVisibility(View.GONE);
		holder.getVideoUrl().setVisibility(View.GONE);
		((ViewGroup) holder.getVideoTitle().getParent()).setVisibility(View.GONE);
		((ViewGroup) holder.getPostPhoto().getParent()).setVisibility(View.GONE);
		holder.getMessage().setVisibility(View.GONE);
		holder.getStreamAlbum().setVisibility(View.GONE);
		holder.getStreamLink().setVisibility(View.GONE);
		holder.getButtonBar().setVisibility(View.GONE);
		holder.getPostDescription().setVisibility(View.GONE);
		holder.getPostCaption().setVisibility(View.GONE);
		holder.getPostName().setVisibility(View.GONE);
		holder.getPostPicturePlay().setVisibility(View.GONE);
		holder.getButtonBarDivider().setVisibility(View.GONE);
		
		holder.getMessage().setMaxLines(KlyphPreferences.getNewsfeedCutLongPost() ? 10 : Integer.MAX_VALUE);

		headerAdapter.mergeData(holder, stream);

		messageAdapter.mergeData(holder, stream);

		manageAttachment(view, holder, stream);

		if (holder.getPostPhoto().getVisibility() == View.GONE && holder.getStreamAlbum().getVisibility() == View.GONE
			&& holder.getStreamLink().getVisibility() == View.GONE)
			holder.getButtonBarDivider().setVisibility(View.VISIBLE);
	}

	private void manageAttachment(View view, StreamHolder holder, Stream stream)
	{
		Attachment attachment = stream.getAttachment();
		Media media = attachment.getMedia().size() > 0 ? attachment.getMedia().get(0) : null;
		
		int type = stream.getType();
		if ((type == 245 || type == 257) && stream.getParent_stream() != null)
		{
			final Stream parentStream = stream.getParent_stream();
			headerAdapter.mergeData(holder, parentStream, true);
			messageAdapter.mergeData(holder, parentStream);
			
			buttonBarAdapter.mergeData(holder, stream);
		}
		
		if (type == 161 && stream.getDescription_tags().size() > 0 && stream.getLiked_pages().size() > 0)
		{
			linkAdapter.manageLikedPage(holder, stream);
		}
		if (stream.isStatus())
		{
			statusAdapter.mergeData(holder, stream);
		}
		else if (stream.isPhoto())
		{
			photoAdapter.mergeData(holder, stream, stream.getPhoto());
		}
		else if (stream.isVideo())
		{
			photoAdapter.mergeData(holder, stream, stream.getVideo());
		}
		else if (attachment.isPhoto() || ((media != null && media.isFydv())))
		{
			photoAdapter.mergeData(holder, stream);
			buttonBarAdapter.mergeData(holder, stream);
		}
		else if (attachment.isAlbum())
		{
			albumAdapter.mergeData(holder, stream);
			buttonBarAdapter.mergeData(holder, stream);
		}
		else if (attachment.isCheckin())
		{
			manageAttachmentCheckin(view, holder, stream);
			buttonBarAdapter.mergeData(holder, stream);
		}
		else if (stream.getLink().isEventLink())
		{
			linkAdapter.mergeData(holder, stream, stream.getLink());
		}
		else if (type == 161 || attachment.getMedia().size() > 0)
		{
			linkAdapter.mergeData(holder, stream);
			buttonBarAdapter.mergeData(holder, stream);
		}
		else if (stream.isLink())
		{
			linkAdapter.mergeData(holder, stream, stream.getLink());
		}
		else 
		{
			buttonBarAdapter.mergeData(holder, stream);
		}
	}

	private void manageAttachmentCheckin(View view, StreamHolder holder, Stream stream)
	{
		if (stream.getDescription().length() == 0)
		{
			Attachment attachment = stream.getAttachment();

			holder.getStory().setText(attachment.getCaption());
			TextViewUtil.setElementClickable(getContext(view), holder.getStory(), stream.getActor_name(),
					stream.getActor_id(), "user", false);
			TextViewUtil.setElementClickable(getContext(view), holder.getStory(), attachment.getName(), stream.getPlace(),
					"page", false);
		}

		/*
		 * Tag tag = new Tag(); tag.setName(attachment.getName());
		 * tag.setId(attachment.getFb_checkin().getPage_id());
		 * tag.setOffset(attachment.getCaption().indexOf(attachment.getName()));
		 * tag.setLength(attachment.getName().length()); ArrayList<Tag> tags =
		 * new ArrayList<Tag>(); tags.add(tag);
		 */

		// addClickableTextForTags(holder.getStory(), attachment.getCaption(),
		// tags);
	}
}
