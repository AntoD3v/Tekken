package com.tekken.template.impl;

import com.tekken.template.TemplatePage;

public interface TemplateBuilder {

    void configurationBuilder();
    void importationBuilder();

    void cleanup();

    TemplatePage build();

}
