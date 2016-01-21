package org.taurusxi.taurusxicommon.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.taurusxi.taurusxicommon.utils.MLog;
import org.taurusxi.taurusxicommon.utils.StringUtils;

//import butterknife.ButterKnife;

/**
 * Created by wumin on 16/1/14.
 */
public abstract class TXFragment extends Fragment {

    protected String TAG = getLogTag();

    private String getLogTag() {
        return this.getClass().getSimpleName();
    }

    protected Activity context;
    protected View rootView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = (Activity) context;
    }

    @Override
    public final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onFragmentCreate(savedInstanceState);
    }

    protected void onFragmentCreate(Bundle savedInstanceState) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getContentView(), container, false);
//        ButterKnife.bind(this, rootView);

        initView(savedInstanceState);
        initEvents(savedInstanceState);
        initData(savedInstanceState,getArguments());

        return rootView;
    }

    protected void initData(Bundle savedInstanceState,Bundle bundle) {

    }

    protected abstract int getContentView();

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void initEvents(Bundle savedInstanceState);

    public void e(String msg) {
        MLog.e(TAG, StringUtils.formatNull(msg));
    }

    public void d(String msg) {
        MLog.d(TAG, StringUtils.formatNull(msg));
    }

    public void v(String msg) {
        MLog.v(TAG, StringUtils.formatNull(msg));
    }

    public void showShortToast(String msg) {
        if (context != null) {
            Toast.makeText(context, StringUtils.formatNull(msg), Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkContext(boolean flag) {
        return (context != null) && flag;
    }

    @Override
    public void onDetach() {
        this.context = null;
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
//        ButterKnife.unbind(this);
        super.onDestroyView();
    }
}
