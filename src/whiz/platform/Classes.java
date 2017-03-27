/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.platform;

/**
 * Utility class, extending the Ace's one, for working with classes.
 */
public class Classes extends ace.platform.Classes {

	public static final <T> T invokeMethod(final Class<?> clazz, final String methodName, final Object instance, final Object... args) {
		try {
			return (T) clazz.getMethod(methodName).invoke(instance, args);
		} catch (final Exception e) {
			GEH.setLastException(e);
			return null;
		}
	}

}
