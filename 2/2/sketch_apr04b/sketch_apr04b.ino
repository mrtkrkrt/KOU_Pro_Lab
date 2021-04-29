#include <stdio.h>
#include <stdlib.h>
#include <SPI.h>
#include <SD.h>

//Read from file
File myFile;
int count = 0;
int paralar[5];
int islemUcret[4];
int islemKalan[4];
char islemler[4][10];
int paraBirimler[5];
int islemSayac[4];
int yuklenenBanknotSayisi[5];
int verilenBanknotSayisi[5];
int toplamBakiye = 0;
int kalanBakiye = 0;
int kullaniciBakiye = 0;
bool islemYuklemeMi = true;

//In Term
int btn1 = 8;
int btn2 = 7;
int btn3 = 6;
int btn4 = 5;
int btn5 = 3;
int btn6 = 2;
int greenLed = 10;
int redLed = 9;

void parseMoney(String line) {
  int j = 0;
  for (int i = 0; i < line.length(); i++) {
    if (i == line.length() - 1) {
      j++;
      paralar[j] = paralar[j] + (line.charAt(i) - '0');
      break;
    }
    if (line.charAt(i) == ',') j++;
    else {
      paralar[j] = paralar[j] * 10;
      if ((line.charAt(i) < '0') || (line.charAt(i) > '9')) {
        paralar[j] = 0;
      }
      else {
        paralar[j] = paralar[j] + (line.charAt(i) - '0');
      }
    }
  }
}

void parseProcess(String line, int sira) {
  int j = 0, k = 0, ucret = 0, kalan = 0, cikarma = 1;
  char islem[15];

  if (sira == 3) cikarma = 0;

  for (int i = 0; i < line.length() - cikarma; i++) {
    if (line.charAt(i) == ',') {
      j++;
    } else {
      if (j == 1) {
        islem[k] = line.charAt(i);
        k++;
      } else if (j == 2) {
        kalan = kalan * 10;
        kalan = kalan + (line.charAt(i) - '0');
      } else if (j == 3) {
        ucret = ucret * 10;
        ucret = ucret + (line.charAt(i) - '0');
      }
    }
  }
  islemUcret[sira] = ucret;
  islemKalan[sira] = kalan;
  strcpy(islemler[sira], islem);
}

void readAndParse() {
  int sira = 0;
  Serial.begin(9600);
  while (!Serial) {
    ;
  }

  Serial.print("Initializing SD card...");

  if (!SD.begin(4)) {
    Serial.println("initialization failed!");
    while (1);
  }
  Serial.println("initialization done.");

  myFile = SD.open("link.txt");

  if (myFile) {
    while (myFile.available()) {
      String line = myFile.readStringUntil('\n');
      if (count == 0) {
        parseMoney(line);
      } else {
        parseProcess(line, sira);
        sira++;
      }
      count++;
    }
  } else {
    Serial.println("error opening test.txt");
  }
  myFile.close();
}

void islemlerGuncelle() {
  strcpy(islemler[0], "Kopukleme");
  strcpy(islemler[1], "Yikama");
  strcpy(islemler[2], "Kurulama");
  strcpy(islemler[3], "Cilalama");
}

void birimleriAktar() {
  paraBirimler[0] = 5;
  paraBirimler[1] = 10;
  paraBirimler[2] = 20;
  paraBirimler[3] = 50;
  paraBirimler[4] = 100;
}

void bakiyeGuncelle() {
  toplamBakiye = 0;
  for (int i = 0; i < 5; i++) {
    toplamBakiye += paralar[i] * paraBirimler[i];
  }
}

void setup() {
  readAndParse();
  elemanlariBagla();
  birimleriAktar();
  bakiyeGuncelle();
  islemlerGuncelle();
  printInfos();
}

void elemanlariBagla() {
  pinMode(btn1, INPUT);
  pinMode(btn2, INPUT);
  pinMode(btn3, INPUT);
  pinMode(btn4, INPUT);
  pinMode(btn5, INPUT);
  pinMode(btn6, INPUT);
  pinMode(redLed, OUTPUT);
  pinMode(greenLed, OUTPUT);
}

