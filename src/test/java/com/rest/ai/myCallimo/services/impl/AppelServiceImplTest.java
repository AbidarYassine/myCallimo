package com.rest.ai.myCallimo.services.impl;

import com.rest.ai.myCallimo.exception.NotFoundException;
import com.rest.ai.myCallimo.exception.user.UserNotFoundException;
import com.rest.ai.myCallimo.request.AppelDto;
import com.rest.ai.myCallimo.response.AppelResponse;
import com.rest.ai.myCallimo.services.facade.AppelService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AppelServiceImplTest {

    @Autowired
    private AppelService appelService;

    @Test
    public void saveAppelSucces() {
        AppelDto expectedValue = AppelDto.builder()
                .date(new Date())
                .typeAppel("RDV")
                .duree("1h30")
                .build();
        AppelResponse result = appelService.save(expectedValue);
        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getId());


        Assertions.assertEquals(expectedValue.getDuree(), result.getDuree());
        Assertions.assertEquals(expectedValue.getTypeAppel(), result.getTypeAppel());

    }

    @Test
    public void saveAppelWithCaller() {
        AppelDto expectedValue = AppelDto.builder()
                .date(new Date())
                .typeAppel("RDV")
                .duree("1h30")
                .build();
        AppelResponse result = appelService.saveWithCaller(expectedValue, 3523, 1279);

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals(result.getCaller().getId(), 3523);
        Assertions.assertEquals(expectedValue.getDuree(), result.getDuree());
        Assertions.assertEquals(expectedValue.getTypeAppel(), result.getTypeAppel());
    }

    @Test
    public void saveAppelWithSup() {
        AppelDto expectedValue = AppelDto.builder()
                .date(new Date())
                .typeAppel("RDV")
                .duree("1h30")
                .build();
        AppelResponse result = appelService.saveWithSupervisor(expectedValue, 3519, 1279);

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals(expectedValue.getDuree(), result.getDuree());
        Assertions.assertEquals(expectedValue.getTypeAppel(), result.getTypeAppel());
    }

    @Test
    public void saveAppelWithSupFailure() {
        AppelDto expectedValue = AppelDto.builder()
                .date(new Date())
                .typeAppel("RDV")
                .duree("1h30")
                .build();
        Assertions.assertThrows(NotFoundException.class, () -> appelService.saveWithSupervisor(expectedValue, 0, 10));
    }

    @Test
    public void saveAppelWithCallerFailure() {
        AppelDto expectedValue = AppelDto.builder()
                .date(new Date())
                .typeAppel("RDV")
                .duree("1h30")
                .build();
        Assertions.assertThrows(UserNotFoundException.class, () -> appelService.saveWithCaller(expectedValue, 0, 10));
    }

}