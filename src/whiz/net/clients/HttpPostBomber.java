/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.clients;

/**
 * Useful HTTP POST bomber class.
 */
public abstract class HttpPostBomber extends HttpBomber {

	/**
	 * Default constructor.
	 */
	public HttpPostBomber() {
		super(HttpPostBomber.class);
	}

	@Override protected HttpConnection getConnector(final String... values) throws Exception {
		return new HttpPoster(values[0], values[1]);
	}

	/**
	 * Fires a parallel bombing burst of the specified number of requests.
	 * 
	 * @param number 
	 */
	@Override public void parallelBomb(final int number) {
		spawn(number, getRequestTarget(number), getRequestContent(number));
	}

	/**
	 * Fires a serial bombing burst of the specified number of requests.
	 * 
	 * @param number 
	 */
	@Override public void serialBomb(final int number) {
		fire(number, getRequestTarget(number), getRequestContent(number));
	}

	/**
	 * Method to be overrode to specify the request content for the specified request number.
	 * 
	 * @param requestNumber
	 * @return the request body
	 */
	public abstract String getRequestContent(final int requestNumber);

}
