package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    private static final String OWNERS_CREATE_OR_UPDATE_OWNER_FORM = "owners/createOrUpdateOwnerForm";
    private static final String REDIRECT_OWNERS_5 = "redirect:/owners/5";
    @Mock
    OwnerService ownerService;

    @InjectMocks
    OwnerController ownerController;

    @Mock
    BindingResult result;

    @Captor // annotation based
    ArgumentCaptor<String> stringArgumentCaptor;

    @Test
    void processFindFormWildcardString() {
       // given
       Owner owner = new Owner(1L, "John", "Doe");
       List<Owner> owners = new ArrayList<>();
       final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
       given(ownerService
               .findAllByLastNameLike(captor.capture()))
               .willReturn(owners);
       //when
        String viewName = ownerController
                .processFindForm(owner, result, null);
        //then
        assertThat("%Doe%")
                .isEqualToIgnoringCase(captor.getValue());
    }

    @Test
    void processFindFormWildcardStringAnnotation() {
        // given
        Owner owner = new Owner(1L, "John", "Doe");
        List<Owner> owners = new ArrayList<>();
        given(ownerService
                .findAllByLastNameLike(stringArgumentCaptor.capture()))
                .willReturn(owners);
        //when
        String viewName = ownerController
                .processFindForm(owner, result, null);
        //then
        assertThat("%Doe%")
                .isEqualToIgnoringCase(stringArgumentCaptor.getValue());
    }

    @Test
    void processCreationFormHasErrors() {
        //given
        Owner owner = new Owner(1L, "John", "Doe");
        given(result.hasErrors()).willReturn(true);

        //when
        String viewName = ownerController.processCreationForm(owner, result);

        //then
        assertThat(viewName).isEqualToIgnoringCase(OWNERS_CREATE_OR_UPDATE_OWNER_FORM);
    }

    @Test
    void processCreationFormHasNoErrors() {
        //given
        Owner owner = new Owner(5L, "John", "Doe");
        given(result.hasErrors()).willReturn(false);
        given(ownerService.save(any())).willReturn(owner);

        //when
        String viewName = ownerController.processCreationForm(owner, result);

        //then
        assertThat(viewName).isEqualToIgnoringCase(REDIRECT_OWNERS_5);
    }

    @Test
    void initUpdateOwnerForm() {
    }

    @Test
    void processUpdateOwnerForm() {
    }
}