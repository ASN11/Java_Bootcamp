package annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.SOURCE;

@Target(ElementType.TYPE)
@Retention(SOURCE)
public @interface HtmlForm {
    String fileName();
    String action();
    String method();

}
