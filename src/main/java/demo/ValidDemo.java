package demo;

import javax.validation.Validation;

public class ValidDemo {

	public static void main(String[] args) {
		
		Validation.buildDefaultValidatorFactory().getValidator();
	}
}
