package playerstudio.project.List;

public class Content {
    private  String name;
    private  String image;
    private  String tv;
    private  String staff;
    private  String  voice;
    private  String js;


    public Content(String image,String name,String tv,String staff,String voice,String js){
        this.image=image;
        this.name=name;
        this.tv=tv;
        this.staff=staff;
        this.voice=voice;
        this.js=js;
    }
    public String getTv() {
        return tv;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getJs() {
        return js;
    }

    public String getStaff() {
        return staff;
    }

    public String getVoice() {
        return voice;
    }

    public void setTv(String tv) {
        this.tv = tv;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setJs(String js) {
        this.js = js;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }


}
