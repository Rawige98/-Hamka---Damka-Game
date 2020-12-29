package Utils;

import java.lang.reflect.Type;
import java.util.List;
import org.omg.CORBA.IRObject;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import Model.BlackSoldier;
import Model.BlackTile;
import Model.Board;
import Model.Game;
import Model.Player;
import Model.Question;
import Model.RuntimeTypeAdapterFactory;
import Model.Tile;
import Model.WhiteSoldier;
import Model.WhiteTile;

/**
 * JsonParser is a generic singleton class applyed to parse the JsonObject /
 * JsonObject to List/Object of one of the project classes that are saved in a
 * text files formated as Json, that exists in JsonFiles package
 * 
 * @author rawix
 *
 */
public class JsonParser {

	private static JsonParser instance;
	private Gson gson;
	private GsonBuilder builder;

	private JsonParser() {
		builder = new GsonBuilder();
		builder.setPrettyPrinting();
//		RuntimeTypeAdapterFactory<Tile> adapter = RuntimeTypeAdapterFactory.of(Tile.class, Tile.class.getName())
//				.registerSubtype(Tile.class, Tile.class.getName())
//				.registerSubtype(BlackTile.class, BlackTile.class.getName())
//				.registerSubtype(BlackSoldier.class, BlackSoldier.class.getName())
//				.registerSubtype(WhiteTile.class, WhiteTile.class.getName())
//				.registerSubtype(WhiteSoldier.class, WhiteSoldier.class.getName());
//		builder.registerTypeAdapterFactory(adapter);
		builder.registerTypeAdapter(Tile[][].class, new InterfaceAdapter<Tile[][]>());
		gson = builder.setExclusionStrategies(new ExclusionStrategy() {
			@Override
			public boolean shouldSkipField(FieldAttributes f) {
				return f.getDeclaringClass().equals(IRObject.class);
			}
			@Override
			public boolean shouldSkipClass(Class<?> clazz) {
				return false;
			}
		}).create();
		;
	}
	public static JsonParser getInstance() {
		if (instance == null)
			instance = new JsonParser();
		return instance;
	}
	/**
	 * This method parses JsonArray to List of objects
	 * 
	 * @param       <T> the generic type of the class
	 * @param data  string parameter that contains the JsonArray that had been read
	 *              from a text file
	 * @param clazz an object that describes witch class we want to parse the
	 *              JsonArray to
	 * @return List contains parsed objects of the specific class
	 */
	public <T> List<T> parseToList(String data, Object clazz) {
		try {
			Type type = null;
			if (clazz instanceof Board)
				type = new TypeToken<List<Board>>() {
				}.getType();
			else if (clazz instanceof Game)
				type = new TypeToken<List<Game>>() {
				}.getType();
			else if (clazz instanceof Player)
				type = new TypeToken<List<Player>>() {
				}.getType();
			else if (clazz instanceof Question)
				type = new TypeToken<List<Question>>() {
				}.getType();
			else if (clazz instanceof BlackSoldier)
				type = new TypeToken<BlackSoldier>() {
				}.getType();
			else if (clazz instanceof BlackTile)
				type = new TypeToken<BlackTile>() {
				}.getType();
			else if (clazz instanceof WhiteSoldier)
				type = new TypeToken<WhiteSoldier>() {
				}.getType();
			else if (clazz instanceof WhiteTile)
				type = new TypeToken<WhiteTile>() {
				}.getType();
			else if (clazz instanceof Tile)
				type = new TypeToken<Tile>() {
				}.getType();
			else if (clazz instanceof String)
				type = new TypeToken<List<String>>() {
				}.getType();
			List<T> parsedList = gson.fromJson(data, type);
			return parsedList;
		} catch (JsonParseException e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * This method parses JsonObject to one object
	 * 
	 * @param       <T> the generic type of the class
	 * @param data  string parameter that contains the JsonObject that had been read
	 *              from a text file
	 * @param clazz an object that describes witch class we want to parse the
	 *              JsonObject to
	 * @return parsed object of the specific class
	 */
	public Object parseToObject(String data, Object clazz) {
		try {
			Object parsedObject;
			Type type = null;
			if (clazz instanceof Board)
				type = new TypeToken<Board>() {
				}.getType();
			else if (clazz instanceof Game)
				type = new TypeToken<Game>() {
				}.getType();
			else if (clazz instanceof Player)
				type = new TypeToken<Player>() {
				}.getType();
			else if (clazz instanceof Question)
				type = new TypeToken<Question>() {
				}.getType();
			else if (clazz instanceof BlackSoldier)
				type = new TypeToken<BlackSoldier>() {
				}.getType();
			else if (clazz instanceof BlackTile)
				type = new TypeToken<BlackTile>() {
				}.getType();
			else if (clazz instanceof WhiteSoldier)
				type = new TypeToken<WhiteSoldier>() {
				}.getType();
			else if (clazz instanceof WhiteTile)
				type = new TypeToken<WhiteTile>() {
				}.getType();
			else if (clazz instanceof Tile)
				type = new TypeToken<Tile>() {
				}.getType();
			else if (clazz instanceof String)
				type = new TypeToken<String>() {
				}.getType();
			parsedObject = gson.fromJson(data, type);
			return parsedObject;
		} catch (JsonParseException e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * This method parse an object to JsonObject format
	 * 
	 * @param             <T> the generic type of the class
	 * @param objectsList list of object we want to parse
	 * @param clazz       object that desctibes the class of the list elements
	 * @return string contains the list in a json format
	 */
	public <T> String parseListToJsonArray(List<T> objectsList, Object clazz) {
		try {
			Type type = null;
			if (clazz instanceof Board)
				type = new TypeToken<List<Board>>() {
				}.getType();
			else if (clazz instanceof Game)
				type = new TypeToken<List<Game>>() {
				}.getType();
			else if (clazz instanceof Player)
				type = new TypeToken<List<Player>>() {
				}.getType();
			else if (clazz instanceof Question)
				type = new TypeToken<List<Question>>() {
				}.getType();
			else if (clazz instanceof BlackSoldier)
				type = new TypeToken<BlackSoldier>() {
				}.getType();
			else if (clazz instanceof BlackTile)
				type = new TypeToken<BlackTile>() {
				}.getType();
			else if (clazz instanceof WhiteSoldier)
				type = new TypeToken<WhiteSoldier>() {
				}.getType();
			else if (clazz instanceof WhiteTile)
				type = new TypeToken<WhiteTile>() {
				}.getType();
			else if (clazz instanceof Tile)
				type = new TypeToken<Tile>() {
				}.getType();
			else if (clazz instanceof String)
				type = new TypeToken<List<String>>() {
				}.getType();
			String parsedList = gson.toJson(objectsList, type);
			return parsedList;
		} catch (JsonParseException e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * This method parse an object to JsonObject format
	 * 
	 * @param object the object that will be parsed
	 * @return string that contains the object in json format
	 */
	public String parseObjectToJsonObject(Object object) {
		try {
			String jsonData;
			Type type = null;
			if (object instanceof Board)
				type = new TypeToken<Board>() {
				}.getType();
			else if (object instanceof Game)
				type = new TypeToken<Game>() {
				}.getType();
			else if (object instanceof Player)
				type = new TypeToken<Player>() {
				}.getType();
			else if (object instanceof Question)
				type = new TypeToken<Question>() {
				}.getType();
			else if (object instanceof BlackSoldier)
				type = new TypeToken<BlackSoldier>() {
				}.getType();
			else if (object instanceof BlackTile)
				type = new TypeToken<BlackTile>() {
				}.getType();
			else if (object instanceof WhiteSoldier)
				type = new TypeToken<WhiteSoldier>() {
				}.getType();
			else if (object instanceof WhiteTile)
				type = new TypeToken<WhiteTile>() {
				}.getType();
			else if (object instanceof Tile)
				type = new TypeToken<Tile>() {
				}.getType();
			jsonData = gson.toJson(object, type);
			return jsonData;
		} catch (JsonParseException e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

}