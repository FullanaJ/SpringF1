package org.api.springf1.repository;

import org.api.springf1.model.Driver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.junit.Assert.assertTrue;

@Testcontainers
@DataJpaTest
public class DriverRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15.5");

    @Autowired DriverRepository driverRepository;

    static Driver driver;
    static Driver driver2;
    @BeforeAll
    static void setUp(){
        driver = Driver.builder().code("JFE").surname("Jorge").forename("Fullana").build();
        driver2 = Driver.builder().code("EFJ").surname("Jorge").forename("Fullana").build();

    }

    @Test
    @Order(1)
    void shouldReturnMoreThanOneDriverWhenSaveTwoDrivers(){

        driverRepository.save(driver);
        driverRepository.save(driver2);

        Assertions.assertEquals(2,driverRepository.findAll().size());
    }

    @Test
    @Order(2)
    void shouldReturnDriverNotNullWhenFindByCode(){
        driverRepository.save(driver);
        Assertions.assertNotNull(driverRepository.findByCodeIgnoreCase("jfe").get());
    }
    @Test
    @Order(3)
    void  shouldReturnDriverNotNullWhenUpdateDriver(){
        driverRepository.save(driver);
        Assertions.assertNotNull(driverRepository.save(driver));
        Assertions.assertEquals( 1,driverRepository.findAll().size());
    }
    @Test
    @Order(4)
    void  shouldReturnNullDriverWhenDelete(){
        driverRepository.save(driver);
        driverRepository.delete(driver);
        assertTrue(driverRepository.findByCodeIgnoreCase(driver.getCode()).isEmpty());
    }

}
