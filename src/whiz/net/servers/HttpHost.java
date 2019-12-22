/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.servers;

import ace.concurrency.Threads;
import com.sun.net.httpserver.HttpsConfigurator;
import java.net.InetSocketAddress;
import java.net.URL;
import whiz.net.URIBuilder;

/**
 * HTTP host class.
 */
public class HttpHost extends HttpStand {

	/**
	 * Default port for the HTTP host.
	 */
	public static int DEFAULT_PORT = 80;

	/**
	 * Default number of threads for the HTTP host.
	 */
	public static int DEFAULT_THREADS = Threads.DEFAULT;

	/**
	 * Default constructor.
	 */
	public HttpHost() {
		this(HttpHost.class);
	}

	/**
	 * Constructor accepting an HTTPS configurator.
	 * 
	 * @param httpsConfigurator 
	 */
	public HttpHost(final HttpsConfigurator httpsConfigurator) {
		this(HttpHost.class, httpsConfigurator);
	}

	/**
	 * Constructor accepting a class instance.
	 * 
	 * @param clazz 
	 */
	public HttpHost(final Class<?> clazz) {
		this(clazz, new InetSocketAddress(DEFAULT_PORT), DEFAULT_THREADS, DEFAULT_AUTOSTART);
	}

	/**
	 * Constructor accepting a class instance and an HTTPS configurator.
	 * 
	 * @param clazz
	 * @param httpsConfigurator 
	 */
	public HttpHost(final Class<?> clazz, final HttpsConfigurator httpsConfigurator) {
		this(clazz, new InetSocketAddress(DEFAULT_PORT), DEFAULT_THREADS, DEFAULT_AUTOSTART, httpsConfigurator);
	}

	/**
	 * Constructor accepting an auto start flag.
	 * 
	 * @param autostart 
	 */
	public HttpHost(final boolean autostart) {
		this(HttpHost.class, autostart);
	}

	/**
	 * Constructor accepting an auto start flag and a HTTPS configurator.
	 * 
	 * @param autostart 
	 * @param httpsConfigurator 
	 */
	public HttpHost(final boolean autostart, final HttpsConfigurator httpsConfigurator) {
		this(HttpHost.class, autostart, httpsConfigurator);
	}

	/**
	 * Constructor accepting a class instance and an auto start flag.
	 * 
	 * @param clazz
	 * @param autostart 
	 */
	public HttpHost(final Class<?> clazz, final boolean autostart) {
		this(clazz, new InetSocketAddress(DEFAULT_PORT), DEFAULT_THREADS, autostart);
	}

	/**
	 * Constructor accepting a class instance, an auto start flag and a HTTPS configurator.
	 * 
	 * @param clazz
	 * @param autostart 
	 * @param httpsConfigurator 
	 */
	public HttpHost(final Class<?> clazz, final boolean autostart, final HttpsConfigurator httpsConfigurator) {
		this(clazz, new InetSocketAddress(DEFAULT_PORT), DEFAULT_THREADS, autostart, httpsConfigurator);
	}

	/**
	 * Constructor accepting a port.
	 * 
	 * @param port 
	 */
	public HttpHost(final int port) {
		this(HttpHost.class, port);
	}

	/**
	 * Constructor accepting a port and a HTTPS configurator.
	 * 
	 * @param port 
	 * @param httpsConfigurator 
	 */
	public HttpHost(final int port, final HttpsConfigurator httpsConfigurator) {
		this(HttpHost.class, port, httpsConfigurator);
	}

	/**
	 * Constructor accepting a class instance and a port.
	 * 
	 * @param clazz
	 * @param port 
	 */
	public HttpHost(final Class<?> clazz, final int port) {
		this(clazz, new InetSocketAddress(port), DEFAULT_THREADS, DEFAULT_AUTOSTART);
	}

	/**
	 * Constructor accepting a class instance, a port and a HTTPS configurator.
	 * 
	 * @param clazz
	 * @param port 
	 * @param httpsConfigurator 
	 */
	public HttpHost(final Class<?> clazz, final int port, final HttpsConfigurator httpsConfigurator) {
		this(clazz, new InetSocketAddress(port), DEFAULT_THREADS, DEFAULT_AUTOSTART, httpsConfigurator);
	}

