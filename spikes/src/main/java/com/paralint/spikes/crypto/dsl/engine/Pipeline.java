package com.paralint.spikes.crypto.dsl.engine;

import java.io.File;
import java.io.IOException;

import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.kohsuke.groovy.sandbox.SandboxTransformer;

import com.paralint.spikes.crypto.dsl.assets.Asset;
import com.paralint.spikes.crypto.dsl.assets.AssetRepository;
import com.paralint.spikes.crypto.dsl.keys.Key;

import groovy.lang.Binding;
import groovy.lang.GroovyRuntimeException;
import groovy.lang.GroovyShell;
import groovy.lang.MissingMethodException;

public class Pipeline {
	public static final String REPOSITORY_OBJECT = "repository";
	public static final String ASSET_OBJECT = "asset";
	public static final String KEY_OBJECT = "key";
	public static final String ENGINE_OBJECT = "engine";

	public static String processCryptoDSLFile(String name) {
		Binding binding = new Binding();

		CompilerConfiguration compilerConfig = new CompilerConfiguration();
		compilerConfig.addCompilationCustomizers(new SandboxTransformer());

		GroovyShell shell = new GroovyShell(binding, compilerConfig);

		AssetRepository repository = new AssetRepository();

		Asset a1 = repository.findAsset("asset1");
		Key k1 = a1.getKey("hmac");
		
		//Make the TransformationEngine available to the script
		binding.setProperty(ENGINE_OBJECT, new TransformationEngine());
		binding.setProperty(REPOSITORY_OBJECT, repository);
		binding.setProperty(ASSET_OBJECT, a1);
		binding.setProperty(KEY_OBJECT, k1);

		PipelineSandboxFilter sandboxFilter = new PipelineSandboxFilter();
		sandboxFilter.register();

		File file = new File(name);

		try {
			shell.evaluate(file);
		} catch (CompilationFailedException cfe) {
			System.err.println(String.format("File %s does not compile. %s", name, cfe.getMessage()));
		} catch (IOException ioe) {
			System.err.println(String.format("Could not read from file %s. %s", name, ioe.getMessage()));
		} catch (MissingMethodException mme) {
			System.err.println(String.format("Script %s calls undefined transformation %s", name, mme.getMethod()));
		} catch (GroovyRuntimeException gre) {
			System.err.println(String.format("Script %s failed with error: %s", name, gre.getMessage()));
		} catch (SecurityException se) {
			System.err.println(
					String.format("Script %s terminated because of insecure behavior: %s", name, se.getMessage()));
		}

		System.out.println("Script execution completed");

		return "I will deal with the return type later";
	}
}
