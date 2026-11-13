package examenAccesoUD1.repositories;

import java.util.Set;

import examenAccesoUD1.models.*;

public interface IRepositorioCriaturas {
	public Set<Criatura> findAll();
	public Set<Criatura> alta(String nombre, Tipo tipo, boolean inmortal, int peligrosidad, int magia, String region, String clima) throws CriaturasFantasticasException;
	public Criatura findById(String nombre) throws CriaturasFantasticasException;
	public void exportarJSON(Set<Criatura> criaturas, String nombreFichero) throws CriaturasFantasticasException;
}
