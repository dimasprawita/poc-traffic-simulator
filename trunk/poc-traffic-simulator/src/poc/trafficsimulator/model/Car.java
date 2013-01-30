package poc.trafficsimulator.model;
import java.util.List;

import javax.swing.JOptionPane;

import poc.trafficsimulator.control.RoadManager;

public class Car {

	private Percurso percurso;
	private int trechoAtual;
	private int numeroAtual;
	private boolean fim = false;

	public Car(Percurso p) {
		percurso = p;
		trechoAtual = 0;
		numeroAtual = 0;
	}

	public void add(Trecho trecho) {
		percurso.add(trecho);
	}

	public void iniciar() {
		trechoAtual = 0;
		List<Trecho> trechos = percurso.getTrechos();
		if (trechos.size() == 0) {
			return;
		}
		Trecho trecho = trechos.get(trechoAtual);
		numeroAtual = trecho.getInicio();
		RoadManager.getInstance().move(null, this);
	}

	public void andar() {
		if (fim) {
			return;
		}
		String endereoTemp = getEnderecoAtual();
		int trechoTemp = trechoAtual;
		int numeroTemp = numeroAtual;
		List<Trecho> trechos = percurso.getTrechos();
		Trecho trecho = trechos.get(trechoAtual);
		if (trecho.isOrdemCrescente()) {
			numeroAtual += 20;
		} else {
			numeroAtual -= 20;
		}
		boolean fimTrecho = numeroAtual > trecho.getFim();
		if (!trecho.isOrdemCrescente()) {
			fimTrecho = numeroAtual < trecho.getFim();
		}
		if (fimTrecho) {
			if (trechoAtual + 1 >= trechos.size()) {
				iniciar();
			} else {
				trechoAtual++;
				numeroAtual = trechos.get(trechoAtual).getInicio();
			}
		}
		boolean directionEquals = RoadManager.getInstance().getCarro(this,
				endereoTemp) != null
				&& RoadManager.getInstance().getCarro(this, endereoTemp)
						.gettrechoAtual().isOrdemCrescente() == gettrechoAtual()
						.isOrdemCrescente();
		if (!RoadManager.getInstance().isEmpty(getEnderecoAtual())
				&& directionEquals) {
			numeroAtual = numeroTemp;
			trechoAtual = trechoTemp;
			JOptionPane.showMessageDialog(null, "parado em "+getEnderecoAtual());
		} else {
			RoadManager.getInstance().move(endereoTemp, this);
		}
	}

	public Trecho gettrechoAtual() {
		List<Trecho> trechos = percurso.getTrechos();
		return trechos.get(trechoAtual);
	}

	public String getEnderecoAtual() {
		return percurso.getTrechos().get(trechoAtual).getRua() + ", "
				+ numeroAtual;
	}

}
