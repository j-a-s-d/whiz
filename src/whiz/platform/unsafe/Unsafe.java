/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.platform.unsafe;

import ace.platform.Reflection;
import whiz.Whiz;

/**
 * Utility class for obtaining the JVM unsafe object.
 */
@SuppressWarnings("PMD.UnusedPrivateMethod")
public class Unsafe extends Whiz {

	// NOTE: mostly based on https://dzone.com/articles/understanding-sunmiscunsafe
	// and http://www.docjar.com/docs/api/sun/misc/Unsafe.html
	private static sun.misc.Unsafe _unsafe;

	static sun.misc.Unsafe get() {
		if (!assigned(_unsafe)) {
			// NOTE: for multiversion-multiplatform compatibility,
			// makeInstance is preferred over fetchInstance and getInstance methods.
			_unsafe = makeInstance();
		}
		return _unsafe;
	}

	private static sun.misc.Unsafe makeInstance() {
		try {
			return (sun.misc.Unsafe) Reflection.getConstructorAsAccessible(sun.misc.Unsafe.class).newInstance();
		} catch (final Exception e) {
			GEH.setLastException(e);
			return null;
		}
	}

	private static sun.misc.Unsafe getInstance() {
		return sun.misc.Unsafe.getUnsafe();
	}

	private static sun.misc.Unsafe fetchInstance() {
		try {
			return (sun.misc.Unsafe) Reflection.getFieldAsAccessible(sun.misc.Unsafe.class, "theUnsafe").get(null);
		} catch (final Exception e) {
			GEH.setLastException(e);
			return null;
		}
	}

}
