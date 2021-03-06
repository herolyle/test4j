package org.test4j.testng.spring.strategy.register;

import org.test4j.fortest.service.BeanClazzUserServiceImpl;
import org.test4j.fortest.service.UserService;
import org.test4j.module.spring.annotations.AutoBeanInject;
import org.test4j.module.spring.annotations.AutoBeanInject.BeanMap;
import org.test4j.module.spring.annotations.SpringBeanByName;
import org.test4j.module.spring.annotations.SpringContext;
import org.test4j.testng.Test4J;
import org.testng.annotations.Test;

@SpringContext({ "org/test4j/module/spring/testedbeans/xml/data-source.xml" })
@AutoBeanInject(maps = { @BeanMap(intf = "**.*Service", impl = "**.*ServiceImpl"),
        @BeanMap(intf = "**.*Dao", impl = "**.*DaoImpl") })
@Test(groups = "test4j")
public class SpringBeanRegisterTest_SpringBeanForValue extends Test4J {

    @SpringBeanByName(claz = BeanClazzUserServiceImpl.class)
    private UserService userService1;

    @SpringBeanByName("userService")
    private UserService userService2;

    public void getSpringBean() {
        String serviceName1 = userService1.getServiceName();
        want.string(serviceName1).isEqualTo("BeanClazzUserServiceImpl");

        String serviceName2 = userService2.getServiceName();
        want.string(serviceName2).isEqualTo("UserServiceImpl");
    }
}
