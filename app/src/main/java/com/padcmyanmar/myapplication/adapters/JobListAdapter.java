package com.padcmyanmar.myapplication.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.padcmyanmar.myapplication.R;
import com.padcmyanmar.myapplication.data.vos.JobVO;
import com.padcmyanmar.myapplication.delegates.JobItemDelegate;
import com.padcmyanmar.myapplication.viewholders.JobViewHolder;

public class JobListAdapter extends BaseRecyclerAdapter<JobViewHolder, JobVO> {
    private Context mContext;
    private LayoutInflater mLayoutInflator;
    private JobItemDelegate mJobItemDelegate;

    public JobListAdapter(Context context, JobItemDelegate jobItemDelegate) {
        super(context);
        mContext = context;
        mLayoutInflator = LayoutInflater.from(mContext);
        mJobItemDelegate = jobItemDelegate;
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflator.inflate(R.layout.view_item_job,parent,false);

        return new JobViewHolder(view,mJobItemDelegate);
    }
}
