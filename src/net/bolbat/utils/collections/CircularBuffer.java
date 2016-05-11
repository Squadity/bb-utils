package net.bolbat.utils.collections;

import static net.bolbat.utils.lang.Validations.checkArgument;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import net.bolbat.utils.annotation.Audience;
import net.bolbat.utils.annotation.Concurrency;
import net.bolbat.utils.annotation.Stability;
import net.bolbat.utils.lang.CastUtils;
import net.bolbat.utils.lang.ToStringUtils;

/**
 * Circular buffer implementation for situations when we need to obtain each time next value from the original collection in circular way.<br>
 * This implementation is thread safe and immutable.
 * 
 * @author Alexandr Bolbat
 *
 * @param <E>
 *            elements type
 */
@Audience.Public
@Stability.Evolving
@Concurrency.ThreadSafe
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
	 * Elements array.<br>
	 * It will be always {@link Object} array instance.
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
	@SuppressWarnings("unchecked")
	private CircularBuffer(final Object[] aElements) {
		this.elements = (E[]) aElements;
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
		checkArgument(aElements != null, "aElements argument is null");

		final E[] array = CastUtils.cast(aElements.toArray());
		return of(array);
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
		checkArgument(aElements != null, "aElements argument is null");

		return new CircularBuffer<>(Arrays.copyOf(aElements, aElements.length, Object[].class));
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
		return Math.abs(lastIndex.get()) % size;
	}

	/**
	 * Is this buffer is empty.
	 * 
	 * @return <code>true</code> if empty or <code>false</code>
	 */
	public boolean isEmpty() {
		return empty;
	}

	/**
	 * Get this buffer size.
	 * 
	 * @return <code>int</code>
	 */
	public int size() {
		return size;
	}

	/**
	 * Check is this buffer contains given element.
	 * 
	 * @param element
	 *            element
	 * @return <code>true</code> if contains or <code>false</code>
	 */
	public boolean contains(final E element) {
		if (empty)
			return false;

		for (final E e : elements)
			if (isEquals(element, e))
				return true;

		return false;
	}

	/**
	 * Create this buffer copy with added given element to the end.
	 * 
	 * @param element
	 *            element to add
	 * @return new buffer instance
	 */
	public CircularBuffer<E> add(final E element) {
		final List<E> result = new ArrayList<>(Arrays.asList(elements));
		result.add(element);
		return of(result);
	}

	/**
	 * Create this buffer copy with removed given element (all occurrences in original buffer).
	 * 
	 * @param element
	 *            element to remove
	 * @return new buffer instance
	 */
	public CircularBuffer<E> remove(final E element) {
		if (empty)
			return new CircularBuffer<>(new Object[0]);

		final List<E> result = new ArrayList<>();
		for (final E e : elements)
			if (!isEquals(element, e))
				result.add(e);

		return of(result);
	}

	/**
	 * Check is first element equals to second.
	 * 
	 * @param first
	 *            first
	 * @param second
	 *            second
	 * @return <code>true</code> if equals or <code>false</code>
	 */
	private boolean isEquals(final E first, final E second) {
		return (first != null && first.equals(second)) || (first == null && second == null);
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
		builder.append(" [elements=").append(ToStringUtils.toString(elements));
		builder.append("]");
		return builder.toString();
	}

}
