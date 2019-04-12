package playerstudio.project.News;

public class Content {
    private  String title;
    private  String ltitle;
    private  String image;
    private  String content;

    public Content(String title,String ltitle,String image,String content){
        this.image=image;
        this.title=title;
        this.ltitle=ltitle;
        this.content=content;

    }

    public String getTitle() {
        return title;
    }

    public String getImage() {

        return image;
    }

    public String getContent() {
        return content;
    }

    public String getLtitle() {
        return ltitle;
    }
}
