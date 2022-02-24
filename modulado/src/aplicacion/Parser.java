package aplicacion;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import aplicacion.Variable;

public class Parser {

	public static void main(String[] args) throws IOException {
		// PROFE Y/O MONITORX
		//INSERTE EN NOMBRE ARCHIVO LA DIRECCION COMPLETA DEL ARCHIVO QUE QUIEREN REVISAR
		//PERDON POR PONERLOS A LEER 1500 LINEAS DE CODIGO ;)..(pero esta bien... y sin librerias >:)  )
		String nombreArchivo = "C:/a_semestre_lll/FISICA/taller0final/data/inputt.txt";
		
	    BufferedReader br = new BufferedReader(new FileReader(nombreArchivo));
	    ArrayList<String> dirturn = new ArrayList<>();
	    dirturn.add(":right");// {":left",":right", ":around"};
	    dirturn.add(":left");
	    dirturn.add(":around");
	    ArrayList<String> dirturnn = new ArrayList<>();
	    dirturnn.add(":right");// {":left",":right", ":around"};
	    dirturnn.add(":left");
	    dirturnn.add(":back");
	    dirturnn.add(":front");
	    ArrayList<String> dirface = new ArrayList<>();
	    dirface.add(":north");// {":left",":right", ":around"};
	    dirface.add(":south");
	    dirface.add(":east");
	    dirface.add(":west");
	    ArrayList<String> validos = new ArrayList<>();
	    String[] revalidos= new String[] {"q","w","e","r","t","y","u","i","o","p","a","s","d","f","g","h","j","k","l","z","x","c","v","b","n","m","Q","W","E","R","T","Y","U","I","O","P","A","S","D","F","G","H","J","K","L","Z","X","C","V","B","N","M","-",":","(",")"," ","	","1","2","3","4","5","6","7","8","9","0","="};
	    for(String va:revalidos) 
	    {
	    	validos.add(va);
	    }
		String linea = br.readLine();
		int contpar=0;
		boolean condi=true;
		String acumu = "";
		ArrayList<Variable> variables= new  ArrayList<>();
		ArrayList<Funcion> funciones = new ArrayList<>();
		String[] comando = new String[7];
		boolean espe = false;

		while (linea != null && condi)
		{
			for(int i=0; i<linea.length(); i++) {
				String car= Character.toString(linea.charAt(i));
				if (!validos.contains(car))
				{
					espe=true;
					System.out.println("\n Caracteres no validos, por culpa de un:  "+car);
				}
				if (car.equals("("))
				{
					contpar+=1;
					
				}
				else if(car.equals(")")) 
				{
					contpar-=1;
				}
				acumu = acumu.concat(car);
			}
			linea = br.readLine();
		}
		System.out.println(acumu);
		acumu = acumu.concat(" ");
		System.out.println(contpar);
		boolean rta = false;
		if(contpar==0 && !espe) 
		{
			rta = revisarTodo(acumu,variables,dirturn,dirface,funciones, dirturnn);
		}
		
		for(Variable pru: variables) {
			System.out.println(pru.getNombre()+":  "+ pru.getValor());
		}
		for(Funcion pru: funciones) {
			System.out.println(pru.getNombre()+":  "+ pru.getInternas()+"--->"+pru.getRelleno());
		}
		if(rta)
		{
			System.out.println("El codigo está BIEN escrito");
		}
		else
		{
			System.out.println("El codigo está REMAL escrito");
		}
		br.close();
		
		
		


	}
	
