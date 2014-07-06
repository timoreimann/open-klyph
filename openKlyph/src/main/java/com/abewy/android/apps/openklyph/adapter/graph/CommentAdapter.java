package com.abewy.android.apps.openklyph.adapter.graph;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import com.abewy.android.apps.openklyph.KlyphBundleExtras;
import com.abewy.android.apps.openklyph.R;
import com.abewy.android.apps.openklyph.adapter.KlyphAdapter;
import com.abewy.android.apps.openklyph.adapter.holder.CommentHolder;
import com.abewy.android.apps.openklyph.app.ImageActivity;
import com.abewy.android.apps.openklyph.core.graph.Comment;
import com.abewy.android.apps.openklyph.core.graph.Comment.Attachment;
import com.abewy.android.apps.openklyph.core.graph.Comment.Attachment.Media.Image;
import com.abewy.android.apps.openklyph.core.graph.Comment.Attachment.Target;
import com.abewy.android.apps.openklyph.core.graph.GraphObject;
import com.abewy.android.apps.openklyph.core.util.AttrUtil;
import com.abewy.android.apps.openklyph.util.DateUtil;
import com.abewy.android.apps.openklyph.util.KlyphUtil;
import com.abewy.android.apps.openklyph.util.TextViewUtil;
import com.abewy.android.apps.openklyph.widget.ProfileImageView;
import com.abewy.android.extended.widget.RatioImageView;
import com.abewy.util.Android;
import com.abewy.util.PhoneUtil;

public class CommentAdapter extends KlyphAdapter
{
	private int			placeHolder			= -1;
	private int			profilePlaceHolder	= -1;
	private final int	colorFilter			= 0xAA000000;

	public CommentAdapter()
	{
		super();
	}

	@Override
	protected int getLayout()
	{
		return R.layout.item_comment;
	}

	@Override
	protected void attachHolder(View view)
	{
		ImageView depthBar = (ImageView) view.findViewById(R.id.depth_bar);
		ImageView authorPicture = (ImageView) view.findViewById(R.id.author_picture);
		TextView authorName = (TextView) view.findViewById(R.id.author_name);
		TextView commentText = (TextView) view.findViewById(R.id.comment_text);
		ImageView commentImage = (ImageView) view.findViewById(R.id.comment_image);
		ImageView commentImagePlay = (ImageView) view.findViewById(R.id.comment_image_play);
		ImageView commentLinkImage = (ImageView) view.findViewById(R.id.comment_link_image);
		ImageView commentLinkImageBackground = (ImageView) view.findViewById(R.id.comment_link_image_background);
		TextView commentLinkName = (TextView) view.findViewById(R.id.comment_link_name);
		TextView commentLinkUrl = (TextView) view.findViewById(R.id.comment_link_url);
		TextView commentLinkDescription = (TextView) view.findViewById(R.id.comment_link_description);

		setHolder(view, new CommentHolder(depthBar, authorPicture, authorName, commentText, commentImage, commentImagePlay, commentLinkImage,
				commentLinkImageBackground, commentLinkName, commentLinkUrl, commentLinkDescription));
	}

