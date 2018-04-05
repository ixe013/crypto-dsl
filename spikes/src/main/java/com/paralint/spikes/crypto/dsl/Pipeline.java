package com.paralint.spikes.crypto.dsl;

public class Pipeline {
	static byte[] getKeyInFormatXYZ() {
		
		//Get the asset like we actually do
		Asset asset = new Asset();
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
