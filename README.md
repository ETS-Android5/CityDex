# CityDex

CityDex a été réalisé lors d'un projet universitaire.
CityDex est une application mobile ludique de collection de panneau d'entrée d'agglomération de France.
Pour collectionner des villes, l'utilisateur prend en photo des panneaux d'entrée d'agglomération.
Puis la ville est ajoutée à sa collection.

# Technologies

Java et Android SDK

L'application est entièrement écrite en Java en utilisant le SDK Android.

Detection d'objet

L'application utilise la détection d'objet pour reconnaitre un panneau dans une image.
Nous utilisons TensorFlowLite ????

OCR (Reconnaissance optique de caractère)

L'application utilise l'OCR pour lire le nom de ville dans le panneau.
Nous avions d'abord utilisé tess-two, qui permet d'utiliser Tesseract dans Android.
Cependant les résultats étant très moyen et la bibliothèque n'étant plus maintenu, nous avons décidé de changer de bibliothèque.
Nous utilisons maintenant le ML Kit de Google.

Localisation

L'application utilise la localisation comme mesure d'anti-triche.
Nous utilisions la bibliothèque fourni dans le SDK Android.
Cependant la localisation mettait beaucoup de temps à venir et le code devenait compliqué à maintenir.
Nous avons changé de bibliothèque pour utiliser la localisation du GooglePlayService (FusedLocationProviderClient)


# Contributeurs

Théo LE BAIL

Bastien TAROT

Nathan POLLART

Axel GUÉRANGER

Charles DECELLIÈRES (professeur tuteur)

<!--

Rajouter lien playstore
Rajouter le fait de creer une issue s'il y a un probleme



Création d’une application ludique type « Pokédex » ayant pour but d’intéresser l’utilisateur à une thématique.
Cette application aura pour enjeu de répertorier tous les panneaux d’agglomération dans une interface simple d’utilisation. L’application sera disponible sur mobile et pourra implémenter d’éventuelles mises à jour en fonction des demandes du client.
Pour ce faire l’utilisateur disposera d’informations sur la thématique choisie, ici des panneaux de ville et devra réaliser des clichés pour compléter sa bibliothèque personnelle. L’application devra permettre :
    • D’afficher des informations générales sur les différentes villes obtenu par l'utilisateur
    • De réaliser et stocker des photos des villes obtenu par l'utilisateur
    • De déterminer automatiquement à quel élément correspond le cliché pris (les exemples de classification d’image de Tensorflow Lite pour Android peuvent être une bonne piste-->
