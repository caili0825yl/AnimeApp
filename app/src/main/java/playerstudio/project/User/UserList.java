package playerstudio.project.User;

public class UserList {
    private String menu;
    private int image;

    public  UserList(String  menu,int image){
        this.menu=menu;
        this.image=image;
    }

    public int getImage() {
        return image;
    }

    public String getMenu() {
        return menu;
    }
}
