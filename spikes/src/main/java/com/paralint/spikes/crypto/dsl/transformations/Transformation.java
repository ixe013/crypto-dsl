package com.paralint.spikes.crypto.dsl;

import java.util.Map;

public interface Transformation {
	/*
	 * The Asset is read-only
	 * key is the key as it is right now (with previous transformations applied). If the original key
	 * is required, get it from the asset.
	 * Context can be used to pass parameters from one transform to another
	 */
	public Key Transform(Map<String, Object> context, Asset asset, Key key, String[] params);

}
