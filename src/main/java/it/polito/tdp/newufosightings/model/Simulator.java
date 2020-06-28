package it.polito.tdp.newufosightings.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;

import it.polito.tdp.newufosightings.model.Event.EventType;

public class Simulator {
	
	private Model model;
	private PriorityQueue<Event> queue;
	private List<Sighting> sig;
	private Integer t;
	private Integer alfa;
	private List<StatoLivello> livelloAllerta;
	private Map<String, State> stateMap;
	
	private final Integer DEFCON5 = 5;
	
	public void init(Model model, List<Sighting> sig, Integer t, Integer alfa) {
		this.t = t;
		this.alfa = alfa;
		this.model = model;
		this.sig = sig;
		this.queue = new PriorityQueue<>();
		this.livelloAllerta = new ArrayList<>();
		this.stateMap = this.model.getStateMap();
		
		for(State s : this.model.getStates()) {
			this.livelloAllerta.add(new StatoLivello(s, (float) this.DEFCON5));
		}
		
		this.generateEvents();
	}
	
	public void generateEvents() {		
		for(Sighting s : this.sig) {
			Event e = new Event(EventType.AVVISTAMENTO, s.getDatetime(), this.stateMap.get(s.getState()));
			this.queue.add(e);
		}
	}
	
	public void run() {
		while(!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			processEvent(e);			
		}
	}
	
	public void processEvent(Event e) {
		
		switch(e.getTipo()) {
		
		case AVVISTAMENTO:
			
		Random random = new Random();
		StatoLivello sl = this.getStatoLivello(e.getState());
		
		// caso di decremento per l'avvistamento		
		if(sl.getLivello()!=1) {
			this.livelloAllerta.get(this.livelloAllerta.indexOf(sl)).decrementa(1);
			Event ev = new Event(EventType.INCREMENTO, e.getTime().plusDays(this.t), e.getState(), 1);
			queue.add(ev);
		}
		
		// caso in cui con probabilità alfa si decrementa anche negli stati adiacenti
		Integer p = random.nextInt(100)+1;
		if(p<=this.alfa) {
			List<State> vicini = this.model.getVicini(sl.getS());
			for(State s : vicini) {
				StatoLivello st = this.getStatoLivello(s);
	
				if(this.livelloAllerta.get(this.livelloAllerta.indexOf(st)).getLivello()!=0) {
					this.livelloAllerta.get(this.livelloAllerta.indexOf(st)).decrementa((float) 0.5);
					Event ev = new Event(EventType.INCREMENTO, e.getTime().plusDays(this.t), st.getS(), (float) 0.5);
					queue.add(ev);
				}
			}
		}
		
		break;
		
		case INCREMENTO:
			
			StatoLivello sl1 = this.getStatoLivello(e.getState());
			
			if(this.livelloAllerta.get(this.livelloAllerta.indexOf(sl1)).getLivello()!=5) {
				this.livelloAllerta.get(this.livelloAllerta.indexOf(sl1)).incrementa(e.getIncremento());
			}
			
			break;
		}
	}
	
	public StatoLivello getStatoLivello(State state) {		
		for(StatoLivello sn : livelloAllerta) {
			if((sn.getS()).equals(state)) {
				return sn;
			}
		}
		return null;
	}
	
	public List<StatoLivello> getLivelliAllerta() {
		return this.livelloAllerta;
	}

}
