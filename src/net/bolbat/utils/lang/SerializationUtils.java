package net.bolbat.utils.lang;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.bolbat.utils.io.IOUtils;

/**
 * Serialization utility.
 * 
 * @author Alexandr Bolbat
 */
public final class SerializationUtils {

	/**
	 * Serialization buffer size.
	 */
	public static final int SERIALIZATION_BUFFER_SIZE = 512;

	/**
	 * Default constructor with preventing instantiations of this class.
	 */
	private SerializationUtils() {
		throw new IllegalAccessError("Shouldn't be instantiated.");
	}

	/**
	 * Clone {@link Serializable} <T> {@link Collection}.
	 * 
	 * @param toClone
	 *            {@link Collection} with {@link Serializable} <T> to clone, can't be <code>null</code>
	 * @return {@link List} of <T>
	 */
	public static <T extends Serializable> List<T> clone(final Collection<T> toClone) {
		if (toClone == null)
			throw new IllegalArgumentException("toClone argument is null.");

		final List<T> result = new ArrayList<>();
		for (final T obj : toClone)
			result.add(obj != null ? clone(obj) : obj);

		return result;
	}

	/**
	 * Clone {@link Serializable} <T>.
	 * 
	 * @param toClone
	 *            {@link Serializable} <T> to clone, can't be <code>null</code>
	 * @return <T>
	 */

	public static <T extends Serializable> T clone(final T toClone) {
		if (toClone == null)
			throw new IllegalArgumentException("toClone argument is null.");

		try {
			@SuppressWarnings("unchecked")
			final T result = (T) deserialize(serialize(toClone));
			return result;
		} catch (final ClassCastException e) {
			throw new SerializationException(e);
		}
	}

	/**
	 * Serialize object to <code>byte</code> array.
	 * 
	 * @param data
	 *            {@link Serializable} object data, can't be <code>null</code>
	 * @return <code>byte</code> array
	 */
	public static byte[] serialize(final Serializable data) {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream(SERIALIZATION_BUFFER_SIZE);
		serialize(data, baos);
		return baos.toByteArray();
	}

	/**
	 * Serialize object to {@link OutputStream}.
	 * 
	 * @param data
	 *            {@link Serializable}, can't be <code>null</code>
	 * @param output
	 *            {@link OutputStream}, can't be <code>null</code>
	 */
	public static void serialize(final Serializable data, final OutputStream output) {
		if (data == null)
			throw new IllegalArgumentException("data argument is null");
		if (output == null)
			throw new IllegalArgumentException("output argument is null");

		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(output);
			out.writeObject(data);
		} catch (final IOException e) {
			throw new SerializationException(e);
		} finally {
			IOUtils.close(out);
		}
	}

	/**
	 * Deserialize object from <code>byte</code> array.
	 * 
	 * @param rawData
	 *            <code>byte</code> array, can't be <code>null</code>
	 * @return {@link Object}
	 */
	public static Object deserialize(final byte[] rawData) {
		if (rawData == null)
			throw new IllegalArgumentException("rawData argument is null");

		final ByteArrayInputStream bais = new ByteArrayInputStream(rawData);
		return deserialize(bais);
	}

	/**
	 * Deserialize object from {@link InputStream}.
	 * 
	 * @param input
	 *            {@link InputStream}, can't be <code>null</code>
	 * @return {@link Object}
	 */
	public static Object deserialize(final InputStream input) {
		if (input == null)
			throw new IllegalArgumentException("input argument is null");

		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(input);
			return in.readObject();
		} catch (final ClassNotFoundException | IOException e) {
			throw new SerializationException(e);
		} finally {
			IOUtils.close(in);
		}
	}

}