	/**
	 * Constructor accepting a port and a threads amount.
	 * 
	 * @param port 
	 * @param threads 
	 */
	public HttpHost(final int port, final int threads) {
		this(HttpHost.class, port, threads);
	}

	/**
	 * Constructor accepting a port, a threads amount and a HTTPS configurator.
	 * 
	 * @param port 
	 * @param threads 
	 * @param httpsConfigurator 
	 */
	public HttpHost(final int port, final int threads, final HttpsConfigurator httpsConfigurator) {
		this(HttpHost.class, port, threads, httpsConfigurator);
	}

	/**
	 * Constructor accepting a class instance, a port and a threads amount.
	 * 
	 * @param clazz
	 * @param port 
	 * @param threads 
	 */
	public HttpHost(final Class<?> clazz, final int port, final int threads) {
		this(clazz, new InetSocketAddress(port), threads, DEFAULT_AUTOSTART);
	}

	/**
	 * Constructor accepting a class instance, a port, a threads amount and a HTTPS configurator.
	 * 
	 * @param clazz
	 * @param port 
	 * @param threads 
	 * @param httpsConfigurator 
	 */
	public HttpHost(final Class<?> clazz, final int port, final int threads, final HttpsConfigurator httpsConfigurator) {
		this(clazz, new InetSocketAddress(port), threads, DEFAULT_AUTOSTART, httpsConfigurator);
	}

	/**
	 * Constructor accepting a port and an auto start flag.
	 * 
	 * @param port
	 * @param autostart 
	 */
	public HttpHost(final int port, final boolean autostart) {
		this(HttpHost.class, port, autostart);
	}

	/**
	 * Constructor accepting a port, an auto start flag and a HTTPS configurator.
	 * 
	 * @param port
	 * @param autostart 
	 * @param httpsConfigurator 
	 */
	public HttpHost(final int port, final boolean autostart, final HttpsConfigurator httpsConfigurator) {
		this(HttpHost.class, port, autostart, httpsConfigurator);
	}

	/**
	 * Constructor accepting a class instance, a port and an auto start flag.
	 * 
	 * @param clazz
	 * @param port
	 * @param autostart 
	 */
	public HttpHost(final Class<?> clazz, final int port, final boolean autostart) {
		this(clazz, new InetSocketAddress(port), DEFAULT_THREADS, autostart);
	}

	/**
	 * Constructor accepting a class instance, a port, an auto start flag and a HTTPS configurator.
	 * 
	 * @param clazz
	 * @param port
	 * @param autostart 
	 * @param httpsConfigurator 
	 */
	public HttpHost(final Class<?> clazz, final int port, final boolean autostart, final HttpsConfigurator httpsConfigurator) {
		this(clazz, new InetSocketAddress(port), DEFAULT_THREADS, autostart, httpsConfigurator);
	}

	/**
	 * Constructor accepting a port, a threads amount and an auto start flag.
	 * 
	 * @param port
	 * @param threads
	 * @param autostart 
	 */
	public HttpHost(final int port, final int threads, final boolean autostart) {
		this(HttpHost.class, port, threads, autostart);
	}

	/**
	 * Constructor accepting a port, a threads amount, an auto start flag and a HTTPS configurator.
	 * 
	 * @param port
	 * @param threads
	 * @param autostart 
	 * @param httpsConfigurator 
	 */
	public HttpHost(final int port, final int threads, final boolean autostart, final HttpsConfigurator httpsConfigurator) {
		this(HttpHost.class, port, threads, autostart, httpsConfigurator);
	}

	/**
	 * Constructor accepting a class instance, a port, a threads amount and an auto start flag.
	 * 
	 * @param clazz
	 * @param port
	 * @param threads
	 * @param autostart 
	 */
	public HttpHost(final Class<?> clazz, final int port, final int threads, final boolean autostart) {
		this(clazz, new InetSocketAddress(port), threads, autostart);
	}

