package copperwire.io.tnt;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) // on class level
public @interface TesterInfo {

	public enum Priority {
		ALL, GET_ORDER, INSPECT_AND_PACK, RFQ, BOOK, PICKUP_AND_SHIP, FEEDBACK, INVOICE

	}

	Priority priority() default Priority.ALL;

	//String[] tags() default "";

	//String createdBy() default "Abhishek";

	String lastModified() default "25/11/2020";

}