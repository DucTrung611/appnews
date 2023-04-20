package com.example.buoi_5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class TinTuc_Adapter extends BaseAdapter {
    Context context;
    List<Tin_tuc> tin_tucList;

    public TinTuc_Adapter(Context context, List<Tin_tuc> tin_tucList) {
        this.context = context;
        this.tin_tucList = tin_tucList;
    }

    @Override
    public int getCount() {
        return tin_tucList.size();
    }

    @Override
    public Object getItem(int position) {
        return tin_tucList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public static class ViewHoler{
        TextView tv_link;
        TextView tv_title;
        TextView tv_des;
        TextView tv_pubDate;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoler viewHoler;
        if (convertView == null){
            viewHoler = new ViewHoler();

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.show_tin_tuc , null);

            viewHoler.tv_link = convertView.findViewById(R.id.tv_id);
            viewHoler.tv_title = convertView.findViewById(R.id.tv_title);
            viewHoler.tv_des = convertView.findViewById(R.id.tv_des);
            viewHoler.tv_pubDate = convertView.findViewById(R.id.tv_pubDate);

            convertView.setTag(viewHoler);
        }else {
            viewHoler = (ViewHoler) convertView.getTag();
        }
//        viewHoler.tv_link.setText(tin_tucList.get(position).getLink());
        viewHoler.tv_link.setText("");
        viewHoler.tv_title.setText(tin_tucList.get(position).getTitle());
//        viewHoler.tv_des.setText(tin_tucList.get(position).getDes());
        viewHoler.tv_des.setText("");
        viewHoler.tv_pubDate.setText(tin_tucList.get(position).getPubDate());
        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
