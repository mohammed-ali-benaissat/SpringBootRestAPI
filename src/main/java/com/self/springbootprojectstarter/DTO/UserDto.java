package com.self.springbootprojectstarter.DTO;

// Validation annotations from Jakarta Bean Validation API
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 🔥 UserDto = Data Transfer Object for User
 *
 * Why DTO?
 * - Protects your Entity from direct exposure to clients
 * - Allows validation at API boundary
 * - Keeps API stable even if database schema changes
 * - Can include only the fields you want clients to see
 *
 * In this example, we include:
 * - id (for read operations)
 * - name, email, age (basic fields)
 * - profileId (represents relationship to Profile)
 */
public class UserDto {

    // ------------------------
    // 🔹 ID
    // ------------------------
    // Exposed to clients so they know the user's identity.
    // Usually optional on CREATE requests.
    private Long id;

    // ------------------------
    // 🔹 NAME
    // ------------------------
    @NotBlank(message = "Name cannot be empty")
    // @NotBlank → must not be null and must contain at least one non-space character
    private String name;

    // ------------------------
    // 🔹 EMAIL
    // ------------------------
    @NotBlank(message = "Email is required")
    // Must not be empty
    @Email(regexp = ".+@.+\\..+", message = "Email must be valid")
    // Must follow simple email format
    private String email;

    // ------------------------
    // 🔹 AGE
    // ------------------------
    @NotNull(message = "Age is required")
    // Must not be null
    @Min(value = 1, message = "Age must be 1 or higher")
    @Max(value = 150, message = "Age must be 150 or lower")
    // Validation ensures sensible age
    private Integer age;

    // ------------------------
    // 🔹 RELATIONSHIP
    // ------------------------
    // Instead of embedding full Profile object, we just store profileId
    // This keeps DTO simple and avoids sending nested objects
    private Long profileId;

    // ------------------------
    // 🔹 CONSTRUCTORS
    // ------------------------
    // Used when creating new UserDto manually
    public UserDto(String name, Integer age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    // Default constructor required by frameworks (e.g., Jackson)
    public UserDto() {}

    // ------------------------
    // 🔹 GETTERS
    // ------------------------
    public String getName(){
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public Long getProfileId() {
        return profileId;
    }

    public Long getId() {
        return id;
    }

    // ------------------------
    // 🔹 SETTERS
    // ------------------------
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 🔹 Key Points about DTOs:
     * 1. Validation annotations (@NotBlank, @Email, @Min, @Max) are applied **before hitting service**
     *    → ensures we don’t store invalid data
     * 2. DTO separates API model from Entity model → safe architecture
     * 3. Relationships are represented by IDs → prevents deep/nested JSON issues
     * 4. DTO is lightweight → easy to serialize/deserialize as JSON
     */
}