package it.polito.tdp.artsmia.model;

public class Adiacenza implements Comparable<Adiacenza> {

	private int a1;
	private int a2;
	private int peso;
	public Adiacenza(int a1, int a2, int peso) {
		super();
		this.a1 = a1;
		this.a2 = a2;
		this.peso = peso;
	}
	public int getA1() {
		return a1;
	}
	public int getA2() {
		return a2;
	}
	public int getPeso() {
		return peso;
	}
	@Override
	public int compareTo(Adiacenza o) {
		// TODO Auto-generated method stub
		return (int)(o.peso-this.peso);
	}
	
	
}
