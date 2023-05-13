#!/bin/bash

echo "Olá :D seja bem-vindo!"
echo "Estou aqui para te ajudar a instalar a aplicação Smart Analytic Machine!"
sleep 2
echo "Gostaria de fazer a instalação? (y/n)"
read resp1
if [ $resp1 = "y" ]
then 
        echo "Legal! Você aceitou instalar a aplicação Smart Analytic Machine!"
        sleep 1
        echo "Antes de instalar irei verificar se você possui os recursos para a nossa aplicação"
        sleep 1
        echo "analisando."
        sleep 1
        echo "analisando.."
        sleep 1
        echo "analisando..."
        sleep 1

        echo "Verificando Docker..."
        sleep 1
        docker --version
        if [ $? -eq 0 ]
        then 
                echo "Legal!"
                echo "Docker já está instalado!"
                sleep 1
        else 
                echo "Docker não instalado!"
                echo "Instalando o Docker..."
                sudo apt install docker.io -y
        fi

        echo "Verificando JAVA..."
        sleep 1
        java --version
        if [ $? -eq 0 ]
        then
                echo "Legal!"
                echo "JAVA já esta instalado!"
                sleep 1
        else 
                echo "JAVA não instalado!"
                echo "Instalando o JAVA..."
                sudo apt install openjdk-17-jdk openjdk-17-jre -y
        fi
        
        echo "Agora irei instalar a aplicação..."
        sleep 1
        git clone https://github.com/Grupo1-2ADSB/aplicacao-java/
        sleep 1
        echo "Instalação feita com sucesso!"
        echo "Abrindo a aplicação..."
        sleep 2
        cd aplicacao-java/cli/target
        sudo chmod 777  smart-analytic-machine-1.0-SNAPSHOT-jar-with-dependencies.jar
        java -jar smart-analytic-machine-1.0-SNAPSHOT-jar-with-dependencies.jar
        echo "Sucesso!"
else 
        echo "Você NÃO ACEITOU a instalação :("
        sleep 1
        echo "Caso mude de idéia ou erro de digitação, execute novamento o script e siga os passos ;)"
        sleep 1
        echo "Caso tenha alguma dúvida, entre em contato conosco!"
        sleep 1
        echo "smartanalyticmachine.azurewebsites.net"
        echo "Até! ;D"
fi


