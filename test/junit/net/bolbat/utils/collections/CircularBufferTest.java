package net.bolbat.utils.collections;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link CircularBuffer} test.
 * 
 * @author Alexandr Bolbat
 */
// TODO finish me with good code coverage for testing class
public class CircularBufferTest {

	/**
	 * {@link Logger} instance.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(CircularBufferTest.class);

	/**
	 * Testing threads amount.
	 */
	private static final int THREADS = 100;

	/**
	 * Thread test calls amount.
	 */
	private static final int CALLS_PER_THREAD = 10000;

	/**
	 * Testing instance.
	 */
	private CircularBuffer<AtomicInteger> buffer;

	@Before
	public void before() {
		after();
		buffer = CircularBuffer.of(new AtomicInteger(), new AtomicInteger(), new AtomicInteger(), new AtomicInteger());
	}

	@After
	public void after() {
		buffer = null;
	}

	/**
	 * Complex test.
	 */
	@Test
	public void complexTest() {
		final CountDownLatch starter = new CountDownLatch(1);
		final CountDownLatch finisher = new CountDownLatch(THREADS);

		// preparing workers
		for (int i = 0; i < THREADS; i++)
			new Thread(new Worker(buffer, starter, finisher)).start();

		LOGGER.info("Executing with threads[" + THREADS + "], calls-per-thread[" + CALLS_PER_THREAD + "]");
		final long startTime = System.currentTimeMillis();
		// starting workers
		starter.countDown();

		// waiting for results
		try {
			finisher.await();
		} catch (final InterruptedException e) {
			Assert.fail();
		}
		final long executionTime = System.currentTimeMillis() - startTime;
		LOGGER.info("Finished with buffer[" + buffer + "] in[" + executionTime + "ms.]");

		// validating results
		final int expectedIterationsPerElement = THREADS * CALLS_PER_THREAD / buffer.size();
		for (int i = 0; i < buffer.size(); i++)
			Assert.assertEquals(expectedIterationsPerElement, buffer.get(i).get());
	}

	/**
	 * Testing thread worker.
	 * 
	 * @author Alexandr Bolbat
	 */
	private static class Worker implements Runnable {

		/**
		 * {@link CircularBuffer} instance.
		 */
		private final CircularBuffer<AtomicInteger> buffer;

		/**
		 * Starter latch.
		 */
		private final CountDownLatch starter;

		/**
		 * Finisher latch.
		 */
		private final CountDownLatch finisher;

		/**
		 * Default constructor.
		 * 
		 * @param aBuffer
		 *            buffer
		 * @param aStarter
		 *            starter latch
		 * @param aFinisher
		 *            finisher latch
		 */
		public Worker(final CircularBuffer<AtomicInteger> aBuffer, final CountDownLatch aStarter, final CountDownLatch aFinisher) {
			this.buffer = aBuffer;
			this.starter = aStarter;
			this.finisher = aFinisher;
		}

		@Override
		public void run() {
			try {
				starter.await();
			} catch (final InterruptedException e) {
				Assert.fail();
			}

			try {
				for (int i = 0; i < CALLS_PER_THREAD; i++)
					buffer.get().incrementAndGet();
			} finally {
				finisher.countDown();
			}
		}

	}

}
