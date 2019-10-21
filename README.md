# Intelligent-Patient-Helper

A health care system to provide the ability to send an alert from a patient who has a sudden situation to his/her guardian's Smartphone.
Implemented using a temperature sensor, heart rate, and a sender/receiver unit, 
the sender/receiver unit can be connected to the Smartphone using WIFI technology, 
and when the patient temperature change, the sensor sends the value to the sender/receiver unit then to the Smartphone.

temerature sensor :LM35.
sender/receiver unit: Arduino nodeMCU.
Smart phones with the app that provided in this project.

////////////////////////////////////////////////ARDUINO CODE //////////////////////////////////////////////////
/

#include <ESP8266WiFi.h>

#include <FirebaseArduino.h>

#ifndef STASSID

#define STASSID "Maymona’s iPhone"

#define STAPSK  "jojokimmimi"

#define FIREBASE_HOST "iphfirebaseproject-6d036****************"    // the project name address from firebase id

#define FIREBASE_AUTH "qX3DdHqCLWeEvIDUoTA********88*8888888"       // the secret key generated from firebase

#endif

int outputpin= A0;

const char* ssid     = STASSID;
const char* password = STAPSK;

float celsius ;



void setup()
{
  Serial.begin(115200);

  // We start by connecting to a WiFi network

  Serial.println();
  
  Serial.println();
  
  Serial.print("Connecting to ");
  
  Serial.println(ssid);

  /* Explicitly set the ESP8266 to be a WiFi-client, otherwise, it by default,
     would try to act as both a client and an access-point and could cause
     network-issues with your other WiFi-devices on your WiFi-network. */
     
  WiFi.mode(WIFI_STA);
  
  WiFi.begin(ssid, password);
 

  while (WiFi.status() != WL_CONNECTED)
  {
    delay(500);
    
    Serial.print(".");
  }

Serial.println('\n');

  Serial.println("Connection established!"); 
  
  Serial.print("IP address:\t");
  
  Serial.println(WiFi.localIP());
  
   Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH); 
}

void loop()       //main loop

{


int analogValue = analogRead(outputpin);

float millivolts = (analogValue/1024.0)*3300; //3300 is the voltage provided by NodeMCU

float celsius = millivolts/10;

Serial.print("in DegreeC=   ");

Serial.print(celsius);

Serial.println("  celsius");


    

delay(5000);

String c = String(celsius) ;      


Firebase.setString("inDegreeC", c);  

}
/////////////////////////////////////////////////END OF CODE/////////////////////////////////////////////////////
