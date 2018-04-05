package com.paralint.spikes.crypto.dsl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleEntry;

public class TransformationEngine {
	private final Key key;
	private final Asset asset;

	List<Entry<Transformation, String[]>> transformations = new ArrayList<>();

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

	private TransformationEngine(Asset asset, Key key) {
		this.asset = asset;
		this.key = key;
	}

	static TransformationEngine format(Asset asset, Key key) {
		TransformationEngine te = new TransformationEngine(asset, key);
		te.transformations = new ArrayList<>();
		return te;
	}

	
	
	
	public TransformationEngine append(String text) {
		this.transformations.add(createTransformationAndParam(new Appender(), text));
		return this;
	}

	public TransformationEngine toHex() {
		this.transformations.add(createTransformationAndParam(new ToHex()));
		return this;
	}
	
	public TransformationEngine addStep(Transformation transform, String... params) {
		this.transformations.add(createTransformationAndParam(transform, params));
		return this;
	}

	public byte[] build() {
		Key result = this.key;
		Map<String, Object> context = new HashMap<String, Object>();

		for(Entry<Transformation, String[]> transformAndParam : transformations) {
			//*
			String[] params = transformAndParam.getValue();
			Transformation transform = transformAndParam.getKey();
			result = transform.Transform(context, asset, result, params);
			/*/
			//Same thing as above on one line, less convenient for breakpoints
			transform.getKey().Transform(context, asset, result, transform.getValue());
			//*/
		}
		
		return result.get();
	}
}
