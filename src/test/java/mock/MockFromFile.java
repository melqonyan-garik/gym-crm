package mock;

import lombok.SneakyThrows;

public class MockFromFile {

    @SneakyThrows
    public static String getMockData(String resourceName) {
        return getResourceContent( resourceName);
    }

    @SneakyThrows
    public static String getResourceContent(String resourceName) {
        return new String(MockFromFile.class.getClassLoader().getResourceAsStream(resourceName).readAllBytes());
    }
}
