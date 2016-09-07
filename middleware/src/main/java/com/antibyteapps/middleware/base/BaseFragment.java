package com.antibyteapps.middleware.base;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Orhun Dalabasmaz
 * @since 01.03.2015.
 */
public abstract class BaseFragment
		extends Fragment {

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		super.onCreateView(inflater, container, savedInstanceState);
		return inflater.inflate(getResourceId(), container, false);
	}

	public abstract int getResourceId();
}
