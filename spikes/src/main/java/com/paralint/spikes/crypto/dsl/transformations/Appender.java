package com.paralint.spikes.crypto.dsl.transformations;

import java.util.Arrays;
import java.util.Map;

import com.paralint.spikes.crypto.dsl.assets.Asset;
import com.paralint.spikes.crypto.dsl.keys.Key;

public class Appender implements Transformation {

	public static final String PREFIX = "0x";
	@Override
	public Key Transform(Map<String, Object> context, Asset asset, Key key, Object[] params) {
		//assert params have 1 item
		//assert param[0] is a string
		String padSpec = (String) params[0];

		return pad(context, asset, key, padSpec);
	}

	public Key pad(Map<String, Object> context, Asset asset, Key key, String padSpec) {

		byte[] padding;
		
		if (padSpec.startsWith(PREFIX)) {
			
			//The string is raw hex
			//assert parms lenght is even
		    padding = new byte[padSpec.length() / 2 - PREFIX.length()];
		    for (int i = PREFIX.length(); i < padding.length; i++) {
		      int index = i * 2;
		      int v = Integer.parseInt(padSpec.substring(index, index + 2), 16);
		      padding[i] = (byte) v;
		    }
		} else {
			padding = padSpec.getBytes();
		}
		
	    byte[] paddedKey = Arrays.copyOf(key.get(), key.get().length + padding.length);
	    System.arraycopy(padding, 0, paddedKey, key.get().length, padding.length);

		return key.clone(paddedKey);
	}

}
