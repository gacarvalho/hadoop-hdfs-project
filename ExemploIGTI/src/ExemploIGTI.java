
package IGTI;

import java.io.*;
import java.util.*;
import java.util.Random;
import java.text.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;


public class ExemploIGTI extends Configured implements Tool 
{          
    public static void main (final String[] args) throws Exception {   
      int res = ToolRunner.run(new Configuration(), new ExemploIGTI(), args);        
      System.exit(res);           
    }   

    public int run (final String[] args) throws Exception {
      try{ 	             	       	
 	
	JobConf conf = new JobConf(getConf(), ExemploIGTI.class); /*DECLARACAO DO JOBCONF*/
	conf.setJobName("Dados de Games");
	final FileSystem fs = FileSystem.get(conf); /*CRIANDO UM OBJETO PARA MANIPULAR O HDFS*/

	/*CONFIGURANDO OS CAMINHOS DE ENTRADA E SAIDA*/
	Path diretorioEntrada = new Path("DADOS_GAME"), diretorioSaida = new Path("PastaSaida");

	FileInputFormat.setInputPaths(conf, diretorioEntrada); /*DECARANDO O PATH DE ENTRADA*/
	FileOutputFormat.setOutputPath(conf, diretorioSaida); /*DECLARANDO O PATH DE SAIDA*/

	/*CONFIGURANDO A KEY: VALUE*/
	conf.setOutputKeyClass(Text.class);
	conf.setOutputValueClass(Text.class);
         
	/*REFERENCIANDO O MAPPER E O REDUCER*/
	conf.setMapperClass(MapIGTI.class);
	conf.setReducerClass(ReduceIGTI.class);

	JobClient.runJob(conf); /*CHAMANDO A CLASSE JOBCONF*/
	
        }
        catch ( Exception e ) {
            throw e;
        }
        return 0;
     }
 
    public static class MapIGTI extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {
            
      public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter)  throws IOException {                  
        /*DECLARANDO AS VARIAVEIS*/
	Text txtChave = new Text();
	Text txtValor = new Text();
	
	String[] dadosGame = value.toString().split(","); /*QUEBRANDO O REGISTRO EM VETOR*/
	String nomeJogo = dadosGame[1];
	String anoLancamento = dadosGame[3];

	double vendasAmerica = Double.parseDouble(dadosGame[6]);
	double vendasEuropa = Double.parseDouble(dadosGame[7]);

	String strValor = nomeJogo + "|" + String.valueOf(vendasAmerica + vendasEuropa); 
	txtChave.set(anoLancamento);
	txtValor.set(strValor);
	
	output.collect(txtChave, txtValor);
	
      }        
    }
 
    
    public static class ReduceIGTI extends MapReduceBase implements Reducer<Text, Text, Text, Text> {       
       public void reduce (Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {  
          
	Text value = new Text();
	String[] dados = new String[2]; /*CONCATENANDO OS DADOS DA VENDA*/
	double valorVenda = 0, maiorValor = 0;
	String jogoMaiorValor = "";

	while(values.hasNext()){
	  value = values.next();
	  dados = value.toString().split("\\|");
	  valorVenda = Double.parseDouble(dados[1]);

	  if(valorVenda > maiorValor){
		maiorValor = valorVenda;
		jogoMaiorValor = dados[0];
	  } 
	}
	
	value.set(jogoMaiorValor + "|" + String.valueOf(maiorValor));
	output.collect(key, value);


    }
}

   public static class MapIGTIMaior extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {

      public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter)  throws IOException {
        

            
     }
}

    public static class ReduceIGTIMaior extends MapReduceBase implements Reducer<Text, Text, Text, Text> {   
       public void reduce (Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {     
               
  }

}
}


