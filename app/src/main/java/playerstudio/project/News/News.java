package playerstudio.project.News;

import java.util.Date;

public class News {
    private String title;
    private String type;

    private String font;
    private Date date;
    public long id;
    public News(long id,String type,String title,String font,Date date){
        this.font=font;
        this.title=title;
this.id=id;
this.date=date;
this.type=type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
