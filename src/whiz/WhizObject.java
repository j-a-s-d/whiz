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

	public WhizObject(final String clazzSimpleName) {
		_instanceName = clazzSimpleName + STRINGS.COLON + Integer.toString(++_counter);
	}

	public WhizObject(final Class<?> clazz) {
		this(clazz.getSimpleName());
	}

	protected final String getInstanceName() {
		return _instanceName;
	}

	// COMMON
	private Object _tag;

	protected final <T> T getTag() {
		return (T) _tag;
	}

	protected final void setTag(final Object value) {
		_tag = value;
	}

}
