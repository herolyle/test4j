package org.test4j.spec.inner;

import java.lang.annotation.Annotation;

import org.test4j.tools.commons.StringHelper;

public enum StepType {
    Step {
        @Override
        public Class<? extends Annotation> getAnnotatonClaz() {
            return org.test4j.spec.annotations.Step.class;
        }
    },
    Given {
        @Override
        public Class<? extends Annotation> getAnnotatonClaz() {
            return org.test4j.spec.annotations.Given.class;
        }
    },
    When {
        @Override
        public Class<? extends Annotation> getAnnotatonClaz() {
            return org.test4j.spec.annotations.When.class;
        }
    },
    Then {
        @Override
        public Class<? extends Annotation> getAnnotatonClaz() {
            return org.test4j.spec.annotations.Then.class;
        }
    };

    public abstract Class<? extends Annotation> getAnnotatonClaz();

    public static StepType getStepType(String type) {
        if (StringHelper.isBlankOrNull(type)) {
            return Step;
        }
        if ("given".equalsIgnoreCase(type)) {
            return Given;
        }
        if ("when".equalsIgnoreCase(type)) {
            return When;
        }
        if ("then".equalsIgnoreCase(type)) {
            return Then;
        }
        throw new RuntimeException("the step type can only be following values: Given, When and Then; but actual is "
                + type);
    }
}
