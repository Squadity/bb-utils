package net.bolbat.utils.beanmapper;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SampleBean implements Serializable {

	private static final long serialVersionUID = -3721032291192169253L;

	private int intV;
	private String stringV;
	private SampleBean bean;
	private SampleBean[] beanA;
	private List<SampleBean> beanL;
	private Set<SampleBean> beanS;
	private int[] intA;
	private Boolean[] booleanA;
	private List<Short> shortL;
	private Map<String, Integer> stringToIntM;

	public int getIntV() {
		return intV;
	}

	public void setIntV(int intV) {
		this.intV = intV;
	}

	public String getStringV() {
		return stringV;
	}

	public void setStringV(String stringV) {
		this.stringV = stringV;
	}

	public SampleBean getBean() {
		return bean;
	}

	public void setBean(SampleBean bean) {
		this.bean = bean;
	}

	public SampleBean[] getBeanA() {
		return beanA;
	}

	public void setBeanA(SampleBean[] beanA) {
		this.beanA = beanA;
	}

	public List<SampleBean> getBeanL() {
		return beanL;
	}

	public void setBeanL(List<SampleBean> beanL) {
		this.beanL = beanL;
	}

	public Set<SampleBean> getBeanS() {
		return beanS;
	}

	public void setBeanS(Set<SampleBean> beanS) {
		this.beanS = beanS;
	}

	public int[] getIntA() {
		return intA;
	}

	public void setIntA(int[] intA) {
		this.intA = intA;
	}

	public Boolean[] getBooleanA() {
		return booleanA;
	}

	public void setBooleanA(Boolean[] booleanA) {
		this.booleanA = booleanA;
	}

	public List<Short> getShortL() {
		return shortL;
	}

	public void setShortL(List<Short> shortL) {
		this.shortL = shortL;
	}

	public Map<String, Integer> getStringToIntM() {
		return stringToIntM;
	}

	public void setStringToIntM(Map<String, Integer> stringToIntM) {
		this.stringToIntM = stringToIntM;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SampleBean ");
		builder.append("[intV=" + intV);
		builder.append(", stringV=" + stringV);
		builder.append(", bean=" + bean);
		builder.append(", beanA=" + Arrays.toString(beanA));
		builder.append(", beanL=" + beanL);
		builder.append(", beanS=" + beanS);
		builder.append(", intA=" + Arrays.toString(intA));
		builder.append(", booleanA=" + Arrays.toString(booleanA));
		builder.append(", shortL=" + shortL);
		builder.append(", stringToIntM=" + stringToIntM + "]");
		return builder.toString();
	}

}