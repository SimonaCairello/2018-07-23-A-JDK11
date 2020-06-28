/**
 * Sample Skeleton for 'NewUfoSightings.fxml' Controller Class
 */

package it.polito.tdp.newufosightings;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.newufosightings.model.Model;
import it.polito.tdp.newufosightings.model.StatoLivello;
import it.polito.tdp.newufosightings.model.StatoNumero;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class NewUfoSightingsController {

	private Model model;
	
	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader

	@FXML // fx:id="txtAnno"
	private TextField txtAnno; // Value injected by FXMLLoader

	@FXML // fx:id="btnSelezionaAnno"
	private Button btnSelezionaAnno; // Value injected by FXMLLoader

	@FXML // fx:id="cmbBoxForma"
	private ComboBox<String> cmbBoxForma; // Value injected by FXMLLoader

	@FXML // fx:id="btnCreaGrafo"
	private Button btnCreaGrafo; // Value injected by FXMLLoader

	@FXML // fx:id="txtT1"
	private TextField txtT1; // Value injected by FXMLLoader

	@FXML // fx:id="txtAlfa"
	private TextField txtAlfa; // Value injected by FXMLLoader

	@FXML // fx:id="btnSimula"
	private Button btnSimula; // Value injected by FXMLLoader

	@FXML
	void doCreaGrafo(ActionEvent event) {
		this.txtResult.clear();
		String shape = this.cmbBoxForma.getValue();
		if(shape==null) {
			txtResult.appendText("Scegliere una forma dal menu a tendina!\n");
			return;
		}
		
		Integer anno = 0;
		try {
			anno = Integer.parseInt(this.txtAnno.getText());
		} catch (NumberFormatException e) {
			this.txtResult.appendText("Inserire un numero intero!\n");
			return;
		}
		
		if(anno<1910 || anno>2014) {
			this.txtResult.appendText("Inserire un numero compreso tra 1010 e 2014!\n");
			return;
		}
		
		this.model.generateGraph(shape, anno);
		this.txtResult.appendText("Il grafo è stato creato!\n");
		this.txtResult.appendText("Il numero di vertici del grafo è: "+this.model.getNumVertici()+"\n");
		this.txtResult.appendText("Il numero di archi del grafo è: "+this.model.getNumArchi()+"\n\n");
		this.txtResult.appendText("La somma dei pesi degli archi adiacenti per ogni stato è:\n");
		
		List<StatoNumero> statoNumero = this.model.getStatoNum();
		for(StatoNumero sn : statoNumero) {
			this.txtResult.appendText(sn.toString()+"\n");
		}
		
		this.btnSimula.setDisable(false);
	}

	@FXML
	void doSelezionaAnno(ActionEvent event) {
		this.txtResult.clear();
		
		Integer anno = 0;
		try {
			anno = Integer.parseInt(this.txtAnno.getText());
		} catch (NumberFormatException e) {
			this.txtResult.appendText("Inserire un numero intero!\n");
			return;
		}
		
		if(anno<1910 || anno>2014) {
			this.txtResult.appendText("Inserire un numero compreso tra 1010 e 2014!\n");
			return;
		}
		
		this.cmbBoxForma.getItems().setAll(this.model.getShapes(anno));
		this.btnCreaGrafo.setDisable(false);
	}

	@FXML
	void doSimula(ActionEvent event) {
		this.txtResult.clear();
		
		Integer numGiorni = 0;
		try {
			numGiorni = Integer.parseInt(this.txtT1.getText());
		} catch (NumberFormatException e) {
			this.txtResult.appendText("Inserire un numero intero!\n");
			return;
		}
		
		if(numGiorni>365 || numGiorni<1) {
			this.txtResult.appendText("Inserire un numero di giorni maggiori di 0 e minori di 365!\n");
			return;
		}
		
		Integer alfa = 0;
		try {
			alfa = Integer.parseInt(this.txtAlfa.getText());
		} catch (NumberFormatException e) {
			this.txtResult.appendText("Inserire un numero intero!\n");
			return;
		}
		
		if(alfa<0 || alfa>100) {
			this.txtResult.appendText("Inserire un numero compreso tra 0 e 100!\n");
			return;
		}
		
		String shape = this.cmbBoxForma.getValue();
		if(shape==null) {
			txtResult.appendText("Scegliere una forma dal menu a tendina!\n");
			return;
		}
		
		Integer anno = 0;
		try {
			anno = Integer.parseInt(this.txtAnno.getText());
		} catch (NumberFormatException e) {
			this.txtResult.appendText("Inserire un numero intero!\n");
			return;
		}
		
		this.model.simula(numGiorni, alfa, anno, shape);
		
		this.txtResult.appendText("I diversi livelli di allerta per i vari stati sono:\n");
		
		List<StatoLivello> livelli = this.model.getLivelloAllerta();
		for(StatoLivello s : livelli) {
			this.txtResult.appendText(s.toString()+"\n");
		}
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
		assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
		assert btnSelezionaAnno != null : "fx:id=\"btnSelezionaAnno\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
		assert cmbBoxForma != null : "fx:id=\"cmbBoxForma\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
		assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
		assert txtT1 != null : "fx:id=\"txtT1\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
		assert txtAlfa != null : "fx:id=\"txtAlfa\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
		assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";

	}

	public void setModel(Model model) {
		this.model = model;
		this.btnCreaGrafo.setDisable(true);
		this.btnSimula.setDisable(true);
	}
}
