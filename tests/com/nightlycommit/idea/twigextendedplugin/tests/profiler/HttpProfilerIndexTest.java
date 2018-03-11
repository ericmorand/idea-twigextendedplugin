package com.nightlycommit.idea.twigextendedplugin.tests.profiler;

import com.nightlycommit.idea.twigextendedplugin.profiler.HttpProfilerIndex;
import com.nightlycommit.idea.twigextendedplugin.profiler.dict.ProfilerRequestInterface;
import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyLightCodeInsightFixtureTestCase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 * @see com.nightlycommit.idea.twigextendedplugin.profiler.HttpProfilerIndex
 */
public class HttpProfilerIndexTest extends SymfonyLightCodeInsightFixtureTestCase {
    public void setUp() throws Exception {
        super.setUp();
    }

    protected String getTestDataPath() {
        return new File(this.getClass().getResource("fixtures").getFile()).getAbsolutePath();
    }

    public void testGetUrlForRequest() {
        HttpProfilerIndex index = new HttpProfilerIndex(getProject(), "http://www.google.de");

        assertEquals(
            "http://foobar.de",
            index.getUrlForRequest(new MyProfilerUrlRequestInterface("http://foobar.de"))
        );

        assertEquals(
            "http://www.google.de/foobar",
            index.getUrlForRequest(new MyProfilerUrlRequestInterface("foobar"))
        );
    }

    private static class MyProfilerUrlRequestInterface implements ProfilerRequestInterface {
        @NotNull
        private final String profilerUrl;

        MyProfilerUrlRequestInterface(@NotNull String profilerUrl) {
            this.profilerUrl = profilerUrl;
        }

        @NotNull
        @Override
        public String getHash() {
            return "";
        }

        @Nullable
        @Override
        public String getMethod() {
            return "";
        }

        @NotNull
        @Override
        public String getUrl() {
            return "";
        }

        @NotNull
        @Override
        public String getProfilerUrl() {
            return this.profilerUrl;
        }

        @Override
        public int getStatusCode() {
            return 0;
        }

        @Nullable
        @Override
        public <T> T getCollector(Class<T> classFactory) {
            return null;
        }
    }
}