	public static boolean revisarTodo(String acumu, ArrayList<Variable> variables, ArrayList<String> dirturn, ArrayList<String> dirface, ArrayList<Funcion> funciones, ArrayList<String> dirturnn) 
	{
		boolean rta=true;
		for(int j=0; j<acumu.length();j++) 
		{
			
			String carr= Character.toString(acumu.charAt(j));
			//Instruction Starts here
			if(carr.equals("(")) 
			{
				String ins="";
				j+=1;
				carr= Character.toString(acumu.charAt(j));
				while(carr.equals("("))
				{
					j+=1;
					carr= Character.toString(acumu.charAt(j));
				}
				while(!carr.equals(" ") && !carr.equals("	") && !carr.equals(")") && j<acumu.length()) {
					ins = ins.concat(carr);
					j+=1;
					carr= Character.toString(acumu.charAt(j));
				}
				if (ins.equals("defvar"))
				{
					ins="";
					j+=1;
					carr= Character.toString(acumu.charAt(j));
					if (!todoDigitos(carr))
					{
						
						while(!carr.equals(" "))
						{
							carr= Character.toString(acumu.charAt(j));
							ins= ins.concat(carr);
							j+=1;
						}
						ins = ins.strip();
						String valor = "";
						char digi = acumu.charAt(j);
						boolean reprueba = Character.isDigit(digi);
						if (reprueba) 
						{
							valor = valor.concat(Character.toString(digi));
							j+=1;
							digi = acumu.charAt(j);
							reprueba = Character.isDigit(digi);
							while(reprueba) 
							{
								valor = valor.concat(Character.toString(digi));
								j+=1;
								digi = acumu.charAt(j);
								reprueba = Character.isDigit(digi);
							}
							int valorf= Integer.parseInt(valor);
							Variable nueva = new Variable(ins, valorf);
							variables.add(nueva);
							carr= Character.toString(acumu.charAt(j));
							if(!carr.equals(")")) 
							{
								rta=false;
							}
						}
						else 
						{
							rta = false;
						}
						
					}
					else
					{
						rta= false;
					}
					
								
				}
				else if(ins.equals("defun"))
				{
					ins="";
					j+=1;
					carr= Character.toString(acumu.charAt(j));
					if (!todoDigitos(carr))
					{
						
						while(!carr.equals(" ") && !carr.equals("	"))
						{
							ins= ins.concat(carr);
							j+=1;
							carr= Character.toString(acumu.charAt(j));
						}
					
						
						ins = ins.strip();
						while(!carr.equals("(")) 
						{
							j+=1;
							carr= Character.toString(acumu.charAt(j));
						}
						
						ArrayList<String> internas=new ArrayList<>();
						j+=1;
						carr= Character.toString(acumu.charAt(j));
						if (carr.equals(")"))
						{
							
						}
						else
						{
							String inter="";
							while(!carr.equals(")")) 
							{
								
								inter= inter.concat(carr);
								j+=1;
								carr= Character.toString(acumu.charAt(j));
								if (carr.equals(" "))
								{
									internas.add(inter.strip());
									inter="";
								}
							}
							internas.add(inter.strip());
						}
						Funcion lanew = new Funcion(ins,internas);
						ArrayList<Variable> provisional = new ArrayList<>();
						for(Variable va: variables)
						{
							provisional.add(va);
						}
						if (internas.size()>0)
						{
							for(int a=0;a<internas.size(); a++)
							{
								Variable agre= new Variable(internas.get(a),0);
								provisional.add(agre);
							}
						}
						funciones.add(lanew);
						j+=1;
						carr= Character.toString(acumu.charAt(j));
						while(carr.equals(" ") || carr.equals("	")) 
						{
							j+=1;
							carr= Character.toString(acumu.charAt(j));
						}
						String relleno="";
						int paren=2;
						while(paren>0)
						{
							relleno=relleno.concat(carr);
							j+=1;
							carr= Character.toString(acumu.charAt(j));
							if(carr.equals("(")) 
							{
								paren+=1;
							}
							else if(carr.equals(")")) 
							{
								paren-=1;
							}
						}
						relleno = relleno.concat(" ");
						//Funcion reem=funciones.get();
						lanew.setRelleno(relleno);
						funciones.remove(funciones.size()-1);
						funciones.add(lanew);
						if(!revisarTodo(relleno, provisional, dirturn, dirface, funciones, dirturnn))
						{
							rta= false;
						}
						
						
					}	
						
				}
				//EYYYYYYyyyy
				else if(ins.equals("if"))
				{
					ins="";
					String condicion = "";
					while(!carr.equals("(")) 
					{
						j+=1;
						carr= Character.toString(acumu.charAt(j));
					}
					condicion = condicion.concat(carr);
					int parentesis = 1;
					while(parentesis>0) 
					{
						j+=1;
						carr= Character.toString(acumu.charAt(j));
						condicion = condicion.concat(carr);
						if(carr.equals("(")) 
						{
							parentesis +=1;
						}
						else if(carr.equals(")")) 
						{
							parentesis-=1;
						}
					}
					boolean condiRev= revisarCondi(condicion, dirface, funciones,variables);
					if (!condiRev) 
					{
						rta=false;
					}
					j+=1;
					carr= Character.toString(acumu.charAt(j));
					if(rta) 
					{
						while (carr.equals(" ") || carr.equals("	")) 
						{
							j+=1;
							carr= Character.toString(acumu.charAt(j));
						}
						if(carr.equals("(")) 
						{
							String bloq = "";
							bloq = bloq.concat(carr);
							int parr = 1;
							while(parr>0) 
							{
								j+=1;
								carr= Character.toString(acumu.charAt(j));
								bloq = bloq.concat(carr);
								if(carr.equals("(")) 
								{
									parr +=1;
								}
								else if(carr.equals(")")) 
								{
									parr -=1;
								}
							}
							boolean bien= revisarTodo(bloq,variables, dirturn, dirface,funciones, dirturnn);
							if(!bien) 
							{
								rta=false;
							}
							j+=1;
							carr= Character.toString(acumu.charAt(j));
							while (carr.equals(" ") || carr.equals("	")) 
							{
								j+=1;
								carr= Character.toString(acumu.charAt(j));
							}
							if(carr.equals("(")) 
							{
								String bloqq = "";
								bloqq = bloqq.concat(carr);
								int parrr = 1;
								while(parrr>0) 
								{
									j+=1;
									carr= Character.toString(acumu.charAt(j));
									bloqq = bloqq.concat(carr);
									if(carr.equals("(")) 
									{
										parrr +=1;
									}
									else if(carr.equals(")")) 
									{
										parrr -=1;
									}
								}
								boolean bienn= revisarTodo(bloqq,variables, dirturn, dirface,funciones, dirturnn);
								if(!bienn) 
								{
									rta=false;
								}
							}
							else
							{
								rta=false;
							}
							
							
						}
						else
						{
							rta=false;
						}
					}
					
				}
				else if (ins.equals("="))
				{
					ins="";
					j+=1;
					carr= Character.toString(acumu.charAt(j));
					if (!todoDigitos(carr))
					{
						
						while(!carr.equals(" "))
						{
							carr= Character.toString(acumu.charAt(j));
							ins= ins.concat(carr);
							j+=1;
						}
						ins = ins.strip();
						if (variables.size()>0) 
						{
							boolean listo=false;
							for (int l=0; l<variables.size(); l++) 
							{
								if (variables.get(l).getNombre().equals(ins)) 
								{
									listo=true;
									variables.remove(l);
								}
							}
							if(!listo) 
							{
								rta=false;
							}
						}
						else 
						{
							rta=false;
						}
						String valor = "";
						char digi = acumu.charAt(j);
						boolean reprueba = Character.isDigit(digi);
						if (reprueba) 
						{
							valor = valor.concat(Character.toString(digi));
							j+=1;
							digi = acumu.charAt(j);
							reprueba = Character.isDigit(digi);
							while(reprueba) 
							{
								valor = valor.concat(Character.toString(digi));
								j+=1;
								digi = acumu.charAt(j);
								reprueba = Character.isDigit(digi);
							}
							int valorf= Integer.parseInt(valor);
							Variable nueva = new Variable(ins, valorf);
							variables.add(nueva);
							carr= Character.toString(acumu.charAt(j));
							if(!carr.equals(")")) 
							{
								rta=false;
							}
						}
						else 
						{
							rta = false;
						}
						
					}
					else
					{
						rta= false;
					}
				}
				else if(ins.equals("run-dirs")) 
				{
					ins="";
					while(!carr.equals("(")) 
					{
						if(!carr.equals(" ") && !carr.equals("	")) 
						{
							rta=false;
						}
						j+=1;
						carr= Character.toString(acumu.charAt(j));
					}
					j+=1;
					carr= Character.toString(acumu.charAt(j));
					while(!carr.equals(")") && rta) 
					{
						String dir = "";
						while(!carr.equals(" ") && !carr.equals("	") && !carr.equals(")")) 
						{
							dir=dir.concat(carr);
							j+=1;
							carr= Character.toString(acumu.charAt(j));
						}
						j+=1;
						carr= Character.toString(acumu.charAt(j));
						boolean centi = false;
						if(dirturnn.contains(dir)) 
						{
							centi=true;
						}
						if(!centi) 
						{
							rta=false;
						}
					}
				}
				else if(ins.equals("move"))
				{
					String valor="";
					while (carr.equals(" ")) 
					{
						j+=1;
						carr= Character.toString(acumu.charAt(j));
					}
					while(!carr.equals(")")) 
					{
						valor=valor.concat(carr);
						j+=1;
						carr= Character.toString(acumu.charAt(j));
					}
					String[] comando = new String[] {ins, valor};
					boolean x = funciondos(comando,variables, dirturn, dirface);
					if(!x)
					{
						rta=false;
					}
					
				}
				else if(ins.equals("turn"))
				{
					String valor="";
					while (carr.equals(" ")) 
					{
						j+=1;
						carr= Character.toString(acumu.charAt(j));
					}
					while(!carr.equals(")")) 
					{
						valor=valor.concat(carr);
						j+=1;
						carr= Character.toString(acumu.charAt(j));
					}
					String[] comando = new String[] {ins, valor};
					boolean x = funciondos(comando,variables, dirturn, dirface);
					if(!x)
					{
						rta=false;
					}
				}
				else if(ins.equals("face"))
				{
					String valor="";
					while (carr.equals(" ")) 
					{
						j+=1;
						carr= Character.toString(acumu.charAt(j));
					}
					while(!carr.equals(")")) 
					{
						valor=valor.concat(carr);
						j+=1;
						carr= Character.toString(acumu.charAt(j));
					}
					String[] comando = new String[] {ins, valor};
					boolean x = funciondos(comando,variables, dirturn, dirface);
					if(!x)
					{
						rta=false;
					}
				}
				else if(ins.equals("put")) 
				{
					String valor="";
					while (carr.equals(" ")) 
					{
						j+=1;
						carr= Character.toString(acumu.charAt(j));
					}
					while(!carr.equals(" ") && !carr.equals("	")) 
					{
						valor=valor.concat(carr);
						j+=1;
						carr= Character.toString(acumu.charAt(j));
					}
					j+=1;
					carr= Character.toString(acumu.charAt(j));
					String valordos = "";
					while(!carr.equals(")")) 
					{
						valordos=valordos.concat(carr);
						j+=1;
						carr= Character.toString(acumu.charAt(j));
					}
					String[] comando = new String[] {ins, valor, valordos};
					boolean x = funciontres(comando,variables, dirturn, dirturnn, dirface);
					if(!x)
					{
						rta=false;
					}
				}
				else if(ins.equals("pick")) 
				{
					String valor="";
					while (carr.equals(" ")) 
					{
						j+=1;
						carr= Character.toString(acumu.charAt(j));
					}
					while(!carr.equals(" ") && !carr.equals("	")) 
					{
						valor=valor.concat(carr);
						j+=1;
						carr= Character.toString(acumu.charAt(j));
					}
					j+=1;
					carr= Character.toString(acumu.charAt(j));
					String valordos = "";
					while(!carr.equals(")")) 
					{
						valordos=valordos.concat(carr);
						j+=1;
						carr= Character.toString(acumu.charAt(j));
					}
					String[] comando = new String[] {ins, valor, valordos};
					boolean x = funciontres(comando,variables, dirturn, dirturnn, dirface);
					if(!x)
					{
						rta=false;
					}
				}
				else if(ins.equals("move-dir")) 
				{
					String valor="";
					while (carr.equals(" ")) 
					{
						j+=1;
						carr= Character.toString(acumu.charAt(j));
					}
					while(!carr.equals(" ") && !carr.equals("	")) 
					{
						valor=valor.concat(carr);
						j+=1;
						carr= Character.toString(acumu.charAt(j));
					}
					j+=1;
					carr= Character.toString(acumu.charAt(j));
					String valordos = "";
					while(!carr.equals(")")) 
					{
						valordos=valordos.concat(carr);
						j+=1;
						carr= Character.toString(acumu.charAt(j));
					}
					String[] comando = new String[] {ins, valor, valordos};
					boolean x = funciontres(comando,variables, dirturn, dirturnn, dirface);
					if(!x)
					{
						rta=false;
					}
				}
				else if(ins.equals("move-face")) 
				{
					String valor="";
					while (carr.equals(" ")) 
					{
						j+=1;
						carr= Character.toString(acumu.charAt(j));
					}
					while(!carr.equals(" ") && !carr.equals("	")) 
					{
						valor=valor.concat(carr);
						j+=1;
						carr= Character.toString(acumu.charAt(j));
					}
					j+=1;
					carr= Character.toString(acumu.charAt(j));
					String valordos = "";
					while(!carr.equals(")")) 
					{
						valordos=valordos.concat(carr);
						j+=1;
						carr= Character.toString(acumu.charAt(j));
					}
					String[] comando = new String[] {ins, valor, valordos};
					boolean x = funciontres(comando,variables, dirturn, dirturnn, dirface);
					if(!x)
					{
						rta=false;
					}
				}
				else if(ins.equals("facing-p")) 
				{
					int p = 1;
					String condicio="(";
					condicio=condicio.concat(ins);
					while(p>0) 
					{
						condicio = condicio.concat(carr);
						j+=1;
						carr= Character.toString(acumu.charAt(j));
						if(carr.equals("(")) 
						{
							p+=1;
						}
						else if(carr.equals(")")) 
						{
							p-=1;
						}
					}
					condicio = condicio.concat(carr);
					rta = revisarCondi(condicio, dirface, funciones, variables);
					
				}
				else if(ins.equals("can-put-p")) 
				{
					int p = 1;
					String condicio="(";
					condicio=condicio.concat(ins);
					while(p>0) 
					{
						condicio = condicio.concat(carr);
						j+=1;
						carr= Character.toString(acumu.charAt(j));
						if(carr.equals("(")) 
						{
							p+=1;
						}
						else if(carr.equals(")")) 
						{
							p-=1;
						}
					}
					condicio = condicio.concat(carr);
					rta = revisarCondi(condicio, dirface, funciones, variables);
				}
				else if(ins.equals("can-move-p")) 
				{
					int p = 1;
					String condicio="(";
					condicio=condicio.concat(ins);
					while(p>0) 
					{
						condicio = condicio.concat(carr);
						j+=1;
						carr= Character.toString(acumu.charAt(j));
						if(carr.equals("(")) 
						{
							p+=1;
						}
						else if(carr.equals(")")) 
						{
							p-=1;
						}
					}
					condicio = condicio.concat(carr);
					rta = revisarCondi(condicio, dirface, funciones, variables);
				}
				else if(ins.equals("can-pick-p")) 
				{
					int p = 1;
					String condicio="(";
					condicio=condicio.concat(ins);
					while(p>0) 
					{
						condicio = condicio.concat(carr);
						j+=1;
						carr= Character.toString(acumu.charAt(j));
						if(carr.equals("(")) 
						{
							p+=1;
						}
						else if(carr.equals(")")) 
						{
							p-=1;
						}
					}
					condicio = condicio.concat(carr);
					rta = revisarCondi(condicio, dirface, funciones, variables);
				}
				else if(ins.equals("not")) 
				{
					
					int p = 1;
					String condicio="(";
					condicio=condicio.concat(ins);
					while(p>0) 
					{
						condicio = condicio.concat(carr);
						j+=1;
						carr= Character.toString(acumu.charAt(j));
						if(carr.equals("(")) 
						{
							p+=1;
						}
						else if(carr.equals(")")) 
						{
							p-=1;
						}
					}
					condicio = condicio.concat(carr);
					rta = revisarCondi(condicio, dirface, funciones, variables);
					
				}
				else if(ins.equals("skip"))
				{
					
				}
				else if (ins.equals("loop"))
				{
					ins="";
					String condicion = "";
					while(!carr.equals("(")) 
					{
						j+=1;
						carr= Character.toString(acumu.charAt(j));
					}
					condicion = condicion.concat(carr);
					int parentesis = 1;
					while(parentesis>0) 
					{
						j+=1;
						carr= Character.toString(acumu.charAt(j));
						condicion = condicion.concat(carr);
						if(carr.equals("(")) 
						{
							parentesis +=1;
						}
						else if(carr.equals(")")) 
						{
							parentesis-=1;
						}
					}
					boolean condiRev= revisarCondi(condicion, dirface, funciones,variables);
					if (!condiRev) 
					{
						rta=false;
					}
					j+=1;
					carr= Character.toString(acumu.charAt(j));
					if(rta) 
					{
						while (carr.equals(" ") || carr.equals("	")) 
						{
							j+=1;
							carr= Character.toString(acumu.charAt(j));
						}
						if(carr.equals("(")) 
						{
							String bloq = "";
							bloq = bloq.concat(carr);
							int parr = 1;
							while(parr>0) 
							{
								j+=1;
								carr= Character.toString(acumu.charAt(j));
								bloq = bloq.concat(carr);
								if(carr.equals("(")) 
								{
									parr +=1;
								}
								else if(carr.equals(")")) 
								{
									parr -=1;
								}
							}
							boolean bien= revisarTodo(bloq,variables, dirturn, dirface,funciones, dirturnn);
							if(!bien) 
							{
								rta=false;
							}
						}
						else
						{
							rta=false;
						}
					}
					else
					{
						rta=false;
					}
				}
				
				else if (ins.equals("repeat"))
				{
					ins="";
					j+=1;
					carr= Character.toString(acumu.charAt(j));
					
					while (carr.equals(" ") || carr.equals("	")) 
					{
						j+=1;
						carr= Character.toString(acumu.charAt(j));
					}
					
					while (!carr.equals(" ") && !carr.equals("	"))
					{
						ins = ins.concat(carr);
						j+=1;
						carr= Character.toString(acumu.charAt(j));
					}
					boolean logro= todoDigitos(ins);
					if (!logro) 
					{
						logro= buscaVariable(ins, variables);
					}
					if(logro)
					{
						while (carr.equals(" ") || carr.equals("	")) 
						{
							j+=1;
							carr= Character.toString(acumu.charAt(j));
						}
						if(carr.equals("(")) 
						{
							String bloq = "";
							bloq = bloq.concat(carr);
							int parr = 1;
							while(parr>0) 
							{
								j+=1;
								carr= Character.toString(acumu.charAt(j));
								bloq = bloq.concat(carr);
								if(carr.equals("(")) 
								{
									parr +=1;
								}
								else if(carr.equals(")")) 
								{
									parr -=1;
								}
							}
							boolean bien= revisarTodo(bloq,variables, dirturn, dirface,funciones, dirturnn);
							if(!bien) 
							{
								rta=false;
							}
						}
						else
						{
							rta=false;
						}
					}
					else
					{
						rta = false;
					}
				}
				else 
				{
					if(!rta)
					{
						
					}
					else
					{
						rta = false;						
						for(Funcion f: funciones) {
							if(f.getNombre().equals(ins)) 
							{
								ArrayList<String> newint=new ArrayList<>();
								ArrayList<String> internas=f.getInternas();
								if(f.getInternas().size()>0) 
								{
									ArrayList<Variable> provisional = new ArrayList<>();
									boolean c=false;
									while(carr.equals(" ") || carr.equals("	")) 
									{
										j+=1;
										carr= Character.toString(acumu.charAt(j));
									}
									if (carr.equals(")"))
									{
										
									}
									else
									{
										String inter="";
										while(!carr.equals(")")) 
										{
											
											inter= inter.concat(carr);
											j+=1;
											carr= Character.toString(acumu.charAt(j));
											if (carr.equals(" "))
											{
												boolean yacasi = false;
												yacasi = buscaVariable(inter, variables);
												if(!yacasi)
												{
													yacasi = todoDigitos(inter);
												}
												if(yacasi)
												{
													newint.add(inter.strip());
												}
												inter="";
											}
										}
										boolean yacas = false;
										inter = inter.strip();
										yacas = buscaVariable(inter, variables);
										
										if(!yacas)
										{
											yacas = todoDigitos(inter);
										}
										if(yacas)
										{
											newint.add(inter.strip());
										}
										//newint.add(inter.strip());
									}
									if(internas.size()==newint.size())
									{
										c=true;
									}
									if(c) 
									{
										rta=true;
									}
									
								}
								else
								{
									rta = true;
								}
							}
						}
					}
				}
			}
		}
		return rta;
		
	}
	
