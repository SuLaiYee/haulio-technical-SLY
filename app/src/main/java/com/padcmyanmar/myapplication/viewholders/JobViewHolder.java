package com.padcmyanmar.myapplication.viewholders;

import android.view.View;
import android.widget.TextView;

import com.padcmyanmar.myapplication.R;
import com.padcmyanmar.myapplication.data.vos.JobVO;
import com.padcmyanmar.myapplication.delegates.JobItemDelegate;

import butterknife.BindView;

public class JobViewHolder extends BaseViewHolder<JobVO> {

    @BindView(R.id.tv_job_number)
    TextView tvJobNumber;

    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;

    @BindView(R.id.tv_job_address)
    TextView tvJobAddress;

    private JobItemDelegate mJobItemDelegate;

    public JobViewHolder(View itemView, JobItemDelegate jobItemDelegate) {
        super(itemView);
        mJobItemDelegate = jobItemDelegate;
    }

    @Override
    public void setData(JobVO data) {
        if (data != null) {
            mData = data;

            tvJobNumber.setText("Job Number: " + data.getJobId());
            tvCompanyName.setText("Company: " + data.getCompanyName());
            tvJobAddress.setText("Address: " + data.getJobAddress());
        }
    }

    @Override
    public void onClick(View v) {
       mJobItemDelegate.onTapJobItem(mData);
    }
}
