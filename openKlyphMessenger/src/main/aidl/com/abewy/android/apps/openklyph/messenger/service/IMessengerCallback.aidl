package com.abewy.android.apps.openklyph.messenger.service;

import com.abewy.android.apps.openklyph.messenger.service.PPresence;
import com.abewy.android.apps.openklyph.messenger.service.PRosterEntry;

oneway interface IMessengerCallback {
	
	void onPresenceChange(out PPresence presence);
	void onRosterUpdated(out List<PRosterEntry> roster);
}
