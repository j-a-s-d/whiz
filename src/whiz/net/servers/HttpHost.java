/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.servers;

import ace.concurrency.Threads;
import java.net.InetSocketAddress;
import java.net.URL;
import whiz.net.URIBuilder;

public class HttpHost extends HttpStand {

	public static int DEFAULT_PORT = 80;
	public static int DEFAULT_THREADS = Threads.DEFAULT;

	public HttpHost() {
		this(HttpHost.class);
	}

	public HttpHost(final Class<?> clazz) {
		this(clazz, new InetSocketAddress(DEFAULT_PORT), DEFAULT_THREADS, DEFAULT_AUTOSTART);
	}

	public HttpHost(final boolean autostart) {
		this(HttpHost.class, autostart);
	}

	public HttpHost(final Class<?> clazz, final boolean autostart) {
		this(clazz, new InetSocketAddress(DEFAULT_PORT), DEFAULT_THREADS, autostart);
	}

	public HttpHost(final int port) {
		this(HttpHost.class, port);
	}

	public HttpHost(final Class<?> clazz, final int port) {
		this(clazz, new InetSocketAddress(port), DEFAULT_THREADS, DEFAULT_AUTOSTART);
	}

	public HttpHost(final int port, final int threads) {
		this(HttpHost.class, port, threads);
	}

	public HttpHost(final Class<?> clazz, final int port, final int threads) {
		this(clazz, new InetSocketAddress(port), threads, DEFAULT_AUTOSTART);
	}

	public HttpHost(final int port, final boolean autostart) {
		this(HttpHost.class, port, autostart);
	}

	public HttpHost(final Class<?> clazz, final int port, final boolean autostart) {
		this(clazz, new InetSocketAddress(port), DEFAULT_THREADS, autostart);
	}

	public HttpHost(final int port, final int threads, final boolean autostart) {
		this(HttpHost.class, port, threads, autostart);
	}

	public HttpHost(final Class<?> clazz, final int port, final int threads, final boolean autostart) {
		this(clazz, new InetSocketAddress(port), threads, autostart);
	}

	public HttpHost(final InetSocketAddress address) {
		this(HttpHost.class, address);
	}

	public HttpHost(final Class<?> clazz, final InetSocketAddress address) {
		this(clazz, address, DEFAULT_THREADS, DEFAULT_AUTOSTART);
	}

	public HttpHost(final InetSocketAddress address, final int threads) {
		this(HttpHost.class, address, threads);
	}

	public HttpHost(final Class<?> clazz, final InetSocketAddress address, final int threads) {
		this(clazz, address, threads, DEFAULT_AUTOSTART);
	}

	public HttpHost(final InetSocketAddress address, final boolean autostart) {
		this(HttpHost.class, address, autostart);
	}

	public HttpHost(final Class<?> clazz, final InetSocketAddress address, final boolean autostart) {
		this(clazz, address, DEFAULT_THREADS, autostart);
	}

	public HttpHost(final InetSocketAddress address, final int threads, final boolean autostart) {
		this(HttpHost.class, address, threads, autostart);
	}

	public HttpHost(final Class<?> clazz, final InetSocketAddress address, final int threads, final boolean autostart) {
		super(clazz);
		setExecutionThreadsMaxCount(threads);
		if (initialize(address) && autostart) {
			start();
		}
	}

	@Override public HttpHost setName(final String value) {
		return (HttpHost) super.setName(value);
	}

	@Override public HttpHost setExecutionThreadsMaxCount(final Integer value) {
		return (HttpHost) super.setExecutionThreadsMaxCount(value);
	}

	public final boolean getCrossOriginRequestsAllowance() {
		return getCrossOriginAllowance();
	}

	public HttpHost setCrossOriginRequestsAllowance(final boolean value) {
		setCrossOriginAllowance(value);
		return this;
	}

	public URL getBaseURL() {
		return new URIBuilder()
			.setScheme("http")
			.setHost(getHost())
			.setPort(getPort())
		.getAsURL();
	}

	@Override public void onStartListening() {
		// NOTE: override this in inherited classes to control the event
	}

	@Override public void onStopListening() {
		// NOTE: override this in inherited classes to control the event
	}

}
