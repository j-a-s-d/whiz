/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net;

import whiz.Whiz;

/**
 * Utility class for working with HTTP statuses.
 */
public class HttpStatus extends Whiz {

	public static final int CONTINUE = 100; // Continue
	public static final int SWITCHING_PROTOCOLS = 101; // Switching Protocols.
	public static final int PROCESSING = 102; // Processing
	public static final int OK = 200; // OK
	public static final int CREATED = 201; // Created
	public static final int ACCEPTED = 202; // Accepted
	public static final int NON_AUTHORITATIVE_INFORMATION = 203; // Non-Authoritative Information
	public static final int NO_CONTENT = 204; // No Content
	public static final int RESET_CONTENT = 205; // Reset Content
	public static final int PARTIAL_CONTENT = 206; // Partial Content
	public static final int MULTI_STATUS = 207; // Multi-Status
	public static final int ALREADY_REPORTED = 208; // Already Reported
	public static final int IM_USED = 226; // IM Used
	public static final int MULTIPLE_CHOICES = 300; // Multiple Choices
	public static final int MOVED_PERMANENTLY = 301; // Moved Permanently
	// NOTE: the MOVED_TEMPORARILY constant (302 Moved Temporarily.) was replaced by the 307 in HTTP/1.1
	public static final int FOUND = 302; // Found
	public static final int SEE_OTHER = 303; // See Other
	public static final int NOT_MODIFIED = 304; // Not Modified
	public static final int USE_PROXY = 305; // Use Proxy
	public static final int _UNUSED_ = 306; // (Unused)
	public static final int TEMPORARY_REDIRECT = 307; // Temporary Redirect
	public static final int PERMANENT_REDIRECT = 308; // Permanent Redirect (experiemental)
	public static final int BAD_REQUEST = 400; // Bad Request
	public static final int UNAUTHORIZED = 401; // Unauthorized
	public static final int PAYMENT_REQUIRED = 402; // Payment Required
	public static final int FORBIDDEN = 403; // Forbidden
	public static final int NOT_FOUND = 404; // Not Found
	public static final int METHOD_NOT_ALLOWED = 405; // Method Not Allowed
	public static final int NOT_ACCEPTABLE = 406; // Not Acceptable
	public static final int PROXY_AUTHENTICATION_REQUIRED = 407; // Proxy Authentication Required
	public static final int REQUEST_TIMEOUT = 408; // Request Timeout
	public static final int CONFLICT = 409; // Conflict
	public static final int GONE = 410; // Gone
	public static final int LENGTH_REQUIRED = 411; // Length Required
	public static final int PRECONDITION_FAILED = 412; // Precondition failed
	public static final int REQUEST_ENTITY_TOO_LARGE = 413; // Request Entity Too Large
	public static final int REQUEST_URI_TOO_LONG = 414; // Request-URI Too Long
	public static final int UNSUPPORTED_MEDIA_TYPE = 415; // Unsupported Media Type
	public static final int REQUESTED_RANGE_NOT_SATISFIABLE = 416; // Requested Range Not Satisfiable
	public static final int EXPECTATION_FAILED = 417; // Expectation Failed
	public static final int TEAPOT = 418; // I'm a teapot (RFC 2324)
	public static final int INSUFFICIENT_SPACE_ON_RESOURCE = 419; // Insufficient Space on Resource
	public static final int METHOD_FAILURE = 420; // Method Failure
	public static final int DESTINATION_LOCKED = 421; // Destination Locked
	public static final int UNPROCESSABLE_ENTITY = 422; // Unprocessable Entity
	public static final int LOCKED = 423; // Locked
	public static final int FAILED_DEPENDENCY = 424; // Failed Dependency
	public static final int _RESERVED_ = 425; // Reserved (WebDAV)
	public static final int UPGRADE_REQUIRED = 426; // Upgrade Required
	public static final int PRECONDITION_REQUIRED = 428; // Precondition Required
	public static final int TOO_MANY_REQUEST = 429; // Too Many Requests
	public static final int HEADERS_TOO_LARGE = 431; // Request Header Fields Too Large
	public static final int NO_RESPONSE = 444; // No Response
	public static final int RETRY_WITH = 449; // Retry With
	public static final int PARENTAL_CONTROL_BLOCKED = 450; // Blocked by Parental Controls
	public static final int UNAVAILABLE_FOR_LEGAL_REASONS = 451; // Unavailable For Legal Reasons
	public static final int CLIENT_CLOSED_REQUEST = 499; // Client Closed Request
	public static final int INTERNAL_SERVER_ERROR = 500; // Internal Server Error
	public static final int NOT_IMPLEMENTED = 501; // Not Implemented
	public static final int BAD_GATEWAY = 502; // Bad Gateway
	public static final int SERVICE_UNAVAILABLE = 503; // Service Unavailable
	public static final int GATEWAY_TIMEOUT = 504; // Gateway Timeout
	public static final int HTTP_VERSION_NOT_SUPPORTED = 505; // HTTP Version Not Supported
	public static final int VARIANT_ALSO_NEGOTIATES = 506; // Variant Also Negotiates
	public static final int INSUFFICIENT_STORAGE = 507; // Insufficient Storage
	public static final int LOOP_DETECTED = 508; // Loop Detected
	public static final int BANDWIDTH_LIMIT_EXCEEDED = 509; // Bandwidth Limit Exceeded
	public static final int NOT_EXTENDED = 510; // Not Extended
	public static final int NETWORK_AUTHENTICATION_REQUIRED = 511; // Network Authentication Required
	public static final int NETWORK_READ_TIMEOUT_ERROR = 598; // Network Read Timeout Error
	public static final int NETWORK_CONNECT_TIMEOUT_ERROR = 599; // Network Connect Timeout Error

