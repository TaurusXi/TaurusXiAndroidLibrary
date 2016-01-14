package org.taurusxi.taurusxicommon.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public abstract class SimpleTXAdapter<MODEL, VIEW_HOLDER> extends BaseAdapter<MODEL> {

    protected Class<? extends VIEW_HOLDER> viewHolderClass;

    public Class getViewHolderClass() {
        return viewHolderClass;
    }

    public void setViewHolderClass(Class<? extends VIEW_HOLDER> viewHolderClass) {
        this.viewHolderClass = viewHolderClass;
    }

    public SimpleTXAdapter(Context context) {
        super(context);
    }


    public abstract VIEW_HOLDER onCreateViewHodler(View convertView);

    public abstract void onBindViewHolder(VIEW_HOLDER viewHolder, int position);

    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {
        final VIEW_HOLDER viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(itemLayout, parent, false);
            viewHolder = onCreateViewHodler(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (VIEW_HOLDER) convertView.getTag();
        }
        onBindViewHolder(viewHolder, position);

        return convertView;
    }
}
