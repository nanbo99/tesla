/*
 * Copyright 2014-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package io.github.tesla.gateway.netty.filter.request;

import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;

import io.github.tesla.gateway.config.SpringContextHolder;
import io.github.tesla.gateway.netty.servlet.NettyHttpServletRequest;
import io.github.tesla.gateway.routerules.Oauth2TokenComponent;
import io.github.tesla.rule.FilterTypeEnum;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;

/**
 * @author liushiming
 * @version Oauth2HttpRequestFilter.java, v 0.0.1 2018年2月10日 下午6:12:20 liushiming
 */
public class Oauth2HttpRequestFilter extends HttpRequestFilter {


  private final Oauth2TokenComponent oauth2TokenCache =
      SpringContextHolder.getBean(Oauth2TokenComponent.class);

  public static HttpRequestFilter newFilter() {
    return new GrpcAdapterHttpRequestFilter();
  }

  @Override
  public HttpResponse doFilter(HttpRequest originalRequest, HttpObject httpObject,
      ChannelHandlerContext channelHandlerContext) {
    if (httpObject instanceof HttpRequest) {
      try {
        HttpRequest httpRequest = (HttpRequest) httpObject;
        NettyHttpServletRequest servletRequest =
            new NettyHttpServletRequest(httpRequest, "/", channelHandlerContext);
        OAuthAccessResourceRequest oauthRequest =
            new OAuthAccessResourceRequest(servletRequest, ParameterStyle.QUERY);
        String accessToken = oauthRequest.getAccessToken();
        if (!oauth2TokenCache.checkAccessToken(accessToken)) {
          return super.createResponse(HttpResponseStatus.FORBIDDEN, originalRequest);
        }
      } catch (Throwable e) {
        return super.createResponse(HttpResponseStatus.FORBIDDEN, originalRequest);
      }
    }
    return null;
  }

  @Override
  public FilterTypeEnum filterType() {
    return FilterTypeEnum.Oauth2HttpRequestFilter;
  }

}