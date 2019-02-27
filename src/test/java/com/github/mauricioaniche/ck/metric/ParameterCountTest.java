package com.github.mauricioaniche.ck.metric;

import com.github.mauricioaniche.ck.CKNumber;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Map;

public class ParameterCountTest  extends BaseTest{

	private static Map<String, CKNumber> report;

	@BeforeClass
	public static void setUp() {
		report = run(fixturesDir() + "/parametercount");
	}

	@Test
	public void justDeclaration() {
		CKNumber a = report.get("pcount.A");
		Map<String, Integer> variablesUsageA = a.getMethod("a/0").get().getVariablesUsage();
		Assert.assertEquals(new Integer(1), variablesUsageA.get("a"));
		Assert.assertEquals(new Integer(1), variablesUsageA.get("b"));
	}

	@Test
	public void normalUse() {
		CKNumber a = report.get("pcount.A");
		Map<String, Integer> variablesUsageA = a.getMethod("b/0").get().getVariablesUsage();
		Assert.assertEquals(new Integer(5), variablesUsageA.get("a")); // a appears 5 times
		Assert.assertEquals(new Integer(3), variablesUsageA.get("b"));
	}

	@Test
	public void passingVariableToMethod() {
		CKNumber a = report.get("pcount.A");
		Map<String, Integer> variablesUsageA = a.getMethod("bb/0").get().getVariablesUsage();
		Assert.assertEquals(new Integer(2), variablesUsageA.get("a"));
	}

	@Test
	public void methodParameters() {
		CKNumber a = report.get("pcount.A");
		Map<String, Integer> variablesUsageA = a.getMethod("c/1[int]").get().getVariablesUsage();
		Assert.assertEquals(new Integer(1), variablesUsageA.get("a"));

		Map<String, Integer> variablesUsageB = a.getMethod("d/2[int,int]").get().getVariablesUsage();
		Assert.assertEquals(new Integer(5), variablesUsageB.get("a"));
		Assert.assertEquals(new Integer(1), variablesUsageB.get("b"));
	}
}
