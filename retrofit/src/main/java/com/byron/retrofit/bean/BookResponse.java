package com.byron.retrofit.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookResponse {

@SerializedName("count")
@Expose
private Integer count;
@SerializedName("start")
@Expose
private Integer start;
@SerializedName("total")
@Expose
private Integer total;
@SerializedName("books")
@Expose
private List<Book> books = null;

public Integer getCount() {
return count;
}

public void setCount(Integer count) {
this.count = count;
}

public Integer getStart() {
return start;
}

public void setStart(Integer start) {
this.start = start;
}

public Integer getTotal() {
return total;
}

public void setTotal(Integer total) {
this.total = total;
}

public List<Book> getBooks() {
return books;
}

public void setBooks(List<Book> books) {
this.books = books;
}

}