package com.giyer.noogle.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by giyer7 on 3/8/17.
 */

public abstract class BaseEventBusFragment<E extends BaseEventBusFragment.BaseEventBusFragmentListener> extends
        Fragment {
    protected E mCallback;
    protected BaseEventBusActivity mActivity;

    protected boolean ensureActivityImplementsCallback() {return true;}

    /**
     * Initialization of the view
     *
     * @param rootView           - layout to be shown
     * @param savedInstanceState - use this if the savedInstanceState is not null
     * @return View
     */
    public abstract View onInitFragment(View rootView, Bundle savedInstanceState);

    /**
     * suggested layout
     *
     * @return int
     */
    public abstract int getLayout();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!ensureActivityImplementsCallback())
            return;
        try {
            mCallback = (E) getActivity();
            mActivity = (BaseEventBusActivity) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement BaseEventFragmentListener");
        }
    }

    public E getCallback() {
        return mCallback;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayout(), container, false);
        onInitFragment(rootView, savedInstanceState);
        return rootView;
    }

    public void showToast(String message) {
        getCallback().showToast(message);
    }

    interface BaseEventBusFragmentListener {
        void showToast(String message);
    }
}
