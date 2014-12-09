package net.bolbat.utils.beanmapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.bolbat.utils.beanmapper.BeanMapperConfiguration.ErrorStrategy;

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
	private static final Map<String, Object> PARAMS_MAP = new TreeMap<>();

	/**
	 * Parameters for testing {@link MapBasedBeanMapper}.
	 */
	private static SampleBean bean;

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

		bean = new SampleBean();
		bean.setIntV(1);
		bean.setStringV("string 1");
		bean.setIntA(new int[] {1, 2, 3, 4, 5});
		bean.setBooleanA(new Boolean[] {true, true, false, false, false, true});

		SampleBean bean2 = new SampleBean();
		bean2.setIntV(2);
		bean2.setStringV("string 2");

		SampleBean bean3 = new SampleBean();
		bean3.setIntV(3);
		bean3.setStringV( "string 3");

		bean2.setBean(bean3);
		bean.setBean(bean2);
		bean.setShortL(Arrays.asList(new Short[] {1, 2, 3}));

		SampleBean arrayBean1 = new SampleBean();
		arrayBean1.setIntV(11);
		arrayBean1.setStringV("beanA 1 string");
		SampleBean arrayBean2= new SampleBean();
		arrayBean2.setIntV(12);
		SampleBean arrayBean3 = new SampleBean();
		arrayBean3.setStringV("beanA 3 string");
		bean.setBeanA(new SampleBean[]{arrayBean1, arrayBean2, arrayBean3});

		SampleBean listBean1 = new SampleBean();
		listBean1.setIntV(1);
		SampleBean listBeanList1 = new SampleBean();
		listBeanList1.setIntV(11);
		SampleBean listBeanList2 = new SampleBean();
		listBeanList2.setStringV("beanL_1.beanL_2 = stringV");
		SampleBean listBean2 = new SampleBean();
		listBean2.setIntV(2);
		listBean2.setStringV("beanL_2 = stringV");
		SampleBean listBeanSet1 = new SampleBean();
		listBeanSet1.setIntV(21);
		listBeanSet1.setStringV("beanL_2.beanS_1 = stringV");

		listBean1.setBeanL(Arrays.asList(listBeanList1, listBeanList2));
		listBean2.setBeanS(new LinkedHashSet<>(Arrays.asList(listBeanSet1)));
		bean.setBeanL(Arrays.asList(listBean1, listBean2));

		SampleBean setBean1 = new SampleBean();
		setBean1.setIntV(1);
		setBean1.setStringV("beanS_1 = stringV");
		SampleBean setBean2 = new SampleBean();
		setBean2.setIntV(2);
		bean.setBeanS(new LinkedHashSet<>(Arrays.asList(setBean1, setBean2)));

		Map<String, Integer> beanMap = new TreeMap<>();
		beanMap.put("key1", 1);
		beanMap.put("key2", 2);
		beanMap.put("key3", 3);
		bean.setStringToIntM(beanMap);
	}

	/**
	 * Initialization executed after each test.
	 */
	@After
	public void after() {
		PARAMS_MAP.clear();
		bean = null;
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
		List<Short> expectedShortsList = new ArrayList<>();
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

		// checking list of the bean's
		Assert.assertNotNull(bean.getBeanL());
		Assert.assertEquals(2, bean.getBeanL().size());
		// validation of first bean in array
		Assert.assertEquals(1, bean.getBeanL().get(0).getIntV());
		Assert.assertNotNull(bean.getBeanL().get(0).getBeanL());
		Assert.assertEquals(2, bean.getBeanL().get(0).getBeanL().size());
		Assert.assertEquals(11, bean.getBeanL().get(0).getBeanL().get(0).getIntV());
		Assert.assertEquals("beanL_1.beanL_2 = stringV", bean.getBeanL().get(0).getBeanL().get(1).getStringV());
		// validation of second bean in array
		Assert.assertEquals(2, bean.getBeanL().get(1).getIntV());
		Assert.assertEquals("beanL_2 = stringV", bean.getBeanL().get(1).getStringV());
		Assert.assertNotNull(bean.getBeanL().get(1).getBeanS());
		Assert.assertEquals(1, bean.getBeanL().get(1).getBeanS().size());
		SampleBean bL2S1 = bean.getBeanL().get(1).getBeanS().iterator().next();
		Assert.assertEquals(21, bL2S1.getIntV());
		Assert.assertEquals("beanL_2.beanS_1 = stringV", bL2S1.getStringV());

		// checking set of the bean's
		Assert.assertNotNull(bean.getBeanS());
		Assert.assertEquals(2, bean.getBeanS().size());
		// validation of second bean in set
		SampleBean[] bSArray = bean.getBeanS().toArray(new SampleBean[bean.getBeanS().size()]);
		if(bSArray[0].getIntV() == 1){
			Assert.assertEquals(2, bSArray[1].getIntV());
			Assert.assertEquals("beanS_1 = stringV", bSArray[0].getStringV());
		}

		if(bSArray[0].getIntV() == 2){
			Assert.assertEquals(1, bSArray[1].getIntV());
			Assert.assertEquals("beanS_1 = stringV", bSArray[1].getStringV());
		}

		// checking map of the bean's
		Assert.assertNotNull(bean.getStringToIntM());
		Assert.assertEquals(3, bean.getStringToIntM().entrySet().size());
		// validation of first bean in array
		Assert.assertTrue(bean.getStringToIntM().containsKey("key1"));
		Assert.assertTrue(bean.getStringToIntM().containsKey("key2"));
		Assert.assertTrue(bean.getStringToIntM().containsKey("key3"));
		Assert.assertTrue(bean.getStringToIntM().containsValue(1));
		Assert.assertTrue(bean.getStringToIntM().containsValue(2));
		Assert.assertTrue(bean.getStringToIntM().containsValue(3));
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
		Map<String, Object> params = new HashMap<>();
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


	/**
	 * Test for testing bean based mapping.
	 */
	@Test
	public void testBeanBasedMapping() {
		// configuration for throwing exception on wrong parameter's in mapping's
		// by default mapper skipping mapping of fields if parameter's for this field's are wrong
		BeanMapperConfiguration cfg = new BeanMapperConfiguration();
		cfg.setErrorStrategy(BeanMapperConfiguration.ErrorStrategy.THROW_EXCEPTIONS);

		Map<String, Object> propertyMap = BeanMapper.map(bean);
		//System.out.println(propertyMap.toString());
		Object mappedBack = BeanMapper.map(propertyMap, bean.getClass());

		SampleBean mappedBean = SampleBean.class.cast(mappedBack);
		Assert.assertEquals(bean.getIntV(), mappedBean.getIntV());
		Assert.assertEquals(bean.getStringV(), mappedBean.getStringV());
		Assert.assertEquals(bean.getShortL(), mappedBean.getShortL());
		Assert.assertEquals(bean.getBean(), mappedBean.getBean());
		Assert.assertTrue(Arrays.equals(bean.getBeanA(), mappedBean.getBeanA()));
		Assert.assertTrue(Arrays.equals(bean.getBooleanA(), mappedBean.getBooleanA()));
		Assert.assertEquals(bean.getBeanL(), mappedBean.getBeanL());
		Assert.assertEquals(bean.getBeanS(), mappedBean.getBeanS());

		if (bean.getStringToIntM() != null)
			for (Object entry : bean.getStringToIntM().entrySet())
				Assert.assertTrue(mappedBean.getStringToIntM().entrySet().contains(entry));

		//TODO: create more clearly for reading test
	}
}
