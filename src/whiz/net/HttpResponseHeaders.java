/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net;

import whiz.Whiz;

/**
 * Utility class for working with HTTP response headers.
 */
public class HttpResponseHeaders extends Whiz {

	// Standard response fields

	public static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin"; // Specifying which web sites can participate in cross-origin resource sharing (Permanent: standard) /* EXAMPLE: Access-Control-Allow-Origin: * */
	public static final String ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";
	public static final String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";
	public static final String ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";
	public static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
	public static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
	public static final String ACCEPT_PATCH = "Accept-Patch"; // Specifies which patch document formats this server supports (Permanent) /* EXAMPLE: Accept-Patch: text/example;charset=utf-8 */
	public static final String ACCEPT_RANGES = "Accept-Ranges"; // What partial content range types this server supports via byte serving (Permanent) /* EXAMPLE: Accept-Ranges: bytes */
	public static final String AGE = "Age"; // The age the object has been in a proxy cache in seconds (Permanent) /* EXAMPLE: Age: 12 */
	public static final String ALLOW = "Allow"; // Valid methods for a specified resource. To be used for a 405 Method not allowed (Permanent) /* EXAMPLE: Allow: GET, HEAD */
	public static final String ALT_SVC = "Alt-Svc"; // A server uses "Alt-Svc" header (meaning Alternative Services) to indicate that its resources can also be accessed at a different network location (host or port) or using a different protocol When using HTTP/2, servers should instead send an ALTSVC frame. (Permanent) /* EXAMPLE: Alt-Svc: http/1.1="http2.example.com:8001"; ma=7200 */
	public static final String CACHE_CONTROL = "Cache-Control"; // Tells all caching mechanisms from server to client whether they may cache this object. It is measured in seconds (Permanent) /* EXAMPLE: Cache-Control: max-age=3600 */
	public static final String CONNECTION = "Connection"; // Control options for the current connection and list of hop-by-hop response fields.[9] Must not be used with HTTP/2.[10] (Permanent) /* EXAMPLE: Connection: close */
	public static final String CONTENT_DISPOSITION = "Content-Disposition"; // An opportunity to raise a "File Download" dialogue box for a known MIME type with binary format or suggest a filename for dynamic content. Quotes are necessary with special characters. (Permanent) /* EXAMPLE: Content-Disposition: attachment; filename="fname.ext" */
	public static final String CONTENT_ENCODING = "Content-Encoding"; // The type of encoding used on the data. See HTTP compression. (Permanent) /* EXAMPLE: Content-Encoding: gzip */
	public static final String CONTENT_LANGUAGE = "Content-Language"; // The natural language or languages of the intended audience for the enclosed content[42] (Permanent) /* EXAMPLE: Content-Language: da */
	public static final String CONTENT_LENGTH = "Content-Length"; // The length of the response body in octets (8-bit bytes) (Permanent) /* EXAMPLE: Content-Length: 348 */
	public static final String CONTENT_LOCATION = "Content-Location"; // An alternate location for the returned data (Permanent) /* EXAMPLE: Content-Location: /index.htm */
	public static final String CONTENT_MD5 = "Content-MD5"; // A Base64-encoded binary MD5 sum of the content of the response (Obsolete[11]) /* EXAMPLE: Content-MD5: Q2hlY2sgSW50ZWdyaXR5IQ== */
	public static final String CONTENT_RANGE = "Content-Range"; // Where in a full body message this partial message belongs (Permanent) /* EXAMPLE: Content-Range: bytes 21010-47021/47022 */
	public static final String CONTENT_TYPE = "Content-Type"; // The MIME type of this content (Permanent) /* EXAMPLE: Content-Type: text/html; charset=utf-8 */
	public static final String DATE = "Date"; // The date and time that the message was sent (in "HTTP-date" format as defined by RFC 7231) (Permanent) /* EXAMPLE: Date: Tue, 15 Nov 1994 08:12:31 GMT */
	public static final String DELTA_BASE = "Delta-Base"; // Specifies the delta-encoding entity tag of the response[7]. (Permanent) /* EXAMPLE: Delta-Base: "abc" */
	public static final String ETAG = "ETag"; // An identifier for a specific version of a resource, often a message digest (Permanent) /* EXAMPLE: ETag: "737060cd8c284d8af7ad3082f209582d" */
	public static final String EXPIRES = "Expires"; // Gives the date/time after which the response is considered stale (in "HTTP-date" format as defined by RFC 7231) (Permanent: standard) /* EXAMPLE: Expires: Thu, 01 Dec 1994 16:00:00 GMT */
	public static final String IM = "IM"; // Instance-manipulations applied to the response[7]. (Permanent) /* EXAMPLE: IM: feed */
	public static final String LAST_MODIFIED = "Last-Modified"; // The last modified date for the requested object (in "HTTP-date" format as defined by RFC 7231) (Permanent) /* EXAMPLE: Last-Modified: Tue, 15 Nov 1994 12:45:26 GMT */
	public static final String LINK = "Link"; // Used to express a typed relationship with another resource, where the relation type is defined by RFC 5988 (Permanent) /* EXAMPLE: Link: </feed>; rel="alternate"[44] */
	public static final String LOCATION = "Location"; // Used in redirection, or when a new resource has been created. (Permanent) /* EXAMPLE: Example 1: Location: http:// www.w3.org/pub/WWW/People.html Example 2: Location: /pub/WWW/People.html */
	public static final String P3P = "P3P"; // This field is supposed to set P3P policy, in the form of P3P:CP="your_compact_policy". However, P3P did not take off,[45] most browsers have never fully implemented it, a lot of websites set this field with fake policy text, that was enough to fool browsers the existence of P3P policy and grant permissions for third party cookies. (Permanent) /* EXAMPLE: P3P: CP="This is not a P3P policy! See https:// en.wikipedia.org/wiki/Special:CentralAutoLogin/P3P for more info." */
	public static final String PRAGMA = "Pragma"; // Implementation-specific fields that may have various effects anywhere along the request-response chain. (Permanent) /* EXAMPLE: Pragma: no-cache */
	public static final String PROXY_AUTHENTICATE = "Proxy-Authenticate"; // Request authentication to access the proxy. (Permanent) /* EXAMPLE: Proxy-Authenticate: Basic */
	public static final String PUBLIC_KEY_PINS = "Public-Key-Pins"; // HTTP Public Key Pinning, announces hash of website's authentic TLScertificate (Permanent) /* EXAMPLE: Public-Key-Pins: max-age=2592000; pin-sha256="E9CZ9INDbd+2eRQozYqqbQ2yXLVKB9+xcprMF+44U1g="; */
	public static final String RETRY_AFTER = "Retry-After"; // If an entity is temporarily unavailable, this instructs the client to try again later. Value could be a specified period of time (in seconds) or a HTTP-date.[47] (Permanent) /* EXAMPLE: Example 1: Retry-After: 120 Example 2: Retry-After: Fri, 07 Nov 2014 23:59:59 GMT */
	public static final String SERVER = "Server"; // A name for the server (Permanent) /* EXAMPLE: Server: Apache/2.4.1 (Unix) */
	public static final String SET_COOKIE = "Set-Cookie"; // An HTTP cookie (Permanent: standard) /* EXAMPLE: Set-Cookie: UserID=JohnDoe; Max-Age=3600; Version=1 */
	public static final String STRICT_TRANSPORT_SECURITY = "Strict-Transport-Security"; // A HSTS Policy informing the HTTP client how long to cache the HTTPS only policy and whether this applies to subdomains. (Permanent: standard) /* EXAMPLE: Strict-Transport-Security: max-age=16070400; includeSubDomains */
	public static final String TRAILER = "Trailer"; // The Trailer general field value indicates that the given set of header fields is present in the trailer of a message encoded with chunked transfer coding. (Permanent) /* EXAMPLE: Trailer: Max-Forwards */
	public static final String TRANSFER_ENCODING = "Transfer-Encoding"; // The form of encoding used to safely transfer the entity to the user. Currently defined methods are: chunked, compress, deflate, gzip, identity. Must not be used with HTTP/2.[10] (Permanent) /* EXAMPLE: Transfer-Encoding: chunked */
	public static final String TK = "Tk"; // Tracking Status header, value suggested to be sent in response to a DNT(do-not-track), possible values:"!" — under construction "?" — dynamic "G" — gateway to multiple parties "N" — not tracking "T" — tracking "C" — tracking with consent "P" — tracking only if consented "D" — disregarding DNT "U" — updated (Permanent) /* EXAMPLE: Tk: ? */
	public static final String UPGRADE = "Upgrade"; // Ask the client to upgrade to another protocol. Must not be used in HTTP/2[10] (Permanent) /* EXAMPLE: Upgrade: h2c, HTTPS/1.3, IRC/6.9, RTA/x11, websocket */
	public static final String VARY = "Vary"; // Tells downstream proxies how to match future request headers to decide whether the cached response can be used rather than requesting a fresh one from the origin server. (Permanent) /* EXAMPLE: Example 1: Vary: * Example 2: Vary: Accept-Language */
	public static final String VIA = "Via"; // Informs the client of proxies through which the response was sent. (Permanent) /* EXAMPLE: Via: 1.0 fred, 1.1 example.com (Apache/1.1) */
	public static final String WARNING = "Warning"; // A general warning about possible problems with the entity body. (Permanent) /* EXAMPLE: Warning: 199 Miscellaneous warning */
	public static final String WWW_AUTHENTICATE = "WWW-Authenticate"; // Indicates the authentication scheme that should be used to access the requested entity. (Permanent) /* EXAMPLE: WWW-Authenticate: Basic */
	public static final String X_FRAME_OPTIONS = "X-Frame-Options"; // Clickjacking protection: deny - no rendering within a frame, sameorigin- no rendering if origin mismatch, allow-from - allow from specified location, allowall - non-standard, allow from any location (Obsolete[49]) /* EXAMPLE: X-Frame-Options: deny */

