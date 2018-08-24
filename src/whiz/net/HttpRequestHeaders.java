/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net;

import whiz.Whiz;

/**
 * Utility class for working with HTTP request headers.
 */
public class HttpRequestHeaders extends Whiz {

	// Standard request fields

	public static final String A_IM = "A-IM"; // Acceptable instance-manipulations for the request. (Permanent) /* EXAMPLE: A-IM: feed */
	public static final String ACCEPT = "Accept"; // Media type(s) that is(/are) acceptable for the response. See Content negotiation. (Permanent) /* EXAMPLE: Accept: text/html */
	public static final String ACCEPT_CHARSET = "Accept-Charset"; // Character sets that are acceptable. (Permanent) /* EXAMPLE: Accept-Charset: utf-8 */
	public static final String ACCEPT_DATETIME = "Accept-Datetime"; // Acceptable version in time. (Provisional) /* EXAMPLE: Accept-Datetime: Thu, 31 May 2007 20:35:00 GMT */
	public static final String ACCEPT_ENCODING = "Accept-Encoding"; // List of acceptable encodings. See HTTP compression. (Permanent) /* EXAMPLE: Accept-Encoding: gzip, deflate */
	public static final String ACCEPT_LANGUAGE = "Accept-Language"; // List of acceptable human languages for response. See Content negotiation. (Permanent) /* EXAMPLE: Accept-Language: en-US */
	public static final String ACCESS_CONTROL_REQUEST_METHOD = "Access-Control-Request-Method"; // Initiates a request for cross-origin resource sharing with Origin (below). (Permanent: standard) /* EXAMPLE: Access-Control-Request-Method: GET */
	public static final String ACCESS_CONTROL_REQUEST_HEADERS = "Access-Control-Request-Headers";
	public static final String AUTHORIZATION = "Authorization"; // Authentication credentials for HTTP authentication. (Permanent) /* EXAMPLE: Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ== */
	public static final String CACHE_CONTROL = "Cache-Control"; // Used to specify directives that must be obeyed by all caching mechanisms along the request-response chain. (Permanent) /* EXAMPLE: Cache-Control: no-cache */
	public static final String CONNECTION = "Connection"; // Control options for the current connection and list of hop-by-hop request fields. Must not be used with HTTP/2. (Permanent) /* EXAMPLE: Connection: keep-alive Connection: Upgrade */
	public static final String CONTENT_LENGTH = "Content-Length"; // The length of the request body in octets (8-bit bytes). (Permanent) /* EXAMPLE: Content-Length: 348 */
	public static final String CONTENT_MD5 = "Content-MD5"; // A Base64-encoded binary MD5 sum of the content of the request body. (Obsolete) /* EXAMPLE: Content-MD5: Q2hlY2sgSW50ZWdyaXR5IQ== */
	public static final String CONTENT_TYPE = "Content-Type"; // The Media type of the body of the request (used with POST and PUT requests). (Permanent) /* EXAMPLE: Content-Type: application/x-www-form-urlencoded */
	public static final String COOKIE = "Cookie"; // An HTTP cookie previously sent by the server with Set-Cookie (below). (Permanent: standard) /* EXAMPLE: Cookie: $Version=1; Skin=new; */
	public static final String DATE = "Date"; // The date and time that the message was originated (in "HTTP-date" format as defined by RFC 7231 Date/Time Formats). (Permanent) /* EXAMPLE: Date: Tue, 15 Nov 1994 08:12:31 GMT */
	public static final String EXPECT = "Expect"; // Indicates that particular server behaviors are required by the client. (Permanent) /* EXAMPLE: Expect: 100-continue */
	public static final String FORWARDED = "Forwarded"; // Disclose original information of a client connecting to a web server through an HTTP proxy. (Permanent) /* EXAMPLE: Forwarded: for=192.0.2.60;proto=http;by=203.0.113.43Forwarded: for=192.0.2.43, for=198.51.100.17 */
	public static final String FROM = "From"; // The email address of the user making the request. (Permanent) /* EXAMPLE: From: user@example.com */
	public static final String HOST = "Host"; // The domain name of the server (for virtual hosting), and the TCP port number on which the server is listening. The portnumber may be omitted if the port is the standard port for the service requested. Mandatory since HTTP/1.1. If the request is generated directly in HTTP/2, it should not be used. (Permanent) /* EXAMPLE: Host: en.wikipedia.org:8080 Host: en.wikipedia.org */
	public static final String IF_MATCH = "If-Match"; // Only perform the action if the client supplied entity matches the same entity on the server. This is mainly for methods like PUT to only update a resource if it has not been modified since the user last updated it. (Permanent) /* EXAMPLE: If-Match: "737060cd8c284d8af7ad3082f209582d" */
	public static final String IF_MODIFIED_SINCE = "If-Modified-Since"; // Allows a 304 Not Modified to be returned if content is unchanged. (Permanent) /* EXAMPLE: If-Modified-Since: Sat, 29 Oct 1994 19:43:31 GMT */
	public static final String IF_NONE_MATCH = "If-None-Match"; // Allows a 304 Not Modified to be returned if content is unchanged, see HTTP ETag. (Permanent) /* EXAMPLE: If-None-Match: "737060cd8c284d8af7ad3082f209582d" */
	public static final String IF_RANGE = "If-Range"; // If the entity is unchanged, send me the part(s) that I am missing; otherwise, send me the entire new entity. (Permanent) /* EXAMPLE: If-Range: "737060cd8c284d8af7ad3082f209582d" */
	public static final String IF_UNMODIFIED_SINCE = "If-Unmodified-Since"; // Only send the response if the entity has not been modified since a specific time. (Permanent) /* EXAMPLE: If-Unmodified-Since: Sat, 29 Oct 1994 19:43:31 GMT */
	public static final String MAX_FORWARDS = "Max-Forwards"; // Limit the number of times the message can be forwarded through proxies or gateways. (Permanent) /* EXAMPLE: Max-Forwards: 10 */
	public static final String ORIGIN = "Origin"; // Initiates a request for cross-origin resource sharing (asks server for Access-Control-* response fields). (Permanent: standard) /* EXAMPLE: Origin: http://www.example-social-network.com */
	public static final String PRAGMA = "Pragma"; // Implementation-specific fields that may have various effects anywhere along the request-response chain. (Permanent) /* EXAMPLE: Pragma: no-cache */
	public static final String PROXY_AUTHORIZATION = "Proxy-Authorization"; // Authorization credentials for connecting to a proxy. (Permanent) /* EXAMPLE: Proxy-Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ== */
	public static final String RANGE = "Range"; // Request only part of an entity. Bytes are numbered from 0. See Byte serving. (Permanent) /* EXAMPLE: Range: bytes=500-999 */
	public static final String REFERER = "Referer"; // This is the address of the previous web page from which a link to the currently requested page was followed. (The word "referrer” has been misspelled in the RFC as well as in most implementations to the point that it has become standard usage and is considered correct terminology) (Permanent) /* EXAMPLE: Referer: http://en.wikipedia.org/wiki/Main_Page */
	public static final String REFERRER = "Referrer";
	public static final String TE = "TE"; // The transfer encodings the user agent is willing to accept: the same values as for the response header field Transfer-Encoding can be used, plus the "trailers" value (related to the "chunked" transfer method) to notify the server it expects to receive additional fields in the trailer after the last, zero-sized, chunk. Only trailers is supported in HTTP/2. (Permanent) /* EXAMPLE: TE: trailers, deflate */
	public static final String UPGRADE = "Upgrade"; // Ask the server to upgrade to another protocol. Must not be used in HTTP/2. (Permanent) /* EXAMPLE: Upgrade: h2c, HTTPS/1.3, IRC/6.9, RTA/x11, websocket */
	public static final String USER_AGENT = "User-Agent"; // The user agent string of the user agent. (Permanent) /* EXAMPLE: User-Agent: Mozilla/5.0 (X11; Linux x86_64; rv:12.0) Gecko/20100101 Firefox/12.0 */
	public static final String VIA = "Via"; // Informs the server of proxies through which the request was sent. (Permanent) /* EXAMPLE: Via: 1.0 fred, 1.1 example.com (Apache/1.1) */
	public static final String WARNING = "Warning"; // A general warning about possible problems with the entity body. (Permanent) /* EXAMPLE: Warning: 199 Miscellaneous warning */

