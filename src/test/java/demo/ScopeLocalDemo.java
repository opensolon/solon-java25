package demo;

import org.noear.solon.Solon;
import org.noear.solon.util.ScopeLocalJdk25;

/**
 *
 * @author noear 2025/12/18 created
 *
 */
public class ScopeLocalDemo {
    public static void main(String[] args) {
        Solon.start(ScopeLocalDemo.class, args, app->{
           app.factories().scopeLocalFactory(ScopeLocalJdk25::new);
        });
    }
}
