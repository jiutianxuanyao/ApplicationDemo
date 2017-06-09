package com.byron.retrofit.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rating {

@SerializedName("max")
@Expose
private Integer max;
@SerializedName("numRaters")
@Expose
private Integer numRaters;
@SerializedName("average")
@Expose
private String average;
@SerializedName("min")
@Expose
private Integer min;

public Integer getMax() {
return max;
}

public void setMax(Integer max) {
this.max = max;
}

public Integer getNumRaters() {
return numRaters;
}

public void setNumRaters(Integer numRaters) {
this.numRaters = numRaters;
}

public String getAverage() {
return average;
}

public void setAverage(String average) {
this.average = average;
}

public Integer getMin() {
return min;
}

public void setMin(Integer min) {
this.min = min;
}

}