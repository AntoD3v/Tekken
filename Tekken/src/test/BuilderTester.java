import com.tekken.site.Response;
import com.tekken.site.Website;
import com.tekken.template.TemplatePage;
import com.tekken.template.build.Builder;

public class BuilderTester {

    public static void main(String[] args){

        Builder builder = new Builder("sql","<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<tekken>\n" +
                "    <backends>com.tekken.example.PageSQL</backends>\n" +
                "    <getter>/mysql</getter>\n" +
                "</tekken>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Title</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "Ceci est un test de mysql avec {valor} comme valor\n" +
                "</body>\n" +
                "</html>", System.currentTimeMillis());
        TemplatePage templatePage = builder.build();

        templatePage.getBackends().forEach(backend -> {
            System.out.println("backends -> "+backend.toString());
        });
        //System.out.println(builder.build().getHtmlCode());
    }

}
