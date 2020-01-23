package com.padcmyanmar.myapplication.network;

import android.content.Context;

import com.google.gson.Gson;
import com.padcmyanmar.myapplication.data.vos.JobVO;
import com.padcmyanmar.myapplication.events.RestApiEvents;
import com.padcmyanmar.myapplication.utils.RestApiConstants;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HaulioJobDataAgentImpl implements HaulioJobDataAgent {

    private HaulioJobApi theAPI;

    private static HaulioJobDataAgent objInstance;

    private HaulioJobDataAgentImpl() {
        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
        // time 60 sec is optimal.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RestApiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .client(okHttpClient)
                .build();

        theAPI = retrofit.create(HaulioJobApi.class);
    }

    public static HaulioJobDataAgent getInstance() {
        if (objInstance == null) {
            objInstance = new HaulioJobDataAgentImpl();
        }

        return objInstance;
    }

    @Override
    public void loadAllJobList(final Context context) {
        Call<List<JobVO>> loadAllJobListCall = theAPI.loadAllJobList();
        loadAllJobListCall.enqueue(new Callback<List<JobVO>>() {
            @Override
            public void onResponse(Call<List<JobVO>> call, Response<List<JobVO>> response) {
                List<JobVO> jobListResponse = response.body();
                if (jobListResponse != null && jobListResponse.size() > 0) {
                    RestApiEvents.HaulioJobListDataLoadedEvent jobListDataLoadedEvent = new RestApiEvents.HaulioJobListDataLoadedEvent(
                            jobListResponse, context);
                    EventBus.getDefault().post(jobListDataLoadedEvent);
                } else {
                    RestApiEvents.ErrorInvokingAPIEvent errorEvent
                            = new RestApiEvents.ErrorInvokingAPIEvent("No data could be load for now. Please try again later.");
                    EventBus.getDefault().post(errorEvent);
                }
            }

            @Override
            public void onFailure(Call<List<JobVO>> call, Throwable t) {
                RestApiEvents.ErrorInvokingAPIEvent errorEvent
                        = new RestApiEvents.ErrorInvokingAPIEvent(t.getMessage());
                EventBus.getDefault().post(errorEvent);
            }
        });
    }
}
