/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.kotlinhttphostdemo.handlers

import whiz.net.HttpMethods
import whiz.net.servers.HttpRequest
import whiz.net.servers.HttpStringHandler

/**
 * Simple echo handler.
 * Try it via:
 * curl -X POST -d"hello" http://127.0.0.1:5000/echo
 */
public class EchoRequestHandler : HttpStringHandler {

	constructor(route: String) : super(EchoRequestHandler::class.java, HttpMethods.POST_ONLY, route)

    override protected fun transact(request: HttpRequest, body: String): String = body

	override protected fun onWrongMethod(request: HttpRequest, method: String) {}

	override protected fun onClientException(request: HttpRequest, e: Throwable, content: ByteArray) {}
 
}