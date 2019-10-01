/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.clients;

import ace.constants.CHARS;
import ace.constants.STRINGS;
import ace.containers.Lists;
import ace.containers.Maps;
import java.net.HttpCookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import whiz.WhizObject;
import whiz.net.HttpMethod;
import whiz.net.HttpStatus;
import whiz.net.interfaces.HttpConnectionInfo;
import whiz.net.interfaces.HttpCookieHandler;

public class HttpConnector extends WhizObject implements HttpCookieHandler {

	private final String _base;
	protected List<HttpCookie> _cookies;
	protected String _userAgent;
	protected String _contentType;
	protected Integer _connectTimeout;
	protected Integer _readTimeout;
	protected final HashMap<String, String> _customRequestProperties;

	public HttpConnector() {
		this(HttpConnector.class, STRINGS.EMPTY);
	}

	public HttpConnector(final Class<?> clazz) {
		this(clazz, STRINGS.EMPTY);
	}

	public HttpConnector(final String baseUrl) {
		this(HttpConnector.class, baseUrl);
	}

	public HttpConnector(final Class<?> clazz, final String baseUrl) {
		super(clazz);
		final int l = baseUrl.length();
		if (l == 0) {
			_base = null;
		} else if (baseUrl.charAt(l - 1) != CHARS.SLASH) {
			_base = baseUrl;
		} else {
			_base = baseUrl.substring(0, l - 1);
		}
		_cookies = Lists.make();
		_connectTimeout = null;
		_readTimeout = null;
		_contentType = null;
		_userAgent = null;
		_customRequestProperties = Maps.make();
	}

	private String formUrl(final String path) {
		return _base == null || _base.isEmpty() ? path
			: path == null || path.isEmpty() ? _base
				: path.charAt(0) == CHARS.SLASH ? _base + path
				: _base + CHARS.SLASH + path;
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
			if (assigned(_userAgent)) {
				connection.setUserAgent(_userAgent);
			}
			if (assigned(_contentType)) {
				connection.setContentType(_contentType);
			}
			for (final Map.Entry<String, String> n : _customRequestProperties.entrySet()) {
				connection.addCustomRequestHeader(n.getKey(), n.getValue());
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

	private String fireAndGetResponseDataAsString(final HttpConnection hc) {
		final HttpConnectionInfo hci = fire(hc);
		return assigned(hci) && (hci.getResponseCode() == HttpStatus.OK) ? hci.getResponseData() : null;
	}

	private byte[] fireAndGetResponseDataAsByteArray(final HttpConnection hc) {
		final HttpConnectionInfo hci = fire(hc);
		return assigned(hci) && (hci.getResponseCode() == HttpStatus.OK) ? hc.getResponseRawData() : null;
	}

	public HttpConnectionInfo get(final String urlPath) {
		return fire(new HttpGetter(formUrl(urlPath)));
	}

	public byte[] rawGet(final String urlPath) {
		return fireAndGetResponseDataAsByteArray(new HttpGetter(formUrl(urlPath)));
	}

	public String stringGet(final String urlPath) {
		return fireAndGetResponseDataAsString(new HttpGetter(formUrl(urlPath)));
	}

	public HttpConnectionInfo post(final String urlPath, final String requestData) {
		return fire(new HttpPoster(formUrl(urlPath), requestData));
	}

	public byte[] rawPost(final String urlPath, final String requestData) {
		return fireAndGetResponseDataAsByteArray(new HttpPoster(formUrl(urlPath), requestData));
	}

	public String stringPost(final String urlPath, final String requestData) {
		return fireAndGetResponseDataAsString(new HttpPoster(formUrl(urlPath), requestData));
	}

	public HttpConnectionInfo put(final String urlPath, final String requestData) {
		return fire(new HttpPutter(formUrl(urlPath), requestData));
	}

	public byte[] rawPut(final String urlPath, final String requestData) {
		return fireAndGetResponseDataAsByteArray(new HttpPutter(formUrl(urlPath), requestData));
	}

	public String stringPut(final String urlPath, final String requestData) {
		return fireAndGetResponseDataAsString(new HttpPutter(formUrl(urlPath), requestData));
	}

	public HttpConnectionInfo delete(final String urlPath) {
		return fire(new HttpDeleter(formUrl(urlPath)));
	}

	public byte[] rawDelete(final String urlPath) {
		return fireAndGetResponseDataAsByteArray(new HttpDeleter(formUrl(urlPath)));
	}

	public String stringDelete(final String urlPath) {
		return fireAndGetResponseDataAsString(new HttpDeleter(formUrl(urlPath)));
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

	public String getCustomRequestHeader(final String name) {
		return _customRequestProperties.get(name);
	}

	public void addCustomRequestHeader(final String name, final String value) {
		_customRequestProperties.put(name, value);
	}

	public void deleteCustomRequestHeader(final String name) {
		_customRequestProperties.remove(name);
	}

	public void clearCustomRequestHeaders() {
		_customRequestProperties.clear();
	}

	public String getUserAgent() {
		return _userAgent;
	}

	public void setUserAgent(final String value) {
		_userAgent = value;
	}

	public String getContentType() {
		return _contentType;
	}

	public void setContentType(final String value) {
		_contentType = value;
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
