package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.artsmia.model.Adiacenza;
import it.polito.tdp.artsmia.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ArtsmiaController {
	
	private Model model ;
	Graph<Integer, DefaultWeightedEdge> grafo;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private Button btnArtistiConnessi;

    @FXML
    private Button btnCalcolaPercorso;

    @FXML
    private ComboBox<String> boxRuolo;

    @FXML
    private TextField txtArtista;

    @FXML
    private TextArea txtResult;

    @FXML
    void doArtistiConnessi(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Calcola artisti connessi");
    	
    	if(this.grafo.vertexSet().size()==0) {
    		txtResult.setText("Devi prima creare il grafo");
    	}else {
    		List<Adiacenza> adiacenze = this.model.getAdiacenze();
    		//ordinare e stampare
    		Collections.sort(adiacenze);
    		for(Adiacenza a : adiacenze) {
    			txtResult.appendText("\n"+a.getA1()+" "+a.getA2()+" "+a.getPeso());
    		}
    		
    	}
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Calcola percorso");
    	if(this.grafo.vertexSet().size()==0)
    		txtResult.setText("Devi prima creare il grafo");
    	
    	String s = txtArtista.getText();
    	if(s.equals(""))
    		txtResult.setText("Inserisci l'id di un artista");
    	int i;
    	try {
    		i = Integer.parseInt(s);
    		if(!this.grafo.vertexSet().contains(i)) {
    			txtResult.setText("Il vertice non appartiene al grafo");
    		}else {
    		
    			this.model.calcolaPercorso(i);
    			List<Integer> best = this.model.getBest();
    		} 
    	}catch(NumberFormatException e) {
    		e.printStackTrace();
    		txtResult.setText("devi inserire un numero");
    	}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Crea grafo");
    	
    	String ruolo = this.boxRuolo.getValue();
    	if(ruolo.equals(null)) {
    		txtResult.setText("Per favore seleziona un ruolo");
    	}else {
    		this.model.creaGrafo(ruolo);
    		this.grafo = this.model.getGrafo();
    		txtResult.setText("GRAFO CREATO\n");
    		txtResult.appendText("VERTICI : "+grafo.vertexSet().size()+"\n");
    		txtResult.appendText("ARCHI : "+grafo.edgeSet().size());
   
    	}
    	
    }

    public void setModel(Model model) {
    	this.model = model;
    	
    	this.boxRuolo.getItems().clear();
    	this.boxRuolo.getItems().addAll(this.model.getRoles());
    }

    
    @FXML
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnArtistiConnessi != null : "fx:id=\"btnArtistiConnessi\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnCalcolaPercorso != null : "fx:id=\"btnCalcolaPercorso\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert boxRuolo != null : "fx:id=\"boxRuolo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtArtista != null : "fx:id=\"txtArtista\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Artsmia.fxml'.";

    }
}
