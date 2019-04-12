package playerstudio.project.gson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;


public class Utility {
    public static List<GsonAnime> parseJsonWithGson(final String requestText){
        Gson gson = new Gson();


        return gson.fromJson(requestText, new TypeToken<List<GsonAnime>>(){}.getType());


    }

    public static List<GsonNews> parseJsonWithGson1(final String requestText){
        Gson gson = new Gson();


        return gson.fromJson(requestText, new TypeToken<List<GsonNews>>(){}.getType());


    }
    public static List<AnimeN>  parseJsonWithGson2(final String requestText){
        Gson gson = new Gson();


        return gson.fromJson(requestText, new TypeToken<List<AnimeN>>(){}.getType());


    }
}
