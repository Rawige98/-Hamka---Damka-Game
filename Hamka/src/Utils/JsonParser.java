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

	private static JsonParser instance;
	
	private Gson gson;
	private GsonBuilder builder;
	
	public static JsonParser getInstance() {
		if(instance == null) {
			instance = new JsonParser();
		}
		return instance;
	}
	
	private JsonParser() {
		builder = new GsonBuilder();
		gson = builder.create();
	}
	
	public <T> List<T> parseJsonToList(String data, Object clazz){
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
	
	public Object parseToObject(String data , Object clazz) {
		try {
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
	
	public String parseObjectToJson(Object object) {
		try {
			Type type = null;
			if(object instanceof Board)
				type = new TypeToken<Board>(){}.getType();
			else if(object instanceof Game)
				type = new TypeToken<Game>(){}.getType();
			else if(object instanceof Player)
				type = new TypeToken<Player>(){}.getType();
			else if(object instanceof Question)
				type = new TypeToken<Question>(){}.getType();
			else if(object instanceof Tile)
				type = new TypeToken<Tile>(){}.getType();
			return gson.toJson(object, type);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	
	public String parseListToJson(Object clazz){
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
			return gson.toJson(clazz, type);
		} catch (JsonParseException e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
}


