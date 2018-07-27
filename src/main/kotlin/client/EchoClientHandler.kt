package client


import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandler.Sharable
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.util.CharsetUtil


@Sharable
class EchoClientHandler : SimpleChannelInboundHandler<ByteBuf>() {
    override fun channelActive(ctx: ChannelHandlerContext) {
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!",
                CharsetUtil.UTF_8))
    }

    public override fun channelRead0(ctx: ChannelHandlerContext, `in`: ByteBuf) {
        println(
                "client received: " + `in`.toString(CharsetUtil.UTF_8))
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext,
                                 cause: Throwable) {
        cause.printStackTrace()
        ctx.close()
    }
}