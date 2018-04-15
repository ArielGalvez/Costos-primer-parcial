package costosModif2;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.alee.laf.WebLookAndFeel;

public class concentracionEntradas extends JFrame {

	private JTable table;
	private ArrayList <ArrayList<String>> cEntradas= new ArrayList<ArrayList<String>>();
	java.awt.Container c;
	
	public concentracionEntradas(ArrayList <ArrayList<String>> cEn) {
		// TODO Auto-generated constructor stub
		cEntradas=cEn;
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
        mostrarEntradas();
	}

	public void mostrarEntradas(){
		c = getContentPane();
		c.setLayout(null);
    	table = new JTable();	        
        /**llenado*/
    	if(cEntradas.size()>1){
    		ArrayList<String> total= new ArrayList<String>();
    		double totalQ=0, totalCosto=0;
    		double restaQ=0, restaCosto=0;
    		for(int i=0;i<cEntradas.size();i++){
    			//System.out.println(cEntradas.get(i).get(1));
    			if((cEntradas.get(i).get(1)).equals("Devolucion Proveedor")){
    				restaQ+=VistaKardes.convertir(cEntradas.get(i).get(3));
    				restaCosto+= VistaKardes.convertir(cEntradas.get(i).get(4));
    			}else{
    				totalQ+= VistaKardes.convertir(cEntradas.get(i).get(3));
        			totalCosto+= VistaKardes.convertir(cEntradas.get(i).get(4));
    			}
    			//System.out.println(""+restaQ+"  "+restaCosto);
    		}
    		totalQ= totalQ-restaQ;
    		totalCosto= totalCosto-restaCosto;
    		total.add("");total.add("TOTAL");total.add("");total.add(""+totalQ);total.add(""+totalCosto);
    		cEntradas.add(total);
    	}
        
        /**actualizacion*/
        actualizar();
        /**fin de actulizacion*/
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 30, 596, 264);
        scrollPane.setVisible(true);
        c.add(scrollPane);
    }
	public void actualizar(){
		Object []   head = {"FECHA", "DETALLE", "P.u.","Q","VALOR"};
        
        Object [][] alfa = new Object[cEntradas.size()][5];
        for(int i = 0; i < cEntradas.size(); i++)
        {
            alfa [i] = cEntradas.get(i).toArray();   
        }
        table.setModel(new javax.swing.table.DefaultTableModel(alfa, head){
            public boolean isCellEditable(int rowIndex , int columnIndex) {
                return false; 
            }
        });
        table.getTableHeader().setForeground(Color.blue);
        table.getTableHeader().setBackground(Color.green);
        table.getTableHeader().setFont(new Font("Arial",2,12)); 
        int anchos [] = {150,200,100,200,200};
        for(int i=0; i<5; i++){
        	table.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
	}
    
}
