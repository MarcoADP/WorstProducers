package com.textoit.worstproducers;


import static org.junit.jupiter.api.Assertions.assertEquals;

import com.textoit.worstproducers.entity.AwardIntervalMinMax;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AwardIntervalTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void getAwardIntervalMinMax() {

        ResponseEntity<AwardIntervalMinMax> response = this.testRestTemplate
                .exchange("/award-interval", HttpMethod.GET, null, AwardIntervalMinMax.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }


}
