package it.polito.tdp.newufosightings.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.newufosightings.db.NewUfoSightingsDAO;

public class Model {
	
	private NewUfoSightingsDAO dao;
	private Graph<State, DefaultWeightedEdge> graph;
	private Map<String, State> states;
	private Simulator sim;
	
	public Model() {
		this.dao = new NewUfoSightingsDAO();
		this.states = new HashMap<>();
	}
	
	public List<String> getShapes(Integer anno) {
		return this.dao.getShapes(anno);
	}
	
	public void generateGraph(String shape, Integer anno) {
		this.graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(this.graph, this.dao.loadAllStates(this.states).values());
		
		List<Adiacenza> adiacenze = new ArrayList<>(this.dao.getAdiacenze(anno, shape, this.states));
		for(Adiacenza a : adiacenze) {
			Graphs.addEdge(this.graph, a.getS1(), a.getS2(), a.getPeso());
		}
	}
	
	public Integer getNumVertici() {
		return this.graph.vertexSet().size();
	}
	
	public Integer getNumArchi() {
		return this.graph.edgeSet().size();
	}
	
	public List<StatoNumero> getStatoNum() {
		List<StatoNumero> statoNumero = new ArrayList<>();
		Integer somma = 0;
		
		for(State s : this.states.values()) {
			for(DefaultWeightedEdge e : this.graph.edgesOf(s)) {
				somma += (int) this.graph.getEdgeWeight(e);
			}
			StatoNumero stato = new StatoNumero(s, somma);
			statoNumero.add(stato);
		}
		return statoNumero;
	}
	
	public void simula(Integer t, Integer alfa, Integer anno, String shape) {
		this.sim = new Simulator();
		this.sim.init(this, this.getAllSighting(anno, shape), t, alfa);
		this.sim.run();
	}
	
	public List<Sighting> getAllSighting(Integer anno, String shape) {
		return this.dao.getAllSighting(anno, shape);
	}
	
	public List<State> getStates() {
		List<State> s = new ArrayList<>(this.states.values());
		return s;
	}
	
	public List<State> getVicini(State state) {
		return Graphs.neighborListOf(this.graph, state);
	}
	
	public Map<String, State> getStateMap() {
		return this.states;
	}
	
	public List<StatoLivello> getLivelloAllerta() {
		return this.sim.getLivelliAllerta();
	}

}
