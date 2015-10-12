package net.bolbat.utils.concurrency.lock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.bolbat.utils.annotation.Audience;
import net.bolbat.utils.annotation.Concurrency;
import net.bolbat.utils.annotation.Stability;

/**
 * Safe {@link IdBasedLockManager} implementation.
 * 
 * @author Alexandr Bolbat
 * 
 * @param <T>
 *            locking id type
 */
@Audience.Public
@Stability.Stable
@Concurrency.ThreadSafe
public final class SafeIdBasedLockManager<T> implements IdBasedLockManager<T> {

	/**
	 * Generated SerialVersionUID.
	 */
	private static final long serialVersionUID = -7874697154191192576L;

	/**
	 * Locks storage.
	 */
	private final Map<T, IdBasedLock<T>> locks = new HashMap<>();

	/**
	 * Synchronization lock.
	 */
	private final Object synchLock = new Object();

	@Override
	public List<T> getLocksIds() {
		return new ArrayList<>(locks.keySet());
	}

	@Override
	public int getLocksCount() {
		return locks.size();
	}

	@Override
	public IdBasedLock<T> obtainLock(T id) {
		if (id == null)
			throw new IllegalArgumentException("id argument is null.");

		synchronized (synchLock) {
			IdBasedLock<T> lock = locks.get(id);
			if (lock == null) {
				lock = new IdBasedLock<>(id, this);
				locks.put(id, lock);
			}

			lock.increaseReferences();
			return lock;
		}
	}

	@Override
	public void releaseLock(final IdBasedLock<T> lock) {
		if (lock == null)
			throw new IllegalArgumentException("lock argument is null.");

		synchronized (synchLock) {
			if (lock.getReferencesCount() == 1)
				locks.remove(lock.getId());

			lock.decreaseReferences();
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
		builder.append(" [locks=").append(locks);
		builder.append("]");
		return builder.toString();
	}

}
