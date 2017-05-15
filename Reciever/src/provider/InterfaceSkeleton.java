package provider;

import javax.json.JsonObject;

public interface InterfaceSkeleton {
	public JsonObject handle(byte[] buffer, int length);
}
