package mock;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MockFromFile {


    public static String getMockData(String resourceName) {
        return getResourceContent(resourceName);
    }


    public static String getResourceContent(String resourceName) {

        try {
            URL resourceUrl = MockFromFile.class.getResource("/" + resourceName);

            return new String(Files.readAllBytes(Paths.get(resourceUrl.toURI())));
        } catch (Exception e) {
            throw new RuntimeException("cannot read file from resource :" + resourceName, e);
        }
    }
}
