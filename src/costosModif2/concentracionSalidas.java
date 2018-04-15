package costosModif2;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.alee.laf.WebLookAndFeel;

public class concentracionSalidas extends JFrame{
	private JTable table;
	private ArrayList <ArrayList<String>> cSalidas= new ArrayList<ArrayList<String>>();
	private ArrayList <ArrayList<String>> cSalidasModif= new ArrayList<ArrayList<String>>();
	java.awt.Container c;
	int maximoOP;
	public concentracionSalidas(ArrayList <ArrayList<String>> cSa, int maxOP) {
		// TODO Auto-generated constructor stub
		cSalidas=cSa;
		maximoOP=maxOP;
		try{
	    	 WebLookAndFeel.install ();
	     }catch (Exception eX) {}
		setTitle("Concentracion de Entradas");
        setIconImage(new ImageIcon("images/icono2.png").getImage());
        setSize(600,300);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
        mostrarSalidas();
	}

	public void mostrarSalidas(){
		c = getContentPane();
		c.setLayout(null);
    	table = new JTable();	        
        /**llenado*/
    	int id=0;
    	if(cSalidas.size()>1){
    		while(id<cSalidas.size()-1){
    			double c=Double.parseDouble(cSalidas.get(id).get(3));
    			while(c!=Double.parseDouble(cSalidas.get(id).get(3))){
    				
    			}
    			
    		}
    		
    		/*calculo del toal*/
    		ArrayList<String> total= new ArrayList<String>();
    		double totalQ=0, totalCosto=0;
    		double restaQ=0, restaCosto=0;
    		for(int i=0;i<cSalidas.size();i++){
    			if((cSalidas.get(i).get(1)).equals("Devolucion Proveedor")){
    				restaQ+=VistaKardes.convertir(cSalidas.get(i).get(3));
    				restaCosto+= VistaKardes.convertir(cSalidas.get(i).get(4));
    			}else{
    				totalQ+= VistaKardes.convertir(cSalidas.get(i).get(3));
        			totalCosto+= VistaKardes.convertir(cSalidas.get(i).get(4));
    			}
    		}
    		/*totalQ= totalQ-restaQ;
    		totalCosto= totalCosto-restaCosto;
    		total.add("");total.add("TOTAL");total.add("");total.add(""+totalQ);total.add(""+totalCosto);
    		cSalidas.add(total);*/
    	}
        
        /**actualizacion*/
        actulizar();
        /**fin de actulizacion*/
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 30, 596, 264);
        scrollPane.setVisible(true);
        c.add(scrollPane);
    }
	public void actulizar(){
		
		Object []   head  = {"FECHA", "DETALLE", "P.u.","Q #1","V #1","Q T.","V T."};
		//Object []   head1 = {"FECHA", "DETALLE", "P.u.","Q","V"};
		Object []   head2 = {"FECHA", "DETALLE", "P.u.","Q #1","V #1","Q #2","V #2","Q T.","V T."};
		Object []   head3 = {"FECHA", "DETALLE", "P.u.","Q #1","V #1","Q #2","V #2","Q #3","V #3","Q T.","V T."};
		Object []   head4 = {"FECHA", "DETALLE", "P.u.","Q #1","V #1","Q #2","V #2","Q #3","V #3","Q #4","V #4","Q T.","V T."};
        
        Object [][] alfa = new Object[cSalidas.size()][5+(maximoOP*2)];
        for(int i = 0; i < cSalidas.size(); i++)
        {
            alfa [i] = cSalidas.get(i).toArray();   
        }
        if(maximoOP==4) head=head4;
        else if(maximoOP==3) head=head3;
        else if(maximoOP==2) head=head2;
        
        table.setModel(new javax.swing.table.DefaultTableModel(alfa, head){
            public boolean isCellEditable(int rowIndex , int columnIndex) {
                return false; 
            }
        });
        table.getTableHeader().setForeground(Color.blue);
        table.getTableHeader().setBackground(Color.green);
        table.getTableHeader().setFont(new Font("Arial",2,12)); 
        /*int anchos [] = {150,200,100,200,200};
        for(int i=0; i<5; i++){
        	table.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }*/
	}


}
