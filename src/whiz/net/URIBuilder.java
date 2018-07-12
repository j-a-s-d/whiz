/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net;

import ace.Sandboxed;
import ace.constants.STRINGS;
import ace.text.Strings;
import java.net.URI;
import java.net.URL;
import whiz.WhizObject;

/**
 * Useful URI builder.
 */
public class URIBuilder extends WhizObject {

	// NOTE: conforms the RFCs 2396 and 3986 requirement,
	// scheme:[//[user:password@]host[:port]][/]path[?query][#fragment]
	private String _scheme = URISchemes.HTTP;
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

	public URIBuilder(final URIBuilder other) {
		super(URIBuilder.class);
		assign(other);
	}

	public URIBuilder assign(final URIBuilder other) {
		if (assigned(other)) {
			_scheme = other._scheme;
			_username = other._username;
			_password = other._password;
			_host = other._host;
			_port = other._port;
			_path = other._path;
			_query = other._query;
			_fragment = other._fragment;
		}
		return this;
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
		final boolean hasPath = Strings.hasText(_path);
		final boolean hasQuery = Strings.hasText(_query);
		final boolean hasFragment = Strings.hasText(_fragment);
		if (hasPath || hasQuery || hasFragment) {
			sb.append(STRINGS.SLASH);
			if (hasPath) {
				sb.append(_path).append(STRINGS.SLASH);
			}
			if (hasQuery) {
				sb.append(STRINGS.QUESTION).append(_query);
			}
			if (hasFragment) {
				sb.append(STRINGS.NUMERAL).append(_fragment);
			}
		}
		return sb.toString();
	}

	public final URI getAsURI() {
		return new Sandboxed<URI>() { @Override public URI run() throws Exception {
			return new URI(getAsString());
		}}.go();
	}

	public final URL getAsURL() {
		return new Sandboxed<URL>() { @Override public URL run() throws Exception {
			return new URL(getAsString());
		}}.go();
	}

}