	// Common non-standard request fields

	public static final String X_REQUEST_ID = "X-Request-ID"; // Correlates HTTP requests between a client and server. /* EXAMPLE: X-Request-ID: f058ebd6-02f7-4d3f-942e-904344e8cde5 */
	public static final String X_CORRELATION_ID = "X-Correlation-ID";
	public static final String DNT = "DNT"; // Requests a web application to disable their tracking of a user. This is Mozilla's version of the X-Do-Not-Track header field (since Firefox 4.0 Beta 11). Safari and IE9 also have support for this field. On March 7, 2011, a draft proposal was submitted to IETF. The W3CTracking Protection Working Group is producing a specification. /* EXAMPLE: DNT: 1 (Do Not Track Enabled) DNT: 0 (Do Not Track Disabled) */
	public static final String FRONT_END_HTTPS = "Front-End-Https"; // Non-standard header field used by Microsoft applications and load-balancers /* EXAMPLE: Front-End-Https: on */
	public static final String PROXY_CONNECTION = "Proxy-Connection"; // Implemented as a misunderstanding of the HTTP specifications. Common because of mistakes in implementations of early HTTP versions. Has exactly the same functionality as standard Connection field. Must not be used with HTTP/2. /* EXAMPLE: Proxy-Connection: keep-alive */
	public static final String SAVE_DATA = "Save-Data"; // The Save-Data client hint request header available in Chrome, Opera, and Yandex browsers lets developers deliver lighter, faster applications to users who opt-in to data saving mode in their browser. /* EXAMPLE: Save-Data: on */
	public static final String UPGRADE_INSECURE_REQUESTS = "Upgrade-Insecure-Requests"; // Tells a server which (presumably in the middle of a HTTP -> HTTPS migration) hosts mixed content that the client would prefer redirection to HTTPS and can handle Content-Security-Policy: upgrade-insecure-requests Must not be used with HTTP/2 /* EXAMPLE: Upgrade-Insecure-Requests: 1 */
	public static final String X_ATT_DEVICEID = "X-ATT-DeviceId"; // Allows easier parsing of the MakeModel/Firmware that is usually found in the User-Agent String of AT&T Devices /* EXAMPLE: X-Att-Deviceid: GT-P7320/P7320XXLPG */
	public static final String X_CSRF_TOKEN = "X-Csrf-Token"; // Used to prevent cross-site request forgery. Alternative header names are: X-CSRFToken and X-XSRF-TOKEN /* EXAMPLE: X-Csrf-Token: i8XNjC4b8KVok4uw5RftR38Wgp2BFwql */
	public static final String X_FORWARDED_FOR = "X-Forwarded-For"; // A de facto standard for identifying the originating IP address of a client connecting to a web server through an HTTP proxy or load balancer. Superseded by Forwarded header. /* EXAMPLE: X-Forwarded-For: client1, proxy1, proxy2 X-Forwarded-For: 129.78.138.66, 129.78.64.103 */
	public static final String X_FORWARDED_HOST = "X-Forwarded-Host"; // A de facto standard for identifying the original host requested by the client in the Host HTTP request header, since the host name and/or port of the reverse proxy (load balancer) may differ from the origin server handling the request. Superseded by Forwardedheader. /* EXAMPLE: X-Forwarded-Host: en.wikipedia.org:8080 X-Forwarded-Host: en.wikipedia.org */
	public static final String X_FORWARDED_PROTO = "X-Forwarded-Proto"; // A de facto standard for identifying the originating protocol of an HTTP request, since a reverse proxy (or a load balancer) may communicate with a web server using HTTP even if the request to the reverse proxy is HTTPS. An alternative form of the header (X-ProxyUser-Ip) is used by Google clients talking to Google servers. Superseded by Forwarded header. /* EXAMPLE: X-Forwarded-Proto: https */
	public static final String X_HTTP_METHOD_OVERRIDE = "X-Http-Method-Override"; // Requests a web application to override the method specified in the request (typically POST) with the method given in the header field (typically PUT or DELETE). This can be used when a user agent or firewall prevents PUT or DELETE methods from being sent directly (note that this is either a bug in the software component, which ought to be fixed, or an intentional configuration, in which case bypassing it may be the wrong thing to do). /* EXAMPLE: X-HTTP-Method-Override: DELETE */
	public static final String X_REQUESTED_WITH = "X-Requested-With"; // Mainly used to identify Ajax requests. Most JavaScript frameworks send this field with value of XMLHttpRequest /* EXAMPLE: X-Requested-With: XMLHttpRequest */
	public static final String X_UIDH = "X-UIDH"; // Server-side deep packet insertion of a unique ID identifying customers of Verizon Wireless; also known as "perma-cookie" or "supercookie" /* EXAMPLE: X-UIDH: ... */
	public static final String X_WAP_PROFILE = "X-Wap-Profile"; // Links to an XML file on the Internet with a full description and details about the device currently connecting. In the example to the right is an XML file for an AT&T Samsung Galaxy S2. /* EXAMPLE: x-wap-profile:http://wap.samsungmobile.com/uaprof/SGH-I777.xml */

}
