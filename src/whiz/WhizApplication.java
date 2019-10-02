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

public abstract class WhizApplication extends WhizObject implements Initializable, Startable, Stoppable {

	private final boolean _hasArguments;
	private final String[] _arguments;
	private final DateTime _instanceLaunchTime;
	private final String _instanceId;
	private final PeriodFormatter _periodFormatter;
	private Jar _jar;
	private final File _jarDirectory;
	private final Integer _processId;

	public WhizApplication(final Class<?> clazz, final String[] args) {
		this(clazz, GUID.makeAsString(), args);
	}

	public WhizApplication(final Class<?> clazz, final String instanceId, final String[] args) {
		this(clazz, instanceId, Date.now(), args);
	}

	public WhizApplication(final Class<?> clazz, final DateTime instanceLaunchTime, final String[] args) {
		this(clazz, GUID.makeAsString(), instanceLaunchTime, args);
	}

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

	public boolean hasArguments() {
		return _hasArguments;
	}

	public String[] getArguments() {
		return _arguments;
	}

	public DateTime getInstanceLaunchTime() {
		return _instanceLaunchTime;
	}

	public String getInstanceLaunchTimeAsString() {
		return Date.format(getInstanceLaunchTime(), Date.FORMAT_YMD_HMS_SSS);
	}

	public String getInstanceUpTimeAsString() {
		return _periodFormatter.print(new Period(_instanceLaunchTime, Date.now()));
	}

	public String getInstanceId() {
		return _instanceId;
	}

	public void setJar(final Jar jar) {
		_jar = jar;
	}

	public Jar getJar() {
		return _jar;
	}

	public File getJarDirectory() {
		return _jarDirectory;
	}

	public String getJarPath() {
		final String p = FilenameUtils.ensureLastDirectorySeparator(_jarDirectory.getPath());
		final String x = "build" + File.separator;
		// NOTE: determines if it is running in dev time
		return Strings.endsWithIgnoreCase(p, x) ? Strings.dropRight(p, x.length()) : p;
	}

	public Integer getProcessId() {
		return _processId;
	}

}
