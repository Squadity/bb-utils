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
@Stability.Stable
@Concurrency.ThreadSafe
public final class UnsafeIdBasedLockManager<T> implements IdBasedLockManager<T> {

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
		final IdBasedLock<T> fromMap = locks.putIfAbsent(id, lock);
		if (fromMap != null) { // already exist lock
			fromMap.increaseReferences();
			if (fromMap.getReferencesCount() == 1) // if they already released from "locks" by other thread
				locks.put(id, fromMap); // if result of put is not null - then we are in trouble and this is why this implementation is unsafe

			return fromMap;
		}

		lock.increaseReferences();
		return lock;
	}

	@Override
	public void releaseLock(final IdBasedLock<T> lock) {
		checkArgument(lock != null, "lock argument is null");

		if (lock.getReferencesCount() == 1)
			locks.remove(lock.getId());

		lock.decreaseReferences();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
		builder.append(" [locks=").append(locks);
		builder.append("]");
		return builder.toString();
	}

}
