/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.kotlinhttphostdemo

import whiz.Whiz
import whiz.net.servers.HttpHost
import whiz.kotlinhttphostdemo.handlers.*

val HOST = HttpHost(5000)

fun main() {
    Whiz.DEVELOPMENT = true
    HOST.registerHandlers(
        ClientRequestHandler("/"),
        ShutdownRequestHandler("/shutdown"),
        EchoRequestHandler("/echo"),
        CommandRequestHandler("/command")
    )
    HOST.start()
    Whiz.print("listening at " + HOST.getPort() + " ...")
}
