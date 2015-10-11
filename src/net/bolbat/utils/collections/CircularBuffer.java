package net.bolbat.utils.collections;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import net.bolbat.utils.lang.ToStringUtils;

/**
 * Circular buffer implementation for situations when we need to obtain each time next value from the original collection in circular way.<br>
 * This implementation is thread safe.
 * 
 * @author Alexandr Bolbat
 *
 * @param <E>
 *            elements type
 */
public class CircularBuffer<E> implements Serializable {

	/**
	 * Generated SerialVersionUID.
	 */
	private static final long serialVersionUID = 7432470971599446893L;

	/**
	 * Last used index.
	 */
	private final AtomicInteger lastIndex = new AtomicInteger();

	/**
	 * Elements array.
	 */
	private final E[] elements;

	/**
	 * Elements size.
	 */
	private final int size;

	/**
	 * Is elements array are empty.
	 */
	private final boolean empty;

	/**
	 * Default constructor.
	 * 
	 * @param aElements
	 *            elements array
	 */
	@SafeVarargs
	private CircularBuffer(final E... aElements) {
		if (aElements == null)
			throw new IllegalArgumentException("elements argument is null");

		this.elements = aElements;
		this.size = aElements.length;
		this.empty = aElements.length == 0;
	}

	/**
	 * Create {@link CircularBuffer} from {@link Collection}.
	 * 
	 * @param aElements
	 *            elements
	 * @return {@link CircularBuffer}
	 */
	public static <E> CircularBuffer<E> of(final Collection<E> aElements) {
		@SuppressWarnings("unchecked")
		final E[] elements = aElements == null ? null : (E[]) aElements.toArray();
		return new CircularBuffer<>(elements);
	}

	/**
	 * Create {@link CircularBuffer} from elements.
	 * 
	 * @param aElements
	 *            elements
	 * @return {@link CircularBuffer}
	 */
	@SafeVarargs
	public static <E> CircularBuffer<E> of(final E... aElements) {
		return new CircularBuffer<>(aElements == null ? null : Arrays.copyOf(aElements, aElements.length));
	}

	/**
	 * Get next element.
	 * 
	 * @return next element or <code>null</code> if element is <code>null</code> or elements array is empty
	 */
	public E get() {
		return empty ? null : elements[Math.abs(lastIndex.incrementAndGet()) % size];
	}

	/**
	 * Get element by index.<br>
	 * {@link ArrayIndexOutOfBoundsException} will be thrown if elements array is empty or index out of elements array bounds.
	 * 
	 * @param index
	 *            element index
	 * @return element
	 */
	public E get(final int index) {
		if (empty || index < 0 || index >= size)
			throw new ArrayIndexOutOfBoundsException(index);

		return elements[index];
	}

	/**
	 * Get elements array.
	 * 
	 * @return elements array copy or <code>null</code> if elements array is empty
	 */
	public E[] getAll() {
		return empty ? null : Arrays.copyOf(elements, size);
	}

	/**
	 * Get last used index.
	 * 
	 * @return <code>int</code>
	 */
	public int lastIndex() {
		return lastIndex.get() % size;
	}

	/**
	 * Is elements array are empty.
	 * 
	 * @return <code>true</code> if empty or <code>false</code>
	 */
	public boolean isEmpty() {
		return empty;
	}

	/**
	 * Get elements array size.
	 * 
	 * @return <code>int</code>
	 */
	public int size() {
		return size;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
		builder.append(" [elements=").append(ToStringUtils.toString(elements));
		builder.append("]");
		return builder.toString();
	}

}