	public static boolean funciondos(String[] comando,ArrayList<Variable> variables, ArrayList<String> dirturn, ArrayList<String> dirface) 
	{
		boolean correcto=false;
		if (comando[0].equals("move"))
		{
			correcto= todoDigitos(comando[1]);
			if (!correcto) 
			{
				correcto= buscaVariable(comando[1], variables);
				
			}
			
		}
		else if(comando[0].equals("turn"))
		{
			if(dirturn.contains(comando[1])) 
			{
				correcto=true;
			}
		}
		else if(comando[0].equals("face")) 
		{
			if(dirface.contains(comando[1])) 
			{
				correcto=true;
			}
		}
		
		return correcto;
		
	}
	
	public static boolean funciontres(String[] comando,ArrayList<Variable> variables, ArrayList<String> dirturn, ArrayList<String> dirturnn, ArrayList<String> dirface) 
	{
		boolean correcto=false;
		if (comando[0].equals("put"))
		{
			if (comando[1].equals("Balloons")) 
			{
				correcto= todoDigitos(comando[2]);
				if (!correcto) 
				{
					correcto= buscaVariable(comando[2], variables);
				}
			}
			else if (comando[1].equals("Chips")) 
			{
				correcto= todoDigitos(comando[2]);
				if (!correcto) 
				{
					correcto= buscaVariable(comando[2], variables);
				}
			}
			
		}
		else if(comando[0].equals("pick"))
		{
			if (comando[1].equals("Balloons")) 
			{
				correcto= todoDigitos(comando[2]);
				if (!correcto) 
				{
					correcto= buscaVariable(comando[2], variables);
				}
			}
			else if (comando[1].equals("Chips")) 
			{
				correcto= todoDigitos(comando[2]);
				if (!correcto) 
				{
					correcto= buscaVariable(comando[2], variables);
				}
			}
		}
		else if(comando[0].equals("move-dir")) 
		{
			correcto= todoDigitos(comando[1]);
			if (!correcto) 
			{
				correcto= buscaVariable(comando[1], variables);
			}
			if(correcto) 
			{
				if(!dirturnn.contains(comando[2])) 
				{
					correcto=false;
				}
			}
			
		}
		else if(comando[0].equals("move-face")) 
		{
			correcto= todoDigitos(comando[1]);
			if (!correcto) 
			{
				correcto= buscaVariable(comando[1], variables);
			}
			if(correcto) 
			{
				if(!dirface.contains(comando[2])) 
				{
					correcto=false;
				}
			}
		}
		
		return correcto;
		
	}
	
