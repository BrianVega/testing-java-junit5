package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {

    @Mock
    VisitRepository visitRepository;
//    @Mock
//    Visit visitMock;

    @InjectMocks
    VisitSDJpaService service;

    @DisplayName("Test Find All")
    @Test
    void findAll() {
//        Set<Visit> visits = service.findAll();
//
//        assertNotNull(visits);
//        verify(visitRepository).findAll();
    Visit visit = new Visit();

    Set<Visit> visits = new HashSet<>();
    visits.add(visit);

    when(visitRepository.findAll()).thenReturn(visits);
    Set<Visit> foundVisits = service.findAll();
    verify(visitRepository).findAll();
    assertThat(foundVisits).hasSize(1);

    }

    @Test
    void findById() {
//        service.findById(1L);
//        verify(visitRepository).findById(1L);
        Visit visit = new Visit();
        when(visitRepository.findById(anyLong()))
                .thenReturn(Optional.of(visit));

        Visit foundVisit = service.findById(1L);

        verify(visitRepository).findById(anyLong());

        assertThat(foundVisit).isNotNull();
    }

    @Test
    void save() {
//        service.save(visitMock);
//        verify(visitRepository).save(visitMock);
    Visit visit = new Visit();
    when(visitRepository
            .save(any(Visit.class)))
            .thenReturn(visit);

    Visit savedVisit = service.save(visit);

    verify(visitRepository).save(any(Visit.class));

    assertThat(savedVisit).isNotNull();

    }

    @Test
    void delete() {
//        service.delete(visitMock);
//        verify(visitRepository).delete(visitMock);
        Visit visit = new Visit();
        service.delete(visit);
        verify(visitRepository).delete(any(Visit.class));
    }

    @Test
    void deleteById() {
        service.deleteById(1L);
        verify(visitRepository).deleteById(anyLong());
    }
}