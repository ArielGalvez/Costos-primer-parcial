package costosModif2;
import java.util.ArrayList;


public class ordenP{
	private ArrayList<String> ordenes;
	private String fecha="";
	private ArrayList<Double> preciosU;
	public ordenP(String f){
		ordenes = new ArrayList<String>();
		preciosU = new ArrayList<Double>();
		fecha=f;
	}
	public void agregarOp(String op, double p){
		ordenes.add(op);
		preciosU.add(p);
	}
	/*public void eliminarUltimo(){
		ordenes.remove(ordenes.size()-1);
	}*/
	public ArrayList<String> getOrdenes(){
		return ordenes;
	}
	public ArrayList<Double> getPreciosU(){
		return preciosU;
	}
	public String getFecha(){
		return fecha;
	}
	
	public boolean compareTo(String fe){
		boolean res=false;
		if(fe.equals(fecha))
			res=true;
		return res;
	}
	
}
