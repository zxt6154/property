package com.ziroom.suzaku.main.base;

import com.ziroom.suzaku.main.MainApplication;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author libingsi
 * @since 2021-10-12
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BaseTest {

    @BeforeClass
    public static void beforeClass(){
    }

    @Before
    public void before() throws Exception {

    }
}
