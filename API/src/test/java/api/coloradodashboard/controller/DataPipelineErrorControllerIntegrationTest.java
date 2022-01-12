package api.coloradodashboard.controller;

import api.coloradodashboard.repository.DataPipelineErrorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DataPipelineErrorControllerIntegrationTest {
    @InjectMocks
    private DataPipelineErrorController componentUnderTest;
    @Mock
    private DataPipelineErrorRepository repository;

    @Test
    @DisplayName("INTEGRATION: getLatest() invokes repository method.")
    void getLatest() {
        componentUnderTest
                .getLatest();

        verify(repository)
                .getLatest();
    }
}