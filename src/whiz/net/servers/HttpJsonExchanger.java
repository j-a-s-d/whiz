/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.servers;

import com.google.gson.JsonElement;

interface HttpJsonExchanger {

	JsonElement exchange(HttpRequest request, JsonElement json);

}
