import java.util.ArrayList;

public class ThemeList {

    // will need updated if/when theme is added to DataReader
    public static boolean addTheme(Theme theme) {
        for (Theme newTheme : getAvailableThemes()) {
            if (newTheme.getId().equals(theme.getId())) {
                return false;
            }
        }
        DataWriter.createTheme(theme);
        return true;
    }

    public static Theme getCurrentTheme() {
        return theme;
    }
}
