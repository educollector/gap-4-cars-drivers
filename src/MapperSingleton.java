import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import org.javalite.activejdbc.Model;

/**
 * Created by olaskierbiszewska on 02.01.16.
 */
public class MapperSingleton {

    private static final ObjectMapper MAPPER = new ObjectMapper().setAnnotationIntrospector(new JacksonAnnotationIntrospector() {
        @Override
        public boolean hasIgnoreMarker(final AnnotatedMember m) {
            return m.getDeclaringClass() == Model.class || super.hasIgnoreMarker(m);
        }
    });

    public  static ObjectMapper get(){
        return MAPPER;
    }
}
