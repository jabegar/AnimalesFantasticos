package examenAccesoUD1.models;

import java.util.Comparator;
import java.util.Objects;

public class Criatura implements Comparable<Criatura>{
	private String nombre;
	private Tipo tipo;
	private boolean inmortal;
	private int peligrosidad;
	private int magia;
	private Habitat habitat;
	
	public Criatura(String nombre, Tipo tipo, boolean inmortal, int peligrosidad, int magia, Habitat habitat) {
		super();
		this.nombre = nombre;
		this.tipo = tipo;
		this.inmortal = inmortal;
		this.setPeligrosidad(peligrosidad);
		this.setMagia(magia);
		this.habitat = habitat;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public boolean isInmortal() {
		return inmortal;
	}

	public void setInmortal(boolean inmortal) {
		this.inmortal = inmortal;
	}

	public int getPeligrosidad() {
		return peligrosidad;
	}

	public void setPeligrosidad(int peligrosidad) {
		this.peligrosidad = peligrosidad;
		
		if (this.peligrosidad > 100) {
			this.peligrosidad = 100;
		}
		else if (this.peligrosidad < 0) {
			this.peligrosidad = 0;
		}
	}

	public int getMagia() {
		return magia;
	}

	public void setMagia(int magia) {
		this.magia = magia;
		if (this.magia > 100) {
			this.magia = 100;
		}
		else if (this.magia < 0) {
			this.magia = 0;
		}
	}

	public Habitat getHabitat() {
		return habitat;
	}

	public void setHabitat(Habitat habitat) {
		this.habitat = habitat;
	}

	@Override
	public int hashCode() {
		return Objects.hash(nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Criatura other = (Criatura) obj;
		return Objects.equals(nombre, other.nombre);
	}

	@Override
	public String toString() {
		return "Criatura [nombre=" + this.nombre + ", tipo=" + this.tipo + ", region=" + this.habitat.getRegion() + "]";
	}

	@Override
	public int compareTo(Criatura o) {
		return this.getNombre().compareTo(o.getNombre());
	}
}
