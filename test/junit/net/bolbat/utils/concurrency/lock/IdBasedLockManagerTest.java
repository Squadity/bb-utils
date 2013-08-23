package net.bolbat.utils.concurrency.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link IdBasedLockManager} test.
 * 
 * @author Alexandr Bolbat
 */
public final class IdBasedLockManagerTest {

	/**
	 * {@link Logger} instance.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(IdBasedLockManagerTest.class);

	/**
	 * Testing threads amount.
	 */
	private static final int THREADS = 500;

	/**
	 * Thread test calls amount.
	 */
	private static final int CALLS_PER_THREAD = 1000;

	/**
	 * Testing lock id.
	 */
	private static final String LOCK_ID = "qwe";

	/**
	 * Current active testings threads calls amount.
	 */
	private static final AtomicInteger ACTIVE_CALLS = new AtomicInteger(0);

	/**
	 * Maximum active testing threads calls amount.
	 */
	private static final AtomicInteger MAX_ACTIVE_CALLS = new AtomicInteger(0);

	/**
	 * Processed tests calls amount.
	 */
	private static final AtomicInteger PROCESSED_CALLS = new AtomicInteger(0);

	/**
	 * Clearing context for each test.
	 */
	@Before
	public void beforeTest() {
		MAX_ACTIVE_CALLS.set(0);
		ACTIVE_CALLS.set(0);
		PROCESSED_CALLS.set(0);
	}

	/**
	 * Error cases test.
	 */
	@Test
	public void errorCasesTest() {
		// safe lock manager testing
		IdBasedLockManager<String> lockManager = new SafeIdBasedLockManager<String>();
		try {
			lockManager.obtainLock(null);
			Assert.fail();
		} catch (final IllegalArgumentException e) {
			Assert.assertTrue(e.getMessage().startsWith("id"));
		}
		try {
			lockManager.releaseLock(null);
			Assert.fail();
		} catch (final IllegalArgumentException e) {
			Assert.assertTrue(e.getMessage().startsWith("lock"));
		}
	}

	/**
	 * {@link IdBasedLock} test.
	 */
	@Test
	public void lockTest() {
		IdBasedLockManager<String> lockManager = new SafeIdBasedLockManager<String>();
		Assert.assertNotNull(lockManager.getLocksIds());
		Assert.assertEquals(0, lockManager.getLocksIds().size());

		IdBasedLock<String> lock = lockManager.obtainLock(LOCK_ID);
		Assert.assertNotNull(lock);
		Assert.assertNotNull(lockManager.getLocksIds());
		Assert.assertEquals(1, lockManager.getLocksIds().size());
		Assert.assertNotNull(lockManager.getLocksIds().get(0));
		Assert.assertEquals(LOCK_ID, lockManager.getLocksIds().get(0));

		lock.lock();
		try {
			Assert.assertEquals(LOCK_ID, lock.getId());
			Assert.assertEquals(1, lock.getReferencesCount());
			Assert.assertNotNull(lock.toString());
		} finally {
			lock.unlock();
		}

		Assert.assertNotNull(lockManager.getLocksIds());
		Assert.assertEquals(0, lockManager.getLocksIds().size());
	}

	/**
	 * {@link SafeIdBasedLockManager} test.
	 */
	@Test
	public void safeLockManagerTest() {
		final IdBasedLockManager<String> lockManager = new SafeIdBasedLockManager<String>();
		lockManagerTestExecutor(lockManager);

		// checking results
		Assert.assertEquals(0, lockManager.getLocksCount());
		Assert.assertNotNull(lockManager.getLocksIds());
		Assert.assertEquals(0, lockManager.getLocksIds().size());
		Assert.assertEquals(1, MAX_ACTIVE_CALLS.get()); // should not more then 1
		Assert.assertEquals(THREADS * CALLS_PER_THREAD, PROCESSED_CALLS.get());
	}

	/**
	 * {@link IdBasedLockManager} test executor.
	 * 
	 * @param lockManager
	 *            {@link IdBasedLockManager} instance
	 */
	private void lockManagerTestExecutor(final IdBasedLockManager<String> lockManager) {
		final CountDownLatch starter = new CountDownLatch(1);
		final CountDownLatch finisher = new CountDownLatch(THREADS);

		// preparing workers
		for (int i = 0; i < THREADS; i++)
			new Thread(new LockManagerWorker(lockManager, starter, finisher)).start();

		LOGGER.info("Executed for Manager[" + lockManager + "]");
		final long startTime = System.currentTimeMillis();
		// starting workers
		starter.countDown();

		// waiting for results
		try {
			finisher.await();
		} catch (InterruptedException e) {
			Assert.fail();
		}
		final long executionTime = System.currentTimeMillis() - startTime;
		LOGGER.info("Processed calls[" + PROCESSED_CALLS.get() + "] in[" + executionTime + "ms.], Max active calls[" + MAX_ACTIVE_CALLS.get() + "]");
	}

	/**
	 * Lock manager thread worker implementation.
	 * 
	 * @author Alexandr Bolbat
	 */
	private static class LockManagerWorker implements Runnable {

		/**
		 * {@link IdBasedLockManager} instance.
		 */
		private final IdBasedLockManager<String> lockManager;

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
		 * @param aLockManager
		 *            lock manager
		 * @param aStarter
		 *            starter latch
		 * @param aFinisher
		 *            finisher latch
		 */
		public LockManagerWorker(final IdBasedLockManager<String> aLockManager, final CountDownLatch aStarter, final CountDownLatch aFinisher) {
			this.lockManager = aLockManager;
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

			for (int i = 0; i < CALLS_PER_THREAD; i++) {
				IdBasedLock<String> lock = lockManager.obtainLock(LOCK_ID);
				lock.lock();
				int activeCount = ACTIVE_CALLS.incrementAndGet();
				if (activeCount > MAX_ACTIVE_CALLS.get())
					MAX_ACTIVE_CALLS.set(activeCount);

				ACTIVE_CALLS.decrementAndGet();
				lock.unlock();
				PROCESSED_CALLS.incrementAndGet();
			}

			finisher.countDown();
		}

	}

}
