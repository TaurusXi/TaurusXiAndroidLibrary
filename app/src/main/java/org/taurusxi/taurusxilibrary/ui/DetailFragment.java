package org.taurusxi.taurusxilibrary.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.taurusxi.taurusxicommon.activity.CustomTXActivity;
import org.taurusxi.taurusxicommon.adapter.SimpleTXAdapter;
import org.taurusxi.taurusxicommon.exception.TXBaseException;
import org.taurusxi.taurusxicommon.fragment.TXFragment;
import org.taurusxi.taurusxicommon.imageLoader.ImageLoaderHelper;
import org.taurusxi.taurusxicommon.imageLoader.listener.SimpleImageLoaderListener;
import org.taurusxi.taurusxicommon.keyValueModel.utils.SettingHelper;
import org.taurusxi.taurusxicommon.listener.TXResponce;
import org.taurusxi.taurusxicommon.task.NetworkTask;
import org.taurusxi.taurusxicommon.utils.MLog;
import org.taurusxi.taurusxicommon.utils.logger.Logger;
import org.taurusxi.taurusxilibrary.R;
import org.taurusxi.taurusxilibrary.api.ApiImpl;
import org.taurusxi.taurusxilibrary.model.CircleModel;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wumin on 16/1/14.
 */
public class DetailFragment extends TXFragment {

    private static int sCount = 1;

    @Bind(R.id.from_data)
    TextView fromDataTextView;

    @Bind(R.id.intent)
    TextView intentTextView;

    @Bind(R.id.listview)
    ListView listView;

    private SampleAdapter sampleAdapter;

    @Override
    protected int getContentView() {
        return R.layout.fragment_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        sampleAdapter = new SampleAdapter(context);
        listView.setAdapter(sampleAdapter);
    }

    @Override
    protected void initData(Bundle savedInstanceState,Bundle data) {
        if (data!=null){
            String key = data.getString("key");
            fromDataTextView.setText(key+"，滑动退出界面.");
        }
        intentTextView.setText("点击跳转详情___"+ sCount);

        Logger.d("aaaa");
        Logger.e("zzzzz");


    }

    @OnClick(R.id.network)
    public void network(View view){
        ApiImpl.getCircleList(new TXResponce<CircleModel>() {
            @Override
            public void onSuccess(CircleModel circleModel) {
                sampleAdapter.setCount(circleModel.datas);
            }

            @Override
            public void onError(TXBaseException errorException, CircleModel errorModel) {

            }

            @Override
            public boolean onFinish(NetworkTask task) {
                return true;
            }
        });
    }

    @Override
    protected void initEvents(Bundle savedInstanceState) {

    }

    @OnClick(R.id.intent)
    public void intentView(View view){
        sCount++;
        CustomTXActivity.startFragment(context,DetailFragment.class);
    }

    @OnClick(R.id.sql_save)
    public void sqlSave(View view){
        SettingHelper instance = SettingHelper.getInstance();
        instance.saveInt("demo",-1);
        instance.getInt("demo",-1);
    }

    public static class SampleAdapter extends SimpleTXAdapter<CircleModel.Circle,ViewHolder> {

        public SampleAdapter(Context context) {
            super(context);
        }

        @Override
        protected int getItemLayout() {
            return R.layout.item_imageview;
        }

        @Override
        public ViewHolder onCreateViewHodler(View convertView) {
            return new ViewHolder(convertView);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            CircleModel.Circle circle = listData.get(position);
            ImageLoaderHelper.getInstance().loadImage(viewHolder.imageView, "http://pic.shantoo.cn/" + circle.coverKey, new SimpleImageLoaderListener() {
                @Override
                public void loadComplete(String url, ImageView imageView, Bitmap bitmap) {
                    MLog.e(TAG,"url:"+url+"_加载完成");
                }
            });
        }


    }

    public static class ViewHolder{
        @Bind(R.id.imageView)
        ImageView imageView;

        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }

}
