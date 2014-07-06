/**
 * @author Jonathan
 */

package com.abewy.android.apps.openklyph.app;

import java.util.List;
import com.abewy.android.apps.openklyph.core.graph.GraphObject;
import android.app.ActionBar.OnNavigationListener;

public interface IActionbarSpinner
{
	public void displaySpinnerInActionBar(int array, int position, OnNavigationListener listener);

	public void displaySpinnerInActionBar(List<GraphObject> data, int position, OnNavigationListener listener);

	public void removeSpinnerInActionBar();
}
