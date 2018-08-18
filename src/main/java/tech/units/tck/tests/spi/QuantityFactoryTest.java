/*
 * Units of Measurement TCK
 * Copyright © 2005-2017, Jean-Marie Dautelle, Werner Keil, V2COM.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions
 *    and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of JSR-385 nor the names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package tech.units.tck.tests.spi;

import static org.testng.AssertJUnit.assertNotNull;
import static tech.units.tck.TCKRunner.SPEC_ID;
import static tech.units.tck.TCKRunner.SPEC_VERSION;

import java.util.Set;

import javax.measure.Quantity;
import javax.measure.spi.QuantityFactory;
import javax.measure.spi.ServiceProvider;

import org.jboss.test.audit.annotations.SpecAssertion;
import org.jboss.test.audit.annotations.SpecVersion;
import org.reflections.Reflections;
import org.testng.annotations.Test;

/**
 * Tests for QuantityFactory
 *
 * @author  <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 1.0, August 16, 2016
 * @since 1.0
 */
@SpecVersion(spec = SPEC_ID, version = SPEC_VERSION)
public class QuantityFactoryTest {
    private static final String MEASURE_PACKAGE = "javax.measure";
    private static final String SECTION = "5.1";
    
    /**
     * Ensure at least one QuantityFactory implementation exists
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test(groups = {"spi"}, description = SECTION + " Ensure a QuantityFactory implementation exists for every ServiceProvider")
    @SpecAssertion(section = SECTION, id = "51-A1")
    public void testQuantityFactoryExists() {
	for (ServiceProvider provider : ServiceProvider.available()) {
	    assertNotNull("Section " + SECTION + ": ServiceProvider is null", provider);
            Reflections reflections = new Reflections(MEASURE_PACKAGE);
            Set<Class<? extends Quantity>> subTypes = reflections.getSubTypesOf(Quantity.class);
            for (Class clazz : subTypes) {
                QuantityFactory<?> factory = provider.getQuantityFactory(clazz);
                assertNotNull("Section " + SECTION + ": No QuantityFactory available for " + clazz.getSimpleName()
                	+ " in " + provider, factory);
            }
	}
    }
}