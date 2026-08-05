package com.bernardnash.engineers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SoftwareEngineerControllerTest {
    @InjectMocks
    private SoftwareEngineerController softwareEngineerController;
    @Mock SoftwareEngineerService softwareEngineerService;
    @Test
    void givenGetEngineersById_whenIdExists_thenReturnEngineer() {
        when(softwareEngineerService.getSoftwareEngineerById(1L)).thenReturn(new SoftwareEngineer(1L, "Alice", "alice@example.com"));
        SoftwareEngineer softwareEngineer = softwareEngineerController.getEngineerById(1L);

        Assertions.assertEquals(1L, softwareEngineer.getId());
    }
}
