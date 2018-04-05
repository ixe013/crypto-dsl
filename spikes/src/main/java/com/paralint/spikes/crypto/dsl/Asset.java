package com.paralint.spikes.crypto.dsl;

import java.util.HashMap;
import java.util.Map;

public class Asset {
	
	Map<String, Key> keys;
	
	public Asset() {
		keys = new HashMap<String, Key>();
		keys.put("rc2", new Key());
		keys.put("hmac", new Key());
		keys.put("opk", new Key());
	}
	public String emp() {
		return "cn.w.a.12313.tmc";
	}

	public Key getKey(String name) {
		return keys.get(name);
	}
}
