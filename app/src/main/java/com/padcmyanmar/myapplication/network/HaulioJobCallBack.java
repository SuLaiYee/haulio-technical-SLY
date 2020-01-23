package com.padcmyanmar.myapplication.network;

import com.padcmyanmar.myapplication.events.RestApiEvents;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class HaulioJobCallBack<T extends HaulioJobResponse> implements Callback<T> {
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        HaulioJobResponse mmNewsResponse = response.body();
        if(mmNewsResponse==null){
            RestApiEvents.ErrorInvokingAPIEvent errorEvent
                    = new RestApiEvents.ErrorInvokingAPIEvent("No data could be load for now. Please try again later.");
            EventBus.getDefault().post(errorEvent);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        RestApiEvents.ErrorInvokingAPIEvent errorEvent
                = new RestApiEvents.ErrorInvokingAPIEvent(t.getMessage());
        EventBus.getDefault().post(errorEvent);
    }
}
