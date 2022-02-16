# CityDex

CityDex est une application Android qui a été réalisée lors d'un projet universitaire. Il s'agit d'une application mobile ludique de collection de villes à partir des panneaux d'entrée d'agglomération de France. Autrement dit, pour collectionner des villes, l'utilisateur doit prendre en photo des panneaux d'entrée d'agglomération françaises.

<img src="https://user-images.githubusercontent.com/74766923/154358973-19783a27-341d-4fc0-ad1f-9ca93bc5e070.png" width="150"/>

## Sommaire

[Technologies](https://github.com/TLBail/CityDex/blob/main/README.md#technologies)
 - [Java et Android SDK](https://github.com/TLBail/CityDex/blob/main/README.md#java-et-android-sdk)
 - [Détection d'objet](https://github.com/TLBail/CityDex/blob/main/README.md#java-et-android-sdk)
 - [OCR](https://github.com/TLBail/CityDex/blob/main/README.md#ocr-reconnaissance-optique-de-caractère)
 - [OpenCV](https://github.com/TLBail/CityDex/blob/main/README.md#opencv)
 - [API Découpage administratif](https://github.com/TLBail/CityDex/blob/main/README.md#api-découpage-administratif-api-du-gouvernement-français)
 - [Localisation](https://github.com/TLBail/CityDex/blob/main/README.md#localisation)
 - [Notifications](https://github.com/TLBail/CityDex/blob/main/README.md#notifications)
 
[Problèmes](https://github.com/TLBail/CityDex/blob/main/README.md#probl%C3%A8mes)<br/>
[Contributeurs](https://github.com/TLBail/CityDex/blob/main/README.md#contributeurs)

## Technologies

### Java et Android SDK

L'application est entièrement écrite et développée en Java en utilisant le SDK Android ainsi que l'éditeur Android Studio.<br/>
Lien du SDK : https://developer.android.com

### Détection d'objet

L'application utilise la détection d'objet pour reconnaitre un panneau dans une image. Nous utilisons TensorFlowLite avec un modèle entrainé sur plus de 250 images de panneaux pour identifier la postition d'un panneau et sa position dans une image. Nous avons labellisé ces images afin de générer des fichiers .xml nécessaires à l'entraînement du modèle. La position retournée est un rectangle ce qui nous permet de rogner l'image pour faciliter l'OCR.

### OCR (Reconnaissance optique de caractère)

L'application utilise l'OCR pour lire le nom de ville dans le panneau. Nous avions d'abord utilisé tess-two, qui permet d'utiliser Tesseract dans Android. Cependant les résultats étant très moyens et la bibliothèque n'étant plus maintenue, nous avons décidé de changer de bibliothèque. Nous utilisons maintenant le ML Kit Text Recognition (https://developers.google.com/ml-kit/vision/text-recognition) de Google. 

### OpenCV

Nous avons utilisé OpenCV pour préparer la photo prise par un utilisateur pour la donner à l'OCR pour avoir de meilleurs résultats. Nous l'avons par exemple utilisé pour mettre l'image en nuances de gris, augmenter le contraste ou éliminer le bruit. 

### API Découpage administratif (API du gouvernement français)

Cette API nommée API Géo nous permet de récupérer des informations sommaires à propos des villes de France. Elle nous permet aussi de savoir si la ville existe en France et de récupérer toutes les informations qui nous sont nécessaires pour afficher une ville à l'utilisateur.

### Localisation

L'application utilise la localisation comme mesure d'anti-triche. Nous utilisions pour cela la bibliothèque fourni dans le SDK Android. Cependant la détection de la localisation durait beaucoup de temps et le code devenait compliqué à comprendre et à maintenir. Nous avons changé de bibliothèque pour utiliser la localisation du GooglePlayService<br/> (FusedLocationProviderClient : https://developers.google.com/android/reference/com/google/android/gms/location/FusedLocationProviderClient).

### Notifications

Pendant une maintenance de notre application, nous avons également implémenter un système de notifications sur notre application. Nous avons pour cela utilisé différentes classes directement implémetées dans le SDK Android. A chaque lancement, et si le joueur se trouve dans une ville dont il n'a pas le panneau dans sa collection, une notification est envoyée afin d'informer le joueur qu'il ne possède pas cette ville. Tout le système de notification utilise la localisation décrite ci-dessus.

## Problèmes

Si vous rencontrez un bug, un problème ou même si vous avez une quelconque question sur notre application, n'hésitez pas à ouvrir une issue directement via la catégorie issues du Github, nous tenterons de vous répondre au plus vite !

## Contributeurs

Charles DECELLIÈRES (professeur tuteur)

Théo LE BAIL<br/>
Bastien TAROT<br/>
Nathan POLLART<br/>
Axel GUÉRANGER<br/>

<!--

Rajouter lien playstore
Ajouter une partie fonctionnement ?

-->
