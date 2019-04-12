package playerstudio.project.gson;

import java.util.Date;

public class AnimeN {
    private String  title;
    private long id;
    private String font;
    private String type;
    private Date date;
 public AnimeN(String title,String image,long id){
     this.title=title;
     this.font=image;
     this.id=id;
 }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public String getFont() {
        return font;
    }
}
