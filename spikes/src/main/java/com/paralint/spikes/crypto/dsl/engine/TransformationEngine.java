package com.paralint.spikes.crypto.dsl.engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.paralint.spikes.crypto.dsl.assets.Asset;
import com.paralint.spikes.crypto.dsl.keys.Key;
import com.paralint.spikes.crypto.dsl.transformations.Appender;
import com.paralint.spikes.crypto.dsl.transformations.ToHex;
import com.paralint.spikes.crypto.dsl.transformations.Transformation;

import java.util.AbstractMap.SimpleEntry;

public class TransformationEngine {
	private final Key key;
	private final Asset asset;

	List<Entry<Transformation, String[]>> transformations = new ArrayList<>();
	Map<String, Object> context = new HashMap<String, Object>();

	public static final String OUTPUT_DEFAULT_NAME = "key";
	
	static public Entry<Transformation, String[]> createTransformationAndParam(Transformation t) {
		return createTransformationAndParam(t, (String[])null);
	}

	static public Entry<Transformation, String[]> createTransformationAndParam(Transformation t, String p) {
		String[] params = { p };
		return createTransformationAndParam(t, params);
	}

	static public Entry<Transformation, String[]> createTransformationAndParam(Transformation t, String[] params) {
		Entry<Transformation, String[]> transformAndParam = new SimpleEntry<>(t,params);
		return transformAndParam;	
	}

	public TransformationEngine() {
		//FIXME: Will disapear once I learn to inject functions 
		//(not methods) in the Groovy Shell
		this.asset = null;
		this.key = null;
	}

	private TransformationEngine(Asset asset, Key key) {
		this.asset = asset;
		this.key = key;
	}

	static TransformationEngine format(Asset asset, Key key) {
		TransformationEngine te = new TransformationEngine(asset, key);
		te.transformations = new ArrayList<>();
		return te;
	}

	//FIXME: START Dummy methods to test Groovy calling
	public void sayHello(String dude) {
		System.out.println("Hello "+dude);
	}

	public String whoShouldIGreet() {
		return "World";
	}
	
	public TransformationEngine compose(String step) {
		System.out.println(step);
		return this;
	}

    public Object propertyMissing(String name) {
        String msg = "Tried to access missing property " + name;
        System.err.println(msg);
        return msg;
    }
    public Object methodMissing(String name, Object args) {
        List<Object> argsList = Arrays.asList((Object[]) args);
        String msg = "methodMissing called with name '" + name + "' and args = " + argsList;
        System.err.println(msg);
        return this;
    }

	public byte[] save() {
		return save(OUTPUT_DEFAULT_NAME);
	}
	//FIXME: START Dummy methods to test Groovy calling

	public byte[] save(String name) {
		Key result = this.key;
		Map<String, Object> context = new HashMap<String, Object>();

		for(Entry<Transformation, String[]> transformAndParam : transformations) {
			//*
			String[] params = transformAndParam.getValue();
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
