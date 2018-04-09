package com.paralint.spikes.crypto.dsl.engine;

import java.lang.reflect.InvocationTargetException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.paralint.spikes.crypto.dsl.assets.Asset;
import com.paralint.spikes.crypto.dsl.keys.Key;
import com.paralint.spikes.crypto.dsl.transformations.Transformation;
import com.paralint.spikes.crypto.dsl.transformations.sandbox.BaseSandboxedTransformation;

import groovy.lang.MissingMethodException;

public class TransformationEngine {
	private final Key key;
	private final Asset asset;

	List<Entry<Transformation, Object[]>> transformations;
	Map<String, Object> context = new HashMap<String, Object>();

	public static final String OUTPUT_DEFAULT_NAME = "key";
	
	static public Entry<Transformation, Object[]> queueTransformation(Transformation t, Object[] params) {
		Entry<Transformation, Object[]> transformAndParam = new SimpleEntry<>(t,params);
		return transformAndParam;	
	}

	public TransformationEngine() {
		//FIXME: Will disapear once I learn to inject functions 
		//(not methods) in the Groovy Shell
		this.asset = null;
		this.key = null;
		this.clear();
	}

	private TransformationEngine(Asset asset, Key key) {
		this.asset = asset;
		this.key = key;
		this.clear();
	}

	static TransformationEngine format(Asset asset, Key key) {
		TransformationEngine te = new TransformationEngine(asset, key);
		te.transformations = new ArrayList<>();
		return te;
	}

	/*
	 * Collects missing method names as calls to a Java defined transformation.
	 * 
	 * This method is called by GroovyShell when a method is not found on
	 * TransformationEngine.
	 * 
	 */
	public Object methodMissing(String name, Object args) throws ClassNotFoundException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// By convention, we search for a class that has the same name as the
		// missing method
        String capitalFirstLetter   = Character.toString(name.charAt(0)).toUpperCase();
        String className = capitalFirstLetter + name.substring(1);

		// We only look for that classe in the "sandbox" package
        String fullyQualifiedClassName = BaseSandboxedTransformation.class.getPackage().getName() + "." + className;
        
        try {
            Class<?> transformation = Class.forName(fullyQualifiedClassName);
            
			System.out.println("Adding transformation " + transformation.getName());
			this.transformations
					.add(queueTransformation((Transformation) transformation.newInstance(), (Object[]) args));
        } catch (ClassNotFoundException cnfe) {
        	System.err.println("Transformation "+fullyQualifiedClassName+" not found");
			MissingMethodException mme = new MissingMethodException(className, BaseSandboxedTransformation.class,
					(Object[]) args);
			throw mme;
        } catch(InstantiationException ie) {
        	System.err.println("Unable to instanciate class "+fullyQualifiedClassName);
			MissingMethodException mme = new MissingMethodException(className, BaseSandboxedTransformation.class,
					(Object[]) args);
			throw mme;
        } catch (SecurityException se) {
			System.err.println("Java security exception trying to call the constrcutor of " + fullyQualifiedClassName);
			MissingMethodException mme = new MissingMethodException(className, BaseSandboxedTransformation.class,
					(Object[]) args);
			throw mme;
        }

        return this;
    }

	public void clear() {
		this.transformations = new ArrayList<>();
	}

	public byte[] save() {
		return save(OUTPUT_DEFAULT_NAME);
	}
	
	/*
	 * This method executes the transformation pipeline.
	 */
	public byte[] save(String name) {
		Key result = this.key;
		Map<String, Object> context = new HashMap<String, Object>();

		for (Entry<Transformation, Object[]> transformAndParam : this.transformations) {
			//*
			Object[] params = transformAndParam.getValue();
			Transformation transform = transformAndParam.getKey();
			result = transform.Transform(context, asset, result, params);
			/*/
			//Same thing as above but on a single line. Less convenient for breakpoints...
			transform.getKey().Transform(context, asset, result, transform.getValue());
			//*/
		}
		
		//Should save the result under name
		return result.get();
	}
}
