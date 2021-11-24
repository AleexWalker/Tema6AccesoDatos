package exercicis



import com.db4o.Db4o
import java.awt.EventQueue
import java.awt.GridLayout
import java.awt.FlowLayout
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.BoxLayout
import javax.swing.JComboBox
import javax.swing.JButton
import javax.swing.JTextArea
import javax.swing.JLabel
import javax.swing.JTextField
import javax.swing.JTable
import javax.swing.JScrollPane
import javax.swing.table.DefaultTableModel
import com.db4o.Db4oEmbedded
import com.db4o.EmbeddedObjectContainer
import kotlin.system.exitProcess

class FinestraAvancat : JFrame() {
    var llista = arrayListOf<Ruta>()
    var numActual = 0

    // Declaració de la Base de Dades amb lateinit, per a inicialitzar després
    var baseDatos : EmbeddedObjectContainer


    var actualitzant = false
    var modificacio = ""

    val qNom = JTextField(15)
    val qDesn = JTextField(5)
    val qDesnAcum = JTextField(5)
    val qDistancia = JTextField(5)
    val punts = JTable(1, 3)
    val primer = JButton(" << ")
    val anterior = JButton(" < ")
    val seguent = JButton(" > ")
    val ultim = JButton(" >> ")
    val tancar = JButton("Tancar")

    val editar = JButton("Editar")
    val eliminar = JButton("Eliminar")
    val nova = JButton("Nova Ruta")

    val acceptar = JButton("Acceptar")
    val cancelar = JButton("Cancel·lar")
    val mesP = JButton("+p")
    val menysP = JButton("-p")

    init {
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setTitle("JDBC: Visualitzar Rutes Complet")
        setLayout(GridLayout(0, 1))

        // Inicialització de la BD, amb opcions de modificar i esborrar en cascada
        val configuracion = Db4oEmbedded.newConfiguration()
        configuracion.common().objectClass("exercicis.Ruta").cascadeOnDelete(true)
        configuracion.common().objectClass("exercicis.Ruta").cascadeOnUpdate(true)

        baseDatos = Db4oEmbedded.openFile("Rutes.db4o")

        val p_prin = JPanel()
        p_prin.setLayout(BoxLayout(p_prin, BoxLayout.Y_AXIS))
        val panell1 = JPanel(GridLayout(0, 2))
        panell1.add(JLabel("Ruta:"))
        qNom.setEditable(false)
        panell1.add(qNom)
        panell1.add(JLabel("Desnivell:"))
        qDesn.setEditable(false)
        panell1.add(qDesn)
        panell1.add(JLabel("Desnivell acumulat:"))
        qDesnAcum.setEditable(false)
        panell1.add(qDesnAcum)
        panell1.add(JLabel("Distància:"))
        qDistancia.setEditable(false)
        panell1.add(qDistancia)
        panell1.add(JLabel("Punts:"))

        val panell2 = JPanel(GridLayout(0, 1))
        punts.setEnabled(false)
        val scroll = JScrollPane(punts)
        panell2.add(scroll, null)

        val panell5 = JPanel(FlowLayout())
        panell5.add(primer)
        panell5.add(anterior)
        panell5.add(seguent)
        panell5.add(ultim)
        panell5.add(editar)
        panell5.add(eliminar)
        panell5.add(nova)

        acceptar.setVisible(false)
        panell5.add(acceptar)
        cancelar.setVisible(false)
        panell5.add(cancelar)
        mesP.setVisible(false)
        panell5.add(mesP)
        menysP.setVisible(false)
        panell5.add(menysP)

        val panell6 = JPanel(FlowLayout())
        panell6.add(tancar)

        add(p_prin)
        p_prin.add(panell1)
        p_prin.add(panell2)
        p_prin.add(panell5)
        p_prin.add(panell6)
        ActivarAltres(true)
        pack()
        ActivarAltres(false)

        primer.addActionListener {
            // instruccions per a situar-se en la primera ruta, i visualitzar-la
            numActual = 0
            VisRuta()
        }
        anterior.addActionListener {
            // instruccions per a situar-se en la ruta anterior, i visualitzar-la
            numActual--
            VisRuta()
        }
        seguent.addActionListener {
            // instruccions per a situar-se en la ruta següent, i visualitzar-la
            numActual++
            VisRuta()
        }
        ultim.addActionListener {
            // instruccions per a situar-se en l'últim ruta, i visualitzar-la
            numActual = llista.size-1
            VisRuta()
        }
        tancar.addActionListener {
            // instruccions per tancar la BD i eixir del programa
            baseDatos.close()
            exitProcess(0)
        }

        editar.addActionListener {
            // instruccions per a editar la ruta que s'està veient en aquest moment
            // s'han d'activar els quadres de text, i el JTable


            val rutaActual = llista[numActual]
            qNom.isEditable = true
            qDesn.isEditable = true
            qDesnAcum.isEditable = true
            punts.isEnabled = true

            rutaActual.nom = qNom.text.toString()
            rutaActual.desnivell = qDesn.text.toString().toInt()
            rutaActual.desnivellAcumulat = qDesnAcum.toString().toInt()


        }

        eliminar.addActionListener {
            // instruccions per a eliminar la ruta que s'està veient en aquest moment

        }

        nova.addActionListener {
            // instruccions per a posar en blanc els quadres de texti el JTable, per a inserir una nova ruta
            // s'han d'activar els quadres de text, i el JTable

        }

        acceptar.addActionListener {
            // instruccions per a acceptar l'acció que s'està fent (nova ruta, edició o eliminació)

        }

        cancelar.addActionListener {
            // instruccions per a cancel·lar l'acció que s'estava fent

        }

        mesP.addActionListener {
            // instruccions per a afegir una línia en el JTable

        }

        menysP.addActionListener {
            // instruccions per a llevar una línia del JTable

        }

        inicialitzar()
        VisRuta()
    }

