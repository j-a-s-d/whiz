/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz;

import ace.LocalExceptionHandler;
import ace.interfaces.ExceptionsHandler;
import ace.interfaces.ExceptionsMonitor;
import java.util.List;

public class WhizEntity extends Whiz implements ExceptionsHandler {

	// EXCEPTIONS
	private final LocalExceptionHandler _leh = new LocalExceptionHandler();

	/**
	 * Drops the caught exceptions.
	 */
	@Override public final void forgetExceptionsThrown() {
		_leh.forgetExceptionsThrown();
	}

	/**
	 * Retrieves the list of caught exceptions.
	 * 
	 * @return the list of caught exceptions
	 */
	@Override public final List<Throwable> getExceptionsThrown() {
		return _leh.getExceptionsThrown();
	}

	/**
	 * Determines if an exception has been caught.
	 * 
	 * @return <tt>true</tt> if an exception has been caught, <tt>false</tt> otherwise
	 */
	@Override public final boolean hadException() {
		return _leh.hadException();
	}

	/**
	 * Drops the last caught exception.
	 */
	@Override public final void forgetLastException() {
		_leh.forgetLastException();
	}

	/**
	 * Gets the last caught exception.
	 * 
	 * @return the last caught exception
	 */
	@Override public final Throwable getLastException() {
		return _leh.getLastException();
	}

	/**
	 * Gets the last caught exception timestamp.
	 * 
	 * @return the last caught exception timestamp
	 */
	@Override public final long getLastExceptionTimestamp() {
		return _leh.getLastExceptionTimestamp();
	}

	/**
	 * Catches the specified exception.
	 * 
	 * @param e 
	 */
	@Override public final void setLastException(final Throwable e) {
		_leh.setLastException(e);
	}

	/**
	 * Gets the exceptions external monitor.
	 * 
	 * @return the exceptions external monitor
	 */
	@Override public final ExceptionsMonitor getExceptionsMonitor() {
		return _leh.getExceptionsMonitor();
	}

	/**
	 * Sets the exceptions external monitor.
	 * 
	 * @param exceptionsMonitor
	 */
	@Override public final void setExceptionsMonitor(final ExceptionsMonitor exceptionsMonitor) {
		_leh.setExceptionsMonitor(exceptionsMonitor);
	}

	/**
	 * Gets the amount of caught exceptions.
	 * 
	 * @return the amount of caught exceptions
	 */
	@Override public final int getExceptionsCount() {
		return _leh.getExceptionsCount();
	}

	/**
	 * Gets the last caught exception thread.
	 * 
	 * @return the last caught exception thread
	 */
	@Override public final Thread getLastExceptionThread() {
		return _leh.getLastExceptionThread();
	}

}
