package client

import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioSocketChannel
import java.net.InetSocketAddress
import io.netty.channel.socket.SocketChannel


class EchoClient(private val host: String, private val port: Int) {
    fun start() {
        val group = NioEventLoopGroup()
        try {
            val b = Bootstrap()
            b.group(group)
                    .channel(NioSocketChannel::class.java)
                    .remoteAddress(InetSocketAddress(host, port))
                    .handler(object : ChannelInitializer<SocketChannel>() {
                        @Throws(Exception::class)
                        public override fun initChannel(ch: SocketChannel) {
                            ch.pipeline().addLast(
                                    EchoClientHandler())
                        }
                    })
            val f = b.connect().sync()
            f.channel().closeFuture().sync()
        } finally {
            group.shutdownGracefully().sync()
        }
    }
}


fun main(args: Array<String>) {
    if (args.size != 2) {
        System.err.println("Usage: " + EchoClient::class.java.simpleName +
                " <host> <port>"
        )
        return
    }

    val host = args[0]
    val port = Integer.parseInt(args[1])
    EchoClient(host, port).start()
}