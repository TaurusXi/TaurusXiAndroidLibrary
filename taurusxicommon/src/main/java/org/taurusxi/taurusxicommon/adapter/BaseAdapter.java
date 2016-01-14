package org.taurusxi.taurusxicommon.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.taurusxi.taurusxicommon.network.NetworkHelper;

import java.util.List;

public abstract class BaseAdapter<MODEL> extends android.widget.BaseAdapter {

    protected final String TAG = getLogName();

    protected List<MODEL> listData;
    protected NetworkHelper netWorkHelper;

    protected LayoutInflater layoutInflater;

    protected Context mContext;

    protected int count;

    protected int itemLayout;

    public BaseAdapter(Context context) {
        netWorkHelper = NetworkHelper.getInstance();
        this.mContext = context;
        this.layoutInflater = LayoutInflater.from(mContext);
        setItemLayout(getItemLayout());
    }

    protected abstract int getItemLayout();

    @Override
    public int getCount() {
        count = listData == null ? 0 : listData.size();
        return count;
    }


    public void setItemLayout(int itemLayout) {
        this.itemLayout = itemLayout;
    }

    public List<MODEL> getDatas() {
        return listData == null ? null : listData;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setCount(List<MODEL> listData) {
        this.listData = listData;
        this.count = listData == null ? 0 : listData.size();
        notifyDataSetChanged();
    }


    public String getLogName() {
        return this.getClass().getSimpleName();
    }


    protected void setText(String text, TextView textView) {
        if (TextUtils.isEmpty(text)) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setText(text);
            textView.setVisibility(View.VISIBLE);
        }
    }

    protected void setText(TextView textView, String text) {
        if (TextUtils.isEmpty(text)) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setText(text);
            textView.setVisibility(View.VISIBLE);
        }
    }


    protected void setText(TextView textView, String formatText, String text) {
        if (TextUtils.isEmpty(text)) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setText(String.format(formatText, text));
            textView.setVisibility(View.VISIBLE);
        }
    }

    protected void setText(TextView textView, String formatText, String text, String defaultText) {
        if (TextUtils.isEmpty(text)) {
            textView.setText(defaultText);
        } else {
            textView.setText(String.format(formatText, text));
        }

    }


    public List<MODEL> getListData() {
        return listData;
    }

    protected boolean isLastPositon(int position) {
        return position == count - 1;
    }


    protected void removePosition(int position) {
        if (listData != null) {
            listData.remove(position);
            count = listData.size();
            notifyDataSetChanged();
        }
    }

    protected void removeAll(List<MODEL> list) {
        if (listData != null) {
            listData.removeAll(list);
            count = listData.size();
            notifyDataSetChanged();
        }
    }

    /*
    * 当 ListData 为null 或者 size为0 时，返回true
    * */
    public boolean isDataEmpty() {
        return listData == null || listData.size() == 0;
    }

    public void cleanUp(){

    }
}
