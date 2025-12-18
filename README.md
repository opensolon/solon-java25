# solon-java25

此仓库主要为 solon 适配 java25 部分新特性（不方便用反射适配的）


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
