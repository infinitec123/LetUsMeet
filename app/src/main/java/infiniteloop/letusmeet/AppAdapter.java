package infiniteloop.letusmeet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class AppAdapter extends BaseAdapter {

    private Context mContext;
    LayoutInflater inflater;
    private ArrayList<App> mApps;

    public AppAdapter(Context context, ArrayList<App> myList) {
        this.mApps = myList;
        this.mContext = context;
        inflater = LayoutInflater.from(this.mContext);
    }

    @Override
    public int getCount() {
        return mApps.size();
    }

    @Override
    public App getItem(int i) {
        return mApps.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_list, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        App thisApp = getItem(position);

        mViewHolder.tvTitle.setText(thisApp.name);
        mViewHolder.tvDesc.setText(thisApp.packageName);
        mViewHolder.ivIcon.setImageDrawable(thisApp.icon);
        return convertView;
    }

    private class MyViewHolder {
        TextView tvTitle, tvDesc;
        ImageView ivIcon;

        public MyViewHolder(View item) {
            tvTitle = (TextView) item.findViewById(R.id.firstLine);
            tvDesc = (TextView) item.findViewById(R.id.secondLine);
            ivIcon = (ImageView) item.findViewById(R.id.icon);
        }
    }
}
