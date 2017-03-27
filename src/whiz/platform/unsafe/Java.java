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
	// NOTE: the following method does not call the constructor of the instance created
	public static final <T> T allocateInstance(final Class<?> type) {
		try {
			return (T) Unsafe.get().allocateInstance(type);
		} catch (final Exception e) {
			GEH.setLastException(e);
			return null;
		}
	}

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
	public static final void monitorLock(final Object o) {
		Unsafe.get().monitorEnter(o);
	}

	public static final void monitorUnlock(final Object o) {
		Unsafe.get().monitorExit(o);
	}

	// THREADS
	public static final void parkCurrentThread(final boolean isAbsolute, final long time) {
		Unsafe.get().park(isAbsolute, time);
	}

	public static final void unparkThread(final Thread thread) {
		Unsafe.get().unpark(thread);
	}

	// EXCEPTIONS
	public static final void throwException(final Throwable throwable) {
		Unsafe.get().throwException(throwable);
	}

}
