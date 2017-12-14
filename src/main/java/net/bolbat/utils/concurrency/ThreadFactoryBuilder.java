package net.bolbat.utils.concurrency;

import static net.bolbat.utils.lang.StringUtils.isNotEmpty;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

import net.bolbat.utils.annotation.Audience;
import net.bolbat.utils.annotation.Stability;
import net.bolbat.utils.lang.ArrayUtils;

/**
 * {@link ThreadFactory} builder.
 * 
 * @author Alexandr Bolbat
 */
@Audience.Public
@Stability.Evolving
public final class ThreadFactoryBuilder {

	/**
	 * Default {@link ThreadFactory}.
	 */
	private ThreadFactory defaultThreadFactory;

	/**
	 * Thread name format.
	 */
	private String nameFormat;

	/**
	 * Thread name format arguments.
	 */
	private Object[] nameFormatArgs;

	/**
	 * Is thread should be daemon.
	 */
	private Boolean daemon;

	/**
	 * Thread priority.
	 */
	private Integer priority;

	/**
	 * Thread {@link UncaughtExceptionHandler}.
	 */
	private UncaughtExceptionHandler uncaughtExceptionHandler;

	public ThreadFactory getDefaultThreadFactory() {
		return defaultThreadFactory;
	}

	/**
	 * Set default {@link ThreadFactory}.
	 * 
	 * @param aDefaultThreadFactory
	 *            {@link ThreadFactory}
	 * @return {@link ThreadFactoryBuilder}
	 */
	public ThreadFactoryBuilder setDefaultThreadFactory(final ThreadFactory aDefaultThreadFactory) {
		this.defaultThreadFactory = aDefaultThreadFactory;
		return this;
	}

	public String getNameFormat() {
		return nameFormat;
	}

	/**
	 * Set thread name format.
	 * 
	 * @param aNameFormat
	 *            thread name format
	 * @return {@link ThreadFactoryBuilder}
	 */
	public ThreadFactoryBuilder setNameFormat(final String aNameFormat) {
		this.nameFormat = aNameFormat;
		return this;
	}

	/**
	 * Get thread name format arguments.
	 * 
	 * @return array copy or <code>null</code>
	 */
	public Object[] getNameFormatArgs() {
		return ArrayUtils.clone(nameFormatArgs);
	}

	/**
	 * Set thread name format arguments.
	 * 
	 * @param aNameFormatArgs
	 *            thread name format arguments
	 * @return {@link ThreadFactoryBuilder}
	 */
	public ThreadFactoryBuilder setNameFormatArgs(final Object... aNameFormatArgs) {
		this.nameFormatArgs = aNameFormatArgs;
		return this;
	}

	public Boolean getDaemon() {
		return daemon;
	}

	/**
	 * Set thread as daemon.
	 * 
	 * @param aDaemon
	 *            <code>true</code> to set as daemon
	 * @return {@link ThreadFactoryBuilder}
	 */
	public ThreadFactoryBuilder setDaemon(final Boolean aDaemon) {
		this.daemon = aDaemon;
		return this;
	}

	public Integer getPriority() {
		return priority;
	}

	/**
	 * Set thread priority.
	 * 
	 * @param aPriority
	 *            priority
	 * @return {@link ThreadFactoryBuilder}
	 */
	public ThreadFactoryBuilder setPriority(final Integer aPriority) {
		this.priority = aPriority;
		return this;
	}

	public UncaughtExceptionHandler getUncaughtExceptionHandler() {
		return uncaughtExceptionHandler;
	}

	/**
	 * Set thread {@link UncaughtExceptionHandler}.
	 * 
	 * @param aUncaughtExceptionHandler
	 *            {@link UncaughtExceptionHandler}
	 * @return {@link ThreadFactoryBuilder}
	 */
	public ThreadFactoryBuilder setUncaughtExceptionHandler(final UncaughtExceptionHandler aUncaughtExceptionHandler) {
		this.uncaughtExceptionHandler = aUncaughtExceptionHandler;
		return this;
	}

	/**
	 * Build {@link ThreadFactory}.
	 * 
	 * @return {@link ThreadFactory}
	 */
	public ThreadFactory build() {
		final ThreadFactory aDefaultThreadFactory = this.defaultThreadFactory != null ? this.defaultThreadFactory : Executors.defaultThreadFactory();
		final String aNameFormat = this.nameFormat;
		final Object[] aNameFormatArgs = this.nameFormatArgs;
		final Boolean aDaemon = this.daemon;
		final Integer aPriority = this.priority;
		final UncaughtExceptionHandler aUncaughtExceptionHandler = this.uncaughtExceptionHandler;

		return new ThreadFactory() {
			private final AtomicLong threadsCount = isNotEmpty(aNameFormat) ? new AtomicLong(1) : null;

			@Override
			public Thread newThread(final Runnable r) {
				final Thread result = aDefaultThreadFactory.newThread(r);

				if (isNotEmpty(aNameFormat))
					result.setName(String.format(aNameFormat, ArrayUtils.concat(aNameFormatArgs, threadsCount.getAndIncrement())));

				if (aDaemon != null)
					result.setDaemon(aDaemon);

				if (aPriority != null)
					result.setPriority(aPriority);

				if (aUncaughtExceptionHandler != null)
					result.setUncaughtExceptionHandler(aUncaughtExceptionHandler);

				return result;
			}
		};
	}

}
