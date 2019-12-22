/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.servers;

import ace.constants.SIZES;
import ace.constants.STRINGS;
import ace.containers.Lists;
import ace.text.Strings;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import whiz.WhizObject;
import whiz.net.HttpCookies;
import whiz.net.HttpMethod;
import whiz.net.HttpStatus;
import whiz.net.interfaces.HttpCookieHandler;

/**
 * HTTP request class.
 */
public class HttpRequest extends WhizObject implements HttpCookieHandler {

	/**
	 * The direct reply maximum size.
	 */
	public static int DIRECT_REPLY_MAX_SIZE = 64 * (int) SIZES.MEGABYTE;

	/**
	 * The segmented reply buffer size.
	 */
	public static int SEGMENTED_REPLY_BUFFER_SIZE = 64 * (int) SIZES.KILOBYTE;

	/**
	 * The request read buffer size.
	 */
	public static int REQUEST_READ_BUFFER_SIZE = 4 * (int) SIZES.KILOBYTE;

	public final byte[] EMPTY_RESPONSE = new byte[0];
	
	private final HttpStand _stand;
	private final HttpExchange _client;
	private boolean _replied;

	/**
	 * Constructor accepting the HTTP stand and the HTTP exchange client.
	 * 
	 * @param stand
	 * @param client 
	 */
	public HttpRequest(final HttpStand stand, final HttpExchange client) {
		super(HttpRequest.class);
		_replied = false;
		_stand = stand;
		_client = client;
	}

	/**
	 * Determines if this handler has already replied to the client.
	 * 
	 * @return <tt>true</tt> if this handler has already replied to the client, <tt>false</tt> otherwise
	 */
	public boolean hasReplied() {
		return _replied;
	}

	// GENERAL

	/**
	 * Gets the HTTP method.
	 * 
	 * @return the HTTP method
	 */
	public HttpMethod getMethod() {
		try {
			return HttpMethod.valueOf(_client.getRequestMethod());
		} catch (final Exception e) {
			setLastException(e);
			return null;
		}
	}

	/**
	 * Gets the request body as a byte array.
	 * 
	 * @return the request body as a byte array
	 * @throws IOException 
	 */
	public byte[] getRequestBody() throws IOException {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final InputStream in = _client.getRequestBody();
		final byte[] buf = new byte[REQUEST_READ_BUFFER_SIZE];
		int read;
		while ((read = in.read(buf)) != -1) {
			baos.write(buf, 0, read);
		}
		return baos.toByteArray();
	}

	/**
	 * Replies the specified HTTP status code to the client.
	 * 
	 * @param code
	 * @return <tt>true</tt> if the operation was successful, <tt>false</tt> otherwise
	 */
	public boolean reply(final int code) {
		return reply(code, EMPTY_RESPONSE);
	}

	private void writeReplyBuffer(final OutputStream os, final byte[] buffer) throws IOException {
		if (buffer.length > DIRECT_REPLY_MAX_SIZE) {
			final InputStream in = new ByteArrayInputStream(buffer);
			final byte[] b = new byte[SEGMENTED_REPLY_BUFFER_SIZE];
			int read;
			while ((read = in.read(b)) != -1) {
				os.write(b, 0, read);
			}
		} else {
			os.write(buffer);
		}
		os.close();
	}

	/**
	 * Replies the specified HTTP status code to the client with the specified body.
	 * 
	 * @param code
	 * @param buffer
	 * @return <tt>true</tt> if the operation was successful, <tt>false</tt> otherwise
	 */
	public boolean reply(final int code, final byte[] buffer) {
		try {
			if (buffer == null) {
				_client.sendResponseHeaders(code, -1);
			} else {
				_client.sendResponseHeaders(code, buffer.length);
				writeReplyBuffer(_client.getResponseBody(), buffer);
			}
			_replied = true;
		} catch (final Exception e) {
			setLastException(e);
		}
		return _replied;
	}

	/**
	 * Replies with an OK HTTP status code to the client with the specified body.
	 * 
	 * @param buffer
	 * @return <tt>true</tt> if the operation was successful, <tt>false</tt> otherwise
	 */
	public boolean replyOk(final byte[] buffer) {
		return reply(HttpStatus.OK, buffer);
	}

	/**
	 * Replies with an OK (200) HTTP status code to the client.
	 * 
	 * @return <tt>true</tt> if the operation was successful, <tt>false</tt> otherwise
	 */
	public boolean replyOk() {
		return reply(HttpStatus.OK, EMPTY_RESPONSE);
	}

	/**
	 * Replies with an INTERNAL SERVER ERROR (500) HTTP status code to the client.
	 * 
	 * @return <tt>true</tt> if the operation was successful, <tt>false</tt> otherwise
	 */
	public boolean replyInternalError() {
		return reply(HttpStatus.INTERNAL_SERVER_ERROR, EMPTY_RESPONSE);
	}

