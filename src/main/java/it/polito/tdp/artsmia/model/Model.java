package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	ArtsmiaDAO dao = new ArtsmiaDAO();
	Graph<Integer, DefaultWeightedEdge> grafo;
	List<Adiacenza> adiacenze;
	List<Integer> best;
	List<Integer> parziale;
	
	public Model() {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
	}

	public List<String> getRoles(){
		return this.dao.getRoles();
	}
	
	public void creaGrafo(String ruolo) {

		List<Integer> artisti = this.dao.getArtistiRuolo(ruolo);
		Graphs.addAllVertices(this.grafo, artisti);
		
		this.adiacenze = dao.getAllAdiacenze(ruolo);
		
		for(Adiacenza a : adiacenze) {
			Graphs.addEdgeWithVertices(this.grafo, a.getA1(), a.getA2(), a.getPeso());
		}
		System.out.println("VERTICI : "+this.grafo.vertexSet().size());
		System.out.println("ARCHI : "+this.grafo.edgeSet().size());
	}

	public Graph<Integer, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}

	public List<Adiacenza> getAdiacenze() {
		return adiacenze;
	}
	
	public void calcolaPercorso(int id) {
		parziale = new ArrayList<>();
		parziale.add(id);
		
		List<Integer> daVisitare = Graphs.neighborListOf(this.grafo, id);
		best = new ArrayList<>();
		
		int peso = 0;
		for(Integer vicino : Graphs.neighborListOf(this.grafo, id)) {
			if(grafo.getEdge(id, vicino)!=null) {
				peso = (int) grafo.getEdgeWeight(grafo.getEdge(id, vicino));
			}
			if(peso != 0)
				break;
		}
		//RICHIAMA METODO RICORSIVO
		ricorsiva(parziale, daVisitare, peso);
	}
	public void ricorsiva(List<Integer> parziale, List<Integer> daVisitare, int peso) {
		if(parziale.size() > best.size()) {
			//parziale diventa la nuova best se la dimensione è maggiore -> il che vuol dire che il cammino è più lungo
			best = new ArrayList<>(parziale);
		}else {
			//visita ricorsiva
			for(Integer vicino : daVisitare) {
				System.out.println("valuto "+vicino);
				DefaultWeightedEdge e = grafo.getEdge(parziale.size()-1, vicino);
				if(!e.equals(null)) {
					if(grafo.getEdgeWeight(e)==peso) {
						parziale.add(vicino);
						ricorsiva(parziale, Graphs.neighborListOf(this.grafo, vicino), peso);
						parziale.remove(parziale.size()-1);
					}
				}
			}
		}
	}

	public List<Integer> getBest() {
		return best;
	}


	
	
}
