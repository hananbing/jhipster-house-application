package com.sat.house;

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
            .importPackages("com.sat.house");

        noClasses()
            .that()
                .resideInAnyPackage("com.sat.house.service..")
            .or()
                .resideInAnyPackage("com.sat.house.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.sat.house.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
