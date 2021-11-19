package exercicis

import java.io.Serializable

class PuntGeo  (var nom : String,
                var coord : Coordenades ) : Serializable{
    companion object{
        private const val serialVersionUID: Long = 1
    }
}