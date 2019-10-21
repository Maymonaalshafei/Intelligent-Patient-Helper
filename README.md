# Intelligent-Patient-Helper
Intelligent-Patient-Helper



#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>
#ifndef STASSID
#define STASSID "Maymona’s iPhone"
#define STAPSK  "jojokimmimi"
#define FIREBASE_HOST "iphfirebaseproject-6d036.firebaseio.com"        // the project name address from firebase id
#define FIREBASE_AUTH "qX3DdHqCLWeEvIDUoTAvlcpRNCl82nsuJOOwIqoL"       // the secret key generated from firebase
#endif

int outputpin= A0;

const char* ssid     = STASSID;
const char* password = STAPSK;

float celsius ;

const char* host = "djxmmx.net";
const uint16_t port = 17;

void setup() {
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
 

  while (WiFi.status() != WL_CONNECTED) {
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
