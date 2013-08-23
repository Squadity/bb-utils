package net.bolbat.utils.concurrency.lock;

import java.io.Serializable;
import java.util.List;

/**
 * {@link IdBasedLock} manager interface.
 * 
 * @author Alexandr Bolbat
 * 
 * @param <T>
 *            locking id type
 */
public interface IdBasedLockManager<T> extends Serializable {

	/**
	 * Get exist locks id's.
	 * 
	 * @return {@link List} of <T>
	 */
	List<T> getLocksIds();

	/**
	 * Get exist locks count.
	 * 
	 * @return <code>int</code>
	 */
	int getLocksCount();

	/**
	 * Obtain {@link IdBasedLock} from manager.
	 * 
	 * @param id
	 *            lock id, can't be <code>null</code>
	 * @return {@link IdBasedLock} instance
	 */
	IdBasedLock<T> obtainLock(T id);

	/**
	 * Release {@link IdBasedLock}.
	 * 
	 * @param lock
	 *            lock, can't be <code>null</code>
	 */
	void releaseLock(IdBasedLock<T> lock);

}
