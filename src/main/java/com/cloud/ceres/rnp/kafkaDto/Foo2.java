
package com.cloud.ceres.rnp.kafkaDto;

public class Foo2 {

	private String foo;

	public Foo2() {
		super();
	}

	public Foo2(String foo) {
		this.foo = foo;
	}

	public String getFoo() {
		return this.foo;
	}

	public void setFoo(String foo) {
		this.foo = foo;
	}

	@Override
	public String toString() {
		return "Foo2 [foo=" + this.foo + "]";
	}

}