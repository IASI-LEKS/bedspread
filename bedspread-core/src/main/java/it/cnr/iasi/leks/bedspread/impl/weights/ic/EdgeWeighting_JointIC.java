/*
 * 	 This file is part of Bedspread, originally promoted and
 *	 developed at CNR-IASI. For more information visit:
 *	 https://github.com/IASI-LEKS/bedspread
 *	     
 *	 This is free software: you can redistribute it and/or modify
 *	 it under the terms of the GNU General Public License as 
 *	 published by the Free Software Foundation, either version 3 of the 
 *	 License, or (at your option) any later version.
 *	 
 *	 This software is distributed in the hope that it will be useful,
 *	 but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	 GNU General Public License for more details.
 * 
 *	 You should have received a copy of the GNU General Public License
 *	 along with this source.  If not, see <http://www.gnu.org/licenses/>.
 */
package it.cnr.iasi.leks.bedspread.impl.weights.ic;

import it.cnr.iasi.leks.bedspread.rdf.AnyResource;
import it.cnr.iasi.leks.bedspread.rdf.KnowledgeBase;
import it.cnr.iasi.leks.bedspread.rdf.impl.RDFTriple;

/**
 * The Implementation of this class refers to the following publication
 * Schuhumacher M., Ponzetto S.P. 
 * Knowledge-based Graph Document Modeling
 * WSDM'14 February 24-28, 2014, New York, New York, USA.
 *  
 * @author ftaglino
 *
 */
public class EdgeWeighting_JointIC extends Abstract_EdgeWeighting_IC {

	public EdgeWeighting_JointIC(KnowledgeBase kb) {
		super(kb);
	}
	
	/**
	 * Compute the frequence of the triples having node as the subject or as the object with respect to those triples having pred as the predicate 
	 *
	 * @param pred
	 * @param node
	 * @return
	 */
	protected double nodeProbabilityConditionalToPredicate(AnyResource pred, AnyResource node) {
		double result = 0.0;
		double total_triple_by_predicate = kb.countTriplesByPredicate(pred);
		double total_triple_by_predicate_and_node = kb.countTriplesByPredicateAndSubjectOrObject(pred, node);
		result = total_triple_by_predicate_and_node/total_triple_by_predicate;
		return result;
	}

	/**
	 * Compute the Information Content of a node knowing the predicate 
	 * 
	 * @param pred
	 * @param node
	 * @return
	 */
	protected double nodeConditionalToPredicate_IC(AnyResource pred, AnyResource node) {
		double result = 0.0;
		result = - Math.log(nodeProbabilityConditionalToPredicate(pred, node));
		return result;
	}
	
	/**
	 * Compute the Joint Information Content (jointIC) of an edge, which is identified by a triple 
	 * 
	 * edgeWeight_jointIC
	 * 
	 * @param edge
	 * @return
	 */
	@Override
	public double computeEdgeWeight(RDFTriple edge) {
		double result = 0.0;
		result = predicate_IC(edge.getTriplePredicate()) +  nodeConditionalToPredicate_IC(edge.getTriplePredicate(), edge.getTripleObject());
		return result;
	}
	
}
