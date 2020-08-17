package com.test.test2app.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.test2app.R;
import com.zhaoyuntao.androidutils.tools.S;

import java.util.ArrayList;
import java.util.List;

/**
 * created by zhaoyuntao
 * on 2019-12-20
 * description:
 */
public class CountryCodeAdapter extends RecyclerView.Adapter {
    private List<CountryCodeBean> mList = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;

    public CountryCodeAdapter(List<CountryCodeBean> list){
        setData(list);
    }

    public void setData(List<CountryCodeBean> list) {
        this.mList.clear();
        this.mList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.g42pay_sdk_layout_item_country_code, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        S.s("onBindViewHolder");
        final CountryCodeBean countryCodeBean = mList.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, countryCodeBean);
                }
            }
        });
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.countryCodeIcon.setImageResource(countryCodeBean.getDrawableId());
        viewHolder.countryCodeName.setText(countryCodeBean.getCountryName());
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List payloads) {
        S.s("onBindViewHolder2");
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private TextView countryCodeName;
        private ImageView countryCodeIcon;

        ViewHolder(View itemView) {
            super(itemView);
            countryCodeName = itemView.findViewById(R.id.country_code_name);
            countryCodeIcon = itemView.findViewById(R.id.country_code_icon);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, CountryCodeBean countryCodeBean);
    }
}
