package com.byron.retrofit.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Book {

@SerializedName("rating")
@Expose
private Rating rating;
@SerializedName("subtitle")
@Expose
private String subtitle;
@SerializedName("author")
@Expose
private List<String> author = null;
@SerializedName("pubdate")
@Expose
private String pubdate;
@SerializedName("tags")
@Expose
private List<Tag> tags = null;
@SerializedName("origin_title")
@Expose
private String originTitle;
@SerializedName("image")
@Expose
private String image;
@SerializedName("binding")
@Expose
private String binding;
@SerializedName("translator")
@Expose
private List<String> translator = null;
@SerializedName("catalog")
@Expose
private String catalog;
@SerializedName("pages")
@Expose
private String pages;
@SerializedName("images")
@Expose
private Images images;
@SerializedName("alt")
@Expose
private String alt;
@SerializedName("id")
@Expose
private String id;
@SerializedName("publisher")
@Expose
private String publisher;
@SerializedName("isbn10")
@Expose
private String isbn10;
@SerializedName("isbn13")
@Expose
private String isbn13;
@SerializedName("title")
@Expose
private String title;
@SerializedName("url")
@Expose
private String url;
@SerializedName("alt_title")
@Expose
private String altTitle;
@SerializedName("author_intro")
@Expose
private String authorIntro;
@SerializedName("summary")
@Expose
private String summary;
@SerializedName("price")
@Expose
private String price;
@SerializedName("ebook_url")
@Expose
private String ebookUrl;
@SerializedName("ebook_price")
@Expose
private String ebookPrice;
@SerializedName("series")
@Expose
private Series series;

public Rating getRating() {
return rating;
}

public void setRating(Rating rating) {
this.rating = rating;
}

public String getSubtitle() {
return subtitle;
}

public void setSubtitle(String subtitle) {
this.subtitle = subtitle;
}

public List<String> getAuthor() {
return author;
}

public void setAuthor(List<String> author) {
this.author = author;
}

public String getPubdate() {
return pubdate;
}

public void setPubdate(String pubdate) {
this.pubdate = pubdate;
}

public List<Tag> getTags() {
return tags;
}

public void setTags(List<Tag> tags) {
this.tags = tags;
}

public String getOriginTitle() {
return originTitle;
}

public void setOriginTitle(String originTitle) {
this.originTitle = originTitle;
}

public String getImage() {
return image;
}

public void setImage(String image) {
this.image = image;
}

public String getBinding() {
return binding;
}

public void setBinding(String binding) {
this.binding = binding;
}

public List<String> getTranslator() {
return translator;
}

public void setTranslator(List<String> translator) {
this.translator = translator;
}

public String getCatalog() {
return catalog;
}

public void setCatalog(String catalog) {
this.catalog = catalog;
}

public String getPages() {
return pages;
}

public void setPages(String pages) {
this.pages = pages;
}

public Images getImages() {
return images;
}

public void setImages(Images images) {
this.images = images;
}

public String getAlt() {
return alt;
}

public void setAlt(String alt) {
this.alt = alt;
}

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getPublisher() {
return publisher;
}

public void setPublisher(String publisher) {
this.publisher = publisher;
}

public String getIsbn10() {
return isbn10;
}

public void setIsbn10(String isbn10) {
this.isbn10 = isbn10;
}

public String getIsbn13() {
return isbn13;
}

public void setIsbn13(String isbn13) {
this.isbn13 = isbn13;
}

public String getTitle() {
return title;
}

public void setTitle(String title) {
this.title = title;
}

public String getUrl() {
return url;
}

public void setUrl(String url) {
this.url = url;
}

public String getAltTitle() {
return altTitle;
}

public void setAltTitle(String altTitle) {
this.altTitle = altTitle;
}

public String getAuthorIntro() {
return authorIntro;
}

public void setAuthorIntro(String authorIntro) {
this.authorIntro = authorIntro;
}

public String getSummary() {
return summary;
}

public void setSummary(String summary) {
this.summary = summary;
}

public String getPrice() {
return price;
}

public void setPrice(String price) {
this.price = price;
}

public String getEbookUrl() {
return ebookUrl;
}

public void setEbookUrl(String ebookUrl) {
this.ebookUrl = ebookUrl;
}

public String getEbookPrice() {
return ebookPrice;
}

public void setEbookPrice(String ebookPrice) {
this.ebookPrice = ebookPrice;
}

public Series getSeries() {
return series;
}

public void setSeries(Series series) {
this.series = series;
}

}