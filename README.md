# solon-for-jdk25


solon-for-jdk25


```java
public class App {
    public static void main(String[] args) {
        Solon.start(App.class, args, app->{
            //注册工厂
            app.factories().scopeLocalFactory(ScopeLocalJdk25::new);
        });
    }
}
```
