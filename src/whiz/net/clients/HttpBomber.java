/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.clients;

import ace.interfaces.Stoppable;
import java.io.PrintStream;
import whiz.WhizObject;
import whiz.net.interfaces.HttpConnectionInfo;

/**
 * Abstract HTTP bomber class.
 */
public abstract class HttpBomber extends WhizObject implements Stoppable {

	protected PrintStream _out = System.out;
	protected int _count = 100;
	protected boolean _active = true;

	/**
	 * Constructor accepting a class instance.
	 * 
	 * @param clazz 
	 */
	public HttpBomber(final Class<?> clazz) {
		super(clazz);
	}

	/**
	 * Gets the print stream.
	 * 
	 * @return the print stream
	 */
	public PrintStream getPrintStream() {
		return _out;
	}

	/**
	 * Sets the print stream.
	 * 
	 * @param printStream 
	 */
	public void setPrintStream(final PrintStream printStream) {
		if (printStream != null) {
			_out = printStream;
		}
	}

	/**
	 * Gets the amount of the remaining requests.
	 * 
	 * @return the amount of the remaining requests
	 */
	public int getCount() {
		return _count;
	}

	/**
	 * Sets the amount of the remaining requests.
	 * 
	 * @param count 
	 */
	public void setCount(final int count) {
		_count = count;
	}

	/**
	 * Determines if the bomber is firing requests.
	 * 
	 * @return <tt>true</tt> if the bomber is firing requests, <tt>false</tt> otherwise
	 */
	public boolean isBombing() {
		return _active;
	}

	/**
	 * Stops the bomber from firing more requests.
	 */
	@Override public void stop() {
		_active = false;
	}

	protected void printConnectionInfo(final int n, final HttpConnectionInfo c) {
		_out.println("REQ(" + n + "): " + c.getRequestURL() + " | " + c.getRequestMethod() + " | " + c.getRequestData());
		_out.println("RES(" + n + "): [" + c.getResponseTime() + " ms] (" + c.getResponseCode() + ") " + c.getResponseData());
	}

	protected void spawn(final int number, final String... values) {
		new Thread(new Runnable() {
			@Override public void run() {
				fire(number, values);
			}
		}).start();
	}

	protected void fire(final int number, final String... values) {
		try {
			printConnectionInfo(number, getConnector(values).go());
		} catch (final Exception e) {
			_out.println(e.getMessage());
		}
	}

	/**
	 * Starts to fire requests in a parallel fashion.
	 */
	public void startParallelBombing() {
		int x = _count;
		_active = true;
		while (_active && x-- > 0) {
			parallelBomb(_count - x);
		}
		_active = false;
	}

	/**
	 * Starts to fire requests in a serial fashion.
	 */
	public void startSerialBombing() {
		int x = _count;
		_active = true;
		while (_active && x-- > 0) {
			serialBomb(_count - x);
		}
		_active = false;
	}

	protected abstract void parallelBomb(final int number);

	protected abstract void serialBomb(final int number);

	protected abstract HttpConnection getConnector(final String... values) throws Exception;

	/**
	 * Method to be overrode to specify the request target for the specified request number.
	 * 
	 * @param requestNumber
	 * @return the request target
	 */
	public abstract String getRequestTarget(final int requestNumber);

}
