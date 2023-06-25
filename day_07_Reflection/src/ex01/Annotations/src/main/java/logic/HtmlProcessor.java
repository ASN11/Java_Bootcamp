package logic;

import annotations.HtmlForm;
import annotations.HtmlInput;

import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Set;

@AutoService(Processor.class)
@SupportedAnnotationTypes("annotations.*")
public class HtmlProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(HtmlForm.class)) {
            processHtmlForm((TypeElement) element);
        }
        return true;
    }

    private void processHtmlForm(TypeElement typeElement) {
        HtmlForm htmlFormAnnotation = typeElement.getAnnotation(HtmlForm.class);
        String fileName = htmlFormAnnotation.fileName();
        String action = htmlFormAnnotation.action();
        String method = htmlFormAnnotation.method();

        StringBuilder formBuilder = new StringBuilder();
        formBuilder.append("<form action=\"").append(action).append("\" method=\"").append(method).append("\">\n");

        for (Element element : typeElement.getEnclosedElements()) {
            HtmlInput htmlInputAnnotation = element.getAnnotation(HtmlInput.class);
            if (htmlInputAnnotation != null) {
                String inputType = htmlInputAnnotation.type();
                String inputName = htmlInputAnnotation.name();
                String placeholder = htmlInputAnnotation.placeholder();

                formBuilder.append("\t<input type=\"").append(inputType).append("\" name=\"").append(inputName).append("\" placeholder=\"").append(placeholder).append("\">\n");
            }
        }

        formBuilder.append("\t<input type=\"submit\" value=\"Send\">\n");
        formBuilder.append("</form>");

//         Generate the HTML file
        try {
            FileObject fileObject = processingEnv.getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", fileName);
            try (OutputStream outputStream = fileObject.openOutputStream();
                 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8))) {
                writer.write(formBuilder.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
