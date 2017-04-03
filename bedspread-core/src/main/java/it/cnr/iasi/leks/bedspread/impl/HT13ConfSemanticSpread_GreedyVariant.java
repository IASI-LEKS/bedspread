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
package it.cnr.iasi.leks.bedspread.impl;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import com.opencsv.CSVWriter;

import it.cnr.iasi.leks.bedspread.AbstractSemanticSpread;
import it.cnr.iasi.leks.bedspread.ComputationStatus;
import it.cnr.iasi.leks.bedspread.Node;
import it.cnr.iasi.leks.bedspread.TerminationPolicy;
import it.cnr.iasi.leks.bedspread.WeightingFunction;
import it.cnr.iasi.leks.bedspread.config.PropertyUtil;
import it.cnr.iasi.leks.bedspread.exceptions.impl.InteractionProtocolViolationException;
import it.cnr.iasi.leks.bedspread.impl.weights.WeightingFunctionFactory;
import it.cnr.iasi.leks.bedspread.rdf.AnyResource;
import it.cnr.iasi.leks.bedspread.rdf.KnowledgeBase;

/**
 * 
 * @author gulyx
 *
 */
public class HT13ConfSemanticSpread_GreedyVariant extends HT13ConfSemanticSpread {

	public HT13ConfSemanticSpread_GreedyVariant(Node origin, KnowledgeBase kb) throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		super(origin, kb);
	}

	public HT13ConfSemanticSpread_GreedyVariant(Node origin, KnowledgeBase kb, TerminationPolicy term) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		super(origin, kb, term);
	}

	@Override
	protected double computeScore(Node spreadingNode, Node targetNode) {

		this.configureWeightNodes(targetNode, spreadingNode);
		double score = this.calculateScore(spreadingNode, targetNode);
		
		return score;
	}

}
