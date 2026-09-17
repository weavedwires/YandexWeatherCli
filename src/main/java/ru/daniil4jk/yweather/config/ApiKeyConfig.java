package ru.daniil4jk.yweather.config;

public final class ApiKeyConfig extends SomeConfig {
    private static final String API_KEY_FILE_NAME = "api-key.txt";
    public ApiKeyConfig() {
        super(API_KEY_FILE_NAME);
    }

    @Override
    public String read() {
        return super.read().replace(System.lineSeparator(), "").trim();
    }
}
