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
        when(softwareEngineerService.getSoftwareEngineerById(1)).thenReturn(new SoftwareEngineer(1, "Alice", "Java, Spring"));
        SoftwareEngineer softwareEngineer = softwareEngineerController.getEngineerById(1);

        Assertions.assertEquals(1, softwareEngineer.getId());
        // Given Copilot suggestion
//        SoftwareEngineerRepository repository = new InMemorySoftwareEngineerRepository();
//        SoftwareEngineerService service = new SoftwareEngineerService(repository);
//        SoftwareEngineerController controller = new SoftwareEngineerController(service);
//
//        SoftwareEngineer engineer = new SoftwareEngineer(1, "Alice", "Java, Spring");
//        repository.save(engineer);
//
//        // When
//        SoftwareEngineer result = controller.getEngineerById(1);
//
//        // Then
//        assert result != null;
//        assert result.getId().equals(engineer.getId());
//        assert result.getName().equals(engineer.getName());
//        assert result.getSkills().equals(engineer.getSkills());
    }
}
