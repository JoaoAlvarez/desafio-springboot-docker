package com.projeto.api.web.rest.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public final class HeaderUtil {

    private static final Logger log = LoggerFactory.getLogger(HeaderUtil.class);

    private HeaderUtil() {}

    public static HttpHeaders createAlert(String applicationName, String message, String param) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-" + applicationName + "-alert", message);
        headers.add("X-" + applicationName + "-params", URLEncoder.encode(param, StandardCharsets.UTF_8));

        return headers;
    }

    public static HttpHeaders createEntityCreationAlert(
        String applicationName,
        boolean enableTranslation,
        String entityName,
        String param
    ) {
        String message = enableTranslation ? applicationName + "." + entityName + ".created" : entityName + " criado com sucesso ";
        return createAlert(applicationName, message, param);
    }

    public static HttpHeaders createEntityUpdateAlert(String applicationName, boolean enableTranslation, String entityName, String param) {
        String message = enableTranslation ? applicationName + "." + entityName + ".updated" : entityName + " atualizado com sucesso ";
        return createAlert(applicationName, message, param);
    }

    public static HttpHeaders createEntityDeletionAlert(
        String applicationName,
        boolean enableTranslation,
        String entityName,
        String param
    ) {
        String message = enableTranslation ? applicationName + "." + entityName + ".deleted" : entityName + " removido com sucesso ";
        return createAlert(applicationName, message, param);
    }

    public static HttpHeaders createFailureAlert(
        String applicationName,
        boolean enableTranslation,
        String entityName,
        String errorKey,
        String defaultMessage
    ) {
        log.error("Processamento da entidade com erro, {}", defaultMessage);
        String message = enableTranslation ? "error." + errorKey : defaultMessage;
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-" + applicationName + "-error", message);
        headers.add("X-" + applicationName + "-params", entityName);
        return headers;
    }
}