void printInfos() {
  Serial.println("Kasa Bilgileri");
  for (int i = 0; i < 4; i++) {
    Serial.print(islemler[i]);
    Serial.print("    ");
    Serial.print(islemKalan[i]);
    Serial.print("  ");
    Serial.print(islemUcret[i]);
    Serial.println();
  }
  for (int i = 0; i < 5; i++) {
    Serial.print(paralar[i]);
    Serial.print(" tane ");
    Serial.println(paraBirimler[i]);
  }

  Serial.print("Toplam Bakiye = ");
  Serial.println(toplamBakiye);
  Serial.println();
  Serial.println();
  Serial.println();
}

void loop() {

  int randomNumber = random(1, 5);

  bakiyeGuncelle();
  int b1 = digitalRead(btn1);
  int b2 = digitalRead(btn2);
  int b3 = digitalRead(btn3);
  int b4 = digitalRead(btn4);
  int b5 = digitalRead(btn5);
  int b6 = digitalRead(btn6);

  //Para Yükleme Durumu

  if (islemYuklemeMi) {
    if (b1 == HIGH) {
      Serial.println("5 TL");
      paralar[0]++;
      yuklenenBanknotSayisi[0]++;
      kullaniciBakiye += 5;
      delay(500);
    }
    if (b2 == HIGH) {
      Serial.println("10 TL");
      paralar[1]++;
      yuklenenBanknotSayisi[1]++;
      kullaniciBakiye += 10;
      delay(500);
    }
    if (b3 == HIGH) {
      Serial.println("20 TL");
      paralar[2]++;
      yuklenenBanknotSayisi[2]++;
      kullaniciBakiye += 20;
      delay(500);
    }
    if (b4 == HIGH) {
      Serial.println("50 TL");
      paralar[3]++;
      yuklenenBanknotSayisi[3]++;
      kullaniciBakiye += 50;
      delay(500);
    }
    if (b5 == HIGH) {
      Serial.println("100 TL");
      paralar[4]++;
      yuklenenBanknotSayisi[4]++;
      kullaniciBakiye += 100;
      delay(500);
    }
    if (b6 == HIGH) {
      islemYuklemeMi = false;
      Serial.print(kullaniciBakiye);
      Serial.println(" TL Yuklediniz.");
      Serial.println();
      Serial.println();
      delay(500);
    }
  }

  //İşlem Seçme Durumu
  //para sıkışma

  else {
    if (b1 == HIGH) {
      if (islemKalan[0] >= 1) {
        islemKalan[0]--;
        islemSayac[0]++;
        Serial.println("Kopukleme");
      } else {
        Serial.print(islemler[0]);
        Serial.println("  kalmadı..");
      }
      delay(500);
    }
    if (b2 == HIGH) {
      if (islemKalan[1] >= 1) {
        islemKalan[1]--;
        islemSayac[1]++;
        Serial.println("Yikama");
      } else {
        Serial.print(islemler[1]);
        Serial.println("  kalmadı..");
      }
      delay(500);
    }
    if (b3 == HIGH) {
      if (islemKalan[2] >= 1) {
        islemKalan[2]--;
        islemSayac[2]++;
        Serial.println("Kurulama");
      } else {
        Serial.print(islemler[2]);
        Serial.println("  kalmadı..");
      }
      delay(500);
    }
    if (b4 == HIGH) {
      if (islemKalan[3] >= 1) {
        islemKalan[3]--;
        islemSayac[3]++;
        Serial.println("Cilalama");
      } else {
        Serial.print(islemler[3]);
        Serial.println("  kalmadı..");
      }
      delay(500);
    }
    if (b5 == HIGH) {

      if (randomNumber == 2) {
        Serial.println();
        Serial.println("Para Sikisti...");
        Serial.println("Para iade edildi");
        Serial.println("Basa Donuldu");
        Serial.println();
        Serial.println();
        Serial.println();
        kirmiziYak();
        basaDon();
      }
      else {
        int secimBakiye = 0;

        for (int i = 0; i < 4; i++) {
          secimBakiye += islemUcret[i] * islemSayac[i];
        }

        Serial.print("Toplam Tutar = ");
        Serial.println(secimBakiye);

        if (secimBakiye <= kullaniciBakiye) {
          Serial.println("islem basariyla gerceklesti.");
          kalanBakiye = kullaniciBakiye - secimBakiye;
          kullaniciBakiye = kalanBakiye;
          Serial.print("Kalan Bakiye = ");
          Serial.println(kalanBakiye);
          paraUstuVer(kalanBakiye);
          bakiyeGuncelle();
          printInfos();
        } else {
          Serial.println("Bakiye Yetersiz...");
          Serial.println("Basa Donuluyor");
        }
        delay(500);
        islemYuklemeMi = true;
        basaDon();
         Serial.println();
    Serial.println();
      }

      
    }
   if (b6 == HIGH) {
        degerSifirla();
        Serial.println();
        Serial.println("Resetlendi");
        Serial.println();
        delay(500);
      }
  }
  bakiyeGuncelle();
  dosyaYaz();
}

