package com.bernardnash.engineers;

import com.bernardnash.engineers.controller.SoftwareEngineerController;
import com.bernardnash.engineers.entity.SoftwareEngineer;
import com.bernardnash.engineers.model.TechStackType;
import com.bernardnash.engineers.service.SoftwareEngineerService;
import com.bernardnash.engineers.service.dto.CreateSoftwareEngineerDto;
import com.bernardnash.engineers.service.dto.SoftwareEngineerDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class SoftwareEngineerControllerTest {
    @InjectMocks
    private SoftwareEngineerController softwareEngineerController;
    @Mock SoftwareEngineerService softwareEngineerService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(softwareEngineerController).build();
    }

    @Test
    void getEngineersReturnsJsonList() throws Exception {
        when(softwareEngineerService.getAllSoftwareEngineers()).thenReturn(
                List.of(new SoftwareEngineerDto(
                        1L, "Ada", "ada@example.com", List.of(TechStackType.JAVA)))
        );

        mockMvc.perform(get("/api/v1/software-engineers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Ada"))
                .andExpect(jsonPath("$[0].email").value("ada@example.com"))
                .andExpect(jsonPath("$[0].techStacks[0]").value("JAVA"));

        verify(softwareEngineerService).getAllSoftwareEngineers();
    }

    @Test
    void getEngineerByIdReturnsJsonEngineer() throws Exception {
        when(softwareEngineerService.getSoftwareEngineerById(1L))
                .thenReturn(new SoftwareEngineerDto(
                        1L, "Ada", "ada@example.com", List.of(TechStackType.JAVA)));

        mockMvc.perform(get("/api/v1/software-engineers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Ada"))
                .andExpect(jsonPath("$.email").value("ada@example.com"))
                .andExpect(jsonPath("$.techStacks[0]").value("JAVA"));

        verify(softwareEngineerService).getSoftwareEngineerById(1L);
    }

    @Test
    void addSoftwareEngineerPassesRequestBodyToService() throws Exception {
        when(softwareEngineerService.createSoftwareEngineer(any(CreateSoftwareEngineerDto.class)))
                .thenReturn(new SoftwareEngineerDto(
                        1L, "Ada", "ada@example.com", List.of(TechStackType.JAVA)));

        mockMvc.perform(post("/api/v1/software-engineers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name":"Ada","email":"ada@example.com","techStacks":["JAVA"]}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Ada"))
                .andExpect(jsonPath("$.email").value("ada@example.com"))
                .andExpect(jsonPath("$.techStacks[0]").value("JAVA"));

        ArgumentCaptor<CreateSoftwareEngineerDto> engineerCaptor =
                ArgumentCaptor.forClass(CreateSoftwareEngineerDto.class);
        verify(softwareEngineerService).createSoftwareEngineer(engineerCaptor.capture());
        Assertions.assertEquals(new CreateSoftwareEngineerDto(
                "Ada", "ada@example.com", List.of(TechStackType.JAVA)), engineerCaptor.getValue());
    }

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
