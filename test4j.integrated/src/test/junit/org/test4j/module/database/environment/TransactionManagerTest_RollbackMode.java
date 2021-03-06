package org.test4j.module.database.environment;

import java.sql.SQLException;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.test4j.database.table.ITable;
import org.test4j.database.table.TddUserTable;
import org.test4j.fortest.beans.User;
import org.test4j.fortest.service.UserService;
import org.test4j.junit.Test4J;
import org.test4j.module.database.annotations.Transactional;
import org.test4j.module.database.annotations.Transactional.TransactionMode;
import org.test4j.module.spring.annotations.SpringBeanByName;
import org.test4j.module.spring.annotations.SpringContext;

@SuppressWarnings("serial")
@SpringContext({ "org/test4j/module/spring/testedbeans/xml/beans.xml",
        "org/test4j/module/spring/testedbeans/xml/data-source.xml" })
public class TransactionManagerTest_RollbackMode extends Test4J {
    @SpringBeanByName
    private UserService userService;

    @BeforeClass
    public static void cleanTable() {
        db.table(ITable.t_tdd_user).clean().insert(new TddUserTable() {
            {
                this.put(IColumn.f_id, 1);
                this.put(IColumn.f_first_name, "jobs1");
            }
        }).commit();
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testTransactionRollback() throws SQLException {
        db.table(ITable.t_tdd_user).clean().insert(2, new TddUserTable() {
            {
                this.put(IColumn.f_id, 1, 2);
                this.put(IColumn.f_first_name, "darui.wu", "darui.wu2");
            }
        });

        List<User> users1 = this.userService.findAllUser();
        want.collection(users1).sizeEq(2);
        db.table(ITable.t_tdd_user).count().isEqualTo(2);

        this.userService.insertUser(new User("test"));

        List<User> users2 = this.userService.findAllUser();
        want.collection(users2).sizeEq(3);
        db.table(ITable.t_tdd_user).count().isEqualTo(3);
    }

    @AfterClass
    public static void checkTransactionRollback() {
        db.table(ITable.t_tdd_user).count().isEqualTo(1);
    }
}
