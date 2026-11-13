package examenAccesoUD1.controllers;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import examenAccesoUD1.models.Criatura;
import examenAccesoUD1.models.CriaturasFantasticasException;
import examenAccesoUD1.models.Tipo;
import examenAccesoUD1.repositories.IRepositorioCriaturas;
import examenAccesoUD1.repositories.RepositorioCriaturas;
import examenAccesoUD1.services.IServicioCriaturas;
import examenAccesoUD1.services.ServicioCriaturas;

public class GestionaRegistro {

	public static void main(String[] args) {
		Logger logger = LogManager.getLogger(GestionaRegistro.class);
		IServicioCriaturas sc = new ServicioCriaturas("src/main/resources/criaturasfantasticas.csv");
		
		Set<Criatura> criaturas= sc.findAll();
		logger.debug(criaturas);
		logger.debug(criaturas.size());
		
		try {
			sc.alta("Bicho malo", Tipo.BESTIA, false, 50, 50, "Rivendell", "arido");
			sc.alta("Bicho malo", Tipo.SER, true, 50, 50, "Rivendell", "arido");
		} catch (CriaturasFantasticasException e) {
			logger.error(e.getMessage());
		}
		
		logger.debug(criaturas);
		
		logger.debug(sc.findById("Dementor"));
		logger.debug(sc.findById("Nundu"));
		
		Set<Criatura> criaturasTipo = sc.obtenerCriaturaDeTipo(Tipo.SER);
		logger.debug(criaturasTipo);
		
		sc.exportarJson(Tipo.BESTIA);
	}
}
