/*
 * 	 This file is part of Bedspread, originally promoted and
 *	 developed at CNR-IASI. For more information visit:
 *	 https://github.com/IASI-LEKS/bedspread
 *	     
 *	 This is free software: you can redistribute it and/or modify
 *	 it under the terms of the GNU Lesser General Public License as 
 *	 published by the Free Software Foundation, either version 3 of the 
 *	 License, or (at your option) any later version.
 *	 
 *	 This software is distributed in the hope that it will be useful,
 *	 but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	 GNU Lesser General Public License for more details.
 * 
 *	 You should have received a copy of the GNU Lesser General Public License
 *	 along with this source.  If not, see <http://www.gnu.org/licenses/>.
 */
package it.cnr.iasi.leks.bedspread.tests;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import it.cnr.iasi.leks.bedspread.AbstractSemanticSpread;
import it.cnr.iasi.leks.bedspread.ComputationStatus;
import it.cnr.iasi.leks.bedspread.Node;
import it.cnr.iasi.leks.bedspread.SematicSpreadFactory;
import it.cnr.iasi.leks.bedspread.policies.SimpleTerminationPolicy;
import it.cnr.iasi.leks.bedspread.policies.TerminationPolicy;
import it.cnr.iasi.leks.bedspread.rdf.AnyResource;
import it.cnr.iasi.leks.bedspread.rdf.AnyURI;
import it.cnr.iasi.leks.bedspread.rdf.KnowledgeBase;
import it.cnr.iasi.leks.bedspread.rdf.impl.RDFGraph;

import org.junit.Assert;
import org.junit.Test;

/*
 * @author gulyx
 */
public class SemanticSpreadTest extends AbstractTest{

	private RDFGraph rdfGraph;
	private static final String ORIGIN_LABEL = "origin";
	
	@Test
	public void firstMinimalTest() throws IOException{
		KnowledgeBase kb = this.loadMinimalKB();
		Node resourceOrigin = this.extractTrivialOrigin();
		TerminationPolicy term = new SimpleTerminationPolicy();
		
		AbstractSemanticSpread ss = SematicSpreadFactory.getInstance().getSemanticSpread(resourceOrigin,kb,term);
		ss.run();
		
		boolean condition = ss.getComputationStatus().equals(ComputationStatus.Completed);
		Assert.assertTrue(condition);		
	}
	
	private Node extractTrivialOrigin() {
		Set<AnyResource> s = this.rdfGraph.listOfResources();
		AnyResource resource = null;
		boolean foundIt = false;
		for (Iterator<AnyResource> iterator = s.iterator(); iterator.hasNext() && !foundIt;) {
			AnyResource anyResource = iterator.next();
			if (anyResource.getResourceID().equalsIgnoreCase(ORIGIN_LABEL)){
				resource = anyResource;
				foundIt = true;
			}		
		}
//		Pick a random element in the set
		if (resource == null){
			resource = s.iterator().next();
		}		
		Node n = new Node(resource);
		return n;
	}

	private KnowledgeBase loadMinimalKB() throws IOException{
		FileReader kbReader = new FileReader("src/test/resources/simpleRDFGraph.csv");
		this.rdfGraph = new RDFGraph(kbReader);
		
		return this.rdfGraph;
	}
	
	
}