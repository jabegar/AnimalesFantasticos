package examenAccesoUD1.services;

import java.util.Set;

import examenAccesoUD1.models.Criatura;
import examenAccesoUD1.models.CriaturasFantasticasException;
import examenAccesoUD1.models.Tipo;

public interface IServicioCriaturas {
	public Set<Criatura> findAll();
	public void alta(String nombre, Tipo tipo, boolean inmortal, int peligrosidad, int magia, String region, String clima) throws CriaturasFantasticasException;
	public Criatura findById(String nombre);
	public Set<Criatura> obtenerCriaturaDeTipo(Tipo tipo);
	public void exportarJson(Tipo tipo);
}
