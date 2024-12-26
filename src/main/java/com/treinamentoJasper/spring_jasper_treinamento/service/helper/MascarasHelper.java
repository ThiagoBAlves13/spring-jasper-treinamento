package com.treinamentoJasper.spring_jasper_treinamento.service.helper;

import java.text.ParseException;

import javax.swing.text.MaskFormatter;

public class MascarasHelper {
	
	private MaskFormatter cnpj, cpf;
    
    private static final MascarasHelper mascara = new MascarasHelper();
    
    public MascarasHelper(){
        
        try{
        	cnpj = new MaskFormatter("##.###.###/####-##");
        	cnpj.setPlaceholderCharacter('_');
            
            cpf = new MaskFormatter("###.###.###-##");
            cpf.setPlaceholderCharacter('_');
        }catch(Exception e){
            
        }    
    }
    
    public static MascarasHelper getInstance (){
        return mascara;
    }
    
    public MaskFormatter getMascara(String mask){
    	
        if(mask.length() == 11)
                return cpf;
        else if(mask.length() ==14)
                return cnpj;
        else
                return null;
        
    }
    
    public String cpfOuCnpj(String cpfOuCnpj) {
       	MaskFormatter mask;
        try {
            mask = getMascara(cpfOuCnpj);
            mask.setValueContainsLiteralCharacters(false);
            return mask.valueToString(cpfOuCnpj);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    
    public String unmask(String mask) {
    	return mask.replaceAll("[^0-9]*", "");
    }
}
