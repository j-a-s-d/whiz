/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.interfaces;

import ace.interfaces.ExceptionsHandler;
import java.net.HttpCookie;
import java.util.List;

/**
 * Interface for the HTTP connection information replies.
 */
public interface HttpConnectionInfo extends ExceptionsHandler {

	String getRequestMethod();

	String getRequestURL();

	String getRequestData();

	List<HttpCookie> getRequestCookies();

	int getResponseCode();

	long getResponseTime();

	String getResponseData();

	List<HttpCookie> getResponseCookies();

}
