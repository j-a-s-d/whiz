/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.interfaces;

import java.net.HttpCookie;
import java.util.List;

public interface HttpCookieHandler {

	void clearCookies();

	void addCookie(HttpCookie cookie);

	void removeCookie(HttpCookie cookie);

	void addCookies(List<HttpCookie> cookies);

	void setCookies(List<HttpCookie> cookies);

	List<HttpCookie> listCookies();

	HttpCookie getCookieByName(String name);

}
