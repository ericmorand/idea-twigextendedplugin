package com.nightlycommit.idea.twigextendedplugin.tests.completion.command;

import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyLightCodeInsightFixtureTestCase;

import java.io.File;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 * @see com.nightlycommit.idea.twigextendedplugin.completion.command.PhpCommandGotoCompletionRegistrar
 */
public class PhpCommandGotoCompletionRegistrar extends SymfonyLightCodeInsightFixtureTestCase {

    public void setUp() throws Exception {
        super.setUp();
        myFixture.configureFromExistingVirtualFile(myFixture.copyFileToProject("FooCommand.php"));
    }

    public String getTestDataPath() {
        return new File(this.getClass().getResource("fixtures").getFile()).getAbsolutePath();
    }

    public void testCommandArguments() {
        String[] argsLookup = {"arg1", "arg2", "arg3" ,"argDef"};
        assertAtTextCompletionContains("<getArgument>", argsLookup);
        assertAtTextCompletionContains("<hasArgument>", argsLookup);
    }

    public void testCommandOptions() {
        String[] optsLookup = {"opt1", "opt2", "opt3" ,"optDef", "optSingleDef"};
        assertAtTextCompletionContains("<getOption>", optsLookup);
        assertAtTextCompletionContains("<hasOption>", optsLookup);
    }

}
