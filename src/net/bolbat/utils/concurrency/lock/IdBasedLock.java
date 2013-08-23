package net.bolbat.utils.concurrency.lock;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Id based lock implementation.
 * 
 * @author Alexandr Bolbat
 * 
 * @param <T>
 *            locking id type
 */
public class IdBasedLock<T> implements Serializable {

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
	 * Lock.
	 */
	public void lock() {
		lock.lock();
	}

	/**
	 * Unlock.
	 */
	public void unlock() {
		lock.unlock();
		manager.releaseLock(this);
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
	 */
	protected void increaseReferences() {
		referencesCount.incrementAndGet();
	}

	/**
	 * Decrease references count.
	 */
	protected void decreaseReferences() {
		referencesCount.decrementAndGet();
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
	public String toString() {
		return "(" + id + ", " + referencesCount + ")";
	}

}
