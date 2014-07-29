package usercentral;

import dao.XhamsterGallery;
import dao.XhamsterVideo;

public class UpdateItem {

	public static int TYPE_VIDEO = 1;
	public static int TYPE_PICTURES = 2;

	private String title;
	private int type;
	private String link;
	private String thumbUrl;
	private int id;

	public UpdateItem(String title, int type, String link, String thumbUrl, int id) {
		super();
		this.title = title;
		this.type = type;
		this.link = link;
		this.thumbUrl = thumbUrl;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTypeString() {
		if (this.type == TYPE_PICTURES) {
			return "Picture Gallery";
		} else {
			return "Video";
		}

	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getThumbUrl() {
		return thumbUrl;
	}

	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}

	public UpdateItem(String title, int type, String link, String thumbUrl) {
		super();
		this.title = title;
		this.type = type;
		this.link = link;
		this.thumbUrl = thumbUrl;
	}

	public XhamsterVideo getVideo() {
		XhamsterVideo video = new XhamsterVideo(id);
		video.setTitle(title);
		video.setThumbUrl(thumbUrl);
		video.setSiteUrl(link);
		return video;
	}
	
	public XhamsterGallery getGallery(){
		XhamsterGallery gallery = new XhamsterGallery(id);
		gallery.setTitle(title);
		gallery.setThumbSmallUrl(thumbUrl);
		gallery.setPictureUrl(link);
		return gallery;
	}

}
