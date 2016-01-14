package org.taurusxi.taurusxicommon.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import org.taurusxi.taurusxicommon.R;
import org.taurusxi.taurusxicommon.utils.MLog;

/**
 * Created by wumin on 16/1/14.
 */
public class CustomTXActivity extends TXActivity {

    private static final String BUNDLE_EXTRA = "b_extra";
    private static final String FRAGMENT_CLASS = "f_class";
    private static final String FRAGMENT_TAG = "f_tag";

    @Override
    protected int getContentView() {
        return R.layout.custom_activity_layout;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        updateFragment(savedInstanceState);
    }

    private void updateFragment(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }

        Bundle bundleExtra = intent.getBundleExtra(BUNDLE_EXTRA);
        Class<? extends Fragment> clazz = (Class<? extends Fragment>) intent.getSerializableExtra(FRAGMENT_CLASS);

        if (clazz == null) {
            return;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = null;
        try {
            fragment = clazz.newInstance();
            if (bundleExtra != null) {
                fragment.setArguments(bundleExtra);
            }
        } catch (Exception e) {
            MLog.e(TAG, "类反射加载失败");
        }
        fragmentTransaction.replace(R.id.custom_content_layout, fragment, FRAGMENT_TAG);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    protected void initEvents(Bundle savedInstanceState) {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    public static void startFragment(@NonNull Activity activity, @Nullable Bundle bundle, @NonNull Class<? extends Fragment> clazz) {

        if (activity == null) {
            return;
        }
        Intent intent = new Intent(activity, CustomTXActivity.class);
        if (bundle != null) {
            intent.putExtra(BUNDLE_EXTRA, bundle);
        }
        intent.putExtra(FRAGMENT_CLASS, clazz);
        activity.startActivity(intent);

    }

    public static void startFragment(@NonNull Activity activity, @NonNull Class<? extends Fragment> clazz) {
        startFragment(activity, null, clazz);
    }
}
