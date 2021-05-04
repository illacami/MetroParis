package it.polito.tdp.metroparis.model;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

public class Prova {

	public static void main(String[] args) {
		Graph<String,DefaultEdge> grafo = new SimpleGraph<>(DefaultEdge.class);
		
		grafo.addVertex("UNO");
		grafo.addVertex("DUE");
		grafo.addVertex("TRE");
		grafo.addEdge("UNO", "DUE");
		
		System.out.println(grafo);
	}
}