	@Override
	protected void mergeViewWithData(View view, GraphObject data)
	{
		super.mergeViewWithData(view, data);

		Comment comment = (Comment) data;
		final CommentHolder holder = (CommentHolder) getHolder(view);

		holder.getDepthBar().setVisibility(comment.hasParentComment() ? View.VISIBLE : View.GONE);

		holder.getAuthorName().setAutoLinkMask(0);
		holder.getAuthorName().setText("");

		String name = comment.getFrom().getName();
		String time = DateUtil.timeAgoInWords(getContext(view), comment.getCreated_time());

		String text = name + "  " + time + "  ";
		holder.getAuthorName().setText(text);
		TextViewUtil.setElementClickable(getContext(view), holder.getAuthorName(), comment.getFrom().getName(), comment.getFrom().getId(), "user", false);

		Spannable styledText = new SpannableString(holder.getAuthorName().getText());
		TextAppearanceSpan span1 = new TextAppearanceSpan(getContext(holder.getAuthorName()), R.style.Klyph_CommentTextName);
		TextAppearanceSpan span2 = new TextAppearanceSpan(getContext(holder.getAuthorName()), R.style.Klyph_CommentTextTime);

		if (Android.isMinAPI(11))
			styledText.setSpan(span1, 0, name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

		styledText.setSpan(span2, name.length() + 2, name.length() + 2 + time.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		holder.getAuthorName().setText(styledText);

		String commentText = comment.getMessage();
		int numLikes = comment.getLike_count();
		if (numLikes > 0)
		{
			if (commentText.length() > 0)
				commentText += "  ";

			commentText += numLikes + "  ";
		}

		final SpannableStringBuilder sb = new SpannableStringBuilder();
		sb.append(commentText);

		if (numLikes > 0)
		{
			ImageSpan span3 = new ImageSpan(getContext(holder.getAuthorName()), AttrUtil.getResourceId(getContext(holder.getAuthorName()),
					comment.getUser_likes() ? R.attr.userLikeSmallIcon : R.attr.likeIconSmall), DynamicDrawableSpan.ALIGN_BASELINE);
			TextAppearanceSpan span4 = new TextAppearanceSpan(getContext(holder.getAuthorName()), R.style.Klyph_CommentTextTime);

			int likesLength = String.valueOf(numLikes).length();
			sb.setSpan(span3, commentText.length() - 2, commentText.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			sb.setSpan(span4, commentText.length() - (likesLength + 2), commentText.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}

		holder.getCommentText().setText(sb);

		if (placeHolder == -1)
			placeHolder = KlyphUtil.getPlaceHolder(view.getContext());

		if (profilePlaceHolder == -1)
			profilePlaceHolder = KlyphUtil.getProfilePlaceHolder(view.getContext());
		
		((ProfileImageView) holder.getAuthorPicture()).disableBorder();

		String url = comment.getFrom().getPicture().getUrl();
		loadImage(holder.getAuthorPicture(), url, profilePlaceHolder, data);

		holder.getCommentImage().setOnClickListener(null);
		holder.getCommentLinkImage().setOnClickListener(null);

		if (comment.getAttachment().isPhoto() || comment.getAttachment().isVideoShare())
		{
			Image image = comment.getAttachment().getMedia().getImage();
						
			RatioImageView rImageview = (RatioImageView) holder.getCommentImage();
			rImageview.setImageSize(image.getWidth(), image.getHeight());
			
			int parentWidth = ((ViewGroup) holder.getCommentImage().getParent()).getWidth();

			if (parentWidth > 0)
			{
				LayoutParams params = holder.getCommentImage().getLayoutParams();
				params.width = Math.min(parentWidth, image.getWidth());
				holder.getCommentImage().setLayoutParams(params);
			}
			
			loadImage(holder.getCommentImage(), image.getSrc(), placeHolder, data);

			final Target target = comment.getAttachment().getTarget();

			holder.getCommentLinkImage().setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v)
				{
					Intent intent = new Intent(getContext(holder.getCommentImage()), ImageActivity.class);
					intent.putExtra(KlyphBundleExtras.PHOTO_ID, target.getId());
					getContext(holder.getCommentLinkImage()).startActivity(intent);
				}
			});
		}
		else if (comment.getAttachment().isShare())
		{
			final Attachment att = comment.getAttachment();
			Image image = comment.getAttachment().getMedia().getImage();
			holder.getCommentLinkImageBackground().setColorFilter(colorFilter, PorterDuff.Mode.SRC_OVER);
			loadImage(holder.getCommentLinkImage(), image.getSrc(), placeHolder, data);
			loadImage(holder.getCommentLinkImageBackground(), image.getSrc(), placeHolder, data);
			holder.getCommentLinkName().setText(att.getTitle());
			holder.getCommentLinkUrl().setText(att.getUrl());
			holder.getCommentLinkDescription().setText(att.getDescription());

			holder.getCommentLinkImage().setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v)
				{
					PhoneUtil.openURL(getContext(holder.getCommentLinkImage()), att.getUrl());
				}
			});
		}

		if (comment.getAttachment().isVideoShare())
			holder.getCommentImagePlay().setVisibility(View.VISIBLE);
		else
			holder.getCommentImagePlay().setVisibility(View.GONE);

		((View) holder.getCommentImage().getParent())
				.setVisibility(comment.getAttachment().isPhoto() || comment.getAttachment().isVideoShare() ? View.VISIBLE : View.GONE);
		((View) holder.getCommentLinkImage().getParent()).setVisibility(comment.getAttachment().isShare() ? View.VISIBLE : View.GONE);
	}
}