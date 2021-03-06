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

import io.swagger.client.ApiException;
import org.junit.Ignore;
import org.junit.Test;
import org.threeten.bp.LocalDate;

/**
 * API tests for WebApi
 */
@Ignore
public class WebApiTest {

    private final WebApi api = new WebApi();

    /**
     * Send Application
     * <p>
     * Opens up a new application to our build team.  The application will be stored in mariaDB and processed via a dedicated discord channel using the discord bot.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void applyTest() throws ApiException {
        String minecraftUsername = null;
        String discordTag = null;
        LocalDate birthday = null;
        String buildings = null;
        String city = null;
        String country = null;
        api.apply(minecraftUsername, discordTag, birthday, buildings, city, country);

        // TODO: test validations
    }
}
