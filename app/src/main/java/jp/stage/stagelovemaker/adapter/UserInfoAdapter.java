package jp.stage.stagelovemaker.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import jp.stage.stagelovemaker.R;
import jp.stage.stagelovemaker.model.UserInfo;

/**
 * Created by congn on 8/4/2017.
 */

public class UserInfoAdapter extends BaseAdapter {
    public static mViewHolder holder;

    private Context mContext;
    private List<UserInfo> modelList;

    public UserInfoAdapter(Context mContext, List<UserInfo> modelList) {
        this.modelList = modelList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return modelList.size();
    }

    @Override
    public Object getItem(int position) {
        return modelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addItem(UserInfo user) {
        modelList.add(user);
    }

    public void clear() {
        modelList.clear();
    }

    public void remove(int position) {
        modelList.remove(position);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = layoutInflater.inflate(R.layout.custom_item_card, parent, false);
            //configure view holder
            holder = new mViewHolder();
            holder.txtNameAge = (TextView) rowView.findViewById(R.id.txtNameAge_home);
            holder.txtAddress = (TextView) rowView.findViewById(R.id.txtAddress_home);
            holder.imgAvatar = (ImageView) rowView.findViewById(R.id.imgAvatar_home);
            rowView.setTag(holder);
        } else {
            holder = (mViewHolder) convertView.getTag();
        }
        holder.txtNameAge.setText(modelList.get(position).getFirstName() + ", " + modelList.get(position).getBirthday());
        holder.txtAddress.setText(modelList.get(position).getSchool());
        if (holder.imgAvatar != null && mContext != null) {
            Glide.with(mContext).load(modelList.get(position).getAvatars().get(0)).centerCrop().into(holder.imgAvatar);
        }
        return rowView;
    }

    public static class mViewHolder {
        public TextView txtNameAge;
        public TextView txtAddress;
        public ImageView imgAvatar;
    }
}