    fun plenarTaula(ll_punts: MutableList<PuntGeo>) {
        var ll = Array(ll_punts.size) { arrayOfNulls<String>(3) }
        for (i in 0 until ll_punts.size) {
            ll[i][0] = ll_punts.get(i).nom
            ll[i][1] = ll_punts.get(i).coord.latitud.toString()
            ll[i][2] = ll_punts.get(i).coord.longitud.toString()
        }
        val caps = arrayOf("Nom punt", "Latitud", "Longitud")
        punts.setModel(javax.swing.table.DefaultTableModel(ll, caps))
    }

    fun inicialitzar() {
        // instruccions per a inicialitzar llistat i numActual
        val ruta = Ruta()
        val listaRutas = baseDatos.queryByExample<Ruta>(ruta)

        listaRutas.forEach() {
            llista.add(it)
        }
    }

    fun VisRuta() {
        // instruccions per a visualitzar la ruta actual (l'índex el tenim en numActual)
        qNom.text = llista[numActual].nom
        qDesn.text = llista[numActual].desnivell.toString()
        qDesnAcum.text = llista[numActual].desnivellAcumulat.toString()

        plenarTaula(llista[numActual].llistaDePunts)

        var sumaDistancia : Double = 0.0
        for (i in 0..llista[numActual].llistaDePunts.size-2) {

            sumaDistancia += Dist(llista[numActual].getPuntLatitud(i) , llista[numActual].getPuntLongitud(i), llista[numActual].getPuntLatitud(i + 1), llista[numActual].getPuntLongitud(i + 1))
            qDistancia.text = "$sumaDistancia Km"
        }

        ActivarBotons()
    }

    fun ActivarBotons() {
        // instruccions per a activar o desactivar els botons de moviment ( setEnabled(Boolean) )

        if (numActual == 0)
            anterior.isEnabled = false
        else
            anterior.isEnabled = true
        if (numActual == llista.size-1)
            seguent.isEnabled = false
        else
            seguent.isEnabled = true

    }

    fun ActivarAltres(b: Boolean) {
        // instruccions per a mostrar els botons acceptar, cancelar, mesP, menysP,
        // ocultar editar, eliminar, nova. O al revés
        // i descativar els de moviment
        editar.isVisible = b
        eliminar.isVisible = b
        nova.isVisible = b

        acceptar.isVisible = !b
        cancelar.isVisible = !b
        mesP.isVisible = !b
        menysP.isVisible = !b


    }

    fun ActivarQuadres(b: Boolean) {
        // instruccions per activar (o desactivar) els quadres de text i el JTable

    }

    fun PosarQuadresBlanc() {
        //instruccions per a posar en blanc els quadres de text i el JTable quan anem a una nova ruta

    }

    fun AssignaRuta(r: Ruta) {
        // instruccions per a guardar en el paràmetre r el valor dels quadres de text i JTable

    }

    fun agafarRutes(): ArrayList<Ruta>? {
        // instruccions per a tornar totes les rutes de la Base de Dades
        val ruta = Ruta()
        val listaAuxiliar = baseDatos.queryByExample<Ruta>(ruta)
        val listaRutas : ArrayList<Ruta>? = null

        listaAuxiliar.forEach() {
            if (listaRutas != null) {
                listaRutas.add(it)
            }
        }

        return listaRutas
    }

    fun Dist(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val R = 6378.137 // Radi de la Tierra en km
        val dLat = rad(lat2 - lat1)
        val dLong = rad(lon2 - lon1)

        val a =
            Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(rad(lat1)) * Math.cos(rad(lat2)) * Math.sin(dLong / 2) * Math.sin(
                dLong / 2
            )
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        val d = R * c
        return Math.round(d * 100.0) / 100.0
    }

    fun rad(x: Double): Double {
        return x * Math.PI / 180
    }
}

fun main(args: Array<String>) {
    EventQueue.invokeLater {
        FinestraAvancat().isVisible = true
    }
}

