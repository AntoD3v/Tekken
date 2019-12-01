package com.tekken.data.websocket;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.WebSocket;
import io.vertx.core.http.WebSocketBase;
import io.vertx.core.http.WebSocketFrame;
import io.vertx.core.net.SocketAddress;

import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.security.cert.X509Certificate;

public class WebSocketServlet implements Runnable, WebSocket {

    private int port;

    public WebSocketServlet() {

    }

    public void newBind(int port){
        new Thread(this).start();
    }

    @Override
    public void run() {
    }

    @Override
    public WebSocket exceptionHandler(Handler<Throwable> handler) {
        return null;
    }

    @Override
    public WebSocket handler(Handler<Buffer> handler) {
        return null;
    }

    @Override
    public WebSocket pause() {
        return null;
    }

    @Override
    public WebSocket resume() {
        return null;
    }

    @Override
    public WebSocket fetch(long l) {
        return null;
    }

    @Override
    public WebSocket endHandler(Handler<Void> handler) {
        return null;
    }

    @Override
    public WebSocket write(Buffer buffer) {
        return null;
    }

    @Override
    public WebSocket write(Buffer buffer, Handler<AsyncResult<Void>> handler) {
        return null;
    }

    @Override
    public WebSocket setWriteQueueMaxSize(int i) {
        return null;
    }

    @Override
    public boolean writeQueueFull() {
        return false;
    }

    @Override
    public WebSocket drainHandler(Handler<Void> handler) {
        return null;
    }

    @Override
    public String binaryHandlerID() {
        return null;
    }

    @Override
    public String textHandlerID() {
        return null;
    }

    @Override
    public String subProtocol() {
        return null;
    }

    @Override
    public WebSocket writeFrame(WebSocketFrame webSocketFrame) {
        return null;
    }

    @Override
    public WebSocket writeFrame(WebSocketFrame webSocketFrame, Handler<AsyncResult<Void>> handler) {
        return null;
    }

    @Override
    public WebSocket writeFinalTextFrame(String s) {
        return null;
    }

    @Override
    public WebSocket writeFinalTextFrame(String s, Handler<AsyncResult<Void>> handler) {
        return null;
    }

    @Override
    public WebSocket writeFinalBinaryFrame(Buffer buffer) {
        return null;
    }

    @Override
    public WebSocket writeFinalBinaryFrame(Buffer buffer, Handler<AsyncResult<Void>> handler) {
        return null;
    }

    @Override
    public WebSocket writeBinaryMessage(Buffer buffer) {
        return null;
    }

    @Override
    public WebSocket writeBinaryMessage(Buffer buffer, Handler<AsyncResult<Void>> handler) {
        return null;
    }

    @Override
    public WebSocket writeTextMessage(String s) {
        return null;
    }

    @Override
    public WebSocket writeTextMessage(String s, Handler<AsyncResult<Void>> handler) {
        return null;
    }

    @Override
    public WebSocketBase writePing(Buffer buffer) {
        return null;
    }

    @Override
    public WebSocketBase writePong(Buffer buffer) {
        return null;
    }

    @Override
    public WebSocket closeHandler(Handler<Void> handler) {
        return null;
    }

    @Override
    public WebSocket frameHandler(Handler<WebSocketFrame> handler) {
        return null;
    }

    @Override
    public WebSocketBase textMessageHandler(Handler<String> handler) {
        return null;
    }

    @Override
    public WebSocketBase binaryMessageHandler(Handler<Buffer> handler) {
        return null;
    }

    @Override
    public WebSocketBase pongHandler(Handler<Buffer> handler) {
        return null;
    }

    @Override
    public void end() {

    }

    @Override
    public void end(Handler<AsyncResult<Void>> handler) {

    }

    @Override
    public void close() {

    }

    @Override
    public void close(Handler<AsyncResult<Void>> handler) {

    }

    @Override
    public void close(short i) {

    }

    @Override
    public void close(short i, Handler<AsyncResult<Void>> handler) {

    }

    @Override
    public void close(short i, String s) {

    }

    @Override
    public void close(short i, String s, Handler<AsyncResult<Void>> handler) {

    }

    @Override
    public SocketAddress remoteAddress() {
        return null;
    }

    @Override
    public SocketAddress localAddress() {
        return null;
    }

    @Override
    public boolean isSsl() {
        return false;
    }

    @Override
    public SSLSession sslSession() {
        return null;
    }

    @Override
    public X509Certificate[] peerCertificateChain() throws SSLPeerUnverifiedException {
        return new X509Certificate[0];
    }
}