	public static boolean todoDigitos(String arevisar) 
	{
		boolean correcto=false;
		char digi = arevisar.charAt(0);
		boolean prueba = Character.isDigit(digi);
		if (prueba) 
		{
			correcto=true;
			int k=1;
			while(k<arevisar.length()) 
			{
				digi = arevisar.charAt(k);
				prueba = Character.isDigit(digi);
				if(!prueba)
				{
					correcto=false;
				}
				k+=1;
			}
		}
		return correcto;
	}
	
	public static boolean buscaVariable(String nombre,ArrayList<Variable> variables ) {
		boolean listo=true;
		if (variables.size()>0) 
		{
			listo=false;
			for (int j=0; j<variables.size(); j++) 
			{
				if (variables.get(j).getNombre().equals(nombre)) 
				{
					listo=true;
					
				}
			}
		}
		else 
		{
			listo=false;
		}
		return listo;
	}
	
	public static boolean revisarCondi(String condicion, ArrayList<String> dirface, ArrayList<Funcion> funciones, ArrayList<Variable> variables) 
	{
		//")"
		boolean rta = false;
		boolean centinela = false;
		String ins = "";
		int h = 0;
		String carr = Character.toString(condicion.charAt(h));
		while((h<(condicion.length()-1)) && !centinela) 
		{
			h+=1;
			carr = Character.toString(condicion.charAt(h));
			ins = ins.concat(carr);
			if(ins.equals("facing-p")) 
			{
				h+=2;
				carr = Character.toString(condicion.charAt(h));
				if (carr.equals(":")) 
				{
					ins="";
					while(!carr.equals(")") && !carr.equals(" ") && !carr.equals("	")) 
					{
						ins = ins.concat(carr);
						h+=1;
						carr = Character.toString(condicion.charAt(h));
					}
					if(!dirface.contains(ins)) 
					{
						rta = false;
						centinela=true;
					}
					else 
					{
						rta=true;
						centinela=true;
					}
					if(carr.equals(" ") || carr.equals("	")) 
					{
						while(!carr.equals(")")) 
						{
							carr = Character.toString(condicion.charAt(h));
							h+=1;
							if(!carr.equals(")") && !carr.equals(" ") && !carr.equals("	")) 
							{
								rta=false;
								centinela=true;
							}
							
						}
					}
				}
				else
				{
					rta=false;
					centinela=true;
				}
			}
			else if(ins.equals("can-put-p")) 
			{
				ins="";
				h+=2;
				carr = Character.toString(condicion.charAt(h));
				if(carr.equals(carr.toUpperCase())) 
				{
					while(!carr.equals(")") && !carr.equals(" ") && !carr.equals("	")) 
					{
						ins = ins.concat(carr);
						h+=1;
						carr = Character.toString(condicion.charAt(h));
					}
					if(!ins.equals("Balloons") && !ins.equals("Chips")) 
					{
						rta= false;
						centinela = true;
					}
					else 
					{
						ins="";
						h+=1;
						carr = Character.toString(condicion.charAt(h));
						while(!carr.equals(")") && !carr.equals(" ") && !carr.equals("	")) 
						{
							ins = ins.concat(carr);
							h+=1;
							carr = Character.toString(condicion.charAt(h));
						}
						rta=todoDigitos(ins);
						if (!rta) 
						{
							rta= buscaVariable(ins, variables);
						}
						
					}
				}
				else 
				{
					rta=false;
					centinela=true;
				}
				
			}
			else if(ins.equals("can-pick-p")) 
			{
				ins="";
				h+=2;
				carr = Character.toString(condicion.charAt(h));
				if(carr.equals(carr.toUpperCase())) 
				{
					while(!carr.equals(")") && !carr.equals(" ") && !carr.equals("	")) 
					{
						ins = ins.concat(carr);
						h+=1;
						carr = Character.toString(condicion.charAt(h));
					}
					if(!ins.equals("Balloons") && !ins.equals("Chips")) 
					{
						rta= false;
						centinela = true;
					}
					else 
					{
						ins="";
						h+=1;
						carr = Character.toString(condicion.charAt(h));
						while(!carr.equals(")") && !carr.equals(" ") && !carr.equals("	")) 
						{
							ins = ins.concat(carr);
							h+=1;
							carr = Character.toString(condicion.charAt(h));
						}
						rta=todoDigitos(ins);
						if (!rta) 
						{
							rta= buscaVariable(ins, variables);
						}
						
					}
				}
				else 
				{
					rta=false;
					centinela=true;
				}
				
			}
			else if(ins.equals("can-move-p")) 
			{
				h+=2;
				carr = Character.toString(condicion.charAt(h));
				if (carr.equals(":")) 
				{
					ins="";
					while(!carr.equals(")") && !carr.equals(" ") && !carr.equals("	")) 
					{
						ins = ins.concat(carr);
						h+=1;
						carr = Character.toString(condicion.charAt(h));
					}
					
					if(!dirface.contains(ins)) 
					{
						rta = false;
						centinela=true;
					}
					else {
						rta=true;
						centinela=true;
					}
					if(carr.equals(" ") || carr.equals("	")) 
					{
						while(!carr.equals(")") && h<condicion.length()) 
						{
							carr = Character.toString(condicion.charAt(h));
							h+=1;
							if(!carr.equals(")") && !carr.equals(" ") && !carr.equals("	")) 
							{
								rta=false;
								centinela=true;
							}
							
						}
					}
				}
				else
				{
					rta=false;
					centinela=true;
				}
			}
			//PROBLEMAS:(
			else if(ins.equals("not")) 
			{
				h+=1;
				carr= Character.toString(condicion.charAt(h));
				while (carr.equals(" ")) 
				{
					h+=1;
					carr= Character.toString(condicion.charAt(h));
				}
				carr= Character.toString(condicion.charAt(h));
				ins="";
				int par= 1;
				while(par>0)
				{
					ins=ins.concat(carr);
					h+=1;
					carr= Character.toString(condicion.charAt(h));
					if(carr.equals("(")) 
					{
						par+=1;
					}
					else if(carr.equals(")")) 
					{
						par-=1;
					}
				}
				if(Character.toString(ins.charAt(0)).equals("(")) 
				{
					ins=ins.concat(carr);
				}
				else 
				{
					String o = "(";
					ins= o.concat(ins);
					ins= ins.concat(")");
				}
				rta = revisarCondi(ins,dirface,funciones,variables);
			}
			else
			{
				for(Funcion f: funciones) {
					if(f.getNombre().equals(ins)) 
					{
						ArrayList<Variable> provisional = new ArrayList<>();
						ArrayList<String> internas=f.getInternas();
						for(Variable va: variables)
						{
							provisional.add(va);
						}
						if (internas.size()>0)
						{
							for(int a=0;a<internas.size(); a++)
							{
								Variable agre= new Variable(internas.get(a),0);
								provisional.add(agre);
							}
						}
						boolean c =revisarCondi(f.getRelleno(), dirface, funciones, provisional);
						if(c) 
						{
							rta=true;
						}
					}
				}
				
			}
		}
		return rta;
	}
}
