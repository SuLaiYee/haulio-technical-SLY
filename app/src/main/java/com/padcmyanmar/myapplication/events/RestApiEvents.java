package com.padcmyanmar.myapplication.events;

import android.content.Context;

import com.padcmyanmar.myapplication.data.vos.JobVO;

import java.util.List;

public class RestApiEvents {

    public static class EmptyResponseEvent {

    }

    public static class ErrorInvokingAPIEvent {
        private String errorMsg;

        public ErrorInvokingAPIEvent(String errorMsg) {
            this.errorMsg = errorMsg;
        }

        public String getErrorMsg() {
            return errorMsg;
        }
    }

    public static class HaulioJobListDataLoadedEvent {
//        private int loadedPageIndex;
        private List<JobVO> loadedJobList;
        private Context context;

        public HaulioJobListDataLoadedEvent(List<JobVO> loadedJobList, Context context) {
//            this.loadedPageIndex = loadedPageIndex;
            this.loadedJobList = loadedJobList;
            this.context = context;
        }

//        public int getLoadedPageIndex() {
//            return loadedPageIndex;
//        }


        public List<JobVO> getLoadedJobList() {
            return loadedJobList;
        }

        public Context getContext() {
            return context;
        }
    }
}
