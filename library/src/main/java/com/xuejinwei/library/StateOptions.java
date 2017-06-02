package com.xuejinwei.library;

import android.view.View;
import android.support.annotation.LayoutRes;

import java.io.Serializable;

/**
 * Created by xuejinwei on 2017/6/2.
 * Email:xuejinwei@outlook.com
 * <p>
 * Common state options
 */

@SuppressWarnings("WeakerAccess")
public abstract class StateOptions implements Serializable {

    View stateView;
    private MultipStateLayout mAttachedMsl;

    protected abstract @LayoutRes int layoutId();

    void init(View rootView) {
    }

    public View rootView() {
        return stateView;
    }

    public void setAttachedMsl(MultipStateLayout attachedMsl) {
        mAttachedMsl = attachedMsl;
    }

    public MultipStateLayout getAttachedMsl() {
        return mAttachedMsl;
    }
}
