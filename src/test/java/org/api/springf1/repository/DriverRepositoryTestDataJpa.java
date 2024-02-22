package org.api.springf1.repository;

import org.api.springf1.model.Driver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import static org.junit.Assert.assertTrue;
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class DriverRepositoryTestDataJpa {

    @Autowired
    DriverRepository driverRepository;

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
