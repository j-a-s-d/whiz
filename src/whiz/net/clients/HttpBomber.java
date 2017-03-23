/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.clients;

import ace.interfaces.Stoppable;
import java.io.PrintStream;
import whiz.WhizObject;
import whiz.net.interfaces.HttpConnectionInfo;

public abstract class HttpBomber extends WhizObject implements Stoppable {

	protected PrintStream _out = System.out;
	protected int _count = 100;
	protected boolean _active = true;

	public PrintStream getPrintStream() {
		return _out;
	}

	public void setPrintStream(final PrintStream printStream) {
		if (printStream != null) {
			_out = printStream;
		}
	}

	public int getCount() {
		return _count;
	}

	public void setCount(final int count) {
		_count = count;
	}

	public boolean isBombing() {
		return _active;
	}

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

	public void startParallelBombing() {
		int x = _count;
		_active = true;
		while (_active && x-- > 0) {
			parallelBomb(_count - x);
		}
		_active = false;
	}

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

	public abstract String getRequestTarget(final int requestNumber);

}