	/**
	 * Constructor accepting a class instance, a port, a threads amount, an auto start flag and a HTTPS configurator.
	 * 
	 * @param clazz
	 * @param port
	 * @param threads
	 * @param autostart 
	 * @param httpsConfigurator 
	 */
	public HttpHost(final Class<?> clazz, final int port, final int threads, final boolean autostart, final HttpsConfigurator httpsConfigurator) {
		this(clazz, new InetSocketAddress(port), threads, autostart, httpsConfigurator);
	}

	/**
	 * Constructor accepting an internet socket address.
	 * 
	 * @param address 
	 */
	public HttpHost(final InetSocketAddress address) {
		this(HttpHost.class, address);
	}

	/**
	 * Constructor accepting an internet socket address and a HTTPS configurator.
	 * 
	 * @param address 
	 * @param httpsConfigurator 
	 */
	public HttpHost(final InetSocketAddress address, final HttpsConfigurator httpsConfigurator) {
		this(HttpHost.class, address, httpsConfigurator);
	}

	/**
	 * Constructor accepting a class instance and an internet socket address.
	 * 
	 * @param clazz
	 * @param address 
	 */
	public HttpHost(final Class<?> clazz, final InetSocketAddress address) {
		this(clazz, address, DEFAULT_THREADS, DEFAULT_AUTOSTART);
	}

	/**
	 * Constructor accepting a class instance, an internet socket address and a HTTPS configurator.
	 * 
	 * @param clazz
	 * @param address 
	 * @param httpsConfigurator 
	 */
	public HttpHost(final Class<?> clazz, final InetSocketAddress address, final HttpsConfigurator httpsConfigurator) {
		this(clazz, address, DEFAULT_THREADS, DEFAULT_AUTOSTART, httpsConfigurator);
	}

	/**
	 * Constructor accepting an internet socket address and a threads amount.
	 * 
	 * @param address 
	 * @param threads 
	 */
	public HttpHost(final InetSocketAddress address, final int threads) {
		this(HttpHost.class, address, threads);
	}

	/**
	 * Constructor accepting an internet socket address, a threads amount and a HTTPS configurator.
	 * 
	 * @param address 
	 * @param threads 
	 * @param httpsConfigurator 
	 */
	public HttpHost(final InetSocketAddress address, final int threads, final HttpsConfigurator httpsConfigurator) {
		this(HttpHost.class, address, threads, httpsConfigurator);
	}

	/**
	 * Constructor accepting a class instance, an internet socket address and a threads amount.
	 * 
	 * @param clazz
	 * @param address 
	 * @param threads 
	 */
	public HttpHost(final Class<?> clazz, final InetSocketAddress address, final int threads) {
		this(clazz, address, threads, DEFAULT_AUTOSTART);
	}

	/**
	 * Constructor accepting a class instance, an internet socket address, a threads amount and a HTTPS configurator.
	 * 
	 * @param clazz
	 * @param address 
	 * @param threads 
	 * @param httpsConfigurator 
	 */
	public HttpHost(final Class<?> clazz, final InetSocketAddress address, final int threads, final HttpsConfigurator httpsConfigurator) {
		this(clazz, address, threads, DEFAULT_AUTOSTART, httpsConfigurator);
	}

	/**
	 * Constructor accepting an internet socket address and a auto start flag.
	 * 
	 * @param address 
	 * @param autostart 
	 */
	public HttpHost(final InetSocketAddress address, final boolean autostart) {
		this(HttpHost.class, address, autostart);
	}

	/**
	 * Constructor accepting an internet socket address, a auto start flag and a HTTPS configurator.
	 * 
	 * @param address 
	 * @param autostart 
	 * @param httpsConfigurator 
	 */
	public HttpHost(final InetSocketAddress address, final boolean autostart, final HttpsConfigurator httpsConfigurator) {
		this(HttpHost.class, address, autostart, httpsConfigurator);
	}

	/**
	 * Constructor accepting a class instance, an internet socket address and a auto start flag.
	 * 
	 * @param clazz
	 * @param address 
	 * @param autostart 
	 */
	public HttpHost(final Class<?> clazz, final InetSocketAddress address, final boolean autostart) {
		this(clazz, address, DEFAULT_THREADS, autostart);
	}

