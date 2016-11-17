package com.clxcommunications.xms.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.immutables.value.Value;
import org.immutables.value.Value.Style.ImplementationVisibility;

@Target({ ElementType.PACKAGE, ElementType.TYPE })
@Retention(RetentionPolicy.CLASS)
@Value.Style(depluralize = true, from = "using", jdkOnly = true,
        overshadowImplementation = true,
        visibility = ImplementationVisibility.PACKAGE, typeImmutable = "*Impl",
        depluralizeDictionary = { "batch:batches", "status:statuses" })
@interface ValueStylePackage {

}
