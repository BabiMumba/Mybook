package com.babimumba.schoolapp.consol

import android.widget.Toast


fun main() {

    //print difference
    val sha1_one = "20:98:34:10:8B:83:DB:17:F2:70:D0:6A:AD:C0:FF:A5:C4:91:63:57"
    val sha1_two = "20:98:34:10:8b:83:db:17:f2:70:d0:6a:ad:c0:ff:a5:c4:91:63:57"

    if (sha1_one == sha1_two) {
        println("les deux sha1 sont identiques")
    }else{
        println("pas indentique")
    }


    /*val nom = mutableListOf<String>()
    nom.add("babi")

    for(i in 1..3){
        println("entrer le nom numero: $i")
        val adnom = readLine().toString()

        for (z in nom){
            if (z == adnom){
                nom.remove(adnom)
                println("c'est nom existe")
            }
        }
        nom.add(adnom)
*/



    }

 /*  for (one in nom){
       println("bonjour le nom: $one")
   }
*/