	// Common non-standard response fields

	public static final String X_REQUEST_ID = "X-Request-ID"; // Correlates HTTP requests between a client and server. /* EXAMPLE: X-Request-ID: f058ebd6-02f7-4d3f-942e-904344e8cde5 */
	public static final String X_CORRELATION_ID = "X-Correlation-ID";
	public static final String CONTENT_SECURITY_POLICY = "Content-Security-Policy"; // Content Security Policy definition. /* EXAMPLE: X-WebKit-CSP: default-src 'self' */
	public static final String X_CONTENT_SECURITY_POLICY = "X-Content-Security-Policy";
	public static final String X_WEBKIT_CSP = "X-WebKit-CSP";
	public static final String REFRESH = "Refresh"; // Used in redirection, or when a new resource has been created. This refresh redirects after 5 seconds. Header extension introduced by Netscape and supported by most web browsers. /* EXAMPLE: Refresh: 5; url=http://www.w3.org/pub/WWW/People.html */
	public static final String STATUS = "Status"; // CGI header field specifying the status of the HTTP response. Normal HTTP responses use a separate "Status-Line" instead, defined by RFC 7230. /* EXAMPLE: Status: 200 OK */
	public static final String TIMING_ALLOW_ORIGIN = "Timing-Allow-Origin"; // The Timing-Allow-Origin response header specifies origins that are allowed to see values of attributes retrieved via features of the Resource Timing API, which would otherwise be reported as zero due to cross-origin restrictions. /* EXAMPLE: Timing-Allow-Origin: * Timing-Allow-Origin: <origin>[, <origin>]* */
	public static final String X_CONTENT_DURATION = "X-Content-Duration"; // Provide the duration of the audio or video in seconds; only supported by Gecko browsers /* EXAMPLE: X-Content-Duration: 42.666 */
	public static final String X_CONTENT_TYPE_OPTIONS = "X-Content-Type-Options"; // The only defined value, "nosniff", prevents Internet Explorer from MIME-sniffing a response away from the declared content-type. This also applies to Google Chrome, when downloading extensions. /* EXAMPLE: X-Content-Type-Options: nosniff */
	public static final String X_POWERED_BY = "X-Powered-By"; // Specifies the technology (e.g. ASP.NET, PHP, JBoss) supporting the web application (version details are often in X-Runtime, X-Version, or X-AspNet-Version) /* EXAMPLE: X-Powered-By: PHP/5.4.0 */
	public static final String X_UA_COMPATIBLE = "X-UA-Compatible"; // Recommends the preferred rendering engine (often a backward-compatibility mode) to use to display the content. Also used to activate Chrome Frame in Internet Explorer. /* EXAMPLE: X-UA-Compatible: IE=EmulateIE7 X-UA-Compatible: IE=edge X-UA-Compatible: Chrome=1 */
	public static final String X_XSS_PROTECTION = "X-XSS-Protection"; // Cross-site scripting (XSS) filter /* EXAMPLE: X-XSS-Protection: 1; mode=block */

}
