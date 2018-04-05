package com.paralint.spikes.crypto.dsl;

import java.util.Arrays;
import java.util.Map;

public class Appender implements Transformation {

	public static final String PREFIX = "0x";
	@Override
	public Key Transform(Map<String, Object> context, Asset asset, Key key, String[] params) {

		Key result = key;
		
		byte[] padding;
		
		//assert params have 1 item
		if (params[0].startsWith(PREFIX)) {
			
			//The string is raw hex
			//assert parms lenght is even
		    padding = new byte[params[0].length() / 2 - PREFIX.length()];
		    for (int i = PREFIX.length(); i < padding.length; i++) {
		      int index = i * 2;
		      int v = Integer.parseInt(params[0].substring(index, index + 2), 16);
		      padding[i] = (byte) v;
		    }
		} else {
			padding = params[0].getBytes();
		}
		
	    byte[] paddedKey = Arrays.copyOf(key.get(), key.get().length + padding.length);
	    System.arraycopy(padding, 0, paddedKey, key.get().length, padding.length);

		result.set(paddedKey);

		return result;
	}

}
