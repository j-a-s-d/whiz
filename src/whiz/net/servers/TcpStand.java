/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.servers;

import ace.concurrency.Threads;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

/**
 * Abstract TCP stand class.
 */
abstract class TcpStand extends NetworkServer implements Runnable {

	/**
	 * The default amount of threads of the server.
	 */
	public static int DEFAULT_THREADS = 1;

	private ExecutorService _threadPool;
	private ServerSocket _tcpServer;
	private boolean _listening;

	/**
	 * Constructor accepting a class instance.
	 * 
	 * @param clazz 
	 */
	public TcpStand(final Class<?> clazz) {
		super(clazz);
		setExecutionThreadsMaxCount(DEFAULT_THREADS);
	}

	/**
	 * Determines if the server is listening.
	 * 
	 * @return <tt>true</tt> if the server is listening, <tt>false</tt> otherwise
	 */
	public final synchronized boolean isListening() {
		return _listening;
	}

	@Override protected final boolean initialize(final InetSocketAddress address) {
		_tcpServer = null;
		setAddress(address);
		try {
			_tcpServer = new ServerSocket();
			_tcpServer.bind(getAddress());
		} catch (final Exception e) {
			setLastException(e);
		}
		return assigned(_tcpServer);
	}

	/**
	 * Starts the server to listen for incoming connections.
	 */
	public final void start() {
		if (assigned(_tcpServer)) {
			_listening = true;
			onStartListening();
			Threads.spawn(this);
		}
	}

	/**
	 * Stops the server from listening for incoming connections.
	 */
	public final void stop() {
		if (assigned(_tcpServer)) {
			_listening = false;
			try {
				_tcpServer.close();
			} catch (final Exception e) {
				setLastException(e);
			}
			onStopListening();
		}
	}

	@Override public final void run() {
		_threadPool = Threads.getExecutorService(getExecutionThreadsMaxCount());
		while (isListening()) {
			try {
				_threadPool.execute(makeHandler(_tcpServer.accept()));
			} catch (final Exception e) {
				setLastException(e);
			}
		}
		_threadPool.shutdown();
	}

	abstract public TcpAbstractHandler makeHandler(final Socket socket);

}
