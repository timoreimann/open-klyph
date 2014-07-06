package com.abewy.android.apps.openklyph.fragment;

import java.util.List;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.abewy.android.apps.openklyph.KlyphBundleExtras;
import com.abewy.android.apps.openklyph.adapter.SpecialLayout;
import com.abewy.android.apps.openklyph.app.AlbumPhotosActivity;
import com.abewy.android.apps.openklyph.app.AlbumVideosActivity;
import com.abewy.android.apps.openklyph.core.fql.Album;
import com.abewy.android.apps.openklyph.core.graph.GraphObject;
import com.abewy.android.apps.openklyph.request.AsyncRequest.Query;
import com.abewy.android.apps.openklyph.widget.KlyphGridView;
import com.abewy.android.apps.openklyph.R;

public class ElementAlbums extends KlyphFakeHeaderGridFragment
{
	public ElementAlbums()
	{
		setRequestType(Query.ELEMENT_ALBUMS);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		getListView().setDrawSelectorOnTop(false);

		// int padding = (int) getResources().getDimension(R.dimen.ckoobafe_list_padding) / 2;
		// getListView().setPadding(0, padding, 0, padding);
		// getListView().setDividerHeight(0);

		defineEmptyText(R.string.empty_list_no_album);

		setListVisible(false);

		setRequestType(Query.ELEMENT_ALBUMS);

		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	protected int getSpecialLayout()
	{
		return SpecialLayout.GRID;
	}

	@Override
	protected void populate(List<GraphObject> data)
	{
		super.populate(data);

		if (data.size() > 0 && requestHasMoreData())
		{
			setNoMoreData(false);
			Album lastAlbum = (Album) data.get(data.size() - 1);
			
			setOffset(lastAlbum.getOwner_cursor());
		}
		else
		{
			setNoMoreData(true);
		}
	}

	@Override
	public void onGridItemClick(KlyphGridView l, View v, int position, long id)
	{
		Album album = (Album) l.getItemAtPosition(position);

		if (album.getIs_video_album() == true)
		{
			Intent intent = new Intent(getActivity(), AlbumVideosActivity.class);
			intent.putExtra(KlyphBundleExtras.ELEMENT_ID, album.getOwner());
			intent.putExtra(KlyphBundleExtras.NAME, album.getName());
			startActivity(intent);
		}
		else
		{
			Intent intent = new Intent(getActivity(), AlbumPhotosActivity.class);
			intent.putExtra(KlyphBundleExtras.ALBUM_NAME, album.getName());

			if (album.isTaggedAlbum())
			{
				intent.putExtra(KlyphBundleExtras.ALBUM_TAGGED, true);
				intent.putExtra(KlyphBundleExtras.ALBUM_ID, album.getOwner());
			}
			else
			{
				intent.putExtra(KlyphBundleExtras.ALBUM_ID, album.getObject_id());
			}
			startActivity(intent);
		}
	}

	@Override
	protected int getNumColumn()
	{
		return getResources().getInteger(R.integer.klyph_grid_album_num_column);
	}
}
