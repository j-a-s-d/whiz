/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.clients;

/**
 * Useful HTTP GET bomber class.
 */
public abstract class HttpGetBomber extends HttpBomber {

	/**
	 * Default constructor.
	 */
	public HttpGetBomber() {
		super(HttpGetBomber.class);
	}

	@Override protected HttpConnection getConnector(final String... values) throws Exception {
		return new HttpGetter(values[0]);
	}

	/**
	 * Fires a parallel bombing burst of the specified number of requests.
	 * 
	 * @param number 
	 */
	@Override public void parallelBomb(final int number) {
		spawn(number, getRequestTarget(number));
	}

	/**
	 * Fires a serial bombing burst of the specified number of requests.
	 * 
	 * @param number 
	 */
	@Override public void serialBomb(final int number) {
		fire(number, getRequestTarget(number));
	}

}
