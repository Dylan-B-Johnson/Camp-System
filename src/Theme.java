public class Theme {

    private String theme;
    private String description;

    public Theme(String theme, String description) {
        this.theme = theme;
        this.description = description;
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

    public String toString() {
        return "Theme: " + theme +
                "\nDescription: " + description;
    }
}
