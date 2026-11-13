package examenAccesoUD1.models;

import java.util.Comparator;

public class ComparatorCriaturaPeligro implements Comparator<Criatura> {

	@Override
	public int compare(Criatura o1, Criatura o2) {
		if (o1.getPeligrosidad() > o2.getPeligrosidad()) {
			return 1;
		}
		else if (o1.getPeligrosidad() < o2.getPeligrosidad()) {
			return -1;
		}
		else {
			return o1.getNombre().compareTo(o2.getNombre());
		}
	}

}
 