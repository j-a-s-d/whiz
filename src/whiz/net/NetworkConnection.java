/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net;

import whiz.WhizObject;

/**
 * Abstract class for network connections.
 */
public abstract class NetworkConnection extends WhizObject {

	/**
	 * Constructor accepting a class instance.
	 * 
	 * @param clazz 
	 */
	public NetworkConnection(final Class<?> clazz) {
		super(clazz);
	}

}
