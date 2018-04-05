package com.paralint.spikes.crypto.dsl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.paralint.spikes.crypto.dsl.engine.Pipeline;

@SpringBootApplication
public class SpikeApplication {

	public static void main(String[] args) {
		for(String filename : args) {
            System.out.println("=============================");
            System.out.println(filename);
            System.out.println("=============================");
            Pipeline.processCryptoDSLFile(filename);
            System.out.println("-----------------------------");
		}
		
//		SpringApplication.run(DemoApplication.class, args);
	}
}
