package com.c503.hthj.asoco.dangerchemical.waste;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AsocoDangerChemicalApplicationTests {

    @Test
    public void contextLoads() throws Exception {


        String[] movies={"勇闯夺命岛","断箭","空军一号"};
        for (int i = 0; i <movies.length; i++ ){
            System.out.println(movies[i]);
        }
        //JDK1.5推出的增强for循环
        for (String s:movies) {
            System.out.println(s);
        }

        //JDK1.8推出的函数式编程
        Arrays.asList(movies).forEach(movie -> System.out.println(movie));


    }


}
