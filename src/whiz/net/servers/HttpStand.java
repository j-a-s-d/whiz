/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.servers;

import ace.concurrency.Threads;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsServer;
import java.net.InetSocketAddress;
import whiz.net.URISchemes;

/**
 * Abstract HTTP stand class.
 */
public abstract class HttpStand extends NetworkServer {

	/**
	 * The stop delay in seconds.
	 */
	public static int STOP_DELAY_SECONDS = 5;

	private HttpServer _httpServer;
	private HttpsConfigurator _httpsConfigurator;
	private String _uriScheme;
	private boolean _allowCrossOrigin;

	/**
	 * Constructor accepting a class instance and an HTTPS configurator.
	 * 
	 * @param clazz
	 * @param configurator 
	 */
	public HttpStand(final Class<?> clazz, final HttpsConfigurator configurator) {
		super(clazz);
		_allowCrossOrigin = false;
		try {
			if (assigned(_httpsConfigurator = configurator)) {
				_httpServer = HttpsServer.create();
				((HttpsServer) _httpServer).setHttpsConfigurator(_httpsConfigurator);
				_uriScheme = URISchemes.HTTPS;
			} else {
				_httpServer = HttpServer.create();
				_uriScheme = URISchemes.HTTP;
			}
		} catch (final Exception e) {
			setLastException(e);
		}
	}

	@Override protected final boolean initialize(final InetSocketAddress address) {
		setAddress(address);
		try {
			_httpServer.bind(getAddress(), 0);
			return true;
		} catch (final Exception e) {
			setLastException(e);
			return false;
		}
	}

	/**
	 * Gets the URI scheme.
	 * 
	 * @return the URI scheme
	 */
	public String getURIScheme() {
		return _uriScheme;
	}

	/**
	 * Gets the HTTPS configurator.
	 * 
	 * @return the HTTPS configurator
	 */
	public HttpsConfigurator getHttpsConfigurator() {
		return _httpsConfigurator;
	}

	/**
	 * Gets the cross origin allowance flag state.
	 * 
	 * @return the cross origin allowance flag state
	 */
	protected final boolean getCrossOriginAllowance() {
		return _allowCrossOrigin;
	}

	/**
	 * Sets the cross origin allowance flag state.
	 * 
	 * @param value
	 */
	protected final void setCrossOriginAllowance(final boolean value) {
		_allowCrossOrigin = value;
	}

	/**
	 * Starts the server to listen for incoming connections.
	 */
	public final void start() {
		if (assigned(_httpServer)) {
			_httpServer.setExecutor(Threads.getExecutorService(getExecutionThreadsMaxCount()));
			_httpServer.start();
			onStartListening();
		}
	}

	/**
	 * Stops the server from listening for incoming connections.
	 */
	public final void stop() {
		if (assigned(_httpServer)) {
			_httpServer.stop(STOP_DELAY_SECONDS);
			onStopListening();
		}
	}

	/**
	 * Register the specified HTTP handler to manage the requests received at the specified route.
	 * 
	 * @param route
	 * @param handler 
	 */
	public final void registerContext(final String route, final HttpHandler handler) {
		if (assigned(_httpServer)) {
			_httpServer.createContext(route, handler);
		}
	}

	/**
	 * Unregisters the handler for the specified route.
	 * 
	 * @param route 
	 */
	public final void dropHandler(final String route) {
		if (assigned(_httpServer)) {
			_httpServer.removeContext(route);
		}
	}

	/**
	 * Registers the specified HTTP request handler instance.
	 * 
	 * @param requestHandler 
	 */
	public final void registerHandler(final HttpRequestHandler requestHandler) {
		if (assigned(_httpServer)) {
			registerContext(requestHandler.getRoute(), requestHandler.setStand(this));
		}
	}

	/**
	 * Registers the specified HTTP request handler instances.
	 * 
	 * @param requestHandlers 
	 */
	public final void registerHandlers(final HttpRequestHandler... requestHandlers) {
		for (final HttpRequestHandler requestHandler : requestHandlers) {
			registerHandler(requestHandler);
		}
	}

	/**
	 * Unregisters the specified HTTP request handler instance.
	 * 
	 * @param requestHandler 
	 */
	public final void dropHandler(final HttpRequestHandler requestHandler) {
		if (assigned(requestHandler)) {
			dropHandler(requestHandler.getRoute());
		}
	}

	/**
	 * Unregisters the specified HTTP request handler instances.
	 * 
	 * @param requestHandlers 
	 */
	public final void dropHandlers(final HttpRequestHandler... requestHandlers) {
		for (final HttpRequestHandler requestHandler : requestHandlers) {
			dropHandler(requestHandler);
		}
	}

}
