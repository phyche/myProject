package com.test.common.enums;

/**
 * 图片配置
 * 
 * @author Administrator
 * 
 */
public enum ImageConfigEnum {
	
	member_logo_url(260l, 0l, 100l, 0l, 100l, 0l, "会员图像"), 
	member_bg_image(320l, 0l, 100l, 0l, 100l, 0l, "会员背景图片"), 
	_default(400l, 0l, 200l, 0l, 100l, 0l, "默认")
	
	;
	
	/**
	 * 大图 宽
	 */
	private long largeWidth;
	/**
	 * 大图 高
	 */
	private long largeHeight;
	/**
	 * 中图 宽
	 */
	private long mediumWidth;
	/**
	 * 中图 高
	 */
	private long mediumHeight;
	/**
	 * 小图 宽
	 */
	private long thumbnailWidth;
	/**
	 * 小图 高
	 */
	private long thumbnailHeight;
	
	/**
	 * 描述
	 */
	private String des;
	
	private ImageConfigEnum(long largeWidth, long largeHeight,
							long mediumWidth, long mediumHeight,
							long thumbnailWidth, long thumbnailHeight,
							String des){
		this.largeWidth = largeWidth;
		this.largeHeight = largeHeight;
		this.mediumWidth = mediumWidth;
		this.mediumHeight = mediumHeight;
		this.thumbnailWidth = thumbnailWidth;
		this.thumbnailHeight = thumbnailHeight;
		this.des = des;
	}
	public long getLargeWidth() {
		return largeWidth;
	}
	public void setLargeWidth(long largeWidth) {
		this.largeWidth = largeWidth;
	}

	public long getLargeHeight() {
		return largeHeight;
	}
	public void setLargeHeight(long largeHeight) {
		this.largeHeight = largeHeight;
	}


	public long getMediumWidth() {
		return mediumWidth;
	}
	public void setMediumWidth(long mediumWidth) {
		this.mediumWidth = mediumWidth;
	}

	public long getMediumHeight() {
		return mediumHeight;
	}
	public void setMediumHeight(long mediumHeight) {
		this.mediumHeight = mediumHeight;
	}

	public long getThumbnailWidth() {
		return thumbnailWidth;
	}

	public void setThumbnailWidth(long thumbnailWidth) {
		this.thumbnailWidth = thumbnailWidth;
	}

	public long getThumbnailHeight() {
		return thumbnailHeight;
	}

	public void setThumbnailHeight(long thumbnailHeight) {
		this.thumbnailHeight = thumbnailHeight;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public static ImageConfigEnum getByName(String name){
		if(name!=null){
			for(ImageConfigEnum yn:values()){
				if(yn.name().equals(name)){
					return yn;
				}
			}
		}
		return ImageConfigEnum._default;
	}
	
}
