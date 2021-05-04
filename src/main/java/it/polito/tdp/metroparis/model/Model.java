package it.polito.tdp.metroparis.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.metroparis.db.MetroDAO;

public class Model {
	
	Graph<Fermata, DefaultEdge> grafo ;
	
	Map<Fermata, Fermata> predecessore;

	public void creaGrafo() {
		this.grafo = new SimpleGraph<>(DefaultEdge.class) ;
		
		MetroDAO dao = new MetroDAO() ;
		List<Fermata> fermate = dao.getAllFermate() ;
		
//		for(Fermata f : fermate) {
//			this.grafo.addVertex(f) ;
//		}
		
		Graphs.addAllVertices(this.grafo, fermate) ;
		
		// Aggiungiamo gli archi
		
//		for(Fermata f1: this.grafo.vertexSet()) {
//			for(Fermata f2: this.grafo.vertexSet()) {
//				if(!f1.equals(f2) && dao.fermateCollegate(f1, f2)) {
//					this.grafo.addEdge(f1, f2) ;
//				}
//			}
//		}
		
		List<Connessione> connessioni = dao.getAllConnessioni(fermate) ;
		for(Connessione c: connessioni) {
			this.grafo.addEdge(c.getStazP(), c.getStazA()) ;
		}
		
		System.out.format("Grafo creato con %d vertici e %d archi\n",
				this.grafo.vertexSet().size(), this.grafo.edgeSet().size()) ;
//		System.out.println(this.grafo) ;
		
		//TROVARE ARCHI ADIACENTI
	/*	
		Fermata f = null;
		
		Set<DefaultEdge> archi = this.grafo.edgesOf(f); //mi dà un Set di default edges
		for(DefaultEdge e : archi) {
			Fermata f1 = this.grafo.getEdgeSource(e);
			//oppure
			Fermata f2 = this.grafo.getEdgeTarget(e);
			if(f1.equals(f)) {
				//f2 è quello che mi serve
			}else {
				//f1 è quello che mi serve
			}
			
			f1 = Graphs.getOppositeVertex(this.grafo, e, f);
		}
		
		//										.predecessor  se archi orientati
		List<Fermata> fermateAdiacenti = Graphs.successorListOf(this.grafo, f);
	*/
		}
	
	public List<Fermata> fermateRaggiungibili(Fermata partenza){
		//attraverso un iteratore di visita
		BreadthFirstIterator<Fermata, DefaultEdge> bfv = new BreadthFirstIterator<>(this.grafo, partenza);
		
		bfv.addTraversalListener(new TraversalListener<Fermata, DefaultEdge>(){

			@Override
			public void connectedComponentFinished(ConnectedComponentTraversalEvent e) {
			
				
			}

			@Override
			public void connectedComponentStarted(ConnectedComponentTraversalEvent e) {
			
			}

			@Override
			public void edgeTraversed(EdgeTraversalEvent<DefaultEdge> e) {
			
			}

			@Override
			public void vertexTraversed(VertexTraversalEvent<Fermata> e) {
			 System.out.println(e.getVertex());
			}

			@Override
			public void vertexFinished(VertexTraversalEvent<Fermata> e) {
		
			}
			
		});
		
		List<Fermata> result = new ArrayList<>();
		
		while(bfv.hasNext()) {
			Fermata f = bfv.next();
			result.add(f);
		}
		
		return result;
	}
	
	public Fermata trovaFermata (String nome) {
		
		for(Fermata f : this.grafo.vertexSet()) {
			if(f.getNome().equals(nome))
				return f;
		}
		
		return null;
	}
	
}
