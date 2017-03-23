/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz;

import ace.LocalExceptionHandler;
import ace.interfaces.ExceptionsHandler;
import ace.interfaces.ExceptionsMonitor;
import java.util.List;

public class WhizEntity extends Whiz implements ExceptionsHandler {

	// EXCEPTIONS
	private final LocalExceptionHandler _leh = new LocalExceptionHandler();

	@Override public final void forgetExceptionsThrown() {
		_leh.forgetExceptionsThrown();
	}

	@Override public final List<Throwable> getExceptionsThrown() {
		return _leh.getExceptionsThrown();
	}

	@Override public final boolean hadException() {
		return _leh.hadException();
	}

	@Override public final void forgetLastException() {
		_leh.forgetLastException();
	}

	@Override public final Throwable getLastException() {
		return _leh.getLastException();
	}

	@Override public final long getLastExceptionTimestamp() {
		return _leh.getLastExceptionTimestamp();
	}

	@Override public final void setLastException(final Throwable e) {
		_leh.setLastException(e);
	}

	@Override public final ExceptionsMonitor getExceptionsMonitor() {
		return _leh.getExceptionsMonitor();
	}

	@Override public final void setExceptionsMonitor(final ExceptionsMonitor exceptionsMonitor) {
		_leh.setExceptionsMonitor(exceptionsMonitor);
	}

	@Override public final int getExceptionsCount() {
		return _leh.getExceptionsCount();
	}

	@Override public final Thread getLastExceptionThread() {
		return _leh.getLastExceptionThread();
	}

}
