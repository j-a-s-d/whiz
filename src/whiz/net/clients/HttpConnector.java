/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.clients;

import ace.constants.STRINGS;
import ace.containers.Lists;
import java.net.HttpCookie;
import java.util.List;
import whiz.WhizObject;
import whiz.net.HttpMethod;
import whiz.net.HttpStatus;
import whiz.net.interfaces.HttpConnectionInfo;
import whiz.net.interfaces.HttpCookieHandler;

public class HttpConnector extends WhizObject implements HttpCookieHandler {

	public static final char c_SLASH = '/';

	private final String _base;
	protected List<HttpCookie> _cookies;
	protected Integer _connectTimeout;
	protected Integer _readTimeout;

	public HttpConnector() {
		this(STRINGS.EMPTY);
	}

	public HttpConnector(final String baseUrl) {
		final int l = baseUrl.length();
		if (l == 0) {
			_base = null;
		} else if (baseUrl.charAt(l - 1) != c_SLASH) {
			_base = baseUrl;
		} else {
			_base = baseUrl.substring(0, l - 1);
		}
		_cookies = Lists.make();
		_connectTimeout = null;
		_readTimeout = null;
	}

	private String formUrl(final String path) {
		return _base == null || _base.isEmpty() ? path
			: path == null || path.isEmpty() ? _base
				: path.charAt(0) == c_SLASH ? _base + path
				: _base + c_SLASH + path;
	}

	private HttpConnectionInfo fire(final HttpConnection connection) {
		HttpConnectionInfo result = null;
		try {
			if (assigned(_connectTimeout)) {
				connection.setConnectTimeout(_connectTimeout);
			}
			if (assigned(_readTimeout)) {
				connection.setReadTimeout(_readTimeout);
			}
			connection.setCookies(_cookies);
			_cookies.clear();
			result = connection.go();
			_cookies.addAll(result.getResponseCookies());
		} catch (final Exception e) {
			setLastException(e);
		}
		return result;
	}

	private String getResponseDataAsString(final HttpConnectionInfo hci) {
		return assigned(hci) && (hci.getResponseCode() == HttpStatus.OK) ? hci.getResponseData() : null;
	}

	public HttpConnectionInfo get(final String urlPath) {
		return fire(new HttpGetter(formUrl(urlPath)));
	}

	public String stringGet(final String urlPath) {
		return getResponseDataAsString(fire(new HttpGetter(formUrl(urlPath))));
	}

	public HttpConnectionInfo post(final String urlPath, final String requestData) {
		return fire(new HttpPoster(formUrl(urlPath), requestData));
	}

	public String stringPost(final String urlPath, final String requestData) {
		return getResponseDataAsString(fire(new HttpPoster(formUrl(urlPath), requestData)));
	}

	public HttpConnectionInfo put(final String urlPath, final String requestData) {
		return fire(new HttpPutter(formUrl(urlPath), requestData));
	}

	public String stringPut(final String urlPath, final String requestData) {
		return getResponseDataAsString(fire(new HttpPutter(formUrl(urlPath), requestData)));
	}

	public HttpConnectionInfo delete(final String urlPath) {
		return fire(new HttpDeleter(formUrl(urlPath)));
	}

	public String stringDelete(final String urlPath) {
		return getResponseDataAsString(fire(new HttpDeleter(formUrl(urlPath))));
	}

	public HttpConnectionInfo head(final String urlPath) {
		return fire(new HttpConnection(formUrl(urlPath), HttpMethod.HEAD, null) {});
	}

	public HttpConnectionInfo options(final String urlPath) {
		return fire(new HttpConnection(formUrl(urlPath), HttpMethod.OPTIONS, null) {});
	}

	public HttpConnectionInfo trace(final String urlPath) {
		return fire(new HttpConnection(formUrl(urlPath), HttpMethod.TRACE, null) {});
	}

	// HttpCookieHandler overrides
	@Override public void clearCookies() {
		_cookies.clear();
	}

	@Override public void addCookie(final HttpCookie cookie) {
		_cookies.add(cookie);
	}

	@Override public void removeCookie(final HttpCookie cookie) {
		_cookies.remove(cookie);
	}

	@Override public void addCookies(final List<HttpCookie> cookies) {
		_cookies.addAll(cookies);
	}

	@Override public void setCookies(final List<HttpCookie> cookies) {
		_cookies.clear();
		_cookies.addAll(cookies);
	}

	@Override public List<HttpCookie> listCookies() {
		return _cookies;
	}

	@Override public HttpCookie getCookieByName(final String name) {
		for (final HttpCookie cookie : _cookies) {
			if (name.equals(cookie.getName())) {
				return cookie;
			}
		}
		return null;
	}

	public Integer getReadTimeout() {
		return _readTimeout;
	}

	public void setReadTimeout(final int value) {
		_readTimeout = value;
	}

	public Integer getConnectTimeout() {
		return _connectTimeout;
	}

	public void setConnectTimeout(final int value) {
		_connectTimeout = value;
	}

}
