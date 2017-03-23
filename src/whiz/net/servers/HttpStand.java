/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.servers;

import ace.concurrency.Threads;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

public abstract class HttpStand extends NetworkServer {

	public static int STOP_DELAY_SECONDS = 5;

	private HttpServer _httpServer;
	private boolean _allowCrossOrigin;

	public HttpStand(final Class<?> clazz) {
		super(clazz);
	}

	@Override protected final boolean initialize(final InetSocketAddress address) {
		_httpServer = null;
		setAddress(address);
		try {
			_httpServer = HttpServer.create();
			_httpServer.bind(getAddress(), 0);
		} catch (final Exception e) {
			setLastException(e);
		}
		return assigned(_httpServer);
	}

	protected final boolean getCrossOriginAllowance() {
		return _allowCrossOrigin;
	}

	protected final void setCrossOriginAllowance(final boolean value) {
		_allowCrossOrigin = value;
	}

	public final void start() {
		if (assigned(_httpServer)) {
			_httpServer.setExecutor(Threads.getExecutorService(getExecutionThreadsMaxCount()));
			_httpServer.start();
			onStartListening();
		}
	}

	public final void stop() {
		if (assigned(_httpServer)) {
			_httpServer.stop(STOP_DELAY_SECONDS);
			onStopListening();
		}
	}

	public final void registerContext(final String route, final HttpHandler handler) {
		if (assigned(_httpServer)) {
			_httpServer.createContext(route, handler);
		}
	}

	public final void dropHandler(final String route) {
		if (assigned(_httpServer)) {
			_httpServer.removeContext(route);
		}
	}

	public final void registerHandler(final HttpRequestHandler requestHandler) {
		if (assigned(_httpServer)) {
			registerContext(requestHandler.getRoute(), requestHandler.setStand(this));
		}
	}

	public final void registerHandlers(final HttpRequestHandler... requestHandlers) {
		for (final HttpRequestHandler requestHandler : requestHandlers) {
			registerHandler(requestHandler);
		}
	}

	public final void dropHandler(final HttpRequestHandler requestHandler) {
		if (assigned(requestHandler)) {
			dropHandler(requestHandler.getRoute());
		}
	}

	public final void dropHandlers(final HttpRequestHandler... requestHandlers) {
		for (final HttpRequestHandler requestHandler : requestHandlers) {
			dropHandler(requestHandler);
		}
	}

}
