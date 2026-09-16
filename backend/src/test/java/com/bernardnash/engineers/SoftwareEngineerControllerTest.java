package com.bernardnash.engineers;

import com.bernardnash.engineers.controller.SoftwareEngineerController;
import com.bernardnash.engineers.entity.SoftwareEngineer;
import com.bernardnash.engineers.model.TechStackType;
import com.bernardnash.engineers.service.SoftwareEngineerService;
import com.bernardnash.engineers.service.dto.SoftwareEngineerDto;
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
        when(softwareEngineerService.getSoftwareEngineerById(1L)).thenReturn(new SoftwareEngineerDto(1L, "Alice", "alice@example.com", List.of()));
        var softwareEngineer = softwareEngineerController.getEngineerById(1L);

        Assertions.assertEquals(1L, softwareEngineer.getId());
    }

    @Test
    void givenGetEngineersByTechStack_whenEngineersExist_thenReturnEngineers() {
        SoftwareEngineer alice = new SoftwareEngineer(1L, "Alice", "alice@example.com");
        SoftwareEngineer bob = new SoftwareEngineer(2L, "Bob", "bob@example.com");
        when(softwareEngineerService.getSoftwareEngineersByTechStack(TechStackType.JAVA))
                .thenReturn(List.of(
                        new SoftwareEngineerDto(1L, "Alice", "alice@example.com", List.of(TechStackType.JAVA)),
                        new SoftwareEngineerDto(2L, "Bob", "bob@example.com", List.of(TechStackType.JAVA))
                ));

        var result = softwareEngineerController.getEngineersByTechStack(TechStackType.JAVA);

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Alice", result.get(0).getName());
        Assertions.assertEquals("Bob", result.get(1).getName());
        verify(softwareEngineerService).getSoftwareEngineersByTechStack(TechStackType.JAVA);
    }

    @Test
    void givenGetEngineersByTechStack_whenNoEngineersMatch_thenReturnEmptyList() {
        when(softwareEngineerService.getSoftwareEngineersByTechStack(TechStackType.PYTHON))
                .thenReturn(List.of());

        var result = softwareEngineerController.getEngineersByTechStack(TechStackType.PYTHON);

        Assertions.assertTrue(result.isEmpty());
    }
}
