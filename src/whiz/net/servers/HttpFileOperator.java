/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.servers;

/**
 * Useful HTTP file operator interface.
 */
interface HttpFileOperator {

	boolean validate(HttpRequest request, String fileName);

}
