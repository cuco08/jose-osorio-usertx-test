package mx.clip.assessment.user.tx.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import lombok.extern.slf4j.Slf4j;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.Optional;

@Slf4j
public abstract class AbstractClient {
    protected final HttpClient httpClient;

    protected ObjectMapper mapper = new ObjectMapper();

    public AbstractClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public abstract String getBasePath();

    protected Optional<String> configureHttpUrl(URI endPoint) {
        String httpUrl = null;
        try {
             httpUrl = endPoint.toURL().toString() + getBasePath();
        } catch (MalformedURLException e) {
            // Nothing to do, this error should never occur
        }
        return Optional.ofNullable(httpUrl);
    }

    protected  <T> T executeRequest(HttpRequestBase request, Class<T> tClass) {
        T tResponse = null;

        try {
            HttpResponse response = httpClient.execute(request);

            String jsonResponse = EntityUtils.toString(response.getEntity());

            if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
                tResponse = mapper.readValue(jsonResponse, tClass);
            }
        } catch (Exception e) {
            log.error("Unable to process request={}", request.getURI());
        }

        return tResponse;
    }

    protected <T, V> T executeRequest(HttpEntityEnclosingRequestBase request, Class<T> tClass, V bodyContent) {
        T tResponse = null;

        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String jsonRequest = ow.writeValueAsString(bodyContent);
            request.setEntity(new StringEntity(jsonRequest));
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json");

            HttpResponse response = httpClient.execute(request);

            String jsonResponse = EntityUtils.toString(response.getEntity());

            if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
                tResponse = mapper.readValue(jsonResponse, tClass);
            }
        } catch (Exception e) {
            log.error("Unable to process request={} exception={}", request.getURI(), e);
        }

        return tResponse;
    }
}
