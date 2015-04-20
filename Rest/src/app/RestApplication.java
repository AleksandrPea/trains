import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Set;

/**
 * Created by Admin on 25.03.2015.
 */
@ApplicationPath("road")
public class RestApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        return null;
    }
}
