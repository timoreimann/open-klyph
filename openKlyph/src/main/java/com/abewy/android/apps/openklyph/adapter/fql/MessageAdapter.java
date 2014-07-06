package com.abewy.android.apps.openklyph.adapter.fql;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.abewy.android.apps.openklyph.R;
import com.abewy.android.apps.openklyph.adapter.KlyphAdapter;
import com.abewy.android.apps.openklyph.adapter.holder.MessageHolder;
import com.abewy.android.apps.openklyph.core.fql.Message;
import com.abewy.android.apps.openklyph.core.graph.GraphObject;
import com.abewy.android.apps.openklyph.core.imageloader.ImageLoader;
import com.abewy.android.apps.openklyph.util.DateUtil;
import com.abewy.android.apps.openklyph.util.EmojiUtil;
import com.abewy.android.apps.openklyph.util.KlyphUtil;

public class MessageAdapter extends KlyphAdapter
{
	public MessageAdapter()
	{
		super();
	}

	@Override
	protected int getLayoutRes()
	{
		return R.layout.item_conversation_friend;
	}

	@Override
	protected void attachViewHolder(View view)
	{
		ImageView authorPicture = (ImageView) view.findViewById(R.id.message_author_picture);
		TextView messageTV = (TextView) view.findViewById(R.id.message_body);
		TextView date = (TextView) view.findViewById(R.id.message_date);

		setHolder(view, new MessageHolder(authorPicture, messageTV, date));
	}

	@Override
	public void bindData(View view, GraphObject data)
	{
		MessageHolder holder = (MessageHolder) getHolder(view);
		
		Message message = (Message) data;

		holder.getMessageTextView().setText(EmojiUtil.getSpannableForText(holder.getMessageTextView().getContext(), message.getBody()));
		holder.getDateTextView().setText(DateUtil.getShortDateTime(message.getCreated_time()));
		
		//TextViewUtil.setElementClickable(getContext(view), holder.getAuthorName(), message.getAuthor_name(), message.getAuthor_id(), "user");
		
		ImageLoader.display(holder.getAuthorPicture(), message.getAuthor_pic(), KlyphUtil.getPlaceHolder(holder.getAuthorPicture().getContext()));
	}
}
