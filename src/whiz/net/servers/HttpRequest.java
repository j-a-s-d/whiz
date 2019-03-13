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

public class HttpRequest extends WhizObject implements HttpCookieHandler {
	
	public static int DIRECT_REPLY_MAX_SIZE = 64 * (int) SIZES.MEGABYTE;
	public static int SEGMENTED_REPLY_BUFFER_SIZE = 64 * (int) SIZES.KILOBYTE;
	public static int REQUEST_READ_BUFFER_SIZE = 4 * (int) SIZES.KILOBYTE;
	
	public final byte[] EMPTY_RESPONSE = new byte[0];
	
	private final HttpStand _stand;
	private final HttpExchange _client;
	private boolean _replied;
	
	public HttpRequest(final HttpStand stand, final HttpExchange client) {
		super(HttpRequest.class);
		_replied = false;
		_stand = stand;
		_client = client;
	}
	
	public boolean hasReplied() {
		return _replied;
	}
	
	// GENERAL
	
	public HttpMethod getMethod() {
		try {
			return HttpMethod.valueOf(_client.getRequestMethod());
		} catch (final Exception e) {
			setLastException(e);
			return null;
		}
	}
	
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
	
	public boolean replyOk(final byte[] buffer) {
		return reply(HttpStatus.OK, buffer);
	}
	
	public boolean replyOk() {
		return reply(HttpStatus.OK, EMPTY_RESPONSE);
	}
	
	public boolean replyInternalError() {
		return reply(HttpStatus.INTERNAL_SERVER_ERROR, EMPTY_RESPONSE);
	}
	
	public boolean replyMethodNotAllowed() {
		return reply(HttpStatus.METHOD_NOT_ALLOWED, EMPTY_RESPONSE);
	}
	
	public boolean replyBadRequest() {
		return reply(HttpStatus.BAD_REQUEST, EMPTY_RESPONSE);
	}
	
	public boolean replyNotImplemented() {
		return reply(HttpStatus.NOT_IMPLEMENTED, EMPTY_RESPONSE);
	}
	
	public boolean replyRedirection(final String destination) {
		setResponseHeader("Location", destination);
		return reply(HttpStatus.MOVED_PERMANENTLY, EMPTY_RESPONSE);
	}
	
	// CLIENT RELATED
	
	public HttpExchange getClient() {
		return _client;
	}
	
	public InetAddress getClientAddress() {
		return _client.getRemoteAddress().getAddress();
	}
	
	public String getClientHost() {
		return _client.getRemoteAddress().getAddress().getHostAddress();
	}
	
	// SERVER RELATED
	
	public <T extends HttpHost> T getHttpHost() {
		return (T) _stand;
	}
	
	public HttpServer getServer() {
		return _client.getHttpContext().getServer();
	}
	
	public InetSocketAddress getServerAddress() {
		return _client.getHttpContext().getServer().getAddress();
	}
	
	public String getServerHost() {
		return _client.getHttpContext().getServer().getAddress().getHostName();
	}
	
	// PATH RELATED
	
	public String getRequestPath() {
		return _client.getRequestURI().getPath();
	}
	
	private String[] splitPath(final String path) {
		return Strings.stripBoth(path, '/').split("/");
	}
	
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
	
	public HashMap<String, List<String>> getRequestHeadersAsHashMap() {
		final HashMap<String, List<String>> result = new HashMap();
		for (final Map.Entry<String, List<String>> e : _client.getRequestHeaders().entrySet()) {
			result.put(e.getKey(), e.getValue());
		}
		return result;
	}
	
	public HashMap<String, List<String>> getResponseHeadersAsHashMap() {
		final HashMap<String, List<String>> result = new HashMap();
		for (final Map.Entry<String, List<String>> e : _client.getResponseHeaders().entrySet()) {
			result.put(e.getKey(), e.getValue());
		}
		return result;
	}
	
	public void setResponseHeader(final String name, final String value) {
		_client.getResponseHeaders().set(name, value);
	}
	
	public void setResponseHeader(final String name, final List<String> values) {
		_client.getResponseHeaders().put(name, values);
	}
	
	// COOKIES RELATED
	// HttpCookieHandler overrides
	
	@Override public void clearCookies() {
		setCookies(new ArrayList<HttpCookie>() {});
	}
	
	@Override public void addCookie(final HttpCookie cookie) {
		final List<HttpCookie> tmp = listCookies();
		if (tmp.add(cookie)) {
			setCookies(tmp);
		}
	}
	
	@Override public void removeCookie(final HttpCookie cookie) {
		final ArrayList<HttpCookie> cookies = new ArrayList<HttpCookie>();
		for (final HttpCookie x : listCookies()) {
			if (!cookie.getName().equals(x.getName())) {
				cookies.add(x);
			}
		}
		setCookies(cookies);
	}
	
	@Override public void addCookies(final List<HttpCookie> cookies) {
		final List<HttpCookie> tmp = listCookies();
		if (tmp.addAll(cookies)) {
			setCookies(tmp);
		}
	}
	
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
	
	@Override public List<HttpCookie> listCookies() {
		return Lists.combine(
			getCookiesOfHeader(HttpCookies.COOKIE_OLD_REQUEST_HEADER),
			getCookiesOfHeader(HttpCookies.COOKIE_NEW_REQUEST_HEADER)
		);
	}
	
	@Override public HttpCookie getCookieByName(final String name) {
		for (final HttpCookie cookie : listCookies()) {
			if (name.equals(cookie.getName())) {
				return cookie;
			}
		}
		return null;
	}
	
}
