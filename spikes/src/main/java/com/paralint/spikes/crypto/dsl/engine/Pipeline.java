package com.paralint.spikes.crypto.dsl.engine;

import groovy.lang.GroovyShell;
import groovy.lang.Binding;

import java.io.File;
import java.io.IOException;

import org.codehaus.groovy.control.CompilationFailedException;

import com.paralint.spikes.crypto.dsl.assets.Asset;
import com.paralint.spikes.crypto.dsl.assets.AssetRepository;
import com.paralint.spikes.crypto.dsl.keys.Key;

public class Pipeline {
	public static final String REPOSITORY_OBJECT = "repository";
	public static final String ASSET_OBJECT = "asset";
	public static final String KEY_OBJECT = "key";
	public static final String ENGINE_OBJECT = "engine";

	public static String processCryptoDSLFile(String name) {
		Binding binding = new Binding();
		GroovyShell shell = new GroovyShell(binding);

		AssetRepository repository = new AssetRepository();

		Asset a1 = repository.findAsset("asset1");
		Key k1 = a1.getKey("hmac");
		
		//Make the TransformationEngine available to the script
		binding.setProperty(ENGINE_OBJECT, new TransformationEngine());
		binding.setProperty(REPOSITORY_OBJECT, repository);
		binding.setProperty(ASSET_OBJECT, a1);
		binding.setProperty(KEY_OBJECT, k1);

		File file = new File(name);

		try {
			shell.evaluate(file);
		} catch (CompilationFailedException cfe) {
			System.err.println(String.format("File %s does not compile. %s", name, cfe.getMessage()));
		} catch (IOException ioe) {
			System.err.println(String.format("Could not read from file %s. %s", name, ioe.getMessage()));
		}

		return "I will deal with the return type later";
	}
}
