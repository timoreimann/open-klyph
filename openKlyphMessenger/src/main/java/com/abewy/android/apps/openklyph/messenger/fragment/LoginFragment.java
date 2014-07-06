package com.abewy.android.apps.openklyph.messenger.fragment;

import java.util.Arrays;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.abewy.android.apps.openklyph.core.KlyphFlags;
import com.abewy.android.apps.openklyph.core.KlyphSession;
import com.abewy.android.apps.openklyph.core.fql.User;
import com.abewy.android.apps.openklyph.messenger.R;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

public class LoginFragment extends Fragment
{
	private LoginButton				authButton;
	private ProgressBar				progressBar;

	private UiLifecycleHelper		uiHelper;
	private Session.StatusCallback	callback	= new Session.StatusCallback() {
													@Override
													public void call(Session session, SessionState state, Exception exception)
													{
														onSessionStateChange(session, state, exception);
													}
												};

	public interface LoginFragmentCallBack
	{
		public void onUserInfoFetched(User user);
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(getActivity(), callback);
		uiHelper.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_login, container, false);

		authButton = (LoginButton) view.findViewById(R.id.authButton);
		progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
		TextView tv = (TextView) view.findViewById(R.id.app_name_textview);
		tv.setText(KlyphFlags.IS_PRO_VERSION == true ? R.string.app_pro_large_name : R.string.app_large_name);

		authButton.setReadPermissions(Arrays.asList(
		"user_online_presence", "friends_online_presence", "read_mailbox", "xmpp_login", "email", "user_friends", "user_photos", "friends_photos"));

		authButton.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {

			@Override
			public void onUserInfoFetched(GraphUser user)
			{
				if (Session.getActiveSession().isOpened() && user != null)
				{

					User u = new User();
					u.setUid(user.getId());
					u.setName(user.getName());
					u.setFirst_name(user.getFirstName());
					u.setMiddle_name(user.getMiddleName());
					u.setLast_name(user.getLastName());
					u.setBirthday(user.getBirthday());
					u.setEmail((String) user.getProperty("email"));
					u.setSex((String) user.getProperty("gender"));
					// u.setIn((String)
					// user.getProperty("interested_in"));
					u.setLocale((String) user.getProperty("locale"));
					u.setRelationship_status((String) user.getProperty("relationship_status"));
					u.setUsername((String) user.getProperty("username"));

					try
					{
						u.setTimezone(((Double) user.getProperty("timezone")).intValue());
					}
					catch (Exception e)
					{}

					KlyphSession.setSessionUser(u);

					if (getActivity() != null)
					{
						((LoginFragmentCallBack) getActivity()).onUserInfoFetched(u);
					}
				}
			}
		});

		authButton.setFragment(this);

		return view;
	}

	private void onSessionStateChange(Session session, SessionState state, Exception exception)
	{
		if (getView() != null)
		{
			if (state.isOpened())
			{
				authButton.setVisibility(View.GONE);
				progressBar.setVisibility(View.VISIBLE);
			}
			else if (state.isClosed())
			{
				authButton.setVisibility(View.VISIBLE);
				progressBar.setVisibility(View.GONE);
			}
		}
	}

	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);

		if (!(activity instanceof LoginFragmentCallBack))
		{
			throw new Error("Activity must implements LoginFragmentCallBack");
		}
	}

	@Override
	public void onResume()
	{
		super.onResume();

		Session session = Session.getActiveSession();
		if (session != null && (session.isOpened() || session.isClosed()))
		{
			onSessionStateChange(session, session.getState(), null);
		}

		uiHelper.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onPause()
	{
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		uiHelper.onDestroy();
		uiHelper = null;
		authButton = null;
		progressBar = null;
		callback = null;
	}

	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}
}
