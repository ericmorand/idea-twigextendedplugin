package com.nightlycommit.idea.twigextendedplugin.tests.routing;

import com.google.common.collect.ImmutableMap;
import com.nightlycommit.idea.twigextendedplugin.routing.Route;
import com.nightlycommit.idea.twigextendedplugin.stubs.dict.StubIndexedRoute;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 * @see com.nightlycommit.idea.twigextendedplugin.routing.Route
 */
public class RouteTest extends Assert {

    @Test
    public void testIndexInit() {
        StubIndexedRoute stubIndexedRoute = new StubIndexedRoute("foo");
        stubIndexedRoute.setPath("foo_1");
        stubIndexedRoute.setController("foo");

        Route route = new Route(stubIndexedRoute);
        assertEquals("foo", route.getController());
        assertEquals("foo_1", route.getPath());
    }

    @Test
    public void testIndexNullable() {
        StubIndexedRoute stubIndexedRoute = new StubIndexedRoute("foo");
        Route route = new Route(stubIndexedRoute);
        assertNull(route.getController());
        assertNull(route.getPath());
    }

    @Test
    public void testControllerNullable() {
        assertEquals(null, new Route("foo", new HashSet<>(), new HashMap<String, String>() {{
            put("_controller", null);
        }}, new HashMap<>(), new ArrayList<>()).getController());

        assertEquals("foobar", new Route(
            "foo", new HashSet<>(), ImmutableMap.of("_controller", "foobar"), new HashMap<>(), new ArrayList<>()).getController()
        );
    }

    @Test
    public void testPathVariables() {
        StubIndexedRoute route = new StubIndexedRoute("foobar");
        route.setPath("/foo/{foo}/{foobar}/bar");

        assertTrue("foobar",
            new Route(route).getVariables().containsAll(Arrays.asList("foo", "foobar"))
        );
    }
}
