package demo;

public  class Super {

	protected static final Object cccc = new Object();
	
	private Integer age;
	private String name;
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Son1 convertToSon1() {
		Son1 son = (Son1)this;
		return son;
	}
	
	
}
