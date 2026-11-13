package examenAccesoUD1.services;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import examenAccesoUD1.models.ComparatorCriaturaPeligro;
import examenAccesoUD1.models.Criatura;
import examenAccesoUD1.models.CriaturasFantasticasException;
import examenAccesoUD1.models.Tipo;
import examenAccesoUD1.repositories.IRepositorioCriaturas;
import examenAccesoUD1.repositories.RepositorioCriaturas;

public class ServicioCriaturas implements IServicioCriaturas {
	Logger logger = LogManager.getLogger(ServicioCriaturas.class);
	IRepositorioCriaturas rp;
	Set<Criatura> criaturas;
	
	public ServicioCriaturas(String ruta) {
		this.rp = new RepositorioCriaturas(ruta);
		this.criaturas = new HashSet<Criatura>();
	}

	@Override
	public Set<Criatura> findAll() {
		if (this.criaturas == null || this.criaturas.size() == 0) {
			this.criaturas = this.rp.findAll();
		}
		return this.criaturas;
	}

	@Override
	public void alta(String nombre, Tipo tipo, boolean inmortal, int peligrosidad, int magia, String region, String clima) throws CriaturasFantasticasException {
		this.criaturas = this.rp.alta(nombre, tipo, inmortal, peligrosidad, magia, region, clima);
	}

	@Override
	public Criatura findById(String nombre) {
		Criatura criatura = null;
		try {
			criatura = this.rp.findById(nombre);
		} catch (CriaturasFantasticasException e) {
			logger.error(e.getMessage());
		}
		return criatura;
	}
	
	@Override
	public Set<Criatura> obtenerCriaturaDeTipo(Tipo tipo) {
		Set<Criatura> criaturasTipo = new TreeSet<Criatura>(new ComparatorCriaturaPeligro());
		
		for (Criatura criatura : this.criaturas) {
			if (criatura.getTipo().equals(tipo)) {
				criaturasTipo.add(criatura);
			}
		}
		
		return criaturasTipo;
	}

	@Override
	public void exportarJson(Tipo tipo) {
		Set<Criatura> criaturas = this.obtenerCriaturaDeTipo(tipo);
		try {
			this.rp.exportarJSON(criaturas, tipo + ".json");
		} catch (CriaturasFantasticasException e) {
			logger.error(e.getMessage());
		}					
	}
	
	

}
