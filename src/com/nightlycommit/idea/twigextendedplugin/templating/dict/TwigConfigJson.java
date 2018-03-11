package com.nightlycommit.idea.twigextendedplugin.templating.dict;

import com.nightlycommit.idea.twigextendedplugin.templating.path.dict.TwigPathJson;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class TwigConfigJson {
    private Collection<TwigPathJson> namespaces = new ArrayList<>();

    public Collection<TwigPathJson> getNamespaces() {
        return namespaces;
    }
}
