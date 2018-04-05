package com.paralint.spikes.crypto.dsl.engine;

import com.paralint.spikes.crypto.dsl.assets.Asset;
import com.paralint.spikes.crypto.dsl.keys.Key;
import com.paralint.spikes.crypto.dsl.transformations.Appender;

public class Pipeline {
	public static byte[] getKeyInFormatXYZ() {
		
		//Get the asset like we actually do
		Asset asset = new Asset("demo", "3des");
		//Get the key from the request, like we do
		Key key = asset.getKey("opk");

		TransformationEngine transformer = TransformationEngine.format(asset, key);
		
		return transformer
			.append("000000")
			.append("0xFF")
			.addStep(new Appender(), "112233")
			.build()
			;
	}
}
