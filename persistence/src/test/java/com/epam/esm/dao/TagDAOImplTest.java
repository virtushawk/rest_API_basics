package com.epam.esm.dao;

import com.epam.esm.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("prod")
/*@Sql(scripts = {
        "classpath:create_schema.sql"
})*/
class TagDAOImplTest {

    @Autowired
    private TagDAO tagDAO;

    @Test
    void testSave() {
        System.out.println(tagDAO.findAll());
    }
}
