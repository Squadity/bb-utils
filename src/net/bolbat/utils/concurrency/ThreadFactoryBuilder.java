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

	public ThreadFactoryBuilder setDefaultThreadFactory(final ThreadFactory aDefaultThreadFactory) {
		this.defaultThreadFactory = aDefaultThreadFactory;
		return this;
	}

	public String getNameFormat() {
		return nameFormat;
	}

	public ThreadFactoryBuilder setNameFormat(final String aNameFormat) {
		this.nameFormat = aNameFormat;
		return this;
	}

	public Object[] getNameFormatArgs() {
		return nameFormatArgs;
	}

	public ThreadFactoryBuilder setNameFormatArgs(final Object... aNameFormatArgs) {
		this.nameFormatArgs = aNameFormatArgs;
		return this;
	}

	public Boolean getDaemon() {
		return daemon;
	}

	public ThreadFactoryBuilder setDaemon(final Boolean aDaemon) {
		this.daemon = aDaemon;
		return this;
	}

	public Integer getPriority() {
		return priority;
	}

	public ThreadFactoryBuilder setPriority(final Integer aPriority) {
		this.priority = aPriority;
		return this;
	}

	public UncaughtExceptionHandler getUncaughtExceptionHandler() {
		return uncaughtExceptionHandler;
	}

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
		final ThreadFactory dThreadFactory = this.defaultThreadFactory != null ? this.defaultThreadFactory : Executors.defaultThreadFactory();
		final String nameFormat = this.nameFormat;
		final Object[] nameFormatArgs = this.nameFormatArgs;
		final Boolean daemon = this.daemon;
		final Integer priority = this.priority;
		final UncaughtExceptionHandler uncaughtExceptionHandler = this.uncaughtExceptionHandler;

		return new ThreadFactory() {
			private final AtomicLong threadsCount = isNotEmpty(nameFormat) ? new AtomicLong(1) : null;

			@Override
			public Thread newThread(final Runnable r) {
				final Thread result = dThreadFactory.newThread(r);

				if (isNotEmpty(nameFormat))
					result.setName(String.format(nameFormat, ArrayUtils.concat(nameFormatArgs, threadsCount.getAndIncrement())));

				if (daemon != null)
					result.setDaemon(daemon);

				if (priority != null)
					result.setPriority(priority);

				if (uncaughtExceptionHandler != null)
					result.setUncaughtExceptionHandler(uncaughtExceptionHandler);

				return result;
			}
		};
	}

}
