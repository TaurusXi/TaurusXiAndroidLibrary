package org.taurusxi.taurusxilibrary.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.taurusxi.taurusxicommon.activity.CustomTXActivity;
import org.taurusxi.taurusxicommon.activity.TXActivity;
import org.taurusxi.taurusxicommon.annotation.Keyboard;
import org.taurusxi.taurusxilibrary.R;

public class MainActivity extends TXActivity {

    TextView textView9;
    @Keyboard
    EditText editText;


    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvents(Bundle savedInstanceState) {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    public void demoIntent(View view){
        Bundle bundle = new Bundle();
        bundle.putString("key","从MainActivity过来,滑动退出界面");
        CustomTXActivity.startFragment(context,bundle,DetailFragment.class);

    }

    @Override
    protected boolean needSwipeBack() {
        return false;
    }
}
