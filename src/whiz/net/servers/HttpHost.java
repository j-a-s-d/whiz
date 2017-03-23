/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.servers;

import ace.concurrency.Threads;
import java.net.InetSocketAddress;

public class HttpHost extends HttpStand {

	public static int DEFAULT_PORT = 80;
	public static int DEFAULT_THREADS = Threads.DEFAULT;

	public HttpHost() {
		this(new InetSocketAddress(DEFAULT_PORT), DEFAULT_THREADS, DEFAULT_AUTOSTART);
	}

	public HttpHost(final boolean autostart) {
		this(new InetSocketAddress(DEFAULT_PORT), DEFAULT_THREADS, autostart);
	}

	public HttpHost(final int port) {
		this(new InetSocketAddress(port), DEFAULT_THREADS, DEFAULT_AUTOSTART);
	}

	public HttpHost(final int port, final int threads) {
		this(new InetSocketAddress(port), threads, DEFAULT_AUTOSTART);
	}

	public HttpHost(final int port, final boolean autostart) {
		this(new InetSocketAddress(port), DEFAULT_THREADS, autostart);
	}

	public HttpHost(final int port, final int threads, final boolean autostart) {
		this(new InetSocketAddress(port), threads, autostart);
	}

	public HttpHost(final InetSocketAddress address) {
		this(address, DEFAULT_THREADS, DEFAULT_AUTOSTART);
	}

	public HttpHost(final InetSocketAddress address, final int threads) {
		this(address, threads, DEFAULT_AUTOSTART);
	}

	public HttpHost(final InetSocketAddress address, final boolean autostart) {
		this(address, DEFAULT_THREADS, autostart);
	}

	public HttpHost(final InetSocketAddress address, final int threads, final boolean autostart) {
		setExecutionThreadsMaxCount(threads);
		if (initialize(address) && autostart) {
			start();
		}
	}

	@Override public final HttpHost setName(final String value) {
		return (HttpHost) super.setName(value);
	}

	@Override public final HttpHost setExecutionThreadsMaxCount(final Integer value) {
		return (HttpHost) super.setExecutionThreadsMaxCount(value);
	}

	public final boolean getCrossOriginRequestsAllowance() {
		return getCrossOriginAllowance();
	}

	public final HttpHost setCrossOriginRequestsAllowance(final boolean value) {
		setCrossOriginAllowance(value);
		return this;
	}

	@Override public void onStartListening() {
		// NOTE: override this in inherited classes to control the event
	}

	@Override public void onStopListening() {
		// NOTE: override this in inherited classes to control the event
	}

}
