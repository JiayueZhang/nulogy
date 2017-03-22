import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class packageCost {
	public packageCost(){};
	public String product=new String();
	public String ct=new String();
	public Map<String,String> cate=new HashMap<String,String>();
	public Map<String,String> markup=new HashMap<String,String>();
	
	//category Map, read from txt
	public Map<String,String> readFile(String filename) throws IOException{
		Map<String,String> result=new HashMap<String,String>();
		FileReader input = new FileReader(filename);
		BufferedReader br = new BufferedReader(input);
		
		String input_lines = br.readLine();
		while(input_lines != null) {
			String[] split = input_lines.split(" ");  
		    result.put(split[0], split[1]);
		    input_lines = br.readLine(); 
		}
		br.close();
		
		return result;
		
	}
	
	//write new product-category  
	public void writeFile(String product, String ct) throws IOException{
		//product =product;
		//ct=ct;
		FileOutputStream fso = new FileOutputStream("../p_c_map.txt",true);
		OutputStreamWriter fileWriter = new OutputStreamWriter(fso,Charset.forName("UTF-8"));
		fileWriter.write("\n"+product+" "+ct);
		fileWriter.close();
		fso.close();
	    }
	 //read input from keyboard
	 public String readUserInput() throws IOException {
	        InputStreamReader is_reader = new InputStreamReader(System.in);
	        return new BufferedReader(is_reader).readLine();
	    }
	 
	//compute
	public double computeCost(String args[]) throws IOException{
		double m=0.0,y=0.0;
		double x=Double.parseDouble(args[0]);
		int n=Integer.parseInt(args[1]);
		product=args[2];
		
		if(args[3]!=null)
		    ct=args[3];
		
		//Map for product - category
		cate=readFile("../p_c_map.txt");
		
		//Map for markups
		markup=readFile("../cost.txt");
		
		if(cate.containsKey(product))
	        m =Double.parseDouble(markup.get(cate.get(product))) ;
		else if(args[3]==null){
			System.out.println("Sorry, the product is not classified in the record. Please enter its category.");
		    //read input, no check
			try {
	            String str = readUserInput();
	            ct=str;
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	       
		    writeFile(product,ct);
		    m=Double.parseDouble(markup.get(ct));
		}
		else{ 
			writeFile(product,ct);
		    m=Double.parseDouble(markup.get(ct));
		}
		
		y=(x*1.05)*(0.012*n+m+1);
		
		return y;
		
		
		 
		
		
	}
	
	
	
	
	

	public static void main(String args[]) throws IOException, InterruptedException, ExecutionException{
		packageCost pc=new packageCost();
		double y=0.0;
		String[] arg= new String[4];  
        String str =new String();
		System.out.println("Welcome! Please enter price, number of people and product");
		System.out.println("Example: 1299.99 3 food");
        
        try {
             str = pc.readUserInput();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        String[] split = str.split(" ");
        for(int i=0;i<split.length;i++){
        	arg[i]=split[i];
        }
       
		y=pc.computeCost(arg);
	    System.out.println("The packaging cost of this product is " +y);
}
}
