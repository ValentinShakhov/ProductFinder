package com.assignment.company.repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.assignment.company.model.Rule;
import com.assignment.company.util.RuleParser;

public class FileBasedRuleRepository implements RuleRepository {

    private final RuleParser ruleParser;
    private final Collection<Rule> rules = new ArrayList<>();

    public FileBasedRuleRepository(final RuleParser ruleParser) {
        this.ruleParser = ruleParser;
    }

    @Override
    public Collection<Rule> getRules() {
        return Collections.unmodifiableCollection(rules);
    }

    public void init(final String rulesFileName) throws IOException {
        try (final FileReader fileReader = new FileReader(rulesFileName)) {
            new BufferedReader(fileReader).lines()
                    .map(ruleParser::toRule)
                    .forEach(rules::add);
        }
    }
}
