package net.bolbat.utils.collections;

import static net.bolbat.utils.lang.Validations.checkArgument;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import net.bolbat.utils.annotation.Audience;
import net.bolbat.utils.annotation.Concurrency;
import net.bolbat.utils.annotation.Stability;
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
	 * Elements list.
	 */
	private final List<E> elements;

	/**
	 * Default constructor.
	 * 
	 * @param aElements
	 *            elements list
	 */
	private CircularBuffer(final List<E> aElements) {
		this.elements = aElements != null ? aElements : new ArrayList<>();
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

		return of(Arrays.asList(aElements));
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

		return new CircularBuffer<>(new ArrayList<>(aElements));
	}

	/**
	 * Get next element.
	 * 
	 * @return next element or <code>null</code> if element is <code>null</code> or elements array is empty
	 */
	public E get() {
		return elements.isEmpty() ? null : elements.get(Math.abs(lastIndex.incrementAndGet()) % elements.size());
	}

	/**
	 * Get element by index.<br>
	 * {@link IndexOutOfBoundsException} will be thrown if elements list is empty or index out of elements list bounds.
	 * 
	 * @param index
	 *            element index
	 * @return element
	 */
	public E get(final int index) {
		if (elements.isEmpty() || index < 0 || index >= elements.size())
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + elements.size());

		return elements.get(index);
	}

	/**
	 * Get elements list (unmodifiable).
	 * 
	 * @return unmodifiable elements list
	 */
	public List<E> getAll() {
		return Collections.unmodifiableList(elements);
	}

	/**
	 * Get last used index.
	 * 
	 * @return <code>int</code>
	 */
	public int lastIndex() {
		return Math.abs(lastIndex.get()) % elements.size();
	}

	/**
	 * Is this buffer is empty.
	 * 
	 * @return <code>true</code> if empty or <code>false</code>
	 */
	public boolean isEmpty() {
		return elements.isEmpty();
	}

	/**
	 * Get this buffer size.
	 * 
	 * @return <code>int</code>
	 */
	public int size() {
		return elements.size();
	}

	/**
	 * Check is this buffer contains given element.
	 * 
	 * @param element
	 *            element
	 * @return <code>true</code> if contains or <code>false</code>
	 */
	public boolean contains(final E element) {
		return elements.isEmpty() ? false : elements.contains(element);
	}

	/**
	 * Create this buffer copy with added given element to the end.
	 * 
	 * @param element
	 *            element to add
	 * @return new buffer instance
	 */
	public CircularBuffer<E> add(final E element) {
		final List<E> result = new ArrayList<>(elements);
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
		if (elements.isEmpty())
			return new CircularBuffer<>(null);

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
		return first != null && first.equals(second) || first == null && second == null;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
		builder.append(" [elements=").append(ToStringUtils.toString(elements));
		builder.append("]");
		return builder.toString();
	}

}
