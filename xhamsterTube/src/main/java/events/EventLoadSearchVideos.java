package events;


public class EventLoadSearchVideos {
    String query;
    String category;
    String sorting;
    String date;
    String duration;
    String quality;

    public EventLoadSearchVideos(String query, String category, String sorting, String date, String duration, String quality) {
        this.query = query;
        this.category = category;
        this.sorting = sorting;
        this.date = date;
        this.duration = duration;
        this.quality = quality;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSorting() {
        return sorting;
    }

    public void setSorting(String sorting) {
        this.sorting = sorting;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }
}
