package com.donlian.httpclient;

import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.conn.routing.RouteInfo.LayerType;
import org.apache.http.conn.routing.RouteInfo.TunnelType;
import org.apache.http.protocol.HttpContext;

public class MyRoutePlanner implements HttpRoutePlanner {
    @Override
    public HttpRoute determineRoute(HttpHost target, HttpRequest request,
            HttpContext context) throws HttpException {
        return new HttpRoute(target, null
                , new HttpHost("192.168.2.3", 8181, "http")
                , true, TunnelType.PLAIN, LayerType.PLAIN); //Note: true
    }
}