	/**
	 * Gets the specified HTTP status message code as string.
	 * 
	 * @param code
	 * @return the resulting string
	 */
	public static final String getStatusMessage(final int code) {
		switch (code) {
			case ACCEPTED:
				return "Accepted";
			case ALREADY_REPORTED:
				return "Already Reported";
			case BAD_GATEWAY:
				return "Bad Gateway";
			case BAD_REQUEST:
				return "Bad Request";
			case CONFLICT:
				return "Conflict";
			case CONTINUE:
				return "Continue";
			case CREATED:
				return "Created";
			case DESTINATION_LOCKED:
				return "Destination Locked";
			case EXPECTATION_FAILED:
				return "Expectation Failed";
			case FAILED_DEPENDENCY:
				return "Failed Dependency";
			case FORBIDDEN:
				return "Forbidden";
			case FOUND:
				return "Found";
			case GATEWAY_TIMEOUT:
				return "Gateway Timeout";
			case GONE:
				return "Gone";
			case HTTP_VERSION_NOT_SUPPORTED:
				return "HTTP Version Not Supported";
			case IM_USED:
				return "IM Used";
			case INSUFFICIENT_SPACE_ON_RESOURCE:
				return "Insufficient Space on Resource";
			case INSUFFICIENT_STORAGE:
				return "Insufficient Storage";
			case INTERNAL_SERVER_ERROR:
				return "Internal Server Error";
			case LENGTH_REQUIRED:
				return "Length Required";
			case LOCKED:
				return "Locked";
			case LOOP_DETECTED:
				return "Loop Detected";
			case METHOD_FAILURE:
				return "Method Failure";
			case METHOD_NOT_ALLOWED:
				return "Method Not Allowed";
			case MOVED_PERMANENTLY:
				return "Moved Permanently";
			case MULTI_STATUS:
				return "Multi-Status";
			case MULTIPLE_CHOICES:
				return "Multiple Choices";
			case NO_CONTENT:
				return "No Content";
			case NON_AUTHORITATIVE_INFORMATION:
				return "Non-Authoritative Information";
			case NOT_ACCEPTABLE:
				return "Not Acceptable";
			case NOT_EXTENDED:
				return "Not Extended";
			case NOT_FOUND:
				return "Not Found";
			case NOT_IMPLEMENTED:
				return "Not Implemented";
			case NOT_MODIFIED:
				return "Not Modified";
			case OK:
				return "OK";
			case PARTIAL_CONTENT:
				return "Partial Content";
			case PAYMENT_REQUIRED:
				return "Payment Required";
			case PRECONDITION_FAILED:
				return "Precondition failed";
			case PROCESSING:
				return "Processing";
			case PROXY_AUTHENTICATION_REQUIRED:
				return "Proxy Authentication Required";
			case REQUEST_ENTITY_TOO_LARGE:
				return "Request Entity Too Large";
			case REQUEST_TIMEOUT:
				return "Request Timeout";
			case REQUEST_URI_TOO_LONG:
				return "Request-URI Too Long";
			case REQUESTED_RANGE_NOT_SATISFIABLE:
				return "Requested Range Not Satisfiable";
			case RESET_CONTENT:
				return "Reset Content";
			case SEE_OTHER:
				return "See Other";
			case SERVICE_UNAVAILABLE:
				return "Service Unavailable";
			case SWITCHING_PROTOCOLS:
				return "Switching Protocols";
			case TEMPORARY_REDIRECT:
				return "Temporary Redirect";
			case UNAUTHORIZED:
				return "Unauthorized";
			case UNPROCESSABLE_ENTITY:
				return "Unprocessable Entity";
			case UNSUPPORTED_MEDIA_TYPE:
				return "Unsupported Media Type";
			case UPGRADE_REQUIRED:
				return "Upgrade Required";
			case USE_PROXY:
				return "Use Proxy";
			case VARIANT_ALSO_NEGOTIATES:
				return "Variant Also Negotiates";
			default:
				return null;
		}
	}

}
