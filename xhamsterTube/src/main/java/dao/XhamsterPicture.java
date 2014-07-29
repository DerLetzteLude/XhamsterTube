package dao;

import com.google.gson.Gson;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table XHAMSTER_PICTURE.
 */
public class XhamsterPicture {

	private Long id;
	private String title;
	private String pictureName;
	private String pictureUrl;
	private String sideUrl;
	private String userName;
	private String userURL;
	private String viewCount;
	private String thumbSmallUrl;
	private String thumbBigUrl;
	private String commentCount;
	private String galleryUrl;
	private String galleryName;
	private String addedTime;
	private String likes;
	private String dislikes;
	private String rating;
	private String category1;
	private String category2;
	private String category3;
	private String category4;
	private String category5;
	private long xhamsterUserId;

	public XhamsterPicture() {
	}

	public XhamsterPicture(Long id) {
		this.id = id;
	}

	public XhamsterPicture(Long id, String title, String pictureName, String pictureUrl, String sideUrl, String userName, String userURL,
			String viewCount, String thumbSmallUrl, String thumbBigUrl, String commentCount, String galleryUrl, String galleryName,
			String addedTime, String likes, String dislikes, String rating, String category1, String category2, String category3,
			String category4, String category5, long xhamsterUserId) {
		this.id = id;
		this.title = title;
		this.pictureName = pictureName;
		this.pictureUrl = pictureUrl;
		this.sideUrl = sideUrl;
		this.userName = userName;
		this.userURL = userURL;
		this.viewCount = viewCount;
		this.thumbSmallUrl = thumbSmallUrl;
		this.thumbBigUrl = thumbBigUrl;
		this.commentCount = commentCount;
		this.galleryUrl = galleryUrl;
		this.galleryName = galleryName;
		this.addedTime = addedTime;
		this.likes = likes;
		this.dislikes = dislikes;
		this.rating = rating;
		this.category1 = category1;
		this.category2 = category2;
		this.category3 = category3;
		this.category4 = category4;
		this.category5 = category5;
		this.xhamsterUserId = xhamsterUserId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPictureName() {
		return pictureName;
	}

	public void setPictureName(String pictureName) {
		this.pictureName = pictureName;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public String getSideUrl() {
		return sideUrl;
	}

	public void setSideUrl(String sideUrl) {
		this.sideUrl = sideUrl;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserURL() {
		return userURL;
	}

	public void setUserURL(String userURL) {
		this.userURL = userURL;
	}

	public String getViewCount() {
		return viewCount;
	}

	public void setViewCount(String viewCount) {
		this.viewCount = viewCount;
	}

	public String getThumbSmallUrl() {
		return thumbSmallUrl;
	}

	public void setThumbSmallUrl(String thumbSmallUrl) {
		this.thumbSmallUrl = thumbSmallUrl;
	}

	public String getThumbBigUrl() {
		return thumbBigUrl;
	}

	public void setThumbBigUrl(String thumbBigUrl) {
		this.thumbBigUrl = thumbBigUrl;
	}

	public String getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(String commentCount) {
		this.commentCount = commentCount;
	}

	public String getGalleryUrl() {
		return galleryUrl;
	}

	public void setGalleryUrl(String galleryUrl) {
		this.galleryUrl = galleryUrl;
	}

	public String getGalleryName() {
		return galleryName;
	}

	public void setGalleryName(String galleryName) {
		this.galleryName = galleryName;
	}

	public String getAddedTime() {
		return addedTime;
	}

	public void setAddedTime(String addedTime) {
		this.addedTime = addedTime;
	}

	public String getLikes() {
		return likes;
	}

	public void setLikes(String likes) {
		this.likes = likes;
	}

	public String getDislikes() {
		return dislikes;
	}

	public void setDislikes(String dislikes) {
		this.dislikes = dislikes;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getCategory1() {
		return category1;
	}

	public void setCategory1(String category1) {
		this.category1 = category1;
	}

	public String getCategory2() {
		return category2;
	}

	public void setCategory2(String category2) {
		this.category2 = category2;
	}

	public String getCategory3() {
		return category3;
	}

	public void setCategory3(String category3) {
		this.category3 = category3;
	}

	public String getCategory4() {
		return category4;
	}

	public void setCategory4(String category4) {
		this.category4 = category4;
	}

	public String getCategory5() {
		return category5;
	}

	public void setCategory5(String category5) {
		this.category5 = category5;
	}

	public long getXhamsterUserId() {
		return xhamsterUserId;
	}

	public void setXhamsterUserId(long xhamsterUserId) {
		this.xhamsterUserId = xhamsterUserId;
	}

	public String getGsonString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}

}