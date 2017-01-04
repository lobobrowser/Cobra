package org.cobraparser.html.js;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Ensures that a method is not treated as a getter / setter
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface NotGetterSetter {

}
