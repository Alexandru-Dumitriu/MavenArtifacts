package testData;

import java.net.URISyntaxException;
import java.net.URL;

public class UrlToUriAfter {
    public int applyHashCode(URL u) throws URISyntaxException {
        return (u.toURI().hashCode());
    }
}