	/**
	 * Replies with an METHOD NOT ALLOWED (405) HTTP status code to the client.
	 * 
	 * @return <tt>true</tt> if the operation was successful, <tt>false</tt> otherwise
	 */
	public boolean replyMethodNotAllowed() {
		return reply(HttpStatus.METHOD_NOT_ALLOWED, EMPTY_RESPONSE);
	}

	/**
	 * Replies with an BAD REQUEST (400) HTTP status code to the client.
	 * 
	 * @return <tt>true</tt> if the operation was successful, <tt>false</tt> otherwise
	 */
	public boolean replyBadRequest() {
		return reply(HttpStatus.BAD_REQUEST, EMPTY_RESPONSE);
	}

	/**
	 * Replies with an NOT IMPLEMENTED (501) HTTP status code to the client.
	 * 
	 * @return <tt>true</tt> if the operation was successful, <tt>false</tt> otherwise
	 */
	public boolean replyNotImplemented() {
		return reply(HttpStatus.NOT_IMPLEMENTED, EMPTY_RESPONSE);
	}

	/**
	 * Replies with an MOVED PERMANENTLY (301) HTTP status code to the client with the specified destination.
	 * 
	 * @param destination
	 * @return <tt>true</tt> if the operation was successful, <tt>false</tt> otherwise
	 */
	public boolean replyRedirection(final String destination) {
		setResponseHeader("Location", destination);
		return reply(HttpStatus.MOVED_PERMANENTLY, EMPTY_RESPONSE);
	}
	
	// CLIENT RELATED

	/**
	 * Gets the HTTP exchange client.
	 * 
	 * @return the HTTP exchange client
	 */
	public HttpExchange getClient() {
		return _client;
	}

	/**
	 * Gets the internet address of the client.
	 * 
	 * @return the internet address of the client
	 */
	public InetAddress getClientAddress() {
		return _client.getRemoteAddress().getAddress();
	}

	/**
	 * Gets the host address of the client.
	 * 
	 * @return the host address of the client
	 */
	public String getClientHost() {
		return _client.getRemoteAddress().getAddress().getHostAddress();
	}

	// SERVER RELATED

	/**
	 * Gets the HTTP host instance.
	 * 
	 * @param <T>
	 * @return the HTTP host instance
	 */
	public <T extends HttpHost> T getHttpHost() {
		return (T) _stand;
	}

	/**
	 * Gets the HTTP server instance.
	 * 
	 * @return the HTTP server instance
	 */
	public HttpServer getServer() {
		return _client.getHttpContext().getServer();
	}

	/**
	 * Gets the HTTP server internet socket address.
	 * 
	 * @return the HTTP server internet socket address
	 */
	public InetSocketAddress getServerAddress() {
		return _client.getHttpContext().getServer().getAddress();
	}

	/**
	 * Gets the HTTP server host name.
	 * 
	 * @return the HTTP server host name
	 */
	public String getServerHost() {
		return _client.getHttpContext().getServer().getAddress().getHostName();
	}

	// PATH RELATED

	/**
	 * Gets the request path.
	 * 
	 * @return the request path
	 */
	public String getRequestPath() {
		return _client.getRequestURI().getPath();
	}
	
	private String[] splitPath(final String path) {
		return Strings.stripBoth(path, '/').split("/");
	}

	/**
	 * Determines if the request path matches the specified pattern.
	 * 
	 * @param template
	 * @return <tt>true</tt> if the request path matches the specified pattern, <tt>false</tt> otherwise
	 */
	@SuppressWarnings("PMD.SimplifyStartsWith")
	public boolean requestPathMatchesPattern(final String template) {
		final String[] pathParts = splitPath(_client.getRequestURI().getPath());
		final String[] templateParts = splitPath(template);
		boolean result = true;
		int i = -1;
		for (final String s : templateParts) {
			i++;
			if (!Strings.hasText(s) || (s.startsWith("{") && s.endsWith("}"))) {
				continue;
			}
			result &= pathParts[i].equalsIgnoreCase(s);
		}
		return result;
	}

	/**
	 * Extracts the parameters from the path by the specified template as a hash map.
	 * 
	 * @param template
	 * @return the resulting hash map
	 */
	@SuppressWarnings("PMD.SimplifyStartsWith")
	public HashMap<String, String> extractParametersFromPathByTemplateAsHashMap(final String template) {
		final HashMap<String, String> result = new HashMap();
		final String[] pathParts = splitPath(_client.getRequestURI().getPath());
		final String[] templateParts = splitPath(template);
		int i = -1;
		for (final String s : templateParts) {
			i++;
			if (s.startsWith("{") && s.endsWith("}") && pathParts.length > i) {
				result.put(Strings.dropBoth(s, 1), pathParts[i]);
			}
		}
		return result;
	}

	// QUERYSTRING RELATED

