package com.nightlycommit.idea.twigextendedplugin.profiler.collector;

import com.nightlycommit.idea.twigextendedplugin.profiler.dict.MailMessage;
import com.nightlycommit.idea.twigextendedplugin.profiler.dict.MailMessage;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public interface MailCollectorInterface {
    @NotNull
    Collection<MailMessage> getMessages();
}
