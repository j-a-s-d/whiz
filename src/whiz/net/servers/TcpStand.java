/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.servers;

import ace.concurrency.Threads;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

abstract class TcpStand extends NetworkServer implements Runnable {

	public static int DEFAULT_THREADS = 1;

	private ExecutorService _threadPool;
	private ServerSocket _tcpServer;
	private boolean _listening;

	public TcpStand() {
		setExecutionThreadsMaxCount(DEFAULT_THREADS);
	}

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

	public final void start() {
		if (assigned(_tcpServer)) {
			_listening = true;
			onStartListening();
			Threads.spawn(this);
		}
	}

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
