package com.weibo.keeplooking.nio;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * NIO socket server.
 * 
 * @author Johnny
 *
 */
public class NonBlockingServer {

    public static void main(String args[]) throws Exception {
        NonBlockingServer server = new NonBlockingServer();
        server.startServer();
    }

    public void startServer() throws Exception {
        int channels = 0;
        int nKeys = 0;

        // a selectable channel for stream-oriented listening sockets
        ServerSocketChannel severSocketChannel = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(
                InetAddress.getLocalHost(), 9000);
        severSocketChannel.socket().bind(address);
        severSocketChannel.configureBlocking(false);

        // a multiplexor of SelectableChannel objects
        Selector selector = Selector.open();

        /*
         * a selectable channel's registration with a selector is represented by
         * a SelectionKey object
         */
        SelectionKey selectionKey = severSocketChannel.register(selector,
                SelectionKey.OP_ACCEPT);

        printKeyInfo(selectionKey);

        while (true) {
            debug("starting select...");

            /*
             * this method performs a blocking selection operation. It returns
             * only after at least one channel is selected, this selector's
             * wakeup method is invoked, or the current thread is interrupted,
             * whichever comes first
             */
            nKeys = selector.select();

            if (nKeys > 0) {
                debug("number of keys after select operation: " + nKeys);
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectedKeys.iterator();
                while (iterator.hasNext()) {
                    selectionKey = (SelectionKey) iterator.next();
                    printKeyInfo(selectionKey);
                    iterator.remove();

                    if (selectionKey.isAcceptable()) {
                        /*
                         * return the socket channel for the new connection, or
                         * null if this channel is in non-blocking mode and no
                         * connection is available to be accepted
                         */
                        SocketChannel socketChannel = ((ServerSocketChannel) selectionKey
                                .channel()).accept();

                        socketChannel.configureBlocking(false);
                        channels++;
                        debug("number of Channels: " + channels);

                        socketChannel.register(selector, SelectionKey.OP_READ);
                    }

                    else if (selectionKey.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel) selectionKey
                                .channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        socketChannel.read(buffer);
                        System.out.print("received: ");
                        buffer.rewind();
                        char c = 0;
                        while ((c = buffer.getChar()) > 0) {
                            System.out.print(c);
                        }

                        buffer.flip();
                        socketChannel.write(buffer);
                    }
                }
            }
        }
    }

    private static void debug(String s) {
        System.out.println(s);
    }

    private static void printKeyInfo(SelectionKey sk) {
        String s = new String();
        s = "Att: " + (sk.attachment() == null ? "no" : "yes");
        s += ", Read: " + sk.isReadable();
        s += ", Acpt: " + sk.isAcceptable();
        s += ", Cnct: " + sk.isConnectable();
        s += ", Wrt: " + sk.isWritable();
        s += ", Valid: " + sk.isValid();
        s += ", Ops: " + sk.interestOps();
        debug(s);
    }
}
