package events;


public class EventGalleryIDLoaded {
    private Long id;
    public EventGalleryIDLoaded(Long id){
        this.id=id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
