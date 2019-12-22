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

/**
 * Useful HTTP connector class.
 */
public class HttpConnector extends WhizObject implements HttpCookieHandler {

	private final String _base;
	protected List<HttpCookie> _cookies;
	protected String _userAgent;
	protected String _contentType;
	protected Integer _connectTimeout;
	protected Integer _readTimeout;
	protected final HashMap<String, String> _customRequestProperties;

	/**
	 * Default constructor.
	 */
	public HttpConnector() {
		this(HttpConnector.class, STRINGS.EMPTY);
	}

	/**
	 * Constructor accepting a class instance.
	 * 
	 * @param clazz 
	 */
	public HttpConnector(final Class<?> clazz) {
		this(clazz, STRINGS.EMPTY);
	}

	/**
	 * Constructor accepting a base URL string.
	 * 
	 * @param baseUrl 
	 */
	public HttpConnector(final String baseUrl) {
		this(HttpConnector.class, baseUrl);
	}

	/**
	 * Constructor accepting a class instance and a base URL string.
	 * 
	 * @param clazz
	 * @param baseUrl 
	 */
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

	/**
	 * Fires a GET request to the specified URL path of the base URL.
	 * 
	 * @param urlPath
	 * @return the resulting HTTP connection information instance
	 */
	public HttpConnectionInfo get(final String urlPath) {
		return fire(new HttpGetter(formUrl(urlPath)));
	}

	/**
	 * Fires a GET request to the specified URL path of the base URL.
	 * 
	 * @param urlPath
	 * @return the resulting replied byte array
	 */
	public byte[] rawGet(final String urlPath) {
		return fireAndGetResponseDataAsByteArray(new HttpGetter(formUrl(urlPath)));
	}

	/**
	 * Fires a GET request to the specified URL path of the base URL.
	 * 
	 * @param urlPath
	 * @return the resulting replied string
	 */
	public String stringGet(final String urlPath) {
		return fireAndGetResponseDataAsString(new HttpGetter(formUrl(urlPath)));
	}

	/**
	 * Fires a POST request to the specified URL path of the base URL with the specified request data.
	 * 
	 * @param urlPath
	 * @param requestData
	 * @return the resulting HTTP connection information instance
	 */
	public HttpConnectionInfo post(final String urlPath, final String requestData) {
		return fire(new HttpPoster(formUrl(urlPath), requestData));
	}

	/**
	 * Fires a POST request to the specified URL path of the base URL with the specified request data.
	 * 
	 * @param urlPath
	 * @param requestData
	 * @return the resulting replied byte array
	 */
	public byte[] rawPost(final String urlPath, final String requestData) {
		return fireAndGetResponseDataAsByteArray(new HttpPoster(formUrl(urlPath), requestData));
	}

	/**
	 * Fires a POST request to the specified URL path of the base URL with the specified request data.
	 * 
	 * @param urlPath
	 * @param requestData
	 * @return the resulting replied string
	 */
	public String stringPost(final String urlPath, final String requestData) {
		return fireAndGetResponseDataAsString(new HttpPoster(formUrl(urlPath), requestData));
	}

	/**
	 * Fires a PUT request to the specified URL path of the base URL with the specified request data.
	 * 
	 * @param urlPath
	 * @param requestData
	 * @return the resulting HTTP connection information instance
	 */
	public HttpConnectionInfo put(final String urlPath, final String requestData) {
		return fire(new HttpPutter(formUrl(urlPath), requestData));
	}

	/**
	 * Fires a PUT request to the specified URL path of the base URL with the specified request data.
	 * 
	 * @param urlPath
	 * @param requestData
	 * @return the resulting replied byte array
	 */
	public byte[] rawPut(final String urlPath, final String requestData) {
		return fireAndGetResponseDataAsByteArray(new HttpPutter(formUrl(urlPath), requestData));
	}

	/**
	 * Fires a PUT request to the specified URL path of the base URL with the specified request data.
	 * 
	 * @param urlPath
	 * @param requestData
	 * @return the resulting replied string
	 */
	public String stringPut(final String urlPath, final String requestData) {
		return fireAndGetResponseDataAsString(new HttpPutter(formUrl(urlPath), requestData));
	}

	/**
	 * Fires a DELETE request to the specified URL path of the base URL.
	 * 
	 * @param urlPath
	 * @return the resulting HTTP connection information instance
	 */
	public HttpConnectionInfo delete(final String urlPath) {
		return fire(new HttpDeleter(formUrl(urlPath)));
	}

	/**
	 * Fires a DELETE request to the specified URL path of the base URL.
	 * 
	 * @param urlPath
	 * @return the resulting replied byte array
	 */
	public byte[] rawDelete(final String urlPath) {
		return fireAndGetResponseDataAsByteArray(new HttpDeleter(formUrl(urlPath)));
	}

	/**
	 * Fires a DELETE request to the specified URL path of the base URL.
	 * 
	 * @param urlPath
	 * @return the resulting replied string
	 */
	public String stringDelete(final String urlPath) {
		return fireAndGetResponseDataAsString(new HttpDeleter(formUrl(urlPath)));
	}

