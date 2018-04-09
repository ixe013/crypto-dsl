/**
 * Limits what can be called from a script
 * 
 */
package com.paralint.spikes.crypto.dsl.engine;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.kohsuke.groovy.sandbox.GroovyValueFilter;

public class PipelineSandboxFilter extends GroovyValueFilter {

	private static final Set<Class<?>> ALLOWED_TYPES = new HashSet<Class<?>>(Arrays.asList(
			com.paralint.spikes.crypto.dsl.assets.Asset.class,
			com.paralint.spikes.crypto.dsl.keys.Key.class,
			com.paralint.spikes.crypto.dsl.assets.AssetRepository.class,
			com.paralint.spikes.crypto.dsl.engine.TransformationEngine.class,
			com.paralint.spikes.crypto.dsl.engine.TransformationEngineClosure.class,
			groovy.lang.MetaClassImpl.class,
			groovy.lang.Closure.class,
			groovy.lang.Script.class,
			groovy.lang.GroovyShell.class
			));

	/**
	 * Called for every receiver.
	 */
	@Override
	public Object filterReceiver(Object receiver) {
		String className;
		Class<?> objectClass;
		
		if (receiver instanceof Class<?>) {
			objectClass = (Class<?>) receiver;
			className = objectClass.getName();
		} else {
			objectClass = receiver.getClass();
			className = objectClass.getName();
		}

		System.out.println("Filtering receiver " + className);

		// The script itself is not in any package, we must let it run
		if (className.contains(".")) {
			// But anything else from a package must be whitelisted
			if (!ALLOWED_TYPES.contains(objectClass)) {
				throw new SecurityException("Use of class " + className + " not allowed in scripting sandbox");
			}
		}

		return super.filterReceiver(receiver);
	}

}
