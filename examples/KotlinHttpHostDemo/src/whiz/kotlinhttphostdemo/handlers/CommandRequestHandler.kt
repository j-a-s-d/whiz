/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.kotlinhttphostdemo.handlers

import ace.arrays.GenericArrays
import ace.files.Directories
import ace.platform.OS
import ace.platform.User
import whiz.net.HttpMethods
import whiz.net.servers.HttpRequest
import whiz.net.servers.HttpStringHandler

/**
 * Simple echo handler.
 * Try it via:
 * curl -X POST -d"ls -l" http://127.0.0.1:5000/command
 */
public class CommandRequestHandler : HttpStringHandler {

	val ENVIRONMENT = GenericArrays.make("LC_ALL=en_US.UTF-8", "HOME=" + User.getHomeDirectoryName())

	constructor(route: String) : super(CommandRequestHandler::class.java, HttpMethods.POST_ONLY, route)

	override protected fun transact(request: HttpRequest, body: String): String = OS.runCommand(body, ENVIRONMENT, Directories.CURRENT_PATH)

	override protected fun onWrongMethod(request: HttpRequest, method: String) {}

	override protected fun onClientException(request: HttpRequest, e: Throwable, content: ByteArray) {}

}
