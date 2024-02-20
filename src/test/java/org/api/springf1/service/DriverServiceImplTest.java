package org.api.springf1.service;

import org.api.springf1.dto.DriverDTO;
import org.api.springf1.dto.DriverResponse;
import org.api.springf1.model.Driver;
import org.api.springf1.repository.DriverRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DriverServiceImplTest {

    @Mock
    DriverRepository driverRepository;
    @InjectMocks
    DriverServiceImpl driverService;

    Driver driver;
    DriverDTO driverDTO;
    @BeforeEach
    public void setUp() {
        driver = Driver.builder().id(1L).code("AAA").forename("Ayrton").surname("Bussi").build();
        driverDTO = DriverDTO.builder().id(1L).code("AAA").forename("Ayrton").surname("Bussi").build();
    }

    @Test
    void shouldReturnDriverDTOWhenFindDriverByCode() {
        //Giva
        when(driverRepository.findByCodeIgnoreCase(anyString())).thenReturn(Optional.of(driver));
        //Then
        DriverDTO getDTO = driverService.getDriverByCode("AAA");

        assertEquals(getDTO, driverDTO);
        //getDriverByCode();
    }

    @Test
    void shouldReturnDriverDTOWhenUpdateDriver() {

        when(driverRepository.save(any(Driver.class))).thenReturn(driver);
        when(driverRepository.findById(any())).thenReturn(Optional.ofNullable(driver));

        DriverDTO dto = driverService.updateDriver(driver);

        assertEquals(dto, driverDTO);

        //updateDriver();
    }

    @Test
    void shouldReturnNothingWhenDeleteDriverByCode() {

        when(driverRepository.findByCodeIgnoreCase(any())).thenReturn(Optional.ofNullable(driver));

        assertDoesNotThrow(()->driverService.deleteDriverByCode(driver.getCode()));        //deleteDriverByCode();
    }

    @Test
    void shouldReturnDriverResponseWhenGetAllDrivers() {

        when(driverRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl(List.of(driver,driver)));

        DriverResponse list = driverService.getDrivers(0,5);

        assertEquals(list.content().size(),2);
        //getDrivers()
    }

}