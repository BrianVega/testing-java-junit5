package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.fauxspring.Model;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    private static final String OWNERS_CREATE_OR_UPDATE_OWNER_FORM = "owners/createOrUpdateOwnerForm";
    private static final String REDIRECT_OWNERS_5 = "redirect:/owners/5";
    @Mock
    OwnerService ownerService;

    @Mock
    Model model;

    @InjectMocks
    OwnerController ownerController;

    @Mock
    BindingResult result;

    @Captor // annotation based
    ArgumentCaptor<String> stringArgumentCaptor;

    @BeforeEach
    void setUp() {
        given(ownerService
                .findAllByLastNameLike(stringArgumentCaptor.capture()))
                .willAnswer(invocation -> {
                    List<Owner> owners = new ArrayList<>();

                    String name = invocation.getArgument(0);

                    if(name.equals("%Doe%")) {
                        owners.add(new Owner(1L, "John", "Doe"));
                        return owners;
                    }else if(name.equals("%DontFindMe%")) {
                        return owners;
                    }else if(name.equals("%FindMe%")) {
                        owners.add(new Owner(1L, "John", "Doe"));
                        owners.add(new Owner(2L, "John2", "Doe2"));
                        return owners;
                    }

                    throw new RuntimeException("Invalid argument");
                });
    }

    @Test
    void processFindFormWildcardFound() {
        // given
        Owner owner = new Owner(1L, "John", "FindMe");
        InOrder inOrder = Mockito.inOrder(ownerService, model);
        //when
        String viewName = ownerController
                .processFindForm(owner, result, model);
        //then
        assertThat("%FindMe%")
                .isEqualToIgnoringCase(stringArgumentCaptor.getValue());
        assertThat("owners/ownersList")
                .isEqualToIgnoringCase(viewName);

        // inorder asserts
        inOrder.verify(ownerService)
                .findAllByLastNameLike(anyString());
        inOrder.verify(model, times(1))
                .addAttribute(anyString(), anyList());
        verifyNoMoreInteractions(model);
    }

    @Test
    void processFindFormWildcardStringAnnotation() {
        // given
        Owner owner = new Owner(1L, "John", "Doe");
        //when
        String viewName = ownerController
                .processFindForm(owner, result, null);
        //then
        assertThat("%Doe%")
                .isEqualToIgnoringCase(stringArgumentCaptor.getValue());
        assertThat("redirect:/owners/1")
                .isEqualToIgnoringCase(viewName);
        verifyNoInteractions(model);
    }

    @Test
    void processFindFormWildcardNotFound() {
        // given
        Owner owner = new Owner(1L, "John", "DontFindMe");
        //when
        String viewName = ownerController
                .processFindForm(owner, result, null);
        verifyNoMoreInteractions(ownerService);


        //then
        assertThat("%DontFindMe%")
                .isEqualToIgnoringCase(stringArgumentCaptor.getValue());
        assertThat("owners/findOwners")
                .isEqualToIgnoringCase(viewName);
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