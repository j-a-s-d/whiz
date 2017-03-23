/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.clients;

public abstract class HttpGetBomber extends HttpBomber {

	public HttpGetBomber() {
		super(HttpGetBomber.class);
	}

	@Override protected HttpConnection getConnector(final String... values) throws Exception {
		return new HttpGetter(values[0]);
	}

	@Override public void parallelBomb(final int number) {
		spawn(number, getRequestTarget(number));
	}

	@Override public void serialBomb(final int number) {
		fire(number, getRequestTarget(number));
	}

}
