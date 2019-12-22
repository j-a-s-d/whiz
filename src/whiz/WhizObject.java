/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz;

import ace.constants.STRINGS;

/**
 * Whiz object.
 */
public class WhizObject extends WhizEntity {

	// SPECIFIC
	private static int _counter = 0;

	private String _instanceName;

	/**
	 * Constructor accepting a class simple name.
	 * 
	 * @param clazzSimpleName 
	 */
	public WhizObject(final String clazzSimpleName) {
		_instanceName = clazzSimpleName + STRINGS.COLON + Integer.toString(++_counter);
	}

	/**
	 * Constructor accepting a class instance.
	 * 
	 * @param clazz 
	 */
	public WhizObject(final Class<?> clazz) {
		this(clazz.getSimpleName());
	}

	/**
	 * Gets the instance name.
	 * 
	 * @return the instance name
	 */
	protected final String getInstanceName() {
		return _instanceName;
	}

	// COMMON
	private Object _tag;

	/**
	 * Gets this object instance tag (an associated object instance slot for miscellaneous usage).
	 * 
	 * @param <T>
	 * @return the tag
	 */
	protected final <T> T getTag() {
		return (T) _tag;
	}

	/**
	 * Sets this object instance tag (an associated object instance slot for miscellaneous usage).
	 * 
	 * @param value 
	 */
	protected final void setTag(final Object value) {
		_tag = value;
	}

}
