/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net;

import ace.constants.STRINGS;
import ace.text.Strings;
import whiz.WhizObject;

/**
 * Useful URI builder.
 */
public class URIBuilder extends WhizObject {

	// NOTE: conforms the RFCs 2396 and 3986 requirement,
	// scheme:[//[user:password@]host[:port]][/]path[?query][#fragment]
	private String _scheme;
	private String _username;
	private String _password;
	private String _host;
	private Integer _port;
	private String _path;
	private String _query;
	private String _fragment;

	public URIBuilder() {
		super(URIBuilder.class);
	}

	public final String getScheme() {
		return _scheme;
	}

	public final String getUsername() {
		return _username;
	}

	public final String getPassword() {
		return _password;
	}

	public final String getHost() {
		return _host;
	}

	public final Integer getPort() {
		return _port;
	}

	public final String getPath() {
		return _path;
	}

	public final String getQuery() {
		return _query;
	}

	public final String getFragment() {
		return _fragment;
	}

	public final URIBuilder setScheme(final String value) {
		_scheme = value;
		return this;
	}

	public final URIBuilder setUsername(final String value) {
		_username = value;
		return this;
	}

	public final URIBuilder setPassword(final String value) {
		_password = value;
		return this;
	}

	public final URIBuilder setHost(final String value) {
		_host = value;
		return this;
	}

	public final URIBuilder setPort(final Integer value) {
		_port = value;
		return this;
	}

	public final URIBuilder setPath(final String value) {
		_path = value;
		return this;
	}

	public final URIBuilder setQuery(final String value) {
		_query = value;
		return this;
	}

	public final URIBuilder setFragment(final String value) {
		_fragment = value;
		return this;
	}

	public final String getAsString() {
		final StringBuilder sb = new StringBuilder()
			.append(_scheme)
			.append(STRINGS.COLON)
			.append(STRINGS.SLASH)
			.append(STRINGS.SLASH);
		if (Strings.haveText(_username, _password)) {
			sb.append(_username).append(STRINGS.COLON).append(_password).append(STRINGS.AT);
		}
		sb.append(_host);
		if (assigned(_port)) {
			sb.append(STRINGS.COLON).append(_port);
		}
		sb.append(STRINGS.SLASH);
		if (Strings.hasText(_path)) {
			sb.append(_path).append(STRINGS.SLASH);
		}
		if (Strings.hasText(_query)) {
			sb.append(STRINGS.QUESTION).append(_query);
		}
		if (Strings.hasText(_fragment)) {
			sb.append(STRINGS.NUMERAL).append(_fragment);
		}
		return sb.toString();
	}

}
