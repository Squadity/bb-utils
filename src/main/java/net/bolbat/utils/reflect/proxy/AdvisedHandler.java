package net.bolbat.utils.reflect.proxy;

import static net.bolbat.utils.lang.Validations.checkArgument;

import java.lang.reflect.InvocationHandler;

/**
 * Abstract advised {@link InvocationHandler} implementation.
 * 
 * @author Alexandr Bolbat
 */
public abstract class AdvisedHandler implements Advised, InvocationHandler {

	/**
	 * Proxied target.
	 */
	private final Object target;

	/**
	 * Proxied interfaces.
	 */
	private final Class<?>[] interfaces;

	/**
	 * Default constructor.
	 * 
	 * @param aTarget
	 *            proxied target
	 * @param aInterfaces
	 *            proxied interfaces
	 */
	protected AdvisedHandler(final Object aTarget, final Class<?>[] aInterfaces) {
		checkArgument(aTarget != null, "aTarget argument is null");
		checkArgument(aInterfaces != null && aInterfaces.length > 0, "interfaces argument is empty");

		this.target = aTarget;
		this.interfaces = aInterfaces.clone();
	}

	@Override
	public Object getProxiedTarget() {
		return target;
	}

	@Override
	public Class<?>[] getProxiedInterfaces() {
		return interfaces.clone();
	}

}
