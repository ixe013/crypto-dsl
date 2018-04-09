package com.paralint.spikes.crypto.dsl.engine;

import com.paralint.spikes.crypto.dsl.assets.Asset;
import com.paralint.spikes.crypto.dsl.keys.Key;

import groovy.lang.Closure;

public class TransformationEngineClosure extends Closure<TransformationEngine> {

	private static final long serialVersionUID = 1L;

	public TransformationEngineClosure(Object owner) {
		super(owner);
	}

	public TransformationEngineClosure(Object owner, Object thisObject) {
		super(owner, thisObject);
	}

	@Override
	public TransformationEngine call(Object... params) {
		Asset asset = (Asset)params[0];
		Key key = (Key)params[1];
		
		return TransformationEngine.format(asset, key);
	}

}
