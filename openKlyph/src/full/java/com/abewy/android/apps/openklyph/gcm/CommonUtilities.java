package com.abewy.android.apps.openklyph.gcm;

public final class CommonUtilities
{

	// give your server registration url here
	private static final String	SERVER_URL				= "http://www.yourserver.com/gcm-server/";
	public static final String			REGISTER_URL			= SERVER_URL + "register.php";
	public static final String			UNREGISTER_URL			= SERVER_URL + "unregister.php";

	// Google project id
	public static final String	SENDER_ID				= "[SENDER_ID]";

	/**
	 * Tag used on log messages.
	 */
	static final String			TAG						= "Klyph GCM";

	static final String			DISPLAY_MESSAGE_ACTION	= "com.abewy.android.apps.openklyph.gcm.DISPLAY_MESSAGE";

	static final String			EXTRA_MESSAGE			= "message";
}
