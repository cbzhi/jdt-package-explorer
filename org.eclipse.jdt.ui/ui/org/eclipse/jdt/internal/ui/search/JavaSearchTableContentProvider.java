/*******************************************************************************
 * Copyright (c) 2000, 2003 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jdt.internal.ui.search;

import java.util.HashSet;
import java.util.Set;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;

public class JavaSearchTableContentProvider extends JavaSearchContentProvider implements IStructuredContentProvider {
	public JavaSearchTableContentProvider(JavaSearchResultPage page) {
		super(page);
	}
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof JavaSearchResult) {
			Set filteredElements= new HashSet();
			Object[] rawElements= ((JavaSearchResult)inputElement).getElements();
			for (int i= 0; i < rawElements.length; i++) {
				if (getPage().getDisplayedMatchCount(rawElements[i]) > 0)
					filteredElements.add(rawElements[i]);
			}
			return filteredElements.toArray();
		}
		return EMPTY_ARR;
	}

	public void elementsChanged(Object[] updatedElements) {
		if (fResult == null)
			return;
		int addCount= 0;
		int removeCount= 0;
		TableViewer viewer= (TableViewer) getPage().getViewer();
		for (int i= 0; i < updatedElements.length; i++) {
			if (getPage().getDisplayedMatchCount(updatedElements[i]) > 0) {
				if (viewer.testFindItem(updatedElements[i]) != null)
					viewer.refresh(updatedElements[i]);
				else
					viewer.add(updatedElements[i]);
				addCount++;
			} else {
				viewer.remove(updatedElements[i]);
				removeCount++;
			}
		}
	}

	public void filtersChanged(MatchFilter[] filters) {
		super.filtersChanged(filters);
		getPage().getViewer().refresh();
	}

	public void clear() {
		getPage().getViewer().refresh();
	}

}
