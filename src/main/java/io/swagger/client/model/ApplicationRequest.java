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

package io.swagger.client.model;

import com.google.gson.annotations.SerializedName;
import io.swagger.v3.oas.annotations.media.Schema;
import org.threeten.bp.LocalDate;

import java.util.Objects;

/**
 * ApplicationRequest
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2020-05-27T20:18:37.442Z[GMT]")
public class ApplicationRequest {
    @SerializedName("minecraft_username")
    private String minecraftUsername = null;

    @SerializedName("discord_tag")
    private String discordTag = null;

    @SerializedName("birthday")
    private LocalDate birthday = null;

    @SerializedName("buildings")
    private String buildings = null;

    @SerializedName("city")
    private String city = null;

    @SerializedName("country")
    private String country = null;

    public ApplicationRequest minecraftUsername(String minecraftUsername) {
        this.minecraftUsername = minecraftUsername;
        return this;
    }

    /**
     * The Minecraft username
     *
     * @return minecraftUsername
     **/
    @Schema(example = "Kami_the_Miner", required = true, description = "The Minecraft username")
    public String getMinecraftUsername() {
        return minecraftUsername;
    }

    public void setMinecraftUsername(String minecraftUsername) {
        this.minecraftUsername = minecraftUsername;
    }

    public ApplicationRequest discordTag(String discordTag) {
        this.discordTag = discordTag;
        return this;
    }

    /**
     * The Discord tag
     *
     * @return discordTag
     **/
    @Schema(example = "Kami#3480", required = true, description = "The Discord tag")
    public String getDiscordTag() {
        return discordTag;
    }

    public void setDiscordTag(String discordTag) {
        this.discordTag = discordTag;
    }

    public ApplicationRequest birthday(LocalDate birthday) {
        this.birthday = birthday;
        return this;
    }

    /**
     * The birthday of the applicant
     *
     * @return birthday
     **/
    @Schema(example = "Sat Jan 01 00:00:00 GMT 2000", required = true, description = "The birthday of the applicant")
    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public ApplicationRequest buildings(String buildings) {
        this.buildings = buildings;
        return this;
    }

    /**
     * A link to the gallery
     *
     * @return buildings
     **/
    @Schema(example = "https://imgur.com/a/Yv9k1pl", required = true, description = "A link to the gallery")
    public String getBuildings() {
        return buildings;
    }

    public void setBuildings(String buildings) {
        this.buildings = buildings;
    }

    public ApplicationRequest city(String city) {
        this.city = city;
        return this;
    }

    /**
     * The city one wants to primarily build in
     *
     * @return city
     **/
    @Schema(example = "Zürich", required = true, description = "The city one wants to primarily build in")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public ApplicationRequest country(String country) {
        this.country = country;
        return this;
    }

    /**
     * The country the city is in
     *
     * @return country
     **/
    @Schema(example = "Switzerland", required = true, description = "The country the city is in")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ApplicationRequest applicationRequest = (ApplicationRequest) o;
        return Objects.equals(this.minecraftUsername, applicationRequest.minecraftUsername) &&
                Objects.equals(this.discordTag, applicationRequest.discordTag) &&
                Objects.equals(this.birthday, applicationRequest.birthday) &&
                Objects.equals(this.buildings, applicationRequest.buildings) &&
                Objects.equals(this.city, applicationRequest.city) &&
                Objects.equals(this.country, applicationRequest.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(minecraftUsername, discordTag, birthday, buildings, city, country);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ApplicationRequest {\n");

        sb.append("    minecraftUsername: ").append(toIndentedString(minecraftUsername)).append("\n");
        sb.append("    discordTag: ").append(toIndentedString(discordTag)).append("\n");
        sb.append("    birthday: ").append(toIndentedString(birthday)).append("\n");
        sb.append("    buildings: ").append(toIndentedString(buildings)).append("\n");
        sb.append("    city: ").append(toIndentedString(city)).append("\n");
        sb.append("    country: ").append(toIndentedString(country)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

}