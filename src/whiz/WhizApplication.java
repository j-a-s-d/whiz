/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz;

import ace.arrays.GenericArrays;
import ace.constants.STRINGS;
import ace.date.Date;
import ace.files.Directories;
import ace.files.FilenameUtils;
import ace.interfaces.Initializable;
import ace.interfaces.Startable;
import ace.interfaces.Stoppable;
import ace.platform.Jar;
import ace.platform.Jars;
import ace.platform.OS;
import ace.randomness.GUID;
import ace.text.Strings;
import java.io.File;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

/**
 * Abstract whiz application class.
 */
public abstract class WhizApplication extends WhizObject implements Initializable, Startable, Stoppable {

	private final boolean _hasArguments;
	private final String[] _arguments;
	private final DateTime _instanceLaunchTime;
	private final String _instanceId;
	private final PeriodFormatter _periodFormatter;
	private Jar _jar;
	private final File _jarDirectory;
	private final Integer _processId;

	/**
	 * Constructor accepting a class instance and an arguments array of strings.
	 * 
	 * @param clazz
	 * @param arguments 
	 */
	public WhizApplication(final Class<?> clazz, final String[] arguments) {
		this(clazz, GUID.makeAsString(), arguments);
	}

	/**
	 * Constructor accepting a class instance, an instance identifier and an arguments array of strings.
	 * 
	 * @param clazz
	 * @param instanceId
	 * @param arguments 
	 */
	public WhizApplication(final Class<?> clazz, final String instanceId, final String[] arguments) {
		this(clazz, instanceId, Date.now(), arguments);
	}

	/**
	 * Constructor accepting a class instance, an instance launch time and an arguments array of strings.
	 * 
	 * @param clazz
	 * @param instanceLaunchTime
	 * @param arguments 
	 */
	public WhizApplication(final Class<?> clazz, final DateTime instanceLaunchTime, final String[] arguments) {
		this(clazz, GUID.makeAsString(), instanceLaunchTime, arguments);
	}

	/**
	 * Constructor accepting a class instance, an instance identifier, an instance launch time and an arguments array of strings.
	 * 
	 * @param clazz
	 * @param instanceId
	 * @param instanceLaunchTime
	 * @param arguments 
	 */
	public WhizApplication(final Class<?> clazz, final String instanceId, final DateTime instanceLaunchTime, final String[] arguments) {
		super(clazz);
		Thread.currentThread().setName(clazz.getSimpleName());
		_instanceId = instanceId;
		_instanceLaunchTime = instanceLaunchTime;
		_jarDirectory = ensure(assigned(_jar = Jars.getJarForClass(clazz)) ? _jar.getFile().getParentFile() : Directories.CURRENT, Directories.CURRENT);
		_periodFormatter = new PeriodFormatterBuilder()
			.appendYears().appendSuffix(STRINGS.LOWERCASE_Y, STRINGS.LOWERCASE_Y).appendSeparator(STRINGS.SPACE)
			.appendMonths().appendSuffix(STRINGS.LOWERCASE_M, STRINGS.LOWERCASE_M).appendSeparator(STRINGS.SPACE)
			.appendDays().appendSuffix(STRINGS.LOWERCASE_D, STRINGS.LOWERCASE_D).appendSeparator(STRINGS.SPACE)
			.appendHours().appendSuffix(STRINGS.LOWERCASE_H, STRINGS.LOWERCASE_H).appendSeparator(STRINGS.SPACE)
			.appendMinutes().appendSuffix(STRINGS.LOWERCASE_M, STRINGS.LOWERCASE_M).appendSeparator(STRINGS.SPACE)
			.appendSeconds().appendSuffix(STRINGS.LOWERCASE_S, STRINGS.LOWERCASE_S)
		.toFormatter();
		_processId = OS.isUnix() ? OS.getUnixProcessId() : null;
		_hasArguments = GenericArrays.hasContent(_arguments = arguments);
	}

	/**
	 * Determines if the application received command line arguments.
	 * 
	 * @return <tt>true</tt> if the application received command line arguments, <tt>false</tt> otherwise
	 */
	public boolean hasArguments() {
		return _hasArguments;
	}

	/**
	 * Gets the application command line arguments string array.
	 * 
	 * @return the application command line arguments string array
	 */
	public String[] getArguments() {
		return _arguments;
	}

	/**
	 * Gets the application instance launch time as a date time instance.
	 * 
	 * @return the application instance launch time as a date time instance
	 */
	public DateTime getInstanceLaunchTime() {
		return _instanceLaunchTime;
	}

	/**
	 * Gets the application instance launch time as a date time formatted string.
	 * 
	 * @return the application instance launch time as a date time formatted string
	 */
	public String getInstanceLaunchTimeAsString() {
		return Date.format(getInstanceLaunchTime(), Date.FORMAT_YMD_HMS_SSS);
	}

	/**
	 * Gets the application instance up time as a date time formatted string.
	 * 
	 * @return the application instance up time as a date time formatted string
	 */
	public String getInstanceUpTimeAsString() {
		return _periodFormatter.print(new Period(_instanceLaunchTime, Date.now()));
	}

	/**
	 * Gets the application instance identifier.
	 * 
	 * @return the application instance identifier
	 */
	public String getInstanceId() {
		return _instanceId;
	}

	/**
	 * Sets the associated jar instance.
	 * 
	 * @param jar 
	 */
	public void setJar(final Jar jar) {
		_jar = jar;
	}

	/**
	 * Gets the associated jar instance.
	 * 
	 * @return the associated jar instance
	 */
	public Jar getJar() {
		return _jar;
	}

	/**
	 * Gets the associated jar instance directory.
	 * 
	 * @return the associated jar instance directory
	 */
	public File getJarDirectory() {
		return _jarDirectory;
	}

	/**
	 * Gets the associated jar instance directory path.
	 * 
	 * NOTE: while running in NetBeans IDE, it retrieves the local build path to ensure the correct behavior
	 * 
	 * @return the associated jar instance directory path
	 */
	public String getJarPath() {
		final String p = FilenameUtils.ensureLastDirectorySeparator(_jarDirectory.getPath());
		final String x = "build" + File.separator;
		// NOTE: determines if it is running in dev time
		return Strings.endsWithIgnoreCase(p, x) ? Strings.dropRight(p, x.length()) : p;
	}

	/**
	 * Gets the process identifier.
	 * 
	 * @return the process identifier
	 */
	public Integer getProcessId() {
		return _processId;
	}

}
