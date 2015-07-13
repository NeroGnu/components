package org.buaa.xuxian.net.broadcast;

import net.sf.json.JSONObject;

public interface JsonObjectSwitch<T> {
	public JSONObject toJson();
	public T toObject(JSONObject json);
}
