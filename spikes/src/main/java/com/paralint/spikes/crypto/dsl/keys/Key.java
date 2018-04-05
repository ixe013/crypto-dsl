package com.paralint.spikes.crypto.dsl.keys;

import java.util.Random;

public class Key {
	byte[] key;
	
	public Key() {
		byte[] b = new byte[20];
		new Random().nextBytes(b);
		this.set(b);
	}

	public byte[] get() {
		return key;
	}
	
	public void set(byte[] key) {
		this.key = key;
	}
}
