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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		SampleBean that = (SampleBean) o;

		if (intV != that.intV) return false;
		if (bean != null ? !bean.equals(that.bean) : that.bean != null) return false;
		if (!Arrays.equals(beanA, that.beanA)) return false;
		if (beanL != null ? !beanL.equals(that.beanL) : that.beanL != null) return false;
		if (beanS != null ? !beanS.equals(that.beanS) : that.beanS != null) return false;
		if (!Arrays.equals(booleanA, that.booleanA)) return false;
		if (!Arrays.equals(intA, that.intA)) return false;
		if (shortL != null ? !shortL.equals(that.shortL) : that.shortL != null) return false;
		if (stringToIntM != null ? !stringToIntM.equals(that.stringToIntM) : that.stringToIntM != null) return false;
		if (stringV != null ? !stringV.equals(that.stringV) : that.stringV != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = intV;
		result = 31 * result + (stringV != null ? stringV.hashCode() : 0);
		result = 31 * result + (bean != null ? bean.hashCode() : 0);
		result = 31 * result + (beanA != null ? Arrays.hashCode(beanA) : 0);
		result = 31 * result + (beanL != null ? beanL.hashCode() : 0);
		result = 31 * result + (beanS != null ? beanS.hashCode() : 0);
		result = 31 * result + (intA != null ? Arrays.hashCode(intA) : 0);
		result = 31 * result + (booleanA != null ? Arrays.hashCode(booleanA) : 0);
		result = 31 * result + (shortL != null ? shortL.hashCode() : 0);
		result = 31 * result + (stringToIntM != null ? stringToIntM.hashCode() : 0);
		return result;
	}
}
