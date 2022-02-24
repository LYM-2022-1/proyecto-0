package aplicacion;

public class Variable {
	//Attributes
	private String nombre;
	private int valor;
	
	//Constructor
	public Variable(String nombre, int valor) 
	{
		this.nombre=nombre;
		this.valor = valor;
	}
	
	//Getters
	public String getNombre() {
		return nombre;
	}

	public int getValor() {
		return valor;
	}
	/*
	 * else if (acumu.equals("if"))
						{
							acumu="";
							i+=1;
							car= Character.toString(linea.charAt(i));
							if (car.equals("(")) 
							{
								
								i+=1;
								car= Character.toString(linea.charAt(i));
								if (car.equals(car.toLowerCase()))
								{	
									while(!car.equals(":"))
									{
										car= Character.toString(linea.charAt(i));
										acumu= acumu.concat(car);
										System.out.println(acumu);
										i+=1;
									}
															
										if (acumu.equals("facing-p :"))
										{
										   acumu="";
										   while(!car.equals(")"))
											{
												car= Character.toString(linea.charAt(i));
												acumu= acumu.concat(car);
												System.out.println(acumu);
												i+=1;
											}
										   acumu=acumu.substring(0,acumu.length()-1);
										   if (acumu.equals("north")||acumu.equals("south")|| acumu.equals("east")|| acumu.equals("west"))
										   {
											  i+=1;
											  car= Character.toString(linea.charAt(i));
											  if (!car.equals(")"))
											  {
												 condi=false; 
											  }
													  
										   }
										   else
										   {
											condi=false;
										   }
										  
											
										}
										else if (acumu.equals("can-put-p :"))
										{
											
										}
										else if (acumu.equals("can-pick-p :"))
										{
											
										}
										else if (acumu.equals("can-move-p :"))
										{
											
										}
										else if (acumu.equals("not :"))
										{
											
										}
										else
										{
										 condi=false;
										}
									}
								}
							}
							else
							{
								condi=false;
							}
								
						}
	 */
	/*
	 * if (car.equals("("))
				{
					contpar+=1;
					//The instruction starts here
					while()
					
				}
				else if(car.equals(")")) 
				{
					contpar-=1;
				}
				
				
				else 
				{
					acumu = acumu.concat(car);
				}
	 */
}