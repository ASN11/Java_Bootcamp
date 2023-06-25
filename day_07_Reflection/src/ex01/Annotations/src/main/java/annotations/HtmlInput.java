package annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.SOURCE;

@Target(ElementType.FIELD)
@Retention(SOURCE)
public @interface HtmlInput {
    String type();
    String name();
    String placeholder();
}
