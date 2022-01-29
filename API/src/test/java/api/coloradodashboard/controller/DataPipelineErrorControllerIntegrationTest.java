package api.coloradodashboard.controller;

import api.coloradodashboard.repository.DataPipelineErrorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DataPipelineErrorControllerIntegrationTest {
    @InjectMocks
    private DataPipelineErrorController componentUnderTest;
    @Mock
    private DataPipelineErrorRepository repository;
    @Captor
    private ArgumentCaptor<Long> idArgumentCaptor;
    private Long id = 1L;
    private Integer page = 0;

    @Test
    @DisplayName("INTEGRATION: getBy5() invokes repository method.")
    void getBy5() {
        componentUnderTest
                .getBy5(page);

        verify(repository)
                .getAllByDateTimeDesc(PageRequest.of(page, 5));
    }

    @Test
    @DisplayName("INTEGRATION: getById(id) invokes repository method.")
    void getOneById() {
        componentUnderTest
                .getById(id);

        verify(repository)
                .getOneById(idArgumentCaptor.capture());
        assertThat(idArgumentCaptor.getValue())
                .isEqualTo(id);
    }
}