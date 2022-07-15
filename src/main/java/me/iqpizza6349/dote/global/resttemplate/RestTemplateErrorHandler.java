package me.iqpizza6349.dote.global.resttemplate;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import me.iqpizza6349.dote.global.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
public class RestTemplateErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        final HttpStatus httpStatus = response.getStatusCode();
        return !httpStatus.is2xxSuccessful();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        final String error = getErrorAsString(response);
        log.error("Headers: {}", response.getHeaders());
        log.error("Response failed: {}", error);

        throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 통신 중 오류");
    }

    private String getErrorAsString(@NonNull final ClientHttpResponse response)
            throws IOException {
        try (final BufferedReader bufferedReader
                     = new BufferedReader(new InputStreamReader(response.getBody()))) {
            return bufferedReader.readLine();
        }
    }
}
