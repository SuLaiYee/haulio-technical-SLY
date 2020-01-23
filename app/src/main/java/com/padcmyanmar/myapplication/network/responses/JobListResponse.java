package com.padcmyanmar.myapplication.network.responses;

import com.padcmyanmar.myapplication.data.vos.JobVO;
import com.padcmyanmar.myapplication.network.HaulioJobResponse;

import java.util.ArrayList;
import java.util.List;

public class JobListResponse extends HaulioJobResponse {

    private List<JobVO> jobList;

    public List<JobVO> getJobList() {
        if (jobList == null) {
            jobList = new ArrayList<>();
        }
        return jobList;
    }
}
