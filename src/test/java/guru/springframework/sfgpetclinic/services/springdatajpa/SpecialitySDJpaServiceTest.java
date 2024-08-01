package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.repositories.SpecialtyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    @Mock
    SpecialtyRepository specialtyRepository;

    @InjectMocks
    SpecialitySDJpaService service;

    @Test
    void testDeleteByObject() {
        //given
        Speciality speciality = new Speciality();

        //when
        service.delete(speciality);

        //then
        then(specialtyRepository).should().delete(any(Speciality.class));
    }

    @Test
    void findByIdTest() {
        // GIVEN
        Speciality speciality = new Speciality();
        given(specialtyRepository.findById(1L))
                .willReturn(Optional.of(speciality));
        // WHEN
        Speciality foundSpeciality = service.findById(1L);

        // THEN
        assertThat(foundSpeciality).isNotNull();
        verify(specialtyRepository).findById(anyLong());
        then(specialtyRepository)
                .should(timeout(100))
                .findById(anyLong());
        then(specialtyRepository)
                .shouldHaveNoMoreInteractions();
    }

    @Test
    void deleteById() {
        //given - none

        //when
        service.deleteById(1L);
        service.deleteById(1L);

        //then
        then(specialtyRepository)
                .should(timeout(100).times(2))
                .deleteById(1L);
    }

    @Test
    void deleteByIdAtLeast() {
        //given = none

        //when
        service.deleteById(1L);
        service.deleteById(1L);

        //then
        then(specialtyRepository).should(timeout(1000).atLeastOnce()).deleteById(1L);
    }

    @Test
    void deleteByIdAtMost() {
        // given = none

        //when
        service.deleteById(1L);
        service.deleteById(1L);

        //then
        then(specialtyRepository)
                .should(atMost(5))
                .deleteById(1L);
    }

    @Test
    void deleteByIdNever() {
        // given = none

        //when
        service.deleteById(1L);
        service.deleteById(1L);

        //then
        then(specialtyRepository)
                .should(timeout(200).atLeastOnce())
                .deleteById(1L);

        then(specialtyRepository)
                .should(never())
                .deleteById(5L);
    }

    @Test
    void testDelete() {
        //given = none

        //when
        service.delete(new Speciality());

        //then
        then(specialtyRepository)
                .should()
                .delete(any(Speciality.class));
    }
}