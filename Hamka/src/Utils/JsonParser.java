package Utils;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import Model.Board;
import Model.Game;
import Model.Player;
import Model.Question;
import Model.Tile;
public class JsonParser {

	public static<T> List<T> parseToArray(String data, Object clazz){
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		Type type = null;
		try {
			if(clazz instanceof Board)
				type = new TypeToken<List<Board>>(){}.getType();
			else if(clazz instanceof Game)
				type = new TypeToken<List<Game>>(){}.getType();
			else if(clazz instanceof Player)
				type = new TypeToken<List<Player>>(){}.getType();
			else if(clazz instanceof Question)
				type = new TypeToken<List<Question>>(){}.getType();
			else if(clazz instanceof Tile)
				type = new TypeToken<List<Tile>>(){}.getType();
			List<T> parsedList = gson.fromJson(data, type);
			return parsedList;
		} catch (JsonParseException e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	
	public static Object parseToObject(String data , Object clazz) {
		try {
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();
			Object parsedObject;
			Type type = null;
			if(clazz instanceof Board)
				type = new TypeToken<Board>(){}.getType();
			else if(clazz instanceof Game)
				type = new TypeToken<Game>(){}.getType();
			else if(clazz instanceof Player)
				type = new TypeToken<Player>(){}.getType();
			else if(clazz instanceof Question)
				type = new TypeToken<Question>(){}.getType();
			else if(clazz instanceof Tile)
				type = new TypeToken<Tile>(){}.getType();
			parsedObject = gson.fromJson(data, type);
			return parsedObject;
		} catch (JsonParseException e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
}


