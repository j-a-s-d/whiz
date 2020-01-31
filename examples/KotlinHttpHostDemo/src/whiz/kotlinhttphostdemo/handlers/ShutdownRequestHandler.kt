/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.kotlinhttphostdemo.handlers

import ace.concurrency.Threads
import whiz.net.HttpMethods
import whiz.net.servers.HttpStand
import whiz.net.servers.HttpRequest
import whiz.net.servers.HttpStringHandler

/**
 * Simple shutdown handler.
 * Try it via:
 * curl -X POST http://127.0.0.1:5000/shutdown
 */
public class ShutdownRequestHandler : HttpStringHandler {

	constructor(route: String) : super(ShutdownRequestHandler::class.java, HttpMethods.POST_ONLY, route)

	override protected fun transact(request: HttpRequest, body: String): String {
        Threads.isolatedSpawn(object : Runnable {
            override fun run() {
                nap(1000)
                (getStand() as HttpStand).stop()
                print("shutted down.")
            }
        })
		return "shutting down ..."
	}

	override protected fun onWrongMethod(request: HttpRequest, method: String) {}

	override protected fun onClientException(request: HttpRequest, e: Throwable, content: ByteArray) {}
	
}
