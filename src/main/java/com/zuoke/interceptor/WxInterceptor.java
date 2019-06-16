package com.zuoke.interceptor;

import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.zuoke.common.util.MyCacheUtil;



public class WxInterceptor implements HandlerInterceptor {
	@Autowired
	private MyCacheUtil myCacheUtil;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 设置request和response的字符集，防止乱码
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		// TODO Auto-generated method stub
//		// 不过滤的uri
//		String[] notFilter = new String[] { "/login/", "/public/", "platuser/platuser/view", "platTicket/ticket/view/",
//				"/postmenu", "/wx/msg", "platuser/platuser/sdkconfig" };
//		// 请求的uri
//		String uri = request.getRequestURI();
//		// uri中包含background时才进行过滤
//		if (uri.indexOf("/wx/") != -1) {
//			// 是否过滤
//			boolean doFilter = true;
//			for (String s : notFilter) {
//				if (uri.indexOf(s) != -1) {
//					// 如果uri中包含不过滤的uri，则不进行过滤
//					doFilter = false;
//					break;
//				}
//			}
//			if (doFilter) {
//				// 执行过滤
//				// 从session中获取登录者实体
//				Object obj = myCacheUtil.getValue(SysConstants.SESSIONUSER);
//				if (null == obj) {
//
//					String openid = request.getParameter("openid");
//					String mphao = request.getParameter("mphao");
//					if (!StringUtils.isEmpty(openid)) {
//						WxUserinfo wxUserinfo = wxUserinfoService.findByMpHaoOpenidAndSubscribe(mphao, openid, 1);
//						if (wxUserinfo != null && "1".equals(wxUserinfo.getIsLogin())) {
//							if (!StringUtils.isEmpty(wxUserinfo.getWxPtUserId())) {
//								WxPlatformUser w = wxPlatformUserService.findById(wxUserinfo.getWxPtUserId());
//								if (w != null) {
//									myCacheUtil.setValue(SysConstants.SESSIONUSER, w);
//									return true;
//								}
//							}
//						}
//					}
//
//					if ("false".equals(ConfigUtil.getValue("system", "ISTESTMODEL"))) {
//						// 如果session中不存在登录者实体，则弹出框提示重新登录
//						response.sendRedirect(String.format(WeiXinParam.authorizeUrl, WeiXinParam.APPID,
//								URLEncoder.encode(WeiXinParam.WxWebServer + "/wx/platuser/platuser/view")));
//						return false;
//					}
//				} else {
//					// 如果session中存在登录者实体，则继续
//				}
//			} else {
//				// 如果不执行过滤，则继续
//			}
//		} else {
//			// 如果uri中不包含do，则继续
//		}
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