void degerSifirla() {
  for (int i = 0; i < 4; i++) {
    islemKalan[i] += islemSayac[i];
    islemSayac[i] = 0;
  }
}

void paraUstuVer(int paraUstu) {
  int verilen = 0, temp = paraUstu;

  if (paraUstu > toplamBakiye) {
    Serial.println("Kasadaki Bakiye Yetersiz...");
    return;
  }
  Serial.println();
  Serial.println("Para Ustu");
  for (int i = 4; i >= 0; i--) {
    int banknotSayisi = paraUstu / paraBirimler[i];
    if (paralar[i] <= banknotSayisi) {
      verilenBanknotSayisi[i] = paralar[i];
      paraUstu = paraUstu - (paralar[i] * paraBirimler[i]);
      paralar[i] = 0;
      verilen += paralar[i] * paraBirimler[i];
    } else if (paralar[i] >= banknotSayisi) {
      paralar[i] -= banknotSayisi;
      paraUstu = paraUstu - (banknotSayisi * paraBirimler[i]);
      verilen += banknotSayisi * paraBirimler[i];
      verilenBanknotSayisi[i] = banknotSayisi;
    }
  }

  if (verilen != temp) {
    Serial.println("Kasadaki Bakiye Yetersiz");
  } else {
    for (int i = 0; i < 5; i++) {
      Serial.print(verilenBanknotSayisi[i]);
      Serial.print(" tane ");
      Serial.println(paraBirimler[i]);
    }
  }
  Serial.println();
  Serial.println();
  Serial.println();
}

void kirmiziYak(){
  digitalWrite(redLed,HIGH);
  digitalWrite(greenLed,LOW);
  delay(1000);
  digitalWrite(redLed,LOW);
  digitalWrite(greenLed,HIGH);
}

void basaDon() {
  for (int i = 0; i < 5; i++) {
    paralar[i] -= yuklenenBanknotSayisi[i];
    yuklenenBanknotSayisi[i] = 0;
    verilenBanknotSayisi[i] = 0;
  }
  for (int i = 0; i < 4; i++) {
    islemKalan[i] += islemSayac[i];
    islemSayac[i] = 0;
  }
  kullaniciBakiye = 0;
  kalanBakiye = 0;
  islemYuklemeMi = true;
}

void dosyaYaz(){
  myFile = SD.open("link.txt",FILE_WRITE);

  if(myFile){
    for(int i=0;i<5;i++){
      myFile.print(paralar[i]);
      if(i!=4){
        myFile.print(",");
      }
    }
    myFile.println();
    for(int i=0;i<4;i++){
      myFile.print(i+1);
      myFile.print(",");
      myFile.print(islemler[i]);
      myFile.print(",");
      myFile.print(islemKalan[i]);
      myFile.print(",");
      myFile.println(islemUcret[i]);
    }
  }
}
