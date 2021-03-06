/*******************************************************************************
 * Copyright (c) 2000, 2011 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jdt.ui.tests.search;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.search2.internal.ui.InternalSearchUI;

import org.eclipse.jdt.ui.leaktest.LeakTestCase;
import org.eclipse.jdt.ui.leaktest.LeakTestSetup;

import org.eclipse.jdt.internal.ui.search.JavaSearchQuery;
import org.eclipse.jdt.internal.ui.search.JavaSearchResult;

/**
 */
public class SearchLeakTest extends LeakTestCase {

	private static final Class THIS= SearchLeakTest.class;

	public SearchLeakTest(String name) {
		super(name);
	}

	public static Test suite() {
		return setUpTest(new TestSuite(THIS));
	}

	public static Test setUpTest(Test test) {
		return new LeakTestSetup(new JUnitSourceSetup(test));
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testRemoveSearchQueries() throws Exception {
		InternalSearchUI.getInstance().removeAllQueries();
		JavaSearchQuery query1= SearchTestHelper.runMethodRefQuery("junit.framework.Test", "countTestCases", new String[0]);
		JavaSearchQuery query2= SearchTestHelper.runMethodRefQuery("junit.framework.TestCase", "countTestCases", new String[0]);
		InternalSearchUI.getInstance().removeQuery(query1);
		InternalSearchUI.getInstance().removeQuery(query2);
		query1= null;
		query2= null;
		assertInstanceCount(JavaSearchResult.class, 0);
	}
	public void testRemoveAllQueries() throws Exception {
		SearchTestHelper.runMethodRefQuery("junit.framework.Test", "countTestCases", new String[0]);
		SearchTestHelper.runMethodRefQuery("junit.framework.TestCase", "countTestCases", new String[0]);
		InternalSearchUI.getInstance().removeAllQueries();
		assertInstanceCount(JavaSearchResult.class, 0);
	}
}
