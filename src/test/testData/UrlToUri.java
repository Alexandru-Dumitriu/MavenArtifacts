package testData;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class UrlToUri {
    public static int applyHashCode(URL u) throws URISyntaxException {
        return (u.hashCode());
    }
}
