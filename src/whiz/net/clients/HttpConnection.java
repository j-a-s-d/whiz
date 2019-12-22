/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.clients;

import ace.constants.SIZES;
import ace.containers.Lists;
import ace.containers.Maps;
import ace.text.Strings;
import ace.time.Chronometer;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import whiz.net.HttpCookies;
import whiz.net.HttpMethod;
import whiz.net.HttpRequestHeaders;
import whiz.net.NetworkConnection;
import whiz.net.interfaces.HttpConnectionInfo;
import whiz.net.interfaces.HttpCookieHandler;

/**
 * Abstract HTTP connection class.
 */
public abstract class HttpConnection extends NetworkConnection implements HttpConnectionInfo, HttpCookieHandler {

	/**
	 * The buffer size used to read the HTTP requests.
	 */
	public static int BUFFER_SIZE = 16 * (int) SIZES.KILOBYTE;

	/**
	 * The use cache flag for the HTTP connection.
	 */
	public static boolean USE_CACHE = false;

	/**
	 * The character set used in the HTTP transactions.
	 */
	public static String CHARSET = "utf-8";

	/**
	 * A content type constant specifying the content as text/plain and with UTF-8 character set.
	 */
	public static String CONTENT_TYPE_UTF8_TEXT = "text/plain; charset=utf-8";

	protected HttpURLConnection _connection;
	protected List<HttpCookie> _requestCookies;
	protected List<HttpCookie> _responseCookies;
	protected String _userAgent;
	protected String _contentType;
	protected Integer _connectTimeout;
	protected Integer _readTimeout;
	protected boolean _bodyless;
	protected Chronometer _chrono;
	protected HttpMethod _requestMethod;
	protected String _requestURL;
	protected String _requestData;
	protected int _responseCode;
	protected byte[] _responseRawData;
	protected String _responseData;
	protected long _responseTime;
	protected CookieManager _cookieManager;
	protected final Map<String, String> _customRequestProperties;

	/**
	 * Constructor accepting a URL string, an HTTP method and a string data.
	 * 
	 * @param url
	 * @param method
	 * @param data 
	 */
	protected HttpConnection(final String url, final HttpMethod method, final String data) {
		this(HttpConnection.class, url, method, data);
	}

	/**
	 * Constructor accepting a class instance, a URL string, an HTTP method and a string data.
	 * 
	 * @param clazz
	 * @param url
	 * @param method
	 * @param data 
	 */
	protected HttpConnection(final Class<?> clazz, final String url, final HttpMethod method, final String data) {
		super(clazz);
		_requestURL = url;
		_requestMethod = method;
		_requestData = data;
		_requestCookies = Lists.make();
		_responseCookies = Lists.make();
		_bodyless = method.is(HttpMethod.GET) || method.is(HttpMethod.HEAD);
		_userAgent = null;
		_contentType = null;
		_connectTimeout = null;
		_readTimeout = null;
		_chrono = new Chronometer();
		_cookieManager = new CookieManager();
		_customRequestProperties = Maps.make();
	}

	/**
	 * Constructor accepting a URL instance, an HTTP method and a string data.
	 * 
	 * @param url
	 * @param method
	 * @param data 
	 */
	protected HttpConnection(final URL url, final HttpMethod method, final String data) {
		this(url.toString(), method, data);
	}

	/**
	 * Gets the custom request header with the specified name.
	 * 
	 * @param name
	 * @return the resulting value
	 */
	public String getCustomRequestHeader(final String name) {
		return _customRequestProperties.get(name);
	}

	/**
	 * Adds the specified custom request header with the specified value.
	 * 
	 * @param name
	 * @param value 
	 * @return itself
	 */
	public HttpConnection addCustomRequestHeader(final String name, final String value) {
		_customRequestProperties.put(name, value);
		return this;
	}

	/**
	 * Deletes the specified custom request header.
	 * 
	 * @param name 
	 * @return itself
	 */
	public HttpConnection deleteCustomRequestHeader(final String name) {
		_customRequestProperties.remove(name);
		return this;
	}

