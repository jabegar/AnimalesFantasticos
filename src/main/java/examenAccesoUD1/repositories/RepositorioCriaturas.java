package examenAccesoUD1.repositories;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import java.util.TreeSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import examenAccesoUD1.models.Criatura;
import examenAccesoUD1.models.CriaturasFantasticasException;
import examenAccesoUD1.models.Habitat;
import examenAccesoUD1.models.Tipo;

public class RepositorioCriaturas implements IRepositorioCriaturas {
	Logger logger = LogManager.getLogger(RepositorioCriaturas.class);
	Set<Criatura> criaturas;
	File bdd;
	
	public RepositorioCriaturas(String ruta) {
		this.bdd = new File(ruta);
		// He utilizado TreeSet porque el Set no permite objetos iguales y al hacerlo Tree, lo convierte en un Set ordenado 
		this.criaturas = new TreeSet<Criatura>();
	}
	
	private Set<Criatura> readFichero() {
		Set<Criatura> tmp = new TreeSet<Criatura>();
		if (this.bdd.exists() && this.bdd.isFile()) {
			try {
				FileReader fr  = new FileReader(bdd);
				BufferedReader br = new BufferedReader(fr);
				br.readLine();
				String linea = br.readLine();
				int numeroLinea = 2;
				while(linea != null) {
					String[] lineaSeparada = linea.split(";");
					
					String nombre = "";
					Tipo tipo = Tipo.BESTIA;
					boolean inmortal = false;
					int peligrosidad = 0;
					int magia = 0;
					String region = "";
					String clima = "";
					try {
						for (int i = 0; i < lineaSeparada.length; i++) {
							if (i == 0) {
								nombre = lineaSeparada[i];
								if (nombre.equals("")) {
									throw new CriaturasFantasticasException("Dato en fichero no valido, no se ha podido cargar la linea " + numeroLinea + ": debido a que el nombre no es valido");
								}
							}
							else if (i == 1) {
								String lineaTipo = lineaSeparada[i];
								tipo = Tipo.BESTIA;
								if (lineaTipo.equals("ESPIRITU")) {
									tipo = Tipo.ESPIRITU;
								}
								else if (lineaTipo.equals("SER")) {
									tipo = Tipo.SER;
								}
								else if (lineaTipo.equals("BESTIA")) {
									tipo = Tipo.BESTIA;
								}
								else {
									throw new CriaturasFantasticasException("Dato en fichero no valido, no se ha podido cargar la linea " + numeroLinea + ": debido a que el tipo no es valido");
								}
							}
							else if (i == 2) {
								inmortal = lineaSeparada[i].equals("true");
							}
							else if (i == 3) {
								peligrosidad = Integer.parseInt(lineaSeparada[i]);
							}
							else if (i == 4) {
								magia = Integer.parseInt(lineaSeparada[i]);
								
							}
							else if (i == 5) {
								region = lineaSeparada[i];						
							}
							else if (i == 6) {
								clima = lineaSeparada[i];
							}
						}
						Habitat h = new Habitat(region, clima);
						Criatura c = new Criatura(nombre, tipo, inmortal, peligrosidad, magia, h);
						tmp.add(c);
					}
					catch (CriaturasFantasticasException e) {
						logger.error(e.getMessage());
					}
					finally {
						numeroLinea++;
						linea = br.readLine();
					}
				}
			} catch (FileNotFoundException e) {
				logger.error(e.getMessage());
			}
			catch (IOException e) {
				logger.error(e.getMessage());
			}
			catch (NumberFormatException e) {
				logger.error(e.getMessage());
			}
			
			
		}
				
		return tmp;
	}

	@Override
	public Set<Criatura> findAll() {
		if (this.criaturas == null || this.criaturas.size() == 0) {
			this.criaturas = this.readFichero();
		}
		return this.criaturas;
	}
	
	@Override
	public Set<Criatura> alta(String nombre, Tipo tipo, boolean inmortal, int peligrosidad, int magia, String region, String clima) throws CriaturasFantasticasException {
		Criatura c = new Criatura(nombre, tipo, inmortal, peligrosidad, magia, new Habitat(region, clima));
		Set<Criatura> copia = this.criaturas;
		if (copia.contains(c)) {
			throw new CriaturasFantasticasException("Ya existe esta criatura");
		}
		else {
			copia.add(c);
			this.escribirFichero(copia);
			this.criaturas = copia;
		}
		return this.criaturas;
	}
	
	private void escribirFichero(Set<Criatura> criaturas) {
		try {
			PrintWriter pw = new PrintWriter(this.bdd);
			pw.println("nombre;tipo;inmortal;peligrosidad;magia;region;clima");
			for (Criatura c : criaturas) {
				pw.println(c.getNombre() + ";" + c.getTipo() + ";" + c.isInmortal() + ";" + c.getPeligrosidad() + ";" + c.getMagia() + ";" + c.getHabitat().getRegion() + ";" + c.getHabitat().getClima());
			}
			pw.close();
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		}
	}
	
	public Criatura findById(String nombre) throws CriaturasFantasticasException {
		Criatura criatura = null;
		for (Criatura c : this.criaturas) {
			if (c.getNombre().equals(nombre)) {
				criatura = c;
			}
		}
		
		if (criatura != null) {
			return criatura;
		}
		else {
			throw new CriaturasFantasticasException("La criatura que buscas no existe");
		}
		
	}
	
	public void exportarJSON(Set<Criatura> criaturas, String nombreFichero) throws CriaturasFantasticasException {
		if (criaturas.size() != 0) {
			File jsonFile = new File("src/main/resources/" + nombreFichero);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json = gson.toJson(criaturas);
			try {
				FileWriter fw = new FileWriter(jsonFile);
				fw.write(json);
				fw.close();
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		else {
			throw new CriaturasFantasticasException("No hay criaturas de este tipo");
		}
	}
}
