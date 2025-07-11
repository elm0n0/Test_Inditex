package com.inditex.prueba.tecnica.juanjose;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JuanjoseApplicationTests {

	@Test
    @DisplayName("Debe ejecutar SpringApplication.run() cuando se llama al método main")
    void mainMethodShouldRunSpringApplication() {
        try (MockedStatic<SpringApplication> mockedSpringApplication = mockStatic(SpringApplication.class)) {
            mockedSpringApplication.when(() -> SpringApplication.run(any(Class.class), any(String[].class)))
                                   .thenReturn(null);
            JuanjoseApplication.main(new String[]{});
            mockedSpringApplication.verify(() -> SpringApplication.run(JuanjoseApplication.class, new String[]{}),
                                          org.mockito.Mockito.times(1));
        }
    }

}
