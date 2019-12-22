/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.platform.unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import whiz.Whiz;

/**
 * Utility class for working with some unsafe methods.
 */
public class Java extends Whiz {

	// INSTANCES

	/**
	 * Allocates in memory an instance of the specified class.
	 * 
	 * NOTE: it does not call the constructor of the instance created.
	 * 
	 * @param <T>
	 * @param type
	 * @return the allocated instance
	 */
	public static final <T> T allocateInstance(final Class<?> type) {
		try {
			return (T) Unsafe.get().allocateInstance(type);
		} catch (final Exception e) {
			GEH.setLastException(e);
			return null;
		}
	}

	/**
	 * Gets the memory size of an instance of the specified class.
	 * 
	 * @param clazz
	 * @return the memory size of an instance of the specified class
	 */
	public static final long sizeOfInstance(final Class<?> clazz) {
		Class<?> k = clazz;
		long maximumOffset = 0;
		do {
			for (final Field f : k.getDeclaredFields()) {
				if (!Modifier.isStatic(f.getModifiers())) {
					maximumOffset = Math.max(maximumOffset, Unsafe.get().objectFieldOffset(f));
				}
			}
		} while ((k = k.getSuperclass()) != null);
		return maximumOffset + 8;
	}

	// MONITORS

	/**
	 * Enters in a monitor lock for the specified object.
	 * 
	 * NOTE: this will work up to Java 8.
	 * 
	 * @param o 
	 */
	public static final void monitorLock(final Object o) {
		Unsafe.get().monitorEnter(o);
	}

	/**
	 * Exists from a monitor lock for the specified object.
	 * 
	 * NOTE: this will work up to Java 8.
	 * 
	 * @param o 
	 */
	public static final void monitorUnlock(final Object o) {
		Unsafe.get().monitorExit(o);
	}

	// THREADS

	/**
	 * Parks the current thread.
	 * 
	 * @param isAbsolute
	 * @param time 
	 */
	public static final void parkCurrentThread(final boolean isAbsolute, final long time) {
		Unsafe.get().park(isAbsolute, time);
	}

	/**
	 * Unparks the specified thread.
	 * 
	 * @param thread 
	 */
	public static final void unparkThread(final Thread thread) {
		Unsafe.get().unpark(thread);
	}

	// EXCEPTIONS

	/**
	 * Throws the specified exception.
	 * 
	 * @param throwable 
	 */
	public static final void throwException(final Throwable throwable) {
		Unsafe.get().throwException(throwable);
	}

}
