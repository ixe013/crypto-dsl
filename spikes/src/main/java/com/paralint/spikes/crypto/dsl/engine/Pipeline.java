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
import groovy.lang.Closure;
import groovy.lang.GroovyRuntimeException;
import groovy.lang.GroovyShell;
import groovy.lang.MissingMethodException;

public class Pipeline {
	public static final String REPOSITORY_OBJECT = "repository";
	public static final String ASSET_OBJECT = "asset";
	public static final String KEY_OBJECT = "key";
	public static final String FORMAT_CLOSURE = "format";

	public static String processCryptoDSLFile(String name) {
		Binding binding = new Binding();

		// Instanciate the objects that will be available in the script
		AssetRepository repository = new AssetRepository();
		Asset a1 = repository.findAsset("asset1");
		Key k1 = a1.getKey("hmac");
		Closure<TransformationEngine> format = new TransformationEngineClosure(null);
		
		// Assign variable names to the objects we prepared
		binding.setProperty(FORMAT_CLOSURE, format);
		binding.setProperty(REPOSITORY_OBJECT, repository);
		binding.setProperty(ASSET_OBJECT, a1);
		binding.setProperty(KEY_OBJECT, k1);

		// Add a white listing filter
		PipelineSandboxFilter sandboxFilter = new PipelineSandboxFilter();
		sandboxFilter.register();

		// Prepare the Groovy compiler and interpreter (shell)
		CompilerConfiguration compilerConfig = new CompilerConfiguration();
		compilerConfig.addCompilationCustomizers(new SandboxTransformer());
		GroovyShell shell = new GroovyShell(binding, compilerConfig);

		// Load the script file
		File file = new File(name);

		// Try to run the script
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
