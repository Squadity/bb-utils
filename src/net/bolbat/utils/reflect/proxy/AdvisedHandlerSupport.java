package net.bolbat.utils.reflect.proxy;

import java.lang.reflect.InvocationHandler;

/**
 * {@link ProxyHandlerSupport} for {@link AdvisedHandler}.
 * 
 * @author Alexandr Bolbat
 */
public class AdvisedHandlerSupport implements ProxyHandlerSupport {

	@Override
	public Class<?> getHandlerClass() {
		return AdvisedHandler.class;
	}

	@Override
	public Object getTarget(final InvocationHandler handler) {
		return ((AdvisedHandler) handler).getProxiedTarget();
	}

}
