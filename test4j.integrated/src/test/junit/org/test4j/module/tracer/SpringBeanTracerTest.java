package org.test4j.module.tracer;

import java.lang.reflect.Modifier;

import org.junit.Test;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.test4j.junit.Test4J;
import org.test4j.module.tracer.spring.SpringBeanTracer;
import org.test4j.module.tracer.spring.TracerMethodRegexPointcut;

@SuppressWarnings({ "rawtypes" })
public class SpringBeanTracerTest extends Test4J {
    @Test
    public void testAround() {
        Hello target = new Hello();
        ProxyFactory pf = new ProxyFactory();
        pf.addAdvice(new SpringBeanTracer());
        pf.setTarget(target);
        Hello proxy = (Hello) pf.getProxy();
        proxy.greeting();
    }

    @Test(expected = RuntimeException.class)
    public void testAround_beanThrowException() {
        Hello target = new Hello();
        ProxyFactory pf = new ProxyFactory();
        pf.addAdvice(new SpringBeanTracer());
        pf.setTarget(target);
        Hello proxy = (Hello) pf.getProxy();
        proxy.greetingException();
    }

    @Test
    public void traceSpringBean() {
        Object o = traceSpringBean(new Hello());
        want.object(o).clazIs(Hello.class);
    }

    @Test
    public void traceSpringBean_NoNullConstructor() {
        Object o = traceSpringBean(new NoNullConstructor(""));
        want.object(o).clazIs(NoNullConstructor.class);
    }

    @Test
    public void testPointcut() {
        TracerMethodRegexPointcut pc = new TracerMethodRegexPointcut();

        pc.setPatterns(new String[] { ".*.*" });
        Advisor advisor = new DefaultPointcutAdvisor(pc, new SpringBeanTracer());
        ProxyFactory pf = new ProxyFactory();
        pf.setTarget(new Hello());
        pf.addAdvisor(advisor);
        Hello proxy = (Hello) pf.getProxy();
        proxy.greeting();
    }

    /**
     * 包装spring bean，对spring bean的调用情况进行跟踪记录
     * 
     * @param bean
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object traceSpringBean(Object bean) {
        if (bean == null) {
            return null;
        }
        Class clazz = bean.getClass();
        int modifier = clazz.getModifiers();
        if (Modifier.isFinal(modifier) || Modifier.isPublic(modifier) == false) {
            return bean;
        }

        try {
            clazz.getConstructor();// 获取无参构造函数
        } catch (Throwable e) {
            return bean;
        }

        ProxyFactory pf = new ProxyFactory();
        pf.addAdvice(new SpringBeanTracer());
        pf.setTarget(bean);
        return pf.getProxy();
    }
}
