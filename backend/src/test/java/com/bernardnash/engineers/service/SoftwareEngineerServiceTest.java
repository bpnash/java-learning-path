package com.bernardnash.engineers.service;

import com.bernardnash.engineers.entity.SoftwareEngineer;
import com.bernardnash.engineers.exception.SoftwareEngineerNotFoundException;
import com.bernardnash.engineers.repository.SoftwareEngineerRepository;
import com.bernardnash.engineers.service.dto.CreateSoftwareEngineerDto;
import com.bernardnash.engineers.service.dto.SoftwareEngineerDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SoftwareEngineerServiceTest {

    @Mock
    private SoftwareEngineerRepository repository;

    @Captor
    private ArgumentCaptor<SoftwareEngineer> engineerCaptor;

    @Test
    void createsEntityFromDtoAndReturnsDto() {
        SoftwareEngineer savedEngineer = new SoftwareEngineer("Ada", "Java, Spring");
        when(repository.save(engineerCaptor.capture())).thenReturn(savedEngineer);
        SoftwareEngineerService service = new SoftwareEngineerService(repository);

        SoftwareEngineerDto dto = service.createSoftwareEngineer(new CreateSoftwareEngineerDto("Ada", "Java, Spring"));

        assertThat(engineerCaptor.getValue().getName()).isEqualTo("Ada");
        assertThat(engineerCaptor.getValue().getTechStack()).isEqualTo("Java, Spring");
        assertThat(dto).isEqualTo(new SoftwareEngineerDto(null, "Ada", "Java, Spring"));
    }

    @Test
    void throwsNotFoundExceptionWhenEngineerDoesNotExist() {
        when(repository.findById(42)).thenReturn(Optional.empty());
        SoftwareEngineerService service = new SoftwareEngineerService(repository);

        assertThatThrownBy(() -> service.getSoftwareEngineerById(42))
                .isInstanceOf(SoftwareEngineerNotFoundException.class)
                .hasMessage("Software engineer with id 42 was not found");

        verify(repository).findById(42);
    }
}
