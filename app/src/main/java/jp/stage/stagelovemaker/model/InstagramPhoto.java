package jp.stage.stagelovemaker.model;

/**
 * Created by congn on 8/4/2017.
 */

public class InstagramPhoto {
    private String id;
    private String low_resolution_url;
    private String thumbnail_url;
    private String standard_resolution_url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLow_resolution_url() {
        return low_resolution_url;
    }

    public void setLow_resolution_url(String low_resolution_url) {
        this.low_resolution_url = low_resolution_url;
    }

    public String getThumbnail_url() {
        return thumbnail_url;
    }

    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }

    public String getStandard_resolution_url() {
        return standard_resolution_url;
    }

    public void setStandard_resolution_url(String standard_resolution_url) {
        this.standard_resolution_url = standard_resolution_url;
    }
}
