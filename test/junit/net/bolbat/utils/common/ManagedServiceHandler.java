package net.bolbat.utils.common;

import java.lang.reflect.Method;

import net.bolbat.utils.reflect.proxy.AdvisedHandler;

public class ManagedServiceHandler extends AdvisedHandler {

	public ManagedServiceHandler(final Object aTarget, final Class<?>... aInterfaces) {
		super(aTarget, aInterfaces);
	}

	@Override
	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
		return method.invoke(getProxiedTarget(), args);
	}

}
