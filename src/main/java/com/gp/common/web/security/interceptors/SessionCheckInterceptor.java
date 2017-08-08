/**
 * 
 */
package com.gp.common.web.security.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.gp.common.web.security.model.UserModel;

/**
 * Interceptor for checking if request received by authenticated user or not
 * @author ganeshp
 *
 */
public class SessionCheckInterceptor implements HandlerInterceptor {

	/**
	 * 
	 */
	public SessionCheckInterceptor() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
	 */
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		boolean isProceed=false;
		if(isPublicURL(request)){
			isProceed=true;
		}else{
			HttpSession session=request.getSession(false);
			if(session!=null){
				UserModel um=(UserModel)session.getAttribute("user");
				if(um!=null){
					isProceed=true;
				}
			}
		}
		return isProceed;
	}
	
	public boolean isPublicURL(HttpServletRequest request){
		String url=request.getRequestURI();
		System.out.println(" "+url);
		return true;
	}

}
