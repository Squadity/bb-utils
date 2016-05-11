package net.bolbat.utils.concurrency.lock;

import static net.bolbat.utils.lang.Validations.checkArgument;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.bolbat.utils.annotation.Audience;
import net.bolbat.utils.annotation.Concurrency;
import net.bolbat.utils.annotation.Stability;

/**
 * Unsafe {@link IdBasedLockManager} implementation.
 * 
 * @author Alexandr Bolbat
 * 
 * @param <T>
 *            locking id type
 */
@Audience.Public
@Stability.Evolving
@Concurrency.ThreadSafe
public final class ConcurrentIdBasedLockManager<T> implements IdBasedLockManager<T> {

	/**
	 * Generated SerialVersionUID.
	 */
	private static final long serialVersionUID = 674166364226128518L;

	/**
	 * Locks storage.
	 */
	private final ConcurrentMap<T, IdBasedLock<T>> locks = new ConcurrentHashMap<>();

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
		checkArgument(id != null, "id argument is null");

		final IdBasedLock<T> lock = new IdBasedLock<>(id, this);
		lock.increaseReferences();

		final IdBasedLock<T> fromMap = locks.putIfAbsent(id, lock);
		if (fromMap == null)
			return lock;

		synchronized (fromMap) {
			if (fromMap.increaseReferences() == 1) { // if they already released from "locks" by other thread
				IdBasedLock<T> tmp = null;
				do {
					tmp = locks.putIfAbsent(id, fromMap);
				} while (tmp == null || tmp != null && tmp != fromMap);
			}
		}

		return fromMap;
	}

	@Override
	public void releaseLock(final IdBasedLock<T> lock) {
		checkArgument(lock != null, "lock argument is null");

		synchronized (lock) {
			if (lock.decreaseReferences() == 0)
				locks.remove(lock.getId());
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
