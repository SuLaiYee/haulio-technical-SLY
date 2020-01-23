package com.padcmyanmar.myapplication.network;

import com.padcmyanmar.myapplication.data.vos.JobVO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface HaulioJobApi {

    @GET("bins/8d195.json")
    Call<List<JobVO>> loadAllJobList();
}