	/**
	 * Constructor accepting a class instance, an internet socket address, a auto start flag and a HTTPS configurator.
	 * 
	 * @param clazz
	 * @param address 
	 * @param autostart 
	 * @param httpsConfigurator 
	 */
	public HttpHost(final Class<?> clazz, final InetSocketAddress address, final boolean autostart, final HttpsConfigurator httpsConfigurator) {
		this(clazz, address, DEFAULT_THREADS, autostart, httpsConfigurator);
	}

	/**
	 * Constructor accepting an internet socket address, a threads amount and a auto start flag.
	 * 
	 * @param address 
	 * @param threads 
	 * @param autostart 
	 */
	public HttpHost(final InetSocketAddress address, final int threads, final boolean autostart) {
		this(HttpHost.class, address, threads, autostart);
	}

	/**
	 * Constructor accepting an internet socket address, a threads amount, a auto start flag and a HTTPS configurator.
	 * 
	 * @param address 
	 * @param threads 
	 * @param autostart 
	 * @param httpsConfigurator 
	 */
	public HttpHost(final InetSocketAddress address, final int threads, final boolean autostart, final HttpsConfigurator httpsConfigurator) {
		this(HttpHost.class, address, threads, autostart, httpsConfigurator);
	}

	/**
	 * Constructor accepting a class instance, an internet socket address, a threads amount and a auto start flag.
	 * 
	 * @param clazz
	 * @param address 
	 * @param threads 
	 * @param autostart 
	 */
	public HttpHost(final Class<?> clazz, final InetSocketAddress address, final int threads, final boolean autostart) {
		this(clazz, address, threads, autostart, null);
	}

	/**
	 * Constructor accepting a class instance, an internet socket address, a threads amount, a auto start flag and a HTTPS configurator.
	 * 
	 * @param clazz
	 * @param address 
	 * @param threads 
	 * @param autostart 
	 * @param httpsConfigurator 
	 */
	public HttpHost(final Class<?> clazz, final InetSocketAddress address, final int threads, final boolean autostart, final HttpsConfigurator httpsConfigurator) {
		super(clazz, httpsConfigurator);
		setExecutionThreadsMaxCount(threads);
		if (initialize(address) && autostart) {
			start();
		}
	}

	/**
	 * Sets the server name.
	 * 
	 * @param value
	 * @return itself
	 */
	@Override public HttpHost setName(final String value) {
		return (HttpHost) super.setName(value);
	}

	/**
	 * Sets the maximum amount of execution threads.
	 * 
	 * @param value
	 * @return itself
	 */
	@Override public HttpHost setExecutionThreadsMaxCount(final Integer value) {
		return (HttpHost) super.setExecutionThreadsMaxCount(value);
	}

	/**
	 * Gets the cross origin requests allowance flag.
	 * 
	 * @return the cross origin requests allowance flag value
	 */
	public final boolean getCrossOriginRequestsAllowance() {
		return getCrossOriginAllowance();
	}

	/**
	 * Sets the cross origin requests allowance flag value.
	 * 
	 * @param value
	 * @return itself
	 */
	public HttpHost setCrossOriginRequestsAllowance(final boolean value) {
		setCrossOriginAllowance(value);
		return this;
	}

	/**
	 * Gets the base URL.
	 * 
	 * @return the base URL
	 */
	public URL getBaseURL() {
		return new URIBuilder()
			.setScheme(getURIScheme())
			.setHost(getHost())
			.setPort(getPort())
		.getAsURL();
	}

	/**
	 * On start listening event handler.
	 * 
	 * NOTE: override this in inherited classes to control the event
	 */
	@Override public void onStartListening() {
		//
	}

	/**
	 * On stop listening event handler.
	 * 
	 * NOTE: override this in inherited classes to control the event
	 */
	@Override public void onStopListening() {
		//
	}

	/**
	 * On configure HTTPS event handler.
	 * 
	 * NOTE: override this in inherited classes to control the event
	 *
	 * @return the HTTPS configurator instance to be used
	 */
	public HttpsConfigurator onConfigureHttps() {
		return null;
	}

}
