/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.servers;

import ace.constants.STRINGS;
import ace.interfaces.Treater;
import ace.text.Strings;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import whiz.WhizObject;
import whiz.net.HttpMethod;

public abstract class HttpRequestHandler extends WhizObject implements HttpHandler {

	private HttpStand _stand;
	private final String _route;
	private final HttpMethod[] _methods;
	private final Set<String> _methodNames;
	private final String _allowedMethodsCSV;
	private final Treater<byte[]> _writingAdapter;
	private final Treater<byte[]> _readingAdapter;

	public HttpRequestHandler(final Class<?> clazz, final HttpMethod[] methods, final String route, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter) {
		super(clazz);
		_route = route;
		_methods = methods;
		_methodNames = new HashSet();
		String tmp = "HEAD,OPTIONS,TRACE";
		for (final HttpMethod method : methods) {
			tmp += STRINGS.COMMA + method.name();
			_methodNames.add(method.name());
		}
		_allowedMethodsCSV = tmp;
		_readingAdapter = readingAdapter;
		_writingAdapter = writingAdapter;
	}

	public HttpMethod[] getMethods() {
		return _methods;
	}

	public String getRoute() {
		return _route;
	}

	public <T extends HttpStand> T getStand() {
		return (T) _stand;
	}

	HttpRequestHandler setStand(final HttpStand stand) {
		_stand = stand;
		return this;
	}

	void unregisterFromStand() {
		_stand.dropHandler(this);
	}

	private HttpRequest wrapRequest(final HttpExchange client) {
		final HttpRequest request = new HttpRequest(_stand, client);
		request.setResponseHeader("Content-Type", "text/html");
		request.setResponseHeader("Cache-Control", Arrays.asList("no-cache", "no-store", "max-age=0"));
		if (Strings.hasText(_stand.getName())) {
			request.setResponseHeader("Server", _stand.getName());
		}
		if (_stand.getCrossOriginAllowance()) {
			request.setResponseHeader("Access-Control-Allow-Origin", STRINGS.ASTERISK);
		}
		return request;
	}

	protected void beforeRequestHandling(final HttpRequest request) {
		// NOTE: override this method in subclasses
	}

	protected void afterRequestHandling(final HttpRequest request) {
		// NOTE: override this method in subclasses
	}

	@Override public void handle(final HttpExchange client) throws IOException {
		final HttpRequest request = wrapRequest(client);
		beforeRequestHandling(request);
		final HttpMethod requestMethod = request.getMethod();
		if (requestMethod == null) {
			onWrongMethod(request, client.getRequestMethod());
			request.replyMethodNotAllowed();
		} else {
			switch (requestMethod) {
				case HEAD:
					request.replyOk(null);
					break;
				case OPTIONS:
					request.setResponseHeader("Allow", _allowedMethodsCSV);
					request.replyOk(null);
					break;
				case TRACE:
					request.replyOk(request.getRequestBody());
					break;
				default:
					if (_methodNames.contains(requestMethod.name())) {
						byte[] body = null;
						try {
							if (!requestMethod.is(HttpMethod.GET) && !requestMethod.is(HttpMethod.DELETE)) {
								body = request.getRequestBody();
								if (assigned(_readingAdapter)) {
									body = _readingAdapter.treat(body);
								}
							}
							byte[] response = process(request, body);
							if (!request.hasReplied()) {
								if (response == null) {
									request.replyInternalError();
								} else {
									if (assigned(_writingAdapter)) {
										response = _writingAdapter.treat(response);
									}
									request.replyOk(response);
								}
							}
						} catch (final Throwable e) {
							onClientException(request, e, body);
							setLastException(e);
							request.replyBadRequest();
						}
					} else {
						onWrongMethod(request, requestMethod.name());
						request.replyNotImplemented();
					}
					break;
			}
		}
		afterRequestHandling(request);
	}

	protected abstract void onWrongMethod(final HttpRequest request, final String methodName);

	protected abstract void onClientException(final HttpRequest request, final Throwable e, final byte[] body);

	protected abstract byte[] process(final HttpRequest request, final byte[] body) throws Throwable;

}
