package com.byron.retrofit.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tag {

@SerializedName("count")
@Expose
private Integer count;
@SerializedName("name")
@Expose
private String name;
@SerializedName("title")
@Expose
private String title;

public Integer getCount() {
return count;
}

public void setCount(Integer count) {
this.count = count;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getTitle() {
return title;
}

public void setTitle(String title) {
this.title = title;
}

}