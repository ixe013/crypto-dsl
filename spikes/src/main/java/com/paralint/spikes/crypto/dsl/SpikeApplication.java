package com.paralint.spikes.crypto.dsl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.paralint.spikes.crypto.dsl.engine.Pipeline;

@SpringBootApplication
public class SpikeApplication {

	public static void main(String[] args) {
		byte [] result = Pipeline.getKeyInFormatXYZ();
		System.out.println(result);
//		SpringApplication.run(DemoApplication.class, args);
	}
}
