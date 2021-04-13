![Sobre: Background](https://github.com/gacarvalho/hadoop-hdfs-project/blob/main/Image/background-hadoop-atualizado.png)


## 📌 PROPOSTA DO PROJETO

O objetivo do projeto é apresentar uma solução de dados utilizando o ecossistema Hadoop. O foco do projeto era manipular o HDFS, formatando o sistema de arquivos e executando operações com arquivos e diretórios, aplicando uma codificação MapperReducer com Java. O objetivo final do projeto é apresentar quais foram os jogos mais vendidos em cada ano, distribuindo o arquivo pelo HDFS. 


📢  ETAPA 1 : ANÁLISE DO BANCO DE DADOS RELACIONAL

A base de dados se encontra no SGBD MySQL, que é composto por uma base de dados sobre jogos, plataforma, anoLancamento, genero, fabricante, na_sales (vendas na america), eu_sales (vendas na europa), jp_sales (vendas no japão), other_sales, global_sales. 

![Sobre: Imagem Hadoop 1](https://github.com/gacarvalho/hadoop-hdfs-/blob/main/Image/1.png)
![Sobre: Imagem Hadoop 2](https://github.com/gacarvalho/hadoop-hdfs-/blob/main/Image/2.png)


📢  ETAPA 2 : FORMATANDO O NAMENODE

![Sobre: Imagem Hadoop 2](https://github.com/gacarvalho/hadoop-hdfs-/blob/main/Image/3.png)


📢  ETAPA 3 : EXTRAÇÃO DO MYSQL COM O SQOOP

Após análise da base de dados, foi necessário extrair os dados do MySQL e alocar os dados no HDFS.

```bash
:/usr/local/sqoop$ bin/sqoop import --connect jdbc:mysql://localhost/IGTI?zeroDateTimeBehavior=convert_To_Null --username <usuario> --password <password> --table DADOS_GAME -m 1 --bindir /usr/local/sqoop/lib --target-dir /user/igti/DADOS_GAME
```
![Sobre: Imagem Hadoop 5](https://github.com/gacarvalho/hadoop-hdfs-/blob/main/Image/5.png)

Após o alocamento da base de dados, é possível analisar o bloco da base de dados no pelo comando

```bash
:/usr/local/hadoop$ bin/hdfs dfs -ls /user/igti/DADOS_GAME/part-m-00000
```
![Sobre: Imagem Hadoop](https://github.com/gacarvalho/hadoop-hdfs-/blob/main/Image/7.png)

📢  ETAPA 4 : PROGRAMANDO O MAP REDUCE

Logo após a análise dos dados e extração do mesmo para o HDFS, foi necessário realizar uma programação para aplicar o Mapper e e o Reducer, para isso, a linguagem escolhida foi Java. Na figura abaixo é possível observar a declaração do JobConf e referenciando as classes Map e Reduce.

![Sobre: Imagem Hadoop 9](https://github.com/gacarvalho/hadoop-hdfs-/blob/main/Image/9.png)

Na imagem a seguir, já possível analisar a programação da classe MapIGTI.

![Sobre: Imagem Hadoop 10](https://github.com/gacarvalho/hadoop-hdfs-/blob/main/Image/10.png)

Após a programação da classe MapIGTI, a figura abaixo demonstra a programação da classe ReduceIGTI.

![Sobre: Imagem Hadoop 11](https://github.com/gacarvalho/hadoop-hdfs-/blob/main/Image/1.png)


📢  ETAPA 5 : CONSULTANDO O ARQUIVO FINALIZADO NO HDFS

Após o processo de importação com o sqoop, tratamento pelo Mapper e Reducer, é possível analisar o arquivo final no HDFS no caminho:

```bash
:/usr/local/hadoop$ bin/hdfs dfs -cat /user/igti/PastaSaida/part-00000
```
![Sobre: Imagem Hadoop 14](https://github.com/gacarvalho/hadoop-hdfs-/blob/main/Image/14.png)

Na imagem acima, é possível observar que o objetivo do projeto foi concluido com sucesso, pois foi listado o jogo que MAIS FOI VENDIDO de acordo com cada ANO.  
