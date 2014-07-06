package com.abewy.android.apps.openklyph.app;

import android.os.Bundle;
import com.abewy.android.apps.openklyph.KlyphApplication;
import com.abewy.android.apps.openklyph.KlyphBundleExtras;
import com.abewy.android.apps.openklyph.R;
import com.abewy.android.apps.openklyph.fragment.ConversationFragment;

public class MessageActivity extends TitledFragmentActivity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// Let the Application class know that the first launch is complete
		// If we come from a notification, then do not show the ads
		// When going back to main activity
		KlyphApplication.getInstance().launchComplete();
		
		String id = getIntent().getStringExtra(KlyphBundleExtras.THREAD_ID);
		//String name = getIntent().getStringExtra(KlyphBundleExtras.THREAD_NAME);

		//setTitle(name);
		
		ConversationFragment fragment = (ConversationFragment) getFragmentManager().findFragmentById(R.id.conversation_fragment);
		fragment.setElementId(id);
		fragment.load();
	}

	@Override
	protected int getLayout()
	{
		return R.layout.activity_message;
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
	}
}