	/**
	 * Drops all the existing custom request headers.
	 * 
	 * @return itself
	 */
	public HttpConnection clearCustomRequestHeaders() {
		_customRequestProperties.clear();
		return this;
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
	 * @return itself
	 */
	public HttpConnection setUserAgent(final String value) {
		_userAgent = value;
		return this;
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
	 * @return itself
	 */
	public HttpConnection setReadTimeout(final int value) {
		_readTimeout = value;
		return this;
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
	 * @return itself
	 */
	public HttpConnection setConnectTimeout(final int value) {
		_connectTimeout = value;
		return this;
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
	 * @return itself
	 */
	public HttpConnection setContentType(final String value) {
		_contentType = value;
		return this;
	}

	private void flushCookies() {
		final String cookies = HttpCookies.asString(_requestCookies);
		if (Strings.hasText(cookies)) {
			_connection.setRequestProperty(HttpCookies.COOKIE_OLD_REQUEST_HEADER, cookies);
		}
	}

	private void importCookiesFromHeader(final List<String> header) {
		if (Lists.hasContent(header)) {
			for (final String item : header) {
				for (final HttpCookie cookie : HttpCookie.parse(item)) {
					try {
						_cookieManager.getCookieStore().add(_connection.getURL().toURI(), cookie);
					} catch (final Exception e) {
						setLastException(e);
					}
				}
			}
		}
	}

	private void extractCookies() {
		final Map<String, List<String>> headerFields = _connection.getHeaderFields();
		for (final String s : headerFields.keySet()) {
			if (HttpCookies.isCookieResponseHeader(s)) {
				importCookiesFromHeader(headerFields.get(s));
			}
		}
		_responseCookies.clear();
		_responseCookies.addAll(_cookieManager.getCookieStore().getCookies());
	}

	private void writeRequestData(final String requestData) throws IOException {
		final DataOutputStream wr = new DataOutputStream(_connection.getOutputStream());
		try {
			//wr.writeBytes(requestData);
			wr.write(requestData.getBytes(CHARSET));
		} finally {
			wr.flush();
			wr.close();
		}
	}

	private byte[] readResponseDataAsByteArray() throws IOException {
		final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int nRead;
		final byte[] data = new byte[BUFFER_SIZE];
		while ((nRead = _connection.getInputStream().read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, nRead);
		}
		buffer.flush();
		return buffer.toByteArray();
	}

	/*private String readResponseDataAsString() throws IOException {
		final StringBuilder response = new StringBuilder();
		final BufferedReader in = new BufferedReader(new InputStreamReader(_connection.getInputStream()));
		try {
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
				response.append(STRINGS.EOL);
			}
		} finally {
			in.close();
		}
		return response.toString();
	}*/

	private String readResponseData() throws IOException {
		return new String(_responseRawData = readResponseDataAsByteArray(), CHARSET);
	}

	private void proceed() throws IOException {
		if (assigned(_userAgent)) {
			_connection.setRequestProperty(HttpRequestHeaders.USER_AGENT, _userAgent);
		}
		if (assigned(_contentType)) {
			_connection.setRequestProperty(HttpRequestHeaders.CONTENT_TYPE, _contentType);
		}
		for (final Map.Entry<String, String> n : _customRequestProperties.entrySet()) {
			_connection.setRequestProperty(n.getKey(), n.getValue());
		}
		flushCookies();
		if (!_bodyless) {
			writeRequestData(_requestData);
		}
		_responseData = readResponseData();
		extractCookies();
	}

	/**
	 * Fires the HTTP connection attempt.
	 * 
	 * @return the resulting HTTP connection information instance
	 */
	public final HttpConnectionInfo go() {
		try {
			open();
			try {
				proceed();
			} finally {
				close();
			}
		} catch (final Exception e) {
			setLastException(e);
		}
		return this;
	}

	/**
	 * Fires the HTTPS secure connection attempt.
	 * 
	 * @return the resulting HTTP connection information instance
	 */
	public final HttpConnectionInfo goSecure() {
		try {
			openSecure();
			try {
				proceed();
			} finally {
				close();
			}
		} catch (final Exception e) {
			setLastException(e);
		}
		return this;
	}

	/**
	 * Setups an HTTPS secure connection to the specified URL instance with the specified method.
	 * 
	 * @param url
	 * @param method
	 * @return the resulting HTTPS URL connection instance
	 * @throws Exception 
	 */
	protected static HttpsURLConnection setupHttpsConnection(final URL url, final String method) throws Exception {
		final HttpsURLConnection result = (HttpsURLConnection) url.openConnection();
		result.setRequestMethod(method);
		return result;
	}

	/**
	 * Setups an HTTP connection to the specified URL instance with the specified method.
	 * 
	 * @param url
	 * @param method
	 * @return the resulting HTTP URL connection instance
	 * @throws Exception 
	 */
	protected static HttpURLConnection setupHttpConnection(final URL url, final String method) throws Exception {
		final HttpURLConnection result = (HttpURLConnection) url.openConnection();
		result.setRequestMethod(method);
		return result;
	}

	private void setupConnection(final HttpURLConnection connection) {
		_connection = connection;
		if (assigned(_connectTimeout)) {
			_connection.setConnectTimeout(_connectTimeout);
		}
		if (assigned(_readTimeout)) {
			_connection.setReadTimeout(_readTimeout);
		}
		_connection.setDoOutput(!_bodyless);
		_connection.setUseCaches(USE_CACHE);
		_chrono.start();
	}

	/**
	 * Attempts to open the connection.
	 * 
	 * @throws Exception 
	 */
	protected void open() throws Exception {
		setupConnection(setupHttpConnection(new URL(_requestURL), _requestMethod.name()));
	}

	/**
	 * Attempts to open the secure connection.
	 * 
	 * @throws Exception 
	 */
	protected void openSecure() throws Exception {
		setupConnection(setupHttpsConnection(new URL(_requestURL), _requestMethod.name()));
	}

	/**
	 * Attempts to close the connection.
	 * 
	 * @throws Exception 
	 */
	protected void close() throws Exception {
		_responseCode = _connection.getResponseCode();
		_connection.disconnect();
		_responseTime = _chrono.stop();
	}

	/**
	 * Gets the response data as a byte array.
	 * 
	 * @return the resulting byte array
	 */
	public byte[] getResponseRawData() {
		return _responseRawData;
	}

	// HttpConnectionInfo overrides

	/**
	 * Gets the request method.
	 * 
	 * @return the request method
	 */
	@Override public String getRequestMethod() {
		return _requestMethod.name();
	}

	/**
	 * Gets the request URL string.
	 * 
	 * @return the request URL string
	 */
	@Override public String getRequestURL() {
		return _requestURL;
	}

	/**
	 * Gets the request data as string.
	 * 
	 * @return the request data as string
	 */
	@Override public String getRequestData() {
		return _requestData;
	}

	/**
	 * Gets the request HTTP cookies as a list of HTTP cookies instances.
	 * 
	 * @return the resulting list
	 */
	@Override public List<HttpCookie> getRequestCookies() {
		return _requestCookies;
	}

	/**
	 * Gets the response HTTP cookies as a list of HTTP cookies instances.
	 * 
	 * @return the resulting list
	 */
	@Override public List<HttpCookie> getResponseCookies() {
		return _responseCookies;
	}

	/**
	 * Gets the response code.
	 * 
	 * @return the response code
	 */
	@Override public int getResponseCode() {
		return _responseCode;
	}

	/**
	 * Gets the response data as string.
	 * 
	 * @return the response data as string
	 */
	@Override public String getResponseData() {
		return _responseData;
	}

	/**
	 * Gets the response time.
	 * 
	 * @return the response time
	 */
	@Override public long getResponseTime() {
		return _responseTime;
	}

	// HttpCookieHandler overrides

	/**
	 * Drops all the existing HTTP cookies.
	 */
	@Override public void clearCookies() {
		_requestCookies.clear();
	}

	/**
	 * Adds the specified HTTP cookie instance.
	 * 
	 * @param cookie 
	 */
	@Override public void addCookie(final HttpCookie cookie) {
		_requestCookies.add(cookie);
	}

	/**
	 * Removes the specified HTTP cookie instance.
	 * 
	 * @param cookie 
	 */
	@Override public void removeCookie(final HttpCookie cookie) {
		_requestCookies.remove(cookie);
	}

	/**
	 * Adds the specified list of HTTP cookies instances.
	 * 
	 * @param cookies 
	 */
	@Override public void addCookies(final List<HttpCookie> cookies) {
		_requestCookies.addAll(cookies);
	}

	/**
	 * Sets the specified list of HTTP cookies instances.
	 * 
	 * Already existing HTTP cookies will be dropped.
	 * 
	 * @param cookies 
	 */
	@Override public void setCookies(final List<HttpCookie> cookies) {
		_requestCookies.clear();
		_requestCookies.addAll(cookies);
	}

	/**
	 * Gets the existing HTTP cookies instances as a list.
	 * 
	 * @return the resulting list
	 */
	@Override public List<HttpCookie> listCookies() {
		return _requestCookies;
	}

	/**
	 * Gets the HTTP cookie instance with the specified name.
	 * 
	 * @param name
	 * @return the HTTP cookie instance with the specified name if exists, <tt>null</tt> otherwise
	 */
	@Override public HttpCookie getCookieByName(final String name) {
		for (final HttpCookie cookie : _requestCookies) {
			if (name.equals(cookie.getName())) {
				return cookie;
			}
		}
		return null;
	}

}
