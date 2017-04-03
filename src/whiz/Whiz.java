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
	public static final SemanticVersion WHIZ_VERSION = SemanticVersion.fromString("0.1.0");

	// RTTI
	private static final int CURRENT_STACK_TRACE_INDEX = 4;

	protected static synchronized final String getCallerClassName() {
		final StackTraceElement ste = Threads.getCurrentThreadStackTraceElement(CURRENT_STACK_TRACE_INDEX + 1);
		return ste != null ? ste.getClassName() : STRINGS.EMPTY;
	}

	protected static synchronized final String getCurrentClassName() {
		final StackTraceElement ste = Threads.getCurrentThreadStackTraceElement(CURRENT_STACK_TRACE_INDEX);
		return ste != null ? ste.getClassName() : STRINGS.EMPTY;
	}

	protected static synchronized final String getCallerMethodName() {
		final StackTraceElement ste = Threads.getCurrentThreadStackTraceElement(CURRENT_STACK_TRACE_INDEX + 1);
		return ste != null ? ste.getMethodName() : STRINGS.EMPTY;
	}

	protected static synchronized final String getCurrentMethodName() {
		final StackTraceElement ste = Threads.getCurrentThreadStackTraceElement(CURRENT_STACK_TRACE_INDEX);
		return ste != null ? ste.getMethodName() : STRINGS.EMPTY;
	}

	protected static synchronized final String getCallerFileName() {
		final StackTraceElement ste = Threads.getCurrentThreadStackTraceElement(CURRENT_STACK_TRACE_INDEX + 1);
		return ste != null ? ste.getFileName() : STRINGS.EMPTY;
	}

	protected static synchronized final String getCurrentFileName() {
		final StackTraceElement ste = Threads.getCurrentThreadStackTraceElement(CURRENT_STACK_TRACE_INDEX);
		return ste != null ? ste.getFileName() : STRINGS.EMPTY;
	}

	protected static synchronized final int getCallerLineNumber() {
		final StackTraceElement ste = Threads.getCurrentThreadStackTraceElement(CURRENT_STACK_TRACE_INDEX + 1);
		return ste != null ? ste.getLineNumber() : -1;
	}

	protected static synchronized final int getCurrentLineNumber() {
		final StackTraceElement ste = Threads.getCurrentThreadStackTraceElement(CURRENT_STACK_TRACE_INDEX);
		return ste != null ? ste.getLineNumber() : -1;
	}

	protected static synchronized final String getCallerInstructionLocation() {
		final StackTraceElement ste = Threads.getCurrentThreadStackTraceElement(CURRENT_STACK_TRACE_INDEX + 1);
		return ste != null ? ste.toString() : STRINGS.EMPTY;
	}

	protected static synchronized final String getCurrentInstructionLocation() {
		final StackTraceElement ste = Threads.getCurrentThreadStackTraceElement(CURRENT_STACK_TRACE_INDEX);
		return ste != null ? ste.toString() : STRINGS.EMPTY;
	}

}
