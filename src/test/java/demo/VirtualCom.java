package demo;

import org.noear.solon.annotation.Managed;
import org.noear.solon.core.bean.LifecycleBean;
import org.noear.solon.core.util.ThreadsUtil;

/**
 *
 * @author noear 2026/1/5 created
 *
 */
@Managed
public class VirtualCom implements LifecycleBean {
    @Override
    public void start() throws Throwable {
        try {
            ThreadsUtil.newVirtualThreadPerTaskExecutor().submit(() -> {
                System.out.println("a: " + Thread.currentThread().getName());
            });

            ThreadsUtil.newVirtualThreadPerTaskExecutor("test-v1-").submit(() -> {
                System.out.println("b: " + Thread.currentThread().getName());
            });


            ThreadsUtil.newVirtualThreadFactory().newThread(() -> {
                System.out.println("c: " + Thread.currentThread().getName());
            }).start();

            ThreadsUtil.newVirtualThreadFactory("test-v2-").newThread(() -> {
                System.out.println("d: " + Thread.currentThread().getName());
            }).start();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        Thread.sleep(1000);
    }
}
