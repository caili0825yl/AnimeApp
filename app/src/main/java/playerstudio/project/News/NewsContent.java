package playerstudio.project.News;

public class NewsContent {
    private String title;
    private String image;
    private String content;


    public NewsContent(String title,String image,String content){
        this.title=title;
        this.image=image;
        this.content=content;

    }

    public String getContent() {
        return content;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }


}
