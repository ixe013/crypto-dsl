package com.paralint.spikes.crypto.dsl.assets;

import java.util.HashMap;
import java.util.Map;

import com.paralint.spikes.crypto.dsl.keys.Key;

public class Asset {
	
	private String name;
	private Map<String, Key> keys;
	
	public Asset(String name, String... keytypes) {
		this.name = name;
		
		keys = new HashMap<String, Key>();
		
		for(String type : keytypes) {
            keys.put(type, new Key());
		}
	}

	public String name() {
		return this.name;
	}

	public void setKey(String name, byte[] raw) {
		Key key = this.getKey(name);
		
		keys.put(name, key.clone(raw));
	}

	public Key getKey(String name) {
		return keys.get(name);
	}
}
