package net.bolbat.utils.concurrency.lock;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

import net.bolbat.utils.annotation.Audience;
import net.bolbat.utils.annotation.Concurrency;
import net.bolbat.utils.annotation.Stability;

/**
 * Id based lock implementation.
 * 
 * @author Alexandr Bolbat
 * 
 * @param <T>
 *            locking id type
 */
@Audience.Public
@Stability.Stable
@Concurrency.ThreadSafe
public class IdBasedLock<T> implements Serializable, AutoCloseable {

	/**
	 * Generated SerialVersionUID.
	 */
	private static final long serialVersionUID = 6545930245567508822L;

	/**
	 * {@link ReentrantLock} instance.
	 */
	private final ReentrantLock lock = new ReentrantLock();

	/**
	 * Lock references count.
	 */
	private final AtomicInteger referencesCount = new AtomicInteger(0);

	/**
	 * Lock manager.
	 */
	private final IdBasedLockManager<T> manager;

	/**
	 * Lock id.
	 */
	private final T id;

	/**
	 * Protected constructor.
	 * 
	 * @param aId
	 *            lock id
	 * @param aManager
	 *            lock manager
	 */
	protected IdBasedLock(final T aId, final IdBasedLockManager<T> aManager) {
		this.manager = aManager;
		this.id = aId;
	}

	/**
	 * Acquires the lock.<br>
	 * Check <code>ReentrantLock.lock()</code> for details.
	 * 
	 * @return {@link IdBasedLock}
	 */
	public IdBasedLock<T> lock() {
		lock.lock();
		return this;
	}

	/**
	 * Try to acquires the lock.<br>
	 * Check <code>ReentrantLock.tryLock()</code> for details.
	 *
	 * @return {@code true} if the lock was free and was acquired by the current thread, or the lock was already held by the current thread; and {@code false}
	 *         otherwise
	 */
	public boolean tryLock() {
		return lock.tryLock();
	}

	/**
	 * Try to acquires the lock.<br>
	 * Check <code>ReentrantLock.tryLock(timeout, unit)</code> for details.
	 * 
	 * @param timeout
	 *            the time to wait for the lock
	 * @param unit
	 *            the time unit of the timeout argument
	 * @return {@code true} if the lock was free and was acquired by the current thread or the lock was already held by the current thread and {@code false} if
	 *         the waiting time elapsed before the lock could be acquired
	 * @throws InterruptedException
	 *             if the current thread is interrupted
	 */
	public boolean tryLock(final long timeout, final TimeUnit unit) throws InterruptedException {
		return lock.tryLock(timeout, unit);
	}

	/**
	 * Attempts to release this lock.<br>
	 * Check <code>ReentrantLock.unlock()</code> for details.
	 * 
	 * @return {@link IdBasedLock}
	 */
	public IdBasedLock<T> unlock() {
		lock.unlock();
		manager.releaseLock(this);
		return this;
	}

	/**
	 * Queries if this lock is held by the current thread.<br>
	 * Check <code>ReentrantLock.isHeldByCurrentThread()</code> for details.
	 *
	 * @return {@code true} if current thread holds this lock and {@code false} otherwise
	 */
	public boolean isHeldByCurrentThread() {
		return lock.isHeldByCurrentThread();
	}

	/**
	 * Queries if this lock is held by any thread.<br>
	 * Check <code>ReentrantLock.isLocked()</code> for details.
	 *
	 * @return {@code true} if any thread holds this lock and {@code false} otherwise
	 */
	public boolean isLocked() {
		return lock.isLocked();
	}

	/**
	 * Queries whether any threads are waiting to acquire this lock.<br>
	 * Check <code>ReentrantLock.hasQueuedThreads()</code> for details.
	 *
	 * @return {@code true} if there may be other threads waiting to acquire the lock and {@code false} otherwise
	 */
	public boolean hasQueuedThreads() {
		return lock.hasQueuedThreads();
	}

	/**
	 * Queries whether the given thread is waiting to acquire this lock.<br>
	 * Check <code>ReentrantLock.hasQueuedThread(thread)</code> for details.
	 *
	 * @param thread
	 *            the thread
	 * @return {@code true} if the given thread is queued waiting for this lock and {@code false} otherwise
	 */
	public boolean hasQueuedThread(final Thread thread) {
		return lock.hasQueuedThread(thread);
	}

	/**
	 * Get lock references count.
	 * 
	 * @return <code>int</code>
	 */
	public int getReferencesCount() {
		return referencesCount.get();
	}

	/**
	 * Increase references count.
	 * 
	 * @return increased value as <code>int</code>
	 */
	protected int increaseReferences() {
		return referencesCount.incrementAndGet();
	}

	/**
	 * Decrease references count.
	 * 
	 * @return decreased value as <code>int</code>
	 */
	protected int decreaseReferences() {
		return referencesCount.decrementAndGet();
	}

	/**
	 * Get lock id.
	 * 
	 * @return <T>
	 */
	protected T getId() {
		return id;
	}

	@Override
	public void close() {
		unlock();
	}

	@Override
	public String toString() {
		return "(" + id + ", " + referencesCount + ")";
	}

}
