package net.bb.utils.beanmapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.bb.utils.beanmapper.BeanMapperConfiguration.ErrorStrategy;
import net.bb.utils.beanmapper.impl.MapBasedBeanMapper;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * {@link BeanMapper} test.
 * 
 * @author Alexandr Bolbat
 */
public class BeanMapperTest {

	/**
	 * Parameters for testing {@link MapBasedBeanMapper}.
	 */
	private static final Map<String, Object> PARAMS_MAP = new TreeMap<String, Object>();

	/**
	 * Initialization executed before each test.
	 */
	@Before
	public void before() {
		PARAMS_MAP.put("intV", "1");
		PARAMS_MAP.put("stringV", "string 1");
		PARAMS_MAP.put("intA", "1,2,3,4,5");
		PARAMS_MAP.put("booleanA", "true,true,false,false,false,true");

		PARAMS_MAP.put("bean.intV", 2);
		PARAMS_MAP.put("bean.stringV", "string 2");

		PARAMS_MAP.put("bean.bean.intV", 3);
		PARAMS_MAP.put("bean.bean.stringV", "string 3");

		PARAMS_MAP.put("shortL", "1,2,3");

		// bean's array
		PARAMS_MAP.put("beanA_1.intV", "11");
		PARAMS_MAP.put("beanA_1.stringV", "beanA 1 string");
		PARAMS_MAP.put("beanA_2.intV", 12);
		PARAMS_MAP.put("beanA_3.stringV", "beanA 3 string");

		// bean's collection - list
		PARAMS_MAP.put("beanL_1.intV", 1);
		PARAMS_MAP.put("beanL_1.beanL_1.intV", 11);
		PARAMS_MAP.put("beanL_1.beanL_2.stringV", "beanL_1.beanL_2 = stringV");
		PARAMS_MAP.put("beanL_2.intV", 2);
		PARAMS_MAP.put("beanL_2.stringV", "beanL_2 = stringV");
		PARAMS_MAP.put("beanL_2.beanS_1.intV", "21");
		PARAMS_MAP.put("beanL_2.beanS_1.stringV", "beanL_2.beanS_1 = stringV");

		// bean's collection - set
		PARAMS_MAP.put("beanS_1.intV", 1);
		PARAMS_MAP.put("beanS_1.stringV", "beanS_1 = stringV");
		PARAMS_MAP.put("beanS_2.intV", 2);

		// TODO: implement next functional
		PARAMS_MAP.put("stringToIntM", "key1=1,key2=2,key3=3");

		// System.out.println(PARAMS_MAP);
	}

	/**
	 * Initialization executed after each test.
	 */
	@After
	public void after() {
		PARAMS_MAP.clear();
	}

	/**
	 * Test for testing {@link Map} based mapping.
	 */
	@Test
	public void testMapBasedMapping() {
		// configuration for throwing exception on wrong parameter's in mapping's
		// by default mapper skipping mapping of fields if parameter's for this field's are wrong
		BeanMapperConfiguration cfg = new BeanMapperConfiguration();
		cfg.setErrorStrategy(ErrorStrategy.THROW_EXCEPTIONS);

		// mapping bean with predefined parameter's
		SampleBean bean = BeanMapper.map(PARAMS_MAP, SampleBean.class, null, cfg);
		// System.out.println(bean);

		// checking root level bean
		Assert.assertNotNull(bean);
		Assert.assertEquals(1, bean.getIntV());
		Assert.assertEquals("string 1", bean.getStringV());
		Assert.assertNotNull(bean.getBean());
		Assert.assertArrayEquals(new int[] { 1, 2, 3, 4, 5 }, bean.getIntA());
		Assert.assertArrayEquals(new Boolean[] { true, true, false, false, false, true }, bean.getBooleanA());

		// checking second level bean
		Assert.assertEquals(2, bean.getBean().getIntV());
		Assert.assertEquals("string 2", bean.getBean().getStringV());
		Assert.assertNotNull(bean.getBean().getBean());

		// checking third level bean
		Assert.assertEquals(3, bean.getBean().getBean().getIntV());
		Assert.assertEquals("string 3", bean.getBean().getBean().getStringV());

		// checking list of short's
		List<Short> expectedShortsList = new ArrayList<Short>();
		expectedShortsList.add((short) 1);
		expectedShortsList.add((short) 2);
		expectedShortsList.add((short) 3);
		Assert.assertEquals(expectedShortsList, bean.getShortL());

		// checking array of the bean's
		Assert.assertNotNull(bean.getBeanA());
		Assert.assertEquals(3, bean.getBeanA().length);
		// validation of first bean in array
		Assert.assertEquals(11, bean.getBeanA()[0].getIntV());
		Assert.assertEquals("beanA 1 string", bean.getBeanA()[0].getStringV());
		Assert.assertNull(bean.getBeanA()[0].getBean());
		// validation of second bean in array
		Assert.assertEquals(12, bean.getBeanA()[1].getIntV());
		Assert.assertNull(bean.getBeanA()[1].getStringV());
		Assert.assertNull(bean.getBeanA()[1].getBean());
		// validation of third bean in array
		Assert.assertEquals(0, bean.getBeanA()[2].getIntV());
		Assert.assertEquals("beanA 3 string", bean.getBeanA()[2].getStringV());
		Assert.assertNull(bean.getBeanA()[2].getBean());
	}

	/**
	 * Test for testing primitives (int, boolean, long, etc) and basic (String, Long, Char, etc) types mapping.
	 */
	@Test
	public void testBasicAndPrimitiveTypeMapping() {
		// configuration for throwing exception on wrong parameter's in mapping's
		// by default mapper skipping mapping of fields if parameter's for this field's are wrong
		BeanMapperConfiguration cfg = new BeanMapperConfiguration();
		cfg.setErrorStrategy(ErrorStrategy.THROW_EXCEPTIONS);

		// mapping bean with predefined parameter's
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intValue", 666);
		params.put("booleanValue", "true");
		params.put("floatValue", 333.789);

		Integer intValue = BeanMapper.map(params, Integer.class, "intValue", cfg);
		Assert.assertEquals(new Integer(666), intValue);

		int intPrimitiveValue = BeanMapper.map(params, int.class, "intValue", cfg);
		Assert.assertEquals(666, intPrimitiveValue);

		Boolean booleanValue = BeanMapper.map(params, Boolean.class, "booleanValue", cfg);
		Assert.assertEquals(new Boolean(true), booleanValue);

		Float floatValue = BeanMapper.map(params, Float.class, "floatValue", cfg);
		Assert.assertEquals(new Float(333.789), floatValue);
	}

}
