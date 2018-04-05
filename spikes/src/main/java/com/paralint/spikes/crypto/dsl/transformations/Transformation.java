package com.paralint.spikes.crypto.dsl.transformations;

import java.util.Map;

import com.paralint.spikes.crypto.dsl.assets.Asset;
import com.paralint.spikes.crypto.dsl.keys.Key;

public interface Transformation {
	/*
	 * The Asset is read-only
	 * key is the key as it is right now (with previous transformations applied). If the original key
	 * is required, get it from the asset.
	 * Context can be used to pass parameters from one transform to another
	 */
	public Key Transform(Map<String, Object> context, Asset asset, Key key, Object[] params);

}
