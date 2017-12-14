package net.bolbat.utils.concurrency;

import java.lang.Thread.UncaughtExceptionHandler;

import org.junit.Assert;
import org.junit.Test;

/**
 * {@link ThreadFactoryBuilder} test.
 * 
 * @author Alexandr Bolbat
 */
public class ThreadFactoryBuilderTest {

	public static final Runnable EMPTY_RUNNABLE = new EmptyRunnable();
	public static final UncaughtExceptionHandler E_HANDLER = new SystemOutUncaughtExceptionHandler();

	@Test
	public void defaultsTest() {
		final ThreadFactoryBuilder builder = new ThreadFactoryBuilder();
		Thread thread = builder.build().newThread(EMPTY_RUNNABLE);
		Assert.assertNotNull(thread);
		Assert.assertTrue(thread.getName().startsWith("pool-"));
		Assert.assertTrue(thread.getName().endsWith("-thread-1"));
		Assert.assertEquals(false, thread.isDaemon());
		Assert.assertEquals(Thread.NORM_PRIORITY, thread.getPriority());
		Assert.assertSame(thread.getThreadGroup(), thread.getUncaughtExceptionHandler());
	}

	@Test
	public void basicTest() {
		final ThreadFactoryBuilder builder = new ThreadFactoryBuilder();
		builder.setDaemon(true).setPriority(Thread.MIN_PRIORITY);
		builder.setUncaughtExceptionHandler(E_HANDLER);
		builder.setNameFormat("thread-%d");
		Thread thread = builder.build().newThread(EMPTY_RUNNABLE);
		Assert.assertNotNull(thread);
		Assert.assertEquals("thread-1", thread.getName());
		Assert.assertEquals(true, thread.isDaemon());
		Assert.assertEquals(Thread.MIN_PRIORITY, thread.getPriority());
		Assert.assertSame(E_HANDLER, thread.getUncaughtExceptionHandler());
	}

	@Test
	public void nameFormatTest() {
		final ThreadFactoryBuilder builder = new ThreadFactoryBuilder();

		builder.setNameFormat("custom-pool-thread-%d");
		Thread thread = builder.build().newThread(EMPTY_RUNNABLE);
		Assert.assertEquals("custom-pool-thread-1", thread.getName());

		builder.setNameFormat("thread[%2$d]-pool[%1$s]");
		builder.setNameFormatArgs("custom");
		thread = builder.build().newThread(EMPTY_RUNNABLE);
		Assert.assertEquals("thread[1]-pool[custom]", thread.getName());
	}

	public static class EmptyRunnable implements Runnable {
		@Override
		public void run() {
		}
	}

	public static class SystemOutUncaughtExceptionHandler implements UncaughtExceptionHandler {
		@Override
		public void uncaughtException(final Thread t, final Throwable e) {
			System.out.println("Thread[" + t + "] exception[" + e + "]");
		}
	}

}
