package com.bernardnash.engineers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.verify;
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

    @Test
    void givenGetEngineersByTechStack_whenEngineersExist_thenReturnEngineers() {
        SoftwareEngineer alice = new SoftwareEngineer(1L, "Alice", "alice@example.com");
        SoftwareEngineer bob = new SoftwareEngineer(2L, "Bob", "bob@example.com");
        when(softwareEngineerService.getSoftwareEngineersByTechStack(TechStackType.JAVA))
                .thenReturn(List.of(alice, bob));

        List<SoftwareEngineer> result = softwareEngineerController.getEngineersByTechStack(TechStackType.JAVA);

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Alice", result.get(0).getName());
        Assertions.assertEquals("Bob", result.get(1).getName());
        verify(softwareEngineerService).getSoftwareEngineersByTechStack(TechStackType.JAVA);
    }

    @Test
    void givenGetEngineersByTechStack_whenNoEngineersMatch_thenReturnEmptyList() {
        when(softwareEngineerService.getSoftwareEngineersByTechStack(TechStackType.PYTHON))
                .thenReturn(List.of());

        List<SoftwareEngineer> result = softwareEngineerController.getEngineersByTechStack(TechStackType.PYTHON);

        Assertions.assertTrue(result.isEmpty());
    }
}
