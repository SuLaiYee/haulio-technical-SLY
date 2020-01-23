package com.padcmyanmar.myapplication.data.models;

import android.content.Context;

import com.padcmyanmar.myapplication.data.vos.JobVO;
import com.padcmyanmar.myapplication.events.RestApiEvents;
import com.padcmyanmar.myapplication.network.HaulioJobDataAgent;
import com.padcmyanmar.myapplication.network.HaulioJobDataAgentImpl;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class HaulioJobModel {
    private static HaulioJobModel objInstance;

    private HaulioJobDataAgent haulioJobDataAgent;

    private List<JobVO> jobList;

    public static HaulioJobModel getInstance() {
        if (objInstance == null) {
            objInstance = new HaulioJobModel();
        }
        return objInstance;
    }

    public HaulioJobModel() {
        haulioJobDataAgent = HaulioJobDataAgentImpl.getInstance();
        jobList = new ArrayList<>();

        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }
    }

    public void startLoadingJobList(Context context) {
        haulioJobDataAgent.loadAllJobList(context);
    }

    public List<JobVO> getJobList() {
        return jobList;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onACServiceDataLoaded(RestApiEvents.HaulioJobListDataLoadedEvent haulioJobListDataLoadedEvent) {
        jobList.addAll(haulioJobListDataLoadedEvent.getLoadedJobList());
    }
}
