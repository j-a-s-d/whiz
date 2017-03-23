/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.clients;

public abstract class HttpPostBomber extends HttpBomber {

	@Override protected HttpConnection getConnector(final String... values) throws Exception {
		return new HttpPoster(values[0], values[1]);
	}

	@Override public void parallelBomb(final int number) {
		spawn(number, getRequestTarget(number), getRequestContent(number));
	}

	@Override public void serialBomb(final int number) {
		fire(number, getRequestTarget(number), getRequestContent(number));
	}

	public abstract String getRequestContent(final int requestNumber);

}
