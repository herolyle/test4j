package org.test4j.testng.jmockit;

import mockit.Mocked;

import org.test4j.fortest.formock.SomeInterface;
import org.test4j.fortest.formock.SpringBeanService;
import org.test4j.module.spring.annotations.SpringBeanByName;
import org.test4j.module.spring.annotations.SpringBeanFrom;
import org.test4j.module.spring.annotations.SpringContext;
import org.test4j.testng.Test4J;
import org.testng.annotations.Test;

@SpringContext({ "org/test4j/module/spring/testedbeans/xml/mockbeans-withdependency.xml" })
@Test(groups = { "test4j", "mock-demo" })
public class MockBeanTest_MissingDependency extends Test4J {
    @SpringBeanByName
    private SpringBeanService springBeanService1;

    @SpringBeanFrom
    @Mocked
    protected SomeInterface   dependency2;

    public void testDependency_MockBean() {
        want.object(springBeanService1.getDependency1()).notNull();
        want.object(springBeanService1.getDependency2()).notNull();
    }

    @SpringBeanFrom
    @Mocked
    private SpringBeanService springBeanService2;

    @Test
    public void testDependency_UnMockBean() {
        new Expectations() {
            {
                when(springBeanService2.getDependency1()).thenReturn(dependency2);
                when(springBeanService2.getDependency2()).thenReturn(dependency2);

            }
        };
        want.object(springBeanService2.getDependency1()).notNull();
        want.object(springBeanService2.getDependency2()).notNull();
    }

    /**
     * 重复运行一次，看看Mock字段是否会发生测试方法间干扰
     */
    @Test(dependsOnMethods = "testDependency_UnMockBean")
    public void testDependency_UnMockBean2() {
        new Expectations() {
            {
                when(springBeanService2.getDependency1()).thenReturn(dependency2);
                when(springBeanService2.getDependency2()).thenReturn(dependency2);
            }
        };
        want.object(springBeanService2.getDependency1()).notNull();
        want.object(springBeanService2.getDependency2()).notNull();
    }
}
