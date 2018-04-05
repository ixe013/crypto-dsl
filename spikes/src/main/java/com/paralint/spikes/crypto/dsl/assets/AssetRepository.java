package com.paralint.spikes.crypto.dsl.assets;

import java.util.HashMap;
import java.util.Map;

/*
 * Place holder class that would be your asset registry, looking up assets in a database.
 */
public class AssetRepository {
	final Map<String, Asset> repository;

	public AssetRepository () {
		this.repository = new HashMap<String, Asset>();
		
		//Hardcoded assets
		this.repository.put("asset1", new Asset("asset1", "hmac", "rc2", "3des"));
		this.repository.put("asset2", new Asset("asset2", "hmac", "rc2", "3des"));
		this.repository.put("asset3", new Asset("asset3", "hmac", "rc2", "3des"));
		this.repository.put("asset4", new Asset("asset4", "hmac", "rc2", "3des"));

		this.repository.put("assetA", new Asset("assetA", "aes"));
		this.repository.put("assetB", new Asset("assetB"));

		this.repository.put("assetZ", new Asset("assetZ", "rsa"));
	}
	
	public Asset findAsset(String name) {
		return this.repository.get(name);
	}

}
