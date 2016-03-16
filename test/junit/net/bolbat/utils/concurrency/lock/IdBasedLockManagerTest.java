package net.bolbat.utils.concurrency.lock;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link IdBasedLockManager} test.
 * 
 * @author Alexandr Bolbat
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public final class IdBasedLockManagerTest {

	/**
	 * {@link Logger} instance.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(IdBasedLockManagerTest.class);

	/**
	 * Testing threads amount.
	 */
	private static final int THREADS = 5;

	/**
	 * Thread test calls amount.
	 */
	private static final int CALLS_PER_THREAD = 100000;

	/**
	 * Testing lock id.
	 */
	private static final String LOCK_ID = "qwe";

	/**
	 * Testing locks ids.
	 */
	private static final String[] LOCKS_IDS = new String[] { "1", "2", "3", "4", "5" };

	/**
	 * Random generator.
	 */
	private static final Random RND = new Random();

	/**
	 * Active tests calls amount.
	 */
	private static final AtomicInteger ACTIVE_CALLS = new AtomicInteger(0);

	/**
	 * Maximum active tests calls amount.
	 */
	private static final AtomicInteger ACTIVE_MAX_CALLS = new AtomicInteger(0);

	/**
	 * Processed per id tests calls amount.
	 */
	private static final ConcurrentMap<String, AtomicInteger> PROCESSED_PER_ID_CALLS = new ConcurrentHashMap<>();

	/**
	 * Active per id tests calls amount.
	 */
	private static final ConcurrentMap<String, AtomicInteger> ACTIVE_PER_ID_CALLS = new ConcurrentHashMap<>();

	/**
	 * Maximum active per id tests calls amount.
	 */
	private static final ConcurrentMap<String, AtomicInteger> ACTIVE_MAX_PER_ID_CALLS = new ConcurrentHashMap<>();

	/**
	 * Unsafe per id tests calls amount.
	 */
	private static final ConcurrentMap<String, AtomicInteger> UNSAFE_PER_ID_CALLS = new ConcurrentHashMap<>();

	/**
	 * Clearing context for each test.
	 */
	@Before
	public void beforeTest() {
		ACTIVE_CALLS.set(0);
		ACTIVE_MAX_CALLS.set(0);

		PROCESSED_PER_ID_CALLS.clear();
		PROCESSED_PER_ID_CALLS.put(LOCK_ID, new AtomicInteger(0));
		for (final String id : LOCKS_IDS)
			PROCESSED_PER_ID_CALLS.put(id, new AtomicInteger(0));

		ACTIVE_PER_ID_CALLS.clear();
		ACTIVE_PER_ID_CALLS.put(LOCK_ID, new AtomicInteger(0));
		for (final String id : LOCKS_IDS)
			ACTIVE_PER_ID_CALLS.put(id, new AtomicInteger(0));

		ACTIVE_MAX_PER_ID_CALLS.clear();
		ACTIVE_MAX_PER_ID_CALLS.put(LOCK_ID, new AtomicInteger(0));
		for (final String id : LOCKS_IDS)
			ACTIVE_MAX_PER_ID_CALLS.put(id, new AtomicInteger(0));

		UNSAFE_PER_ID_CALLS.clear();
		UNSAFE_PER_ID_CALLS.put(LOCK_ID, new AtomicInteger(0));
		for (final String id : LOCKS_IDS)
			UNSAFE_PER_ID_CALLS.put(id, new AtomicInteger(0));
	}

	/**
	 * Error cases test.
	 */
	@Test
	public void errorCasesTest() {
		// safe lock manager testing
		IdBasedLockManager<String> lockManager = new SafeIdBasedLockManager<>();
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
	 * Basic {@link SafeIdBasedLockManager} test.
	 */
	@Test
	public void basicSafeLockManagerTest() {
		IdBasedLockManager<String> lockManager = new SafeIdBasedLockManager<>();
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
	 * Basic {@link ConcurrentIdBasedLockManager} test.
	 */
	@Test
	public void basicConcurrentLockManagerTest() {
		IdBasedLockManager<String> lockManager = new ConcurrentIdBasedLockManager<>();
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
	 * Multi threaded {@link SafeIdBasedLockManager} test.
	 */
	@Test
	public void multithreadedSafeLockManagerTest() {
		final IdBasedLockManager<String> lockManager = new SafeIdBasedLockManager<>();
		lockManagerTestExecutor(lockManager, LOCKS_IDS);

		// checking results
		Assert.assertEquals(0, lockManager.getLocksCount());
		Assert.assertNotNull(lockManager.getLocksIds());
		Assert.assertEquals(0, lockManager.getLocksIds().size());

		int processedTotal = 0;
		int unsafeTotal = 0;
		for (final String id : LOCKS_IDS) {
			final int processed = PROCESSED_PER_ID_CALLS.get(id).get();
			processedTotal += processed;
			final int unsafe = UNSAFE_PER_ID_CALLS.get(id).get();
			unsafeTotal += unsafe;

			Assert.assertEquals(1, ACTIVE_MAX_PER_ID_CALLS.get(id).get()); // should be not more then 1
		}

		Assert.assertEquals(THREADS * CALLS_PER_THREAD, processedTotal);
		Assert.assertTrue(THREADS >= ACTIVE_MAX_CALLS.get()); // should be not more then threads amount
		Assert.assertEquals(0, unsafeTotal);
	}

	/**
	 * Multi threaded {@link ConcurrentIdBasedLockManager} test.
	 */
	@Test
	public void multithreadedConcurrentLockManagerTest() {
		final IdBasedLockManager<String> lockManager = new ConcurrentIdBasedLockManager<>();
		lockManagerTestExecutor(lockManager, LOCKS_IDS);

		// checking results
		Assert.assertEquals(0, lockManager.getLocksCount());
		Assert.assertNotNull(lockManager.getLocksIds());
		Assert.assertEquals(0, lockManager.getLocksIds().size());

		int processedTotal = 0;
		int unsafeTotal = 0;
		for (final String id : LOCKS_IDS) {
			final int processed = PROCESSED_PER_ID_CALLS.get(id).get();
			processedTotal += processed;
			final int unsafe = UNSAFE_PER_ID_CALLS.get(id).get();
			unsafeTotal += unsafe;

			Assert.assertEquals(1, ACTIVE_MAX_PER_ID_CALLS.get(id).get()); // should be not more then 1
		}

		Assert.assertEquals(THREADS * CALLS_PER_THREAD, processedTotal);
		Assert.assertEquals(THREADS, ACTIVE_MAX_CALLS.get()); // should be equal to threads amount
		Assert.assertEquals(0, unsafeTotal);
	}

	/**
	 * {@link IdBasedLockManager} test executor.
	 * 
	 * @param lockManager
	 *            {@link IdBasedLockManager} instance
	 */
	private void lockManagerTestExecutor(final IdBasedLockManager<String> lockManager, final String[] ids) {
		final CountDownLatch starter = new CountDownLatch(1);
		final CountDownLatch finisher = new CountDownLatch(THREADS);

		// preparing workers
		for (int i = 0; i < THREADS; i++)
			new Thread(new LockManagerWorker(lockManager, starter, finisher, ids)).start();

		LOGGER.info("Executed for Manager[" + lockManager + "]");
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
		int processedTotal = 0;
		int unsafeTotal = 0;
		for (final String id : ids) {
			final int processed = PROCESSED_PER_ID_CALLS.get(id).get();
			processedTotal += processed;
			final int maxActive = ACTIVE_MAX_PER_ID_CALLS.get(id).get();
			final int unsafe = UNSAFE_PER_ID_CALLS.get(id).get();
			unsafeTotal += unsafe;
			LOGGER.info("    " + "Id[" + id + "] processed[" + processed + "], Max-Active[" + maxActive + "], Unsafe[" + unsafe + "]");
		}

		LOGGER.info("Processed[" + processedTotal + "] in[" + executionTime + "ms.], Max-Active[" + ACTIVE_MAX_CALLS.get() + "], Unsafe[" + unsafeTotal + "]");
	}

	/**
	 * Get random value.
	 * 
	 * @param ids
	 *            values
	 * @return {@link String}
	 */
	private static String getRandom(final String[] ids) {
		return ids[RND.nextInt(ids.length)];
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
		 * Ids.
		 */
		private final String[] ids;

		/**
		 * Default constructor.
		 * 
		 * @param aLockManager
		 *            lock manager
		 * @param aStarter
		 *            starter latch
		 * @param aFinisher
		 *            finisher latch
		 * @param aIds
		 *            ids
		 */
		public LockManagerWorker(final IdBasedLockManager<String> aLockManager, final CountDownLatch aStarter, final CountDownLatch aFinisher,
				final String[] aIds) {
			this.lockManager = aLockManager;
			this.starter = aStarter;
			this.finisher = aFinisher;
			this.ids = aIds;
		}

		@Override
		public void run() {
			try {
				starter.await();
			} catch (final InterruptedException e) {
				Assert.fail();
			}

			for (int i = 0; i < CALLS_PER_THREAD; i++) {
				final String id = getRandom(ids);
				final IdBasedLock<String> lock = lockManager.obtainLock(id);
				lock.lock();

				int activeCount = ACTIVE_CALLS.incrementAndGet();
				if (activeCount > ACTIVE_MAX_CALLS.get())
					ACTIVE_MAX_CALLS.set(activeCount);

				int activePerIdCount = ACTIVE_PER_ID_CALLS.get(id).incrementAndGet();
				if (activePerIdCount > ACTIVE_MAX_PER_ID_CALLS.get(id).get())
					ACTIVE_MAX_PER_ID_CALLS.get(id).set(activePerIdCount);

				if (activePerIdCount > 1)
					UNSAFE_PER_ID_CALLS.get(id).incrementAndGet();

				ACTIVE_PER_ID_CALLS.get(id).decrementAndGet();

				ACTIVE_CALLS.decrementAndGet();

				lock.unlock();

				PROCESSED_PER_ID_CALLS.get(id).incrementAndGet();
			}

			finisher.countDown();
		}

	}

}
