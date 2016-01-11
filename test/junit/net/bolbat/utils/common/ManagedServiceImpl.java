package net.bolbat.utils.common;

import java.lang.reflect.Proxy;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import net.bolbat.utils.lang.CastUtils;
import net.bolbat.utils.reflect.ClassUtils;

public class ManagedServiceImpl implements ManagedService {

	private static final AtomicInteger EXCEPTION_EXECUTIONS = new AtomicInteger(0);

	private final AtomicInteger postConstructExecutions = new AtomicInteger(0);
	private final AtomicInteger preDestroyExecutions = new AtomicInteger(0);
	private final AtomicInteger executeExecutions = new AtomicInteger(0);

	public static ManagedService createManagedService() {
		return new ManagedServiceImpl();
	}

	public static ManagedService createProxiedManagedService() {
		final ManagedService service = createManagedService();
		final Class<?>[] interfaces = ClassUtils.getAllInterfaces(ManagedServiceImpl.class);
		return CastUtils.cast(Proxy.newProxyInstance(service.getClass().getClassLoader(), interfaces, new ManagedServiceHandler(service, interfaces)));
	}

	@PostConstruct
	private void postConstruct1() {
		postConstructExecutions.incrementAndGet();
	}

	@PostConstruct
	public void postConstruct2() {
		postConstructExecutions.incrementAndGet();
	}

	@PreDestroy
	private void preDestroy1() {
		preDestroyExecutions.incrementAndGet();
	}

	@PreDestroy
	void preDestroy2() {
		preDestroyExecutions.incrementAndGet();
	}

	@Execute
	private void execute1() {
		executeExecutions.incrementAndGet();
	}

	@Execute
	protected void execute2() {
		executeExecutions.incrementAndGet();
	}

	@Execute
	public void executeWithRuntimeException() {
		throw new IllegalArgumentException("Testing case");
	}

	@Execute
	private Object executeNotVoid() {
		EXCEPTION_EXECUTIONS.incrementAndGet();
		return null;
	}

	@Execute
	private void executeWithArgs(final Object... args) {
		EXCEPTION_EXECUTIONS.incrementAndGet();
	}

	@Execute
	private static void executeStatic() {
		EXCEPTION_EXECUTIONS.incrementAndGet();
	}

	@Execute
	private void executeWithCheckedException() throws Exception {
		EXCEPTION_EXECUTIONS.incrementAndGet();
	}

	@Override
	public int getExceptionExecutions() {
		return EXCEPTION_EXECUTIONS.get();
	}

	@Override
	public int getPostConstructExecutions() {
		return postConstructExecutions.get();
	}

	@Override
	public int getPreDestroyExecutions() {
		return preDestroyExecutions.get();
	}

	@Override
	public int getExecuteExecutions() {
		return executeExecutions.get();
	}

}
