# Vonat
Egy vonatokat és hozzájuk tartozó vagonokat nyilvántartó alkalmazást kell készítened. 
A vonatokat és a vagonokat relációs adatbázisban kell tárolni. A tesztek futatásához elöször létre kell
hoznod az Entity-ket és Dto-at **fontos**, hogy a megadott package-ben hozd őket létre!),
majd implementálnod kell a meghatározott API végpontokat.


A feladat megoldását a Clean Code elvek betartásával, rétegekre bontva kell biztosítanod.

A megoldáshoz csatolj konténerizációhoz szükséges fájlokat is (dockerfile, futtatáshoz szükséges parancs)!

## Dto-ok
- package: com.codecool.train.dto

### TrainDto
- String id
- String driver
- String destination
- Boolean isLate

### TrainWagonDto
- String trainId
- String wagonId

### WagonDto
- String id
- Integer weight
- WagonType wagonType

## Entity-k
package: com.codecool.train.entity

### Train
String id
String driver
String destination
Boolean isLate
List<Wagon> wagons

### Wagon
String id
Integer weight
WagonType wagonType (ehhez tartozik egy enum az entity packagbe. lehetséges értékei: CARGO,PASSENGER)
Train train

## Endpointok
- Post `/train` TrainDto json-t vár a body-ban. lementi az adott vonatot. 
- Post `/train/addWagon` TrainWagonDto json-t vár a body-ban. A dto alapján vagont add a vonathoz.
- Get  `train/{id}` megkeresi az adott id-jű vonatot és Train jsonként adja vissza. pl:
`
{
"id": "ABCD",
"driver": "Bud Spencer",
"destination": "Budapest",
"isLate": true,
"wagons": []
}
`
- Put `/train/{id}` TrainDto json-t vár a body-ban. updateli az adott vonatot.
- Delete `/train/{id}` törli az adott vonatot. fontos hogy a vonathoz kapcsolodo vagonok ne törlödjenek csak a vonat.
- Get `/train/heavy` `List<Train>` json-ént listázza azokat a vonatokat amelyek vagonjainak össz tömege meghaladja a 25 egységet. pl:
`
  [
  {
  "id": "AECDBA",
  "weight": 6,
  "wagonType": "CARGO"
  },
  {
  "id": "ARCDBA",
  "weight": 3,
  "wagonType": "CARGO"
  }
  ]
`
- Post `/wagon` WagonDto json-t vár a body-ban. lementi az adott vagont.
- Get `/wagon` vissza adja a vagonokat List<Wagon> json-ként
- Put `/wagon/{id}` WagonDto json-t vár a body-ban. updateli az adott vagont.
- Delete `/wagon/{id}` törli az adott vagont.  

Ahol külön nem írtam mit add vissza ott a kérés nem add vissza semmit.
