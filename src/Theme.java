import java.util.UUID;

public class Theme {

    private String theme;
    private String description;
    private UUID id;

    public Theme(String theme, String description, UUID id) {
        this.theme = theme;
        this.description = description;
        this.id = id;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getTheme() {
        return this.theme;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void setID(UUID id) {
        this.id = id;
    }

    public UUID getID() {
        return this.id;
    }

    public String toString() {
        return "Theme: " + theme +
                "\nDescription: " + description;
    }
}
