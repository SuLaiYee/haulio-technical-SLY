package com.padcmyanmar.myapplication.data.vos;

import com.google.gson.annotations.SerializedName;
import com.padcmyanmar.myapplication.network.HaulioJobResponse;

public class JobVO  extends HaulioJobResponse{

    @SerializedName("id")
    private long id;

    @SerializedName("job-id")
    private long jobId;

    @SerializedName("priority")
    private int jobPriority;

    @SerializedName("company")
    private String companyName;

    @SerializedName("address")
    private String jobAddress;

    @SerializedName("geolocation")
    private GeoLocationVO geoLocationVO;

    public long getId() {
        return id;
    }

    public long getJobId() {
        return jobId;
    }

    public int getJobPriority() {
        return jobPriority;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getJobAddress() {
        return jobAddress;
    }

    public GeoLocationVO getGeoLocationVO() {
        return geoLocationVO;
    }
}
