package onedirection.cn.one.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import onedirection.cn.one.R;

/**
 * Created by wang_zhen1 on 2018/6/11 0011.
 */

public class GridVeiwAdapter extends BaseAdapter {

    private List<String> mTitleStr=new ArrayList<>();
    private Context mContext;
    public GridVeiwAdapter(Context context,List<String> strs) {
            this.mTitleStr=strs;
            this.mContext=context;
    }

    @Override
    public int getCount() {
        return mTitleStr.size();
    }


    @Override
    public Object getItem(int position) {
        return mTitleStr.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        //将布局文件转换成View
        View gridview_item = View.inflate(mContext, R.layout.gridview_item, null);

        TextView tv_title = (TextView) gridview_item.findViewById(R.id.tv_num);

        tv_title.setText(mTitleStr.get(position));

        return gridview_item;
    }

}
