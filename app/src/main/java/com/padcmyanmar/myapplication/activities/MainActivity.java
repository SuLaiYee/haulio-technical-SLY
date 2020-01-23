package com.padcmyanmar.myapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.padcmyanmar.myapplication.R;
import com.padcmyanmar.myapplication.adapters.JobListAdapter;
import com.padcmyanmar.myapplication.components.EmptyViewPod;
import com.padcmyanmar.myapplication.components.rvset.SmartRecyclerView;
import com.padcmyanmar.myapplication.data.models.HaulioJobModel;
import com.padcmyanmar.myapplication.data.vos.JobVO;
import com.padcmyanmar.myapplication.delegates.JobItemDelegate;
import com.padcmyanmar.myapplication.events.RestApiEvents;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity implements JobItemDelegate {

    @BindView(R.id.iv_sign_out)
    ImageView ivSignOut;

    @BindView(R.id.rv_job_list)
    SmartRecyclerView rvJobList;

    @BindView(R.id.vp_job_list)
    EmptyViewPod vpJobList;

    private JobListAdapter jobListAdapter;

    private String truckDriverName, truckDriverPhoto;

    public static Intent newIntent(Context context, String truckDriverName, String truckDriverPhoto) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(JobOnMapActivity.TRUCK_DRIVER_NAME, truckDriverName);
        intent.putExtra(JobOnMapActivity.TRUCK_DRIVER_PHOTO, truckDriverPhoto);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this, this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);                            // hide App title name
        }

        truckDriverName = getIntent().getStringExtra(JobOnMapActivity.TRUCK_DRIVER_NAME);
        truckDriverPhoto = getIntent().getStringExtra(JobOnMapActivity.TRUCK_DRIVER_PHOTO);

        HaulioJobModel.getInstance().startLoadingJobList(getApplicationContext());

        vpJobList.setEmptyData("Loading...");
        rvJobList.setEmptyView(vpJobList);
        rvJobList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        jobListAdapter = new JobListAdapter(getApplicationContext(), this);

//        jobListAdapter.setNewData(HaulioJobModel.getInstance().getJobList());

        rvJobList.setAdapter(jobListAdapter);

        ivSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseSignOut();
                googleSignOut();
                navigateToAuthUI();
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onACServiceDataLoaded(RestApiEvents.HaulioJobListDataLoadedEvent haulioJobListDataLoadedEvent) {
        if (haulioJobListDataLoadedEvent.getLoadedJobList() == null && haulioJobListDataLoadedEvent.getLoadedJobList().isEmpty()) {
            vpJobList.setEmptyData("You have no Jobs.");
        } else {
            jobListAdapter.setNewData(haulioJobListDataLoadedEvent.getLoadedJobList());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onDestroy();
    }

    @Override
    public void onTapJobItem(JobVO jobVO) {
        double latitude = jobVO.getGeoLocationVO().getLatitude();
        double longitude = jobVO.getGeoLocationVO().getLongitude();
        String jobNumber = String.valueOf(jobVO.getJobId());
        String jobAddress = jobVO.getJobAddress();
        String companyName = jobVO.getCompanyName();

        Intent intent = JobOnMapActivity.newIntent(getApplicationContext(), latitude, longitude,
                truckDriverPhoto, truckDriverName, jobNumber, jobAddress, companyName);

        startActivity(intent);
    }

    private void navigateToAuthUI() {
        Intent intent = AuthActivity.newIntent(getBaseContext());
        startActivity(intent);
    }
}
