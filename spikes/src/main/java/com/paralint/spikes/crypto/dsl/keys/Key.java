package com.paralint.spikes.crypto.dsl.keys;

import java.util.Random;

public class Key {
	byte[] key;
	
	public Key() {
		byte[] b = new byte[20];
		new Random().nextBytes(b);
		this.set(b);
	}

	public Key(byte[] rawKey) {
		this.set(rawKey);
	}

	public byte[] get() {
		return key;
	}
	
	private void set(byte[] key) {
		this.key = key;
	}
	
	/*
	 * Keys are immutable. To change the bytes, a copy will be returned. This prevents 
	 * users of the pipeline to mess with the original key they are given.
	 */
	public Key clone(byte[] key) {
		return new Key(key);
	}
}
