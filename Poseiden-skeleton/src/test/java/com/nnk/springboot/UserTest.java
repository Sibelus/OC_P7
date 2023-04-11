package com.nnk.springboot;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Autowired
    private UserRepository userRepository;


    @Test
    public void userTest() {
        //GIVEN
        User dbUser = userRepository.findByUsername("admin");

        //THEN
        Assert.assertEquals("Administrator", dbUser.getFullname());
        //Assertions.assertThat(dbUser.getFullname()).isEqualTo("Administrator");
    }
}
