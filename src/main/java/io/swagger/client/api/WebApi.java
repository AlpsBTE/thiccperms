/*
 * BTE API
 * Discord Bot API
 *
 * OpenAPI spec version: 1.0.0
 *
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package io.swagger.client.api;

import io.swagger.client.*;
import org.threeten.bp.LocalDate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebApi {
    private ApiClient apiClient;

    public WebApi() {
        this(Configuration.getDefaultApiClient());
    }

    public WebApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Build call for apply
     *
     * @param minecraftUsername       (required)
     * @param discordTag              (required)
     * @param birthday                (required)
     * @param buildings               (required)
     * @param city                    (required)
     * @param country                 (required)
     * @param progressListener        Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call applyCall(String minecraftUsername, String discordTag, LocalDate birthday, String buildings, String city, String country, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/apply";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();
        if (minecraftUsername != null)
            localVarFormParams.put("minecraft_username", minecraftUsername);
        if (discordTag != null)
            localVarFormParams.put("discord_tag", discordTag);
        if (birthday != null)
            localVarFormParams.put("birthday", birthday);
        if (buildings != null)
            localVarFormParams.put("buildings", buildings);
        if (city != null)
            localVarFormParams.put("city", city);
        if (country != null)
            localVarFormParams.put("country", country);

        final String[] localVarAccepts = {
                "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
                "application/x-www-form-urlencoded"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if (progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                }
            });
        }

        String[] localVarAuthNames = new String[]{};
        return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call applyValidateBeforeCall(String minecraftUsername, String discordTag, LocalDate birthday, String buildings, String city, String country, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        // verify the required parameter 'minecraftUsername' is set
        if (minecraftUsername == null) {
            throw new ApiException("Missing the required parameter 'minecraftUsername' when calling apply(Async)");
        }
        // verify the required parameter 'discordTag' is set
        if (discordTag == null) {
            throw new ApiException("Missing the required parameter 'discordTag' when calling apply(Async)");
        }
        // verify the required parameter 'birthday' is set
        if (birthday == null) {
            throw new ApiException("Missing the required parameter 'birthday' when calling apply(Async)");
        }
        // verify the required parameter 'buildings' is set
        if (buildings == null) {
            throw new ApiException("Missing the required parameter 'buildings' when calling apply(Async)");
        }
        // verify the required parameter 'city' is set
        if (city == null) {
            throw new ApiException("Missing the required parameter 'city' when calling apply(Async)");
        }
        // verify the required parameter 'country' is set
        if (country == null) {
            throw new ApiException("Missing the required parameter 'country' when calling apply(Async)");
        }

        com.squareup.okhttp.Call call = applyCall(minecraftUsername, discordTag, birthday, buildings, city, country, progressListener, progressRequestListener);
        return call;


    }

    /**
     * Send Application
     * Opens up a new application to our build team.  The application will be stored in mariaDB and processed via a dedicated discord channel using the discord bot.
     *
     * @param minecraftUsername (required)
     * @param discordTag        (required)
     * @param birthday          (required)
     * @param buildings         (required)
     * @param city              (required)
     * @param country           (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public void apply(String minecraftUsername, String discordTag, LocalDate birthday, String buildings, String city, String country) throws ApiException {
        applyWithHttpInfo(minecraftUsername, discordTag, birthday, buildings, city, country);
    }

    /**
     * Send Application
     * Opens up a new application to our build team.  The application will be stored in mariaDB and processed via a dedicated discord channel using the discord bot.
     *
     * @param minecraftUsername (required)
     * @param discordTag        (required)
     * @param birthday          (required)
     * @param buildings         (required)
     * @param city              (required)
     * @param country           (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<Void> applyWithHttpInfo(String minecraftUsername, String discordTag, LocalDate birthday, String buildings, String city, String country) throws ApiException {
        com.squareup.okhttp.Call call = applyValidateBeforeCall(minecraftUsername, discordTag, birthday, buildings, city, country, null, null);
        return apiClient.execute(call);
    }

    /**
     * Send Application (asynchronously)
     * Opens up a new application to our build team.  The application will be stored in mariaDB and processed via a dedicated discord channel using the discord bot.
     *
     * @param minecraftUsername (required)
     * @param discordTag        (required)
     * @param birthday          (required)
     * @param buildings         (required)
     * @param city              (required)
     * @param country           (required)
     * @param callback          The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call applyAsync(String minecraftUsername, String discordTag, LocalDate birthday, String buildings, String city, String country, final ApiCallback<Void> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = applyValidateBeforeCall(minecraftUsername, discordTag, birthday, buildings, city, country, progressListener, progressRequestListener);
        apiClient.executeAsync(call, callback);
        return call;
    }
}
