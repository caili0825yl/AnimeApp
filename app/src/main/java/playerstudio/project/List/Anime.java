package playerstudio.project.List;

public class Anime {
    private String font;
    private String name;
    private String platform;
    private String url;
    private long id;

    public Anime(){

    }
    public Anime(long id,String image,String name,String platform,String url){
        this.id=id;
        this.font=image;
        this.name=name;
        this.platform=platform;
        this.url=url;
    }
    public String getFont() {
        return font;
    }

    public String getName() {
        return name;
    }



    public void setFont(String image) {
        this.font = image;
    }

    public void setName(String name) {
        this.name = name;
    }



    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
