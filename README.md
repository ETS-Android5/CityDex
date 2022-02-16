# CityDex

CityDex a été réalisé lors d'un projet universitaire.
C'est une application mobile ludique de collection de ville à partir des panneaux d'entrée d'agglomération de France.
Pour collectionner des villes, l'utilisateur doit prendre en photo des panneaux d'entrée d'agglomération Français.

## Sommaire

### [Technologies](https://github.com/TLBail/CityDex/edit/main/README.md#technologies)
 - [Java et Android SDK](https://github.com/TLBail/CityDex/edit/main/README.md#java-et-android-sdk)
 - [Détection d'objet](https://github.com/TLBail/CityDex/edit/main/README.md#java-et-android-sdk)
 - [OCR](https://github.com/TLBail/CityDex/edit/main/README.md#ocr-reconnaissance-optique-de-caractère)
 - [OpenCV](https://github.com/TLBail/CityDex/edit/main/README.md#opencv)
 - [API Découpage administratif](https://github.com/TLBail/CityDex/edit/main/README.md#api-découpage-administratif-api-du-gouvernement-français)
 - [Localisation](https://github.com/TLBail/CityDex/edit/main/README.md#localisation)

### [Contributeurs](https://github.com/TLBail/CityDex/edit/main/README.md#contributeurs)

## Technologies

### Java et Android SDK

L'application est entièrement écrite en Java en utilisant le SDK Android.
Lien du SDK : https://developer.android.com

### Detection d'objet

L'application utilise la détection d'objet pour reconnaitre un panneau dans une image.
Nous utilisons TensorFlowLite ????

### OCR (Reconnaissance optique de caractère)

L'application utilise l'OCR pour lire le nom de ville dans le panneau.
Nous avions d'abord utilisé tess-two, qui permet d'utiliser Tesseract dans Android.
Cependant les résultats étant très moyen et la bibliothèque n'étant plus maintenu, nous avons décidé de changer de bibliothèque.
Nous utilisons maintenant le ML Kit Text Recognition (https://developers.google.com/ml-kit/vision/text-recognition) de Google.

### OpenCV

Nous avons utilisé OpenCV pour préparer l'image pris par un utilisateur pour la donner à l'OCR pour avoir de meilleur résultat.
Nous l'avons par exemple utilisé pour mettre l'image en nuances de gris, augmenter le contraste, 

### API Découpage administratif (API du gouvernement français)

Cette API nous permet de récupérer des informations sommaires à propos de la ville.
Elle nous permet aussi de savoir si la ville existe en France.

### Localisation

L'application utilise la localisation comme mesure d'anti-triche.
Nous utilisions la bibliothèque fourni dans le SDK Android.
Cependant la localisation mettait beaucoup de temps à venir et le code devenait compliqué à comprendre et à maintenir.
Nous avons changé de bibliothèque pour utiliser la localisation du GooglePlayService (FusedLocationProviderClient : https://developers.google.com/android/reference/com/google/android/gms/location/FusedLocationProviderClient).

## Contributeurs

Théo LE BAIL

Bastien TAROT

Nathan POLLART

Axel GUÉRANGER

Charles DECELLIÈRES (professeur tuteur)

<!--

Rajouter lien playstore
Rajouter le fait d'inciter à creer une issue s'il y a un probleme
Ajouter un sommaire ?
Ajouter une partie fonctionnement ?

-->
