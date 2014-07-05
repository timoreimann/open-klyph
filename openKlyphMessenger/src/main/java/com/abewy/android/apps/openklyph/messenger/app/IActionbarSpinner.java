/**
* @author Jonathan
*/

package com.abewy.android.apps.openklyph.messenger.app;

import android.app.ActionBar.OnNavigationListener;

public interface IActionbarSpinner
{
	public void displaySpinnerInActionBar(int array, int position, OnNavigationListener listener);
	public void removeSpinnerInActionBar();
}