	/**
	 * Fires a HEAD request to the specified URL path of the base URL.
	 * 
	 * @param urlPath
	 * @return the resulting HTTP connection information instance
	 */
	public HttpConnectionInfo head(final String urlPath) {
		return fire(new HttpConnection(formUrl(urlPath), HttpMethod.HEAD, null) {});
	}

	/**
	 * Fires a OPTIONS request to the specified URL path of the base URL.
	 * 
	 * @param urlPath
	 * @return the resulting HTTP connection information instance
	 */
	public HttpConnectionInfo options(final String urlPath) {
		return fire(new HttpConnection(formUrl(urlPath), HttpMethod.OPTIONS, null) {});
	}

	/**
	 * Fires a TRACE request to the specified URL path of the base URL.
	 * 
	 * @param urlPath
	 * @return the resulting HTTP connection information instance
	 */
	public HttpConnectionInfo trace(final String urlPath) {
		return fire(new HttpConnection(formUrl(urlPath), HttpMethod.TRACE, null) {});
	}

	// HttpCookieHandler overrides

	/**
	 * Drops all the existing HTTP cookies.
	 */
	@Override public void clearCookies() {
		_cookies.clear();
	}

	/**
	 * Adds the specified HTTP cookie instance.
	 * 
	 * @param cookie 
	 */
	@Override public void addCookie(final HttpCookie cookie) {
		_cookies.add(cookie);
	}

	/**
	 * Removes the specified HTTP cookie instance.
	 * 
	 * @param cookie 
	 */
	@Override public void removeCookie(final HttpCookie cookie) {
		_cookies.remove(cookie);
	}

	/**
	 * Adds the specified list of HTTP cookies instances.
	 * 
	 * @param cookies 
	 */
	@Override public void addCookies(final List<HttpCookie> cookies) {
		_cookies.addAll(cookies);
	}

	/**
	 * Sets the specified list of HTTP cookies instances.
	 * 
	 * Already existing HTTP cookies will be dropped.
	 * 
	 * @param cookies 
	 */
	@Override public void setCookies(final List<HttpCookie> cookies) {
		_cookies.clear();
		_cookies.addAll(cookies);
	}

	/**
	 * Gets the existing HTTP cookies instances as a list.
	 * 
	 * @return the resulting list
	 */
	@Override public List<HttpCookie> listCookies() {
		return _cookies;
	}

	/**
	 * Gets the HTTP cookie instance with the specified name.
	 * 
	 * @param name
	 * @return the HTTP cookie instance with the specified name if exists, <tt>null</tt> otherwise
	 */
	@Override public HttpCookie getCookieByName(final String name) {
		for (final HttpCookie cookie : _cookies) {
			if (name.equals(cookie.getName())) {
				return cookie;
			}
		}
		return null;
	}

	/**
	 * Gets the custom request header with the specified name.
	 * 
	 * @param name
	 * @return the custom request header with the specified name if exists, <tt>null</tt> otherwise
	 */
	public String getCustomRequestHeader(final String name) {
		return _customRequestProperties.get(name);
	}

	/**
	 * Adds the specified custom request header with the specified value.
	 * 
	 * @param name
	 * @param value 
	 */
	public void addCustomRequestHeader(final String name, final String value) {
		_customRequestProperties.put(name, value);
	}

	/**
	 * Deletes the specified custom request header.
	 * 
	 * @param name 
	 */
	public void deleteCustomRequestHeader(final String name) {
		_customRequestProperties.remove(name);
	}

	/**
	 * Drops all the existing custom request headers.
	 */
	public void clearCustomRequestHeaders() {
		_customRequestProperties.clear();
	}

	/**
	 * Gets the user agent.
	 * 
	 * @return the user agent
	 */
	public String getUserAgent() {
		return _userAgent;
	}

	/**
	 * Sets the user agent with the specified value.
	 * 
	 * @param value 
	 */
	public void setUserAgent(final String value) {
		_userAgent = value;
	}

	/**
	 * Gets the content type.
	 * 
	 * @return the content type
	 */
	public String getContentType() {
		return _contentType;
	}

	/**
	 * Sets the content type with the specified value.
	 * 
	 * @param value 
	 */
	public void setContentType(final String value) {
		_contentType = value;
	}

	/**
	 * Gets the read timeout.
	 * 
	 * @return the read timeout
	 */
	public Integer getReadTimeout() {
		return _readTimeout;
	}

	/**
	 * Sets the read timeout with the specified value.
	 * 
	 * @param value 
	 */
	public void setReadTimeout(final int value) {
		_readTimeout = value;
	}

	/**
	 * Gets the connection timeout.
	 * 
	 * @return the connection timeout
	 */
	public Integer getConnectTimeout() {
		return _connectTimeout;
	}

	/**
	 * Sets the connection timeout with the specified value.
	 * 
	 * @param value 
	 */
	public void setConnectTimeout(final int value) {
		_connectTimeout = value;
	}

}
