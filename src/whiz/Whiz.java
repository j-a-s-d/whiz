/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz;

import ace.Ace;
import ace.SemanticVersion;
import ace.concurrency.Threads;
import ace.constants.STRINGS;

/**
 * Whiz class.
 */
public class Whiz extends Ace {

	/**
	 * Whiz version.
	 */
	public static final SemanticVersion WHIZ_VERSION = SemanticVersion.fromString("0.5.0");

	// RTTI
	private static final int CURRENT_STACK_TRACE_INDEX = 4;

	/**
	 * Gets the caller (who invokes this method) class name.
	 * 
	 * @return the caller class name
	 */
	protected static synchronized final String getCallerClassName() {
		final StackTraceElement ste = Threads.getCurrentThreadStackTraceElement(CURRENT_STACK_TRACE_INDEX + 1);
		return ste != null ? ste.getClassName() : STRINGS.EMPTY;
	}

	/**
	 * Gets the current class name.
	 * 
	 * @return the current class name
	 */
	protected static synchronized final String getCurrentClassName() {
		final StackTraceElement ste = Threads.getCurrentThreadStackTraceElement(CURRENT_STACK_TRACE_INDEX);
		return ste != null ? ste.getClassName() : STRINGS.EMPTY;
	}

	/**
	 * Gets the caller (who invokes this method) method name.
	 * 
	 * @return the caller method name
	 */
	protected static synchronized final String getCallerMethodName() {
		final StackTraceElement ste = Threads.getCurrentThreadStackTraceElement(CURRENT_STACK_TRACE_INDEX + 1);
		return ste != null ? ste.getMethodName() : STRINGS.EMPTY;
	}

	/**
	 * Gets the current method name.
	 * 
	 * @return the current method name
	 */
	protected static synchronized final String getCurrentMethodName() {
		final StackTraceElement ste = Threads.getCurrentThreadStackTraceElement(CURRENT_STACK_TRACE_INDEX);
		return ste != null ? ste.getMethodName() : STRINGS.EMPTY;
	}

	/**
	 * Gets the caller (who invokes this method) file name.
	 * 
	 * @return the caller file name
	 */
	protected static synchronized final String getCallerFileName() {
		final StackTraceElement ste = Threads.getCurrentThreadStackTraceElement(CURRENT_STACK_TRACE_INDEX + 1);
		return ste != null ? ste.getFileName() : STRINGS.EMPTY;
	}

	/**
	 * Gets the current file name.
	 * 
	 * @return the current file name
	 */
	protected static synchronized final String getCurrentFileName() {
		final StackTraceElement ste = Threads.getCurrentThreadStackTraceElement(CURRENT_STACK_TRACE_INDEX);
		return ste != null ? ste.getFileName() : STRINGS.EMPTY;
	}

	/**
	 * Gets the caller (who invokes this method) line number.
	 * 
	 * @return the caller line number
	 */
	protected static synchronized final int getCallerLineNumber() {
		final StackTraceElement ste = Threads.getCurrentThreadStackTraceElement(CURRENT_STACK_TRACE_INDEX + 1);
		return ste != null ? ste.getLineNumber() : -1;
	}

	/**
	 * Gets the current line number.
	 * 
	 * @return the current line number
	 */
	protected static synchronized final int getCurrentLineNumber() {
		final StackTraceElement ste = Threads.getCurrentThreadStackTraceElement(CURRENT_STACK_TRACE_INDEX);
		return ste != null ? ste.getLineNumber() : -1;
	}

	/**
	 * Gets the caller (who invokes this method) instruction location.
	 * 
	 * @return the caller instruction location
	 */
	protected static synchronized final String getCallerInstructionLocation() {
		final StackTraceElement ste = Threads.getCurrentThreadStackTraceElement(CURRENT_STACK_TRACE_INDEX + 1);
		return ste != null ? ste.toString() : STRINGS.EMPTY;
	}

	/**
	 * Gets the current instruction location.
	 * 
	 * @return the current instruction location
	 */
	protected static synchronized final String getCurrentInstructionLocation() {
		final StackTraceElement ste = Threads.getCurrentThreadStackTraceElement(CURRENT_STACK_TRACE_INDEX);
		return ste != null ? ste.toString() : STRINGS.EMPTY;
	}

}
