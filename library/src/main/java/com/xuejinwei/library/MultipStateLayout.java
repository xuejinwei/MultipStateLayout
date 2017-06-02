package com.xuejinwei.library;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by xuejinwei on 2017/6/2.
 * Email:xuejinwei@outlook.com
 */

public class MultipStateLayout extends LinearLayout {

    private static final String MSG_ONE_CHILD = "MultipStateLayout must have one child!";
    private final        Object mOptionsLock  = new Object();
    private View        content;
    private FrameLayout stContainer;
    private StateOptions mCurrentOptions = null;

    public MultipStateLayout(Context context) {
        super(context);
    }

    public MultipStateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() != 1) throw new IllegalStateException(MSG_ONE_CHILD);
        if (isInEditMode()) return; // hide state views in designer
        setOrientation(VERTICAL);
        content = getChildAt(0); // assume first child as content
        LayoutInflater.from(getContext()).inflate(R.layout.msl_template, this, true);
        stContainer = (FrameLayout) findViewById(R.id.mslContainer);
    }

    public void showContent() {
        stContainer.setVisibility(GONE);
        content.setVisibility(VISIBLE);
    }
    
    public void showCustom(final StateOptions options) {

        synchronized (mOptionsLock) {
            if (mCurrentOptions == options) {
                return;
            }
            mCurrentOptions = options;
        }

        stContainer.clearAnimation();
        content.clearAnimation();

        stContainer.post(new Runnable() {
            @Override
            public void run() {
                content.setVisibility(GONE);
                stContainer.setVisibility(VISIBLE);
                refreshContainer(options);
            }
        });
    }

    private void refreshContainer(StateOptions options) {
        stContainer.removeAllViews();
        if (options.stateView == null) {
            options.stateView = LayoutInflater.from(getContext()).inflate(options.layoutId(), null, false);
            stContainer.addView(options.stateView);
            options.setAttachedMsl(this);
            options.init(options.stateView);
        } else {
            View stateView = options.stateView;
            ViewGroup parent = (ViewGroup) stateView.getParent();
            if (parent == null) {
                stContainer.addView(options.stateView);
            }
            options.init(stateView);
        }
    }

}
