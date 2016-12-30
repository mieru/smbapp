package utils;

import java.lang.reflect.Field;

import org.json.JSONException;
import org.json.JSONObject;

public class SmbJSONUtils<T> {

//	public JSONObject entityToJSONObject(T entity) throws JSONException, IllegalArgumentException, IllegalAccessException {
//			JSONObject jsonObject = new JSONObject();
//			for (Field field : entity.getClass().getDeclaredFields()) {
//				if(field.get(entity) == null) continue;
//				field.setAccessible(true);
//				jsonObject.put(field.getName(), field.get(entity));
//			}
//			return jsonObject;
//	}
}