	/**
	 * Gets the request query string as a hash map.
	 * 
	 * @return the request query string as a hash map
	 */
	public HashMap<String, String> getRequestQueryStringAsHashMap() {
		final HashMap<String, String> result = new HashMap();
		final String q = _client.getRequestURI().getQuery();
		if (Strings.hasText(q)) {
			for (final String param : q.split("&")) {
				final String pair[] = param.split("=");
				if (pair.length > 1) {
					result.put(pair[0].trim(), pair[1].trim());
				} else {
					result.put(pair[0].trim(), STRINGS.EMPTY);
				}
			}
		}
		return result;
	}

	// HEADERS RELATED

	/**
	 * Gets the request headers as a hash map.
	 * 
	 * @return the request headers as a hash map
	 */
	public HashMap<String, List<String>> getRequestHeadersAsHashMap() {
		final HashMap<String, List<String>> result = new HashMap();
		for (final Map.Entry<String, List<String>> e : _client.getRequestHeaders().entrySet()) {
			result.put(e.getKey(), e.getValue());
		}
		return result;
	}

	/**
	 * Gets the response headers as a hash map.
	 * 
	 * @return the response headers as a hash map
	 */
	public HashMap<String, List<String>> getResponseHeadersAsHashMap() {
		final HashMap<String, List<String>> result = new HashMap();
		for (final Map.Entry<String, List<String>> e : _client.getResponseHeaders().entrySet()) {
			result.put(e.getKey(), e.getValue());
		}
		return result;
	}

	/**
	 * Gets the specified response header with the specified value.
	 * 
	 * @param name
	 * @param value
	 */
	public void setResponseHeader(final String name, final String value) {
		_client.getResponseHeaders().set(name, value);
	}

	/**
	 * Gets the specified response header with the specified values.
	 * 
	 * @param name
	 * @param values
	 */
	public void setResponseHeader(final String name, final List<String> values) {
		_client.getResponseHeaders().put(name, values);
	}

	// COOKIES RELATED
	// HttpCookieHandler overrides

	/**
	 * Drops all the existing HTTP cookies.
	 */
	@Override public void clearCookies() {
		setCookies(new ArrayList<HttpCookie>() {});
	}

	/**
	 * Adds the specified HTTP cookie instance.
	 * 
	 * @param cookie 
	 */
	@Override public void addCookie(final HttpCookie cookie) {
		final List<HttpCookie> tmp = listCookies();
		if (tmp.add(cookie)) {
			setCookies(tmp);
		}
	}

	/**
	 * Removes the specified HTTP cookie instance.
	 * 
	 * @param cookie 
	 */
	@Override public void removeCookie(final HttpCookie cookie) {
		final ArrayList<HttpCookie> cookies = new ArrayList<HttpCookie>();
		for (final HttpCookie x : listCookies()) {
			if (!cookie.getName().equals(x.getName())) {
				cookies.add(x);
			}
		}
		setCookies(cookies);
	}

	/**
	 * Adds the specified list of HTTP cookies instances.
	 * 
	 * @param cookies 
	 */
	@Override public void addCookies(final List<HttpCookie> cookies) {
		final List<HttpCookie> tmp = listCookies();
		if (tmp.addAll(cookies)) {
			setCookies(tmp);
		}
	}

	/**
	 * Sets the specified list of HTTP cookies instances.
	 * 
	 * Already existing HTTP cookies will be dropped.
	 * 
	 * @param cookies 
	 */
	@Override public void setCookies(final List<HttpCookie> cookies) {
		setResponseHeader(HttpCookies.COOKIE_OLD_RESPONSE_HEADER, new ArrayList<String>() {{
			for (final HttpCookie cookie : cookies) {
				add(cookie.toString());
			}
		}});
	}
	
	private ArrayList<HttpCookie> getCookiesOfHeader(final String cookieHeader) {
		return new ArrayList<HttpCookie>() {{
			final List<String> headers = _client.getRequestHeaders().get(cookieHeader);
			if (assigned(headers)) {
				for (final String header : headers) {
					addAll(HttpCookies.parseCookies(header));
				}
			}
		}};
	}

	/**
	 * Gets the existing HTTP cookies instances as a list.
	 * 
	 * @return the resulting list
	 */
	@Override public List<HttpCookie> listCookies() {
		return Lists.combine(
			getCookiesOfHeader(HttpCookies.COOKIE_OLD_REQUEST_HEADER),
			getCookiesOfHeader(HttpCookies.COOKIE_NEW_REQUEST_HEADER)
		);
	}

	/**
	 * Gets the HTTP cookie instance with the specified name.
	 * 
	 * @param name
	 * @return the HTTP cookie instance with the specified name if exists, <tt>null</tt> otherwise
	 */
	@Override public HttpCookie getCookieByName(final String name) {
		for (final HttpCookie cookie : listCookies()) {
			if (name.equals(cookie.getName())) {
				return cookie;
			}
		}
		return null;
	}

}
