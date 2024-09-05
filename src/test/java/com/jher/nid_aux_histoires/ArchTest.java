package com.jher.nid_aux_histoires;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.jher.nid_aux_histoires");

        noClasses()
            .that()
                .resideInAnyPackage("com.jher.nid_aux_histoires.service..")
            .or()
                .resideInAnyPackage("com.jher.nid_aux_histoires.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.jher.nid_aux_histoires.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
