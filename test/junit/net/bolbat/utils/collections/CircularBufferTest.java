package net.bolbat.utils.collections;

import java.util.ArrayList;
import java.util.Arrays;
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
public class CircularBufferTest {

	/**
	 * {@link Logger} instance.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(CircularBufferTest.class);

	/**
	 * Testing threads amount.
	 */
	private static final int THREADS = 4;

	/**
	 * Thread test calls amount.
	 */
	private static final int CALLS_PER_THREAD = 250000;

	/**
	 * Testing instance.
	 */
	private CircularBuffer<AtomicInteger> buffer;

	@Before
	public void before() {
		after();
	}

	@After
	public void after() {
		buffer = null;
	}

	@Test
	public void complexOnArray() {
		buffer = CircularBuffer.of(new AtomicInteger(), new AtomicInteger(), new AtomicInteger(), new AtomicInteger());
		complexScenario();
	}

	@Test
	public void complexOnList() {
		buffer = CircularBuffer.of(new ArrayList<>(Arrays.asList(new AtomicInteger(), new AtomicInteger(), new AtomicInteger(), new AtomicInteger())));
		complexScenario();
	}

	@Test
	public void contains() {
		final String nullString = null;
		Assert.assertTrue(CircularBuffer.of(nullString).contains(null));
		Assert.assertFalse(CircularBuffer.of().contains(null));

		CircularBuffer<String> b = CircularBuffer.of("1", "2", "3");
		Assert.assertTrue(b.contains("1"));
		Assert.assertFalse(b.contains("4"));
		Assert.assertFalse(b.contains(null));

		b = b.add(null);
		Assert.assertTrue(b.contains(null));
	}

	@Test
	public void add() {
		final CircularBuffer<String> original = CircularBuffer.of();
		Assert.assertEquals(0, original.size());

		CircularBuffer<String> b = original.add("1");
		Assert.assertEquals(1, b.size());
		Assert.assertNotSame(original, b);

		b = b.add(null);
		Assert.assertEquals(2, b.size());
	}

	@Test
	public void remove() {
		final CircularBuffer<String> original = CircularBuffer.of("1", "2", "3", null, "2");
		Assert.assertEquals(5, original.size());

		CircularBuffer<String> b = original.remove("1");
		Assert.assertEquals(4, b.size());
		Assert.assertFalse(b.contains("1"));
		Assert.assertTrue(b.contains("2"));
		Assert.assertTrue(b.contains("3"));
		Assert.assertTrue(b.contains(null));

		b = b.remove("2");
		Assert.assertEquals(2, b.size());
		Assert.assertTrue(b.contains("3"));
		Assert.assertTrue(b.contains(null));

		b = b.remove(null);
		Assert.assertEquals(1, b.size());
		Assert.assertTrue(b.contains("3"));
	}

	/**
	 * Complex testing scenario.
	 */
	public void complexScenario() {
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
				for (int i = 0; i < CALLS_PER_THREAD; i++) {
					final AtomicInteger value = buffer.get();
					Assert.assertNotNull(value);
					value.incrementAndGet();
				}
			} finally {
				finisher.countDown();
			}
		}

	}

}